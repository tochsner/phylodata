<script lang="ts">
	import { enhance } from '$app/forms';
	import { getMainClassifications } from '$lib/classifications';
	import Checkbox from '$lib/components/checkbox.svelte';
	import Header from '$lib/components/header.svelte';
	import Tag from '$lib/components/tag.svelte';
	import { type PaperWithExperiments } from '$lib/types';
	import type { PageProps } from './$types';
	import ComboBox from '$lib/components/comboBox.svelte';

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

<div class="flex items-stretch gap-12 p-8">
	{@render filters()}
	{@render content()}
</div>

{#snippet filters()}
	<form
		class="flex w-56 flex-col gap-8 text-sm"
		method="POST"
		action="?/filter"
		use:enhance={() =>
			async ({ update }) =>
				await update({ reset: false })}
	>
		{@render filesFilter()}
		{@render samplesFilter()}
		{@render treesFilter()}
		{@render evolutionaryModelFilter()}
	</form>
{/snippet}

{#snippet filesFilter()}
	<div class="flex flex-col gap-2">
		<span class="text-base font-semibold">Files</span>
		<Checkbox name="fileType" value="beast2PosteriorTrees" submitOnChange>
			MCMC posterior trees
		</Checkbox>
		<Checkbox name="fileType" value="summaryTree" submitOnChange>Summary trees</Checkbox>
		<Checkbox name="fileType" value="beast2Configuration" submitOnChange>BEAST2 XMLs</Checkbox>
		<Checkbox name="fileType" value="beast2PosteriorLogs" submitOnChange>BEAST2 log files</Checkbox>
		<Checkbox name="fileType" value="codephyModel" submitOnChange>CodePhy models</Checkbox>
	</div>
{/snippet}

{#snippet samplesFilter()}
	<div class="flex flex-col gap-2">
		<span class="text-base font-semibold">Samples</span>

		<Checkbox name="sampleType" value="species" submitOnChange>Species</Checkbox>
		<Checkbox name="sampleType" value="cells" submitOnChange>Cells</Checkbox>
		<Checkbox name="sampleType" value="language" submitOnChange>Languages</Checkbox>
		<Checkbox name="sampleType" value="unknown" submitOnChange>Others</Checkbox>

		<div></div>

		<ComboBox
			name="samples"
			possibleValues={[
				{ value: 'Penguins', label: 'Penguins' },
				{ value: 'Animals', label: 'Animals' },
				{ value: 'Eucariotes', label: 'Eucariotes' },
				{ value: 'Virus', label: 'Virus' }
			]}
			submitOnChange
			placeholder="Search for samples..."
		/>
	</div>
{/snippet}

{#snippet treesFilter()}
	<div class="flex flex-col gap-2">
		<span class="text-base font-semibold">Trees</span>

		<Checkbox name="treesType" value="ultrametric" submitOnChange>Ultrametric</Checkbox>
	</div>
{/snippet}

{#snippet evolutionaryModelFilter()}
	<div class="flex flex-col gap-2">
		<span class="text-base font-semibold">Evolutionary Model</span>

		<ComboBox
			name="samples"
			possibleValues={[
				{ value: 'Penguins', label: 'Penguins' },
				{ value: 'Animals', label: 'Animals' },
				{ value: 'Eucariotes', label: 'Eucariotes' },
				{ value: 'Virus', label: 'Virus' }
			]}
			placeholder="Search for models..."
			submitOnChange
		/>
	</div>
{/snippet}

{#snippet content()}
	<div class="flex flex-1 flex-col gap-4">
		{#await papers}
			<div class="h-[148px] w-full animate-pulse rounded-xl bg-white opacity-50"></div>
			<div class="h-[148px] w-full animate-pulse rounded-xl bg-white opacity-50"></div>
			<div class="h-[148px] w-full animate-pulse rounded-xl bg-white opacity-50"></div>
		{:then papers}
			{#each papers as paper (paper.paper.doi)}
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
	{@const commonClassifications = getMainClassifications(
		paper.experiments.flatMap((exp) => exp.samples)
	)}

	<a
		class="flex cursor-pointer flex-col rounded-xl bg-white p-3 shadow-lg shadow-gray-400/5 hover:opacity-70"
		href={`/experiments/${encodeURIComponent(paper.paper.doi)}`}
		data-sveltekit-preloadData
	>
		<h3 class="text-accent pb-2 text-xl font-bold">
			{paper.paper.title}
		</h3>

		<p class="text-dark pb-3 text-sm">{paper.paper.authors.join('; ')}</p>

		<div class="flex flex-wrap gap-2">
			<Tag label="Number of Experiments">
				{paper.experiments.length}
			</Tag>

			{#each treeTypes as treeType}
				<Tag label="Sample Type">
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
