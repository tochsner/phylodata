<script lang="ts">
	import Header from '$lib/components/header.svelte';
	import Tag from '$lib/components/tag.svelte';
	import { formatDate, formatNumber } from '$lib/utils/formatter';
	import type { PageProps } from './$types';
	import { type Experiment, type PaperWithExperiments } from '$lib/types';
	import Files from './files.svelte';
	import Samples from './samples.svelte';
	import EvolutionaryModels from './evolutionaryModels.svelte';
	import Trees from './trees.svelte';
	import toast from 'svelte-5-french-toast';
	import DownloadInstructions from '$lib/components/downloadInstructions.svelte';
	import ClipboardIcon from '$lib/icons/clipboardIcon.svelte';

	let { data }: PageProps = $props();

	let currentExperimentIdx = $state(0);

	let experimentsToDownload: [string, number][] | undefined = $state();
</script>

<Header>
	<div class="flex items-baseline gap-4">
		<h2 class="text-dark text-2xl font-bold text-nowrap">Experiments for</h2>
		<h2 class="text-accent text-3xl font-bold">{data.paper.title}</h2>
	</div>
</Header>

<div class="flex gap-8 p-8">
	{@render sidebar()}
	{@render content()}
</div>

{#snippet sidebar()}
	<div class="flex w-2/5 max-w-[300px] min-w-56 flex-col gap-8">
		<div class="flex flex-col gap-3">
			<button
				class="border-accent bg-accent flex cursor-pointer items-center space-x-2 rounded-md border px-4 py-2 font-semibold text-white duration-100 hover:scale-[102%] hover:opacity-80"
				onclick={() =>
					(experimentsToDownload = data.experiments.map((exp) => [
						exp.experiment.humanReadableId,
						exp.experiment.version
					]))}
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

				<span>Use all experiments</span>
			</button>

			<button
				class="border-accent text-accent flex cursor-pointer items-center space-x-2 rounded-md border px-4 py-2 font-semibold duration-100 hover:scale-[102%] hover:opacity-80"
				onclick={() => {
					navigator.clipboard.writeText(data.paper.bibtex);
					toast.success('Copied bibtex entry to clipboard');
				}}
			>
				<ClipboardIcon classes="size-6" />

				<span>Copy citation</span>
			</button>
		</div>

		<div class="flex flex-col gap-2 rounded-xl bg-white p-4 shadow-lg shadow-gray-400/5">
			<h3 class="text-sm font-bold">Authors</h3>
			<p class="text-sm">
				{data.paper.authors.join(', ')}
			</p>
		</div>

		<div class="flex flex-col gap-2 rounded-xl bg-white p-4 shadow-lg shadow-gray-400/5">
			<h3 class="text-sm font-bold">Abstract</h3>
			<p class="text-sm">{data.paper.abstract}</p>
		</div>
	</div>
{/snippet}

{#snippet content()}
	<div class="flex flex-1 flex-col gap-8 overflow-x-clip">
		{@render paperOverview()}

		<div>
			{@render tabs()}
			{@render experiment(data.experiments[currentExperimentIdx])}
		</div>
	</div>
{/snippet}

{#snippet paperOverview()}
	<div class="flex flex-wrap items-start gap-2">
		<Tag label="DOI"><a href={data.paper.doi} target="_blank">{data.paper.doi}</a></Tag>
		{#if data.paper.url}
			<Tag label="URL">
				<a href={data.paper.url} target="_blank">{data.paper.url}</a>
			</Tag>
		{/if}
		<Tag label="Year">{data.paper.year}</Tag>
	</div>
{/snippet}

{#snippet tabs()}
	<div class="flex items-center">
		<span class="mr-8 ml-4 font-bold">Experiments:</span>

		{#each data.experiments as experiment, idx (idx)}
			<button
				class="relative cursor-pointer rounded-t-xl px-6 py-2 font-medium"
				class:bg-white={idx === currentExperimentIdx}
				onclick={() => (currentExperimentIdx = idx)}
			>
				{#if idx === currentExperimentIdx}
					<!-- Make outer rounded corner of active tab -->
					<div class="absolute -bottom-3 -left-3 size-6 rounded-full bg-white"></div>
					<div class="bg-background absolute bottom-0 -left-6 size-6 rounded-full"></div>
					<div class="absolute -right-3 -bottom-3 size-6 rounded-full bg-white"></div>
					<div class="bg-background absolute -right-6 bottom-0 size-6 rounded-full"></div>
				{/if}

				<span class:text-accent={idx === currentExperimentIdx}>
					{experiment.experiment.title || `Experiment ${idx + 1}`}
				</span>
			</button>
		{/each}
	</div>
{/snippet}

{#snippet experiment(experiment: PaperWithExperiments['experiments'][number])}
	<div
		class="divide-background flex flex-col divide-y divide-solid rounded-xl bg-white text-sm shadow-lg shadow-gray-400/5"
	>
		{@render experimentOverview(experiment.experiment)}
		<Files files={experiment.files} />
		<Samples samples={experiment.samples} />
		<Trees trees={experiment.trees} />
		<EvolutionaryModels evolutionaryModels={experiment.evolutionaryModel} />
	</div>
{/snippet}

{#snippet experimentOverview(experiment: Experiment)}
	<div class="flex flex-wrap items-center gap-2 p-4">
		<button
			class="border-accent text-accent mr-4 flex cursor-pointer items-center space-x-1 rounded-md border px-3 py-1 font-semibold duration-100 hover:scale-[102%] hover:opacity-80"
			onclick={() => (experimentsToDownload = [[experiment.humanReadableId, experiment.version]])}
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

			<span>Use experiment</span>
		</button>

		<Tag label="License">{experiment.license}</Tag>
		<Tag label="Upload date">{formatDate(experiment.uploadDate)}</Tag>
		<Tag label="version">{formatNumber(experiment.version)}</Tag>
		<Tag label="Experiment ID">{experiment.humanReadableId}</Tag>
	</div>
{/snippet}

<DownloadInstructions {experimentsToDownload} />
