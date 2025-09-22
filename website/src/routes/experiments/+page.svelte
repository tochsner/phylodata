<script lang="ts">
	import Checkbox from '$lib/components/checkbox.svelte';
	import Header from '$lib/components/header.svelte';
	import Tag from '$lib/components/tag.svelte';
	import { type PaperWithExperiments, type File } from '$lib/types';
	import type { PageProps } from './$types';
	import ComboBox from '$lib/components/comboBox.svelte';
	import { titleCase } from '$lib/utils/titleCase';
	import DownloadInstructions from '$lib/components/downloadInstructions.svelte';
	import NoContent from '$lib/components/noContent.svelte';
	import { goto } from '$app/navigation';
	import { formatFileSize } from '$lib/utils/fileSizeFormatter';

	let { data }: PageProps = $props();
	let {
		papers,
		possibleLanguages,
		possibleSpecies,
		possibleEvolutionaryModels,
		experimentsFilter
	} = $derived(data);

	const updateUrl = async () => {
		const params = new URLSearchParams(window.location.search);
		params.set('filter', JSON.stringify(experimentsFilter));
		goto(`?${params.toString()}`, { replaceState: true });
	};

	const getFileType = (fileType: File['type']) => () =>
		experimentsFilter.filesTypes?.includes(fileType) || false;
	const setFileTypes = (fileType: File['type']) => (value: boolean) => {
		if (value) {
			experimentsFilter.filesTypes = experimentsFilter.filesTypes || [];
			experimentsFilter.filesTypes.push(fileType);
		} else {
			experimentsFilter.filesTypes = experimentsFilter.filesTypes?.filter(
				(type) => type !== fileType
			);
		}
		updateUrl();
	};

	const getEvolutionaryModels = () =>
		(experimentsFilter.evolutionaryModels || []).map((model) => ({
			label: titleCase(model),
			value: model
		}));
	const setEvolutionaryModels = (value: { label: string; value: string }[]) => {
		experimentsFilter.evolutionaryModels = value.map((model) => model.value);
		updateUrl();
	};

	const getLanguages = () =>
		(experimentsFilter.languages || []).map((model) => ({
			label: titleCase(model),
			value: model
		}));
	const setLanguages = (value: { label: string; value: string }[]) => {
		experimentsFilter.languages = value.map((model) => model.value);
		updateUrl();
	};

	const getSpecies = () =>
		(experimentsFilter.species || []).map((model) => ({
			label: titleCase(model),
			value: model
		}));
	const setSpecies = (value: { label: string; value: string }[]) => {
		experimentsFilter.species = value.map((model) => model.value);
		updateUrl();
	};

	let experimentsToDownload: [string, number][] | undefined = $state();
</script>

<svelte:head>
	<title>Experiments – PhyloData</title>
	<meta
		name="description"
		content="Explore a wide range of phylogenetic experiments on PhyloData."
	/>
</svelte:head>

<Header>
	<h2 class="text-dark flex-1 text-2xl font-bold">Experiments</h2>
</Header>

<div class="flex items-stretch gap-8 p-4 md:p-8">
	{@render sidebar()}
	{@render content()}
</div>

