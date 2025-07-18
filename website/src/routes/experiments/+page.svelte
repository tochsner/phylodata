<script lang="ts">
	import { enhance } from '$app/forms';
	import { getMainClassifications } from '$lib/classifications';
	import Checkbox from '$lib/components/checkbox.svelte';
	import Header from '$lib/components/header.svelte';
	import Tag from '$lib/components/tag.svelte';
	import { type PaperWithExperiments } from '$lib/types';
	import type { PageProps } from './$types';
	import ComboBox from '$lib/components/comboBox.svelte';
	import { titleCase } from '$lib/utils/titleCase';

	let { data }: PageProps = $props();
	let { papers, possibleSamples, possibleEvolutionaryModels } = data;

	let filteredPapers = $state<Awaited<typeof papers>>([]);

	$effect.pre(() => {
		papers.then((papers) => {
			filteredPapers = papers;
		});
	});
</script>

<Header>
	<h2 class="text-dark flex-1 text-2xl font-bold">Experiments</h2>
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
			async ({ update, result }) => {
				if (result.type === 'success' && result.data) {
					// @ts-ignore
					filteredPapers = result.data;
				}

				return await update({ reset: false });
			}}
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
		<Checkbox name="fileType" value="unknown" submitOnChange>Others</Checkbox>
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

		{#await possibleSamples}
			<div class="h-[38px] w-full animate-pulse rounded-lg bg-white opacity-60"></div>
		{:then possibleSamples}
			<ComboBox
				name="samples"
				possibleValues={possibleSamples.map((sample) => ({
					label: titleCase(sample),
					value: sample
				}))}
				submitOnChange
				placeholder="Search for samples..."
			/>
		{/await}
	</div>
{/snippet}

{#snippet treesFilter()}
	<div class="flex flex-col gap-2">
		<span class="text-base font-semibold">Trees</span>

		<Checkbox name="treesType" value="rooted" submitOnChange>Rooted</Checkbox>
		<Checkbox name="treesType" value="unrooted" submitOnChange>Unrooted</Checkbox>
		<Checkbox name="treesType" value="ultrametric" submitOnChange>Ultrametric</Checkbox>
		<Checkbox name="treesType" value="nonUltrametric" submitOnChange>Not ultrametric</Checkbox>
	</div>
{/snippet}

{#snippet evolutionaryModelFilter()}
	<div class="flex flex-col gap-2">
		<span class="text-base font-semibold">Evolutionary Model</span>

		{#await possibleEvolutionaryModels}
			<div class="h-[38px] w-full animate-pulse rounded-lg bg-white opacity-60"></div>
		{:then possibleEvolutionaryModels}
			<ComboBox
				name="evolutionaryModel"
				possibleValues={possibleEvolutionaryModels.map((sample) => ({
					label: sample,
					value: sample
				}))}
				submitOnChange
				placeholder="Search for models..."
			/>
		{/await}
	</div>
{/snippet}

{#snippet content()}
	<div class="flex flex-1 flex-col gap-4">
		{#await papers}
			<div class="h-[148px] w-full animate-pulse rounded-xl bg-white opacity-60"></div>
			<div class="h-[148px] w-full animate-pulse rounded-xl bg-white opacity-60"></div>
			<div class="h-[148px] w-full animate-pulse rounded-xl bg-white opacity-60"></div>
		{:then}
			{#each filteredPapers as paper (paper.paper.doi)}
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
