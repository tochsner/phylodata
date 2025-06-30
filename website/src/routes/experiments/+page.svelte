<script lang="ts">
	import { enhance } from '$app/forms';
	import { getCommonClassifications } from '$lib/classifications';
	import Checkbox from '$lib/components/checkbox.svelte';
	import Header from '$lib/components/header.svelte';
	import Tag from '$lib/components/tag.svelte';
	import { formatNumber } from '$lib/formatter';
	import { type PaperWithExperiments } from '$lib/types';
	import type { PageProps } from './$types';

	let { data }: PageProps = $props();
	let { papers } = data;
</script>

<Header>
	<div class="flex items-center">
		<h2 class="text-dark flex-1 text-2xl font-bold">Experiments</h2>

		<div class="flex items-center space-x-2">
			<div class="relative">
				<input
					type="text"
					placeholder="Search for experiments..."
					class="focus:ring-accent w-80 rounded-md border border-gray-300 bg-white px-4 py-2 placeholder:italic focus:border-transparent focus:ring-2 focus:outline-none"
				/>
			</div>
			<button
				class="bg-accent cursor-pointer rounded-md p-2 text-white hover:opacity-60"
				onclick={() => {}}
				aria-label="Search"
			>
				<svg class="h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
					<path
						stroke-linecap="round"
						stroke-linejoin="round"
						stroke-width="2"
						d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
					></path>
				</svg>
			</button>
		</div>
	</div>
</Header>

<div class="flex items-stretch gap-8 p-8">
	{@render filters()}

	<div class="bg-accent-light w-[2px] self-stretch"></div>

	{@render content()}
</div>

{#snippet filters()}
	<form
		class="flex flex-col gap-8"
		method="POST"
		action="?/filter"
		use:enhance={() =>
			async ({ update }) =>
				await update({ reset: false })}
	>
		{@render filesFilter()}
		{@render samplesFilter()}
	</form>
{/snippet}

{#snippet filesFilter()}
	<div class="flex flex-col gap-2">
		<span class="font-semibold">Files</span>
		<span class="italic">Experiment has...</span>

		<Checkbox name="MCMC posterior trees" value="MCMC posterior trees" submitOnChange>
			MCMC posterior trees
		</Checkbox>
		<Checkbox name="Summary tree" value="Summary tree" submitOnChange>Summary tree</Checkbox>
		<Checkbox name="BEAST2 configuration files" value="BEAST2 configuration files" submitOnChange>
			BEAST2 configuration files
		</Checkbox>
		<Checkbox name="BEAST2 log files" value="BEAST2 log files" submitOnChange
			>BEAST2 log files</Checkbox
		>
		<Checkbox name="CodePhy model" value="CodePhy model" submitOnChange>CodePhy model</Checkbox>
	</div>
{/snippet}

{#snippet samplesFilter()}
	<div class="flex flex-col gap-2">
		<span class="font-semibold">Samples</span>
		<span class="italic">Samples correspond to...</span>

		<Checkbox name="MCMC posterior trees" value="MCMC posterior trees" submitOnChange>
			Species
		</Checkbox>
		<Checkbox name="Summary tree" value="Summary tree" submitOnChange>Genes</Checkbox>
		<Checkbox name="BEAST2 configuration files" value="BEAST2 configuration files" submitOnChange>
			Cell lineages
		</Checkbox>
		<Checkbox name="BEAST2 log files" value="BEAST2 log files" submitOnChange>Languages</Checkbox>
		<Checkbox name="CodePhy model" value="CodePhy model" submitOnChange>Others</Checkbox>
	</div>
{/snippet}

{#snippet content()}
	<div class="flex flex-1 flex-col gap-4">
		{#await papers}
			<div class="h-[148px] w-full animate-pulse rounded-xl bg-white opacity-50"></div>
			<div class="h-[148px] w-full animate-pulse rounded-xl bg-white opacity-50"></div>
			<div class="h-[148px] w-full animate-pulse rounded-xl bg-white opacity-50"></div>
		{:then papers}
			{#each papers as paper (paper.id)}
				{@render paperOverview(paper)}
			{/each}
		{/await}
	</div>
{/snippet}

{#snippet paperOverview(paper: PaperWithExperiments)}
	{@const treeTypes = [
		...new Set(
			paper.experiments.flatMap((exp) => exp.samples).map((sample) => sample.type.toUpperCase())
		)
	]}
	{@const commonClassifications = getCommonClassifications(
		paper.experiments.flatMap((exp) => exp.samples)
	)}

	<a
		class="flex cursor-pointer flex-col rounded-xl bg-white p-3 shadow-lg shadow-gray-400/5 hover:opacity-70"
		href={`/experiments/${paper.id}`}
		data-sveltekit-preloadData
	>
		<h3 class="text-accent pb-2 text-xl font-bold">
			{paper.title}
		</h3>

		<p class="text-dark pb-3 text-sm">{paper.authors.join('; ')}</p>

		<div class="flex flex-wrap gap-2">
			<Tag label="Number of Experiments">
				{paper.experiments.length}
			</Tag>

			<Tag label="Total Size">
				{formatNumber(
					Math.round(
						paper.experiments.reduce(
							(acc, exp) => acc + exp.files.reduce((acc, file) => acc + file.sizeBytes, 0),
							0
						) / 1024
					)
				)} KB
			</Tag>

			{#each treeTypes as treeType}
				<Tag label="Tree Type">
					{treeType}
				</Tag>
			{/each}

			{#each commonClassifications as classification}
				<Tag label="Contains">
					{classification}
				</Tag>
			{/each}
		</div>
	</a>
{/snippet}