{#snippet sidebar()}
	<div class="w-62 hidden flex-col gap-8 text-sm md:flex">
		{@render useExperimentsButton()}
		{@render speciesFilter()}
		{@render languagesFilter()}
		{@render evolutionaryModelFilter()}
		{@render filesFilter()}
		{@render treesFilter()}
	</div>
{/snippet}

{#snippet useExperimentsButton()}
	{#await papers}
		<div class="h-[50px] w-full animate-pulse rounded-xl bg-white opacity-60"></div>
	{:then papers}
		<button
			class="border-accent bg-accent flex cursor-pointer items-center space-x-2 rounded-full border px-4 py-3 font-semibold text-white duration-100 hover:scale-[102%] hover:opacity-80"
			onclick={() => {
				experimentsToDownload = papers
					.flatMap((paper) => paper.experiments)
					.map((exp) => [exp.experiment.humanReadableId, exp.experiment.version]);
			}}
			type="button"
		>
			<svg
				xmlns="http://www.w3.org/2000/svg"
				fill="none"
				viewBox="0 0 24 24"
				stroke-width="1.5"
				stroke="currentColor"
				class="size-6"
			>
				<path
					stroke-linecap="round"
					stroke-linejoin="round"
					d="M7.5 7.5h-.75A2.25 2.25 0 0 0 4.5 9.75v7.5a2.25 2.25 0 0 0 2.25 2.25h7.5a2.25 2.25 0 0 0 2.25-2.25v-7.5a2.25 2.25 0 0 0-2.25-2.25h-.75m-6 3.75 3 3m0 0 3-3m-3 3V1.5m6 9h.75a2.25 2.25 0 0 1 2.25 2.25v7.5a2.25 2.25 0 0 1-2.25 2.25h-7.5a2.25 2.25 0 0 1-2.25-2.25v-.75"
				/>
			</svg>

			<span class="text-base">
				Use {papers.flatMap((paper) => paper.experiments).length} experiments
			</span>
		</button>
	{/await}
{/snippet}

{#snippet filesFilter()}
	<div class="flex flex-col gap-2">
		<span class="text-base font-semibold">Files</span>
		<Checkbox
			onChange={updateUrl}
			bind:checked={getFileType('posteriorTrees'), setFileTypes('posteriorTrees')}
		>
			MCMC posterior trees
		</Checkbox>
		<Checkbox
			onChange={updateUrl}
			bind:checked={getFileType('summaryTree'), setFileTypes('summaryTree')}
		>
			Summary trees
		</Checkbox>
		<Checkbox
			onChange={updateUrl}
			bind:checked={getFileType('beast2Configuration'), setFileTypes('beast2Configuration')}
		>
			BEAST2 XMLs
		</Checkbox>
		<Checkbox
			onChange={updateUrl}
			bind:checked={getFileType('beast2PosteriorLogs'), setFileTypes('beast2PosteriorLogs')}
		>
			BEAST2 log files
		</Checkbox>
		<Checkbox onChange={updateUrl} bind:checked={getFileType('unknown'), setFileTypes('unknown')}>
			Others
		</Checkbox>
	</div>
{/snippet}

{#snippet speciesFilter()}
	<div class="flex flex-col gap-2">
		<span class="text-base font-semibold">Species</span>

		{#await possibleSpecies}
			<div class="h-[36px] w-full animate-pulse rounded-lg bg-white opacity-60"></div>
		{:then possibleSpecies}
			<ComboBox
				name="species"
				possibleValues={possibleSpecies.map((species) => ({
					label: titleCase(species),
					value: species
				}))}
				placeholder="Search for species..."
				bind:selectedValues={getSpecies, setSpecies}
			/>
		{/await}

		<span class="text-sm italic text-gray-900/60">
			Try <span class="font-bold">Birds</span>,
			<span class="font-bold">Fungi</span>, or
			<span class="font-bold">Aptenodytes</span>.
		</span>
	</div>
{/snippet}

{#snippet languagesFilter()}
	<div class="flex flex-col gap-2">
		<span class="text-base font-semibold">Languages</span>

		{#await possibleLanguages}
			<div class="h-[36px] w-full animate-pulse rounded-lg bg-white opacity-60"></div>
		{:then possibleLanguages}
			<ComboBox
				name="languages"
				possibleValues={possibleLanguages.map((language) => ({
					label: titleCase(language),
					value: language
				}))}
				placeholder="Search for languages..."
				bind:selectedValues={getLanguages, setLanguages}
			/>
		{/await}

		<span class="text-sm italic text-gray-900/60">
			Try <span class="font-bold">Turkish</span> or
			<span class="font-bold">North Azerbaijani</span>.
		</span>
	</div>
{/snippet}

{#snippet treesFilter()}
	<div class="flex flex-col gap-2">
		<span class="text-base font-semibold">Trees</span>

		<Checkbox onChange={updateUrl} bind:checked={experimentsFilter.rootedTrees}>Rooted</Checkbox>
		<Checkbox onChange={updateUrl} bind:checked={experimentsFilter.unrootedTrees}>
			Unrooted
		</Checkbox>
		<Checkbox onChange={updateUrl} bind:checked={experimentsFilter.ultrametricTrees}>
			Ultrametric
		</Checkbox>
		<Checkbox onChange={updateUrl} bind:checked={experimentsFilter.nonUltrametricTrees}>
			Not ultrametric
		</Checkbox>
	</div>
{/snippet}

{#snippet evolutionaryModelFilter()}
	<div class="flex flex-col gap-2">
		<span class="text-base font-semibold">Models</span>

		{#await possibleEvolutionaryModels}
			<div class="h-[36px] w-full animate-pulse rounded-lg bg-white opacity-60"></div>
		{:then possibleEvolutionaryModels}
			<ComboBox
				name="evolutionaryModel"
				possibleValues={possibleEvolutionaryModels.map((model) => ({
					label: model,
					value: model
				}))}
				placeholder="Search for models..."
				bind:selectedValues={getEvolutionaryModels, setEvolutionaryModels}
			/>
		{/await}
	</div>
{/snippet}

{#snippet content()}
	<div class="flex flex-1 flex-col gap-5">
		{#await papers}
			<div class="h-[148px] w-full animate-pulse rounded-xl bg-white opacity-60"></div>
			<div class="h-[148px] w-full animate-pulse rounded-xl bg-white opacity-60"></div>
			<div class="h-[148px] w-full animate-pulse rounded-xl bg-white opacity-60"></div>
		{:then papers}
			{#each papers as paper (paper.paper.doi)}
				{@render paperOverview(paper)}
			{/each}

			<NoContent items={papers}>There are no matching experiments.</NoContent>
		{/await}
	</div>
{/snippet}

{#snippet paperOverview(paper: PaperWithExperiments)}
	{@const totalSize = paper.experiments
		.flatMap((exp) => exp.files)
		.map((file) => file.sizeBytes)
		.reduce((acc, val) => acc + val, 0)}

	<a
		class="flex cursor-pointer flex-col rounded-xl bg-white p-4 shadow-lg shadow-gray-400/10 duration-100 hover:scale-[101%]"
		href={`/experiments/${encodeURIComponent(paper.paper.doi)}`}
		data-sveltekit-preloadData
	>
		<h3 class="text-accent pb-2 text-[1.3rem] font-bold">
			{paper.paper.title}
		</h3>

		<p class="text-dark/60 pb-3">{paper.paper.authors.join('; ')}</p>

		<div class="flex flex-wrap gap-2">
			<Tag label="Number of Experiments">
				{paper.experiments.length}
			</Tag>

			<Tag label="Total size">
				{formatFileSize(totalSize)}
			</Tag>

			{#each paper.experiments as exp}
				<Tag label="Experiment ID">
					{exp.experiment.humanReadableId}
				</Tag>
			{/each}
		</div>
	</a>
{/snippet}

<DownloadInstructions {experimentsToDownload} />
