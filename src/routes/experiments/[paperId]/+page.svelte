<script lang="ts">
	import Header from '$lib/components/header.svelte';
	import Tag from '$lib/components/tag.svelte';
	import { formatDate, formatNumber } from '$lib/formatter';
	import type { PageProps } from './$types';
	import { type Experiment } from './+page.server';
	import Files from './files.svelte';

	let { data }: PageProps = $props();

	let currentExperimentIdx = $state(0);
</script>

<Header>
	<div class="flex items-baseline gap-4">
		<h2 class="text-dark text-2xl font-bold text-nowrap">Experiments for</h2>
		<h2 class="text-accent text-3xl font-bold">{data.title}</h2>
	</div>
</Header>

<div class="flex gap-8 p-8">
	{@render sidebar()}
	{@render content()}
</div>

{#snippet sidebar()}
	<div class="flex w-2/5 max-w-[330px] flex-col gap-8">
		<div class="flex flex-col gap-3">
			<button
				class="border-accent bg-accent flex cursor-pointer items-center space-x-2 rounded-md border px-4 py-2 font-semibold text-white hover:opacity-70"
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

				<span>Download all experiments</span>
			</button>

			<button
				class="border-accent text-accent flex cursor-pointer items-center space-x-2 rounded-md border px-4 py-2 font-semibold hover:opacity-70"
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
						d="M13.19 8.688a4.5 4.5 0 0 1 1.242 7.244l-4.5 4.5a4.5 4.5 0 0 1-6.364-6.364l1.757-1.757m13.35-.622 1.757-1.757a4.5 4.5 0 0 0-6.364-6.364l-4.5 4.5a4.5 4.5 0 0 0 1.242 7.244"
					/>
				</svg>

				<span>Download citation</span>
			</button>
		</div>

		<div class="flex flex-col gap-2 rounded-xl bg-white p-4">
			<h3 class="text-sm font-bold">Authors</h3>
			<p class="text-sm">
				{data.authors.join('; ')}
			</p>
		</div>

		<div class="flex flex-col gap-2 rounded-xl bg-white p-4">
			<h3 class="text-sm font-bold">Abstract</h3>
			<p class="text-sm">{data.abstract}</p>
		</div>
	</div>
{/snippet}

{#snippet content()}
	<div class="flex flex-1 flex-col gap-8">
		{@render paperOverview()}

		<div>
			{@render tabs()}
			{@render experiment(data.experiments[currentExperimentIdx])}
		</div>
	</div>
{/snippet}

{#snippet paperOverview()}
	<div class="flex flex-wrap items-start gap-2">
		<Tag label="DOI"><a href={data.doi}>{data.doi}</a></Tag>
		{#if data.url}<Tag label="URL"><a href={data.url}>{data.url}</a></Tag>{/if}
	</div>
{/snippet}

{#snippet tabs()}
	<div class="flex items-center">
		<span class="mr-8 ml-4 font-bold">Experiments:</span>

		{#each data.experiments as experiment, idx (idx)}
			<button
				class="relative cursor-pointer rounded-t-xl px-6 py-2"
				class:bg-white={idx === currentExperimentIdx}
				class:text-accent={idx === currentExperimentIdx}
				class:font-bold={idx === currentExperimentIdx}
				onclick={() => (currentExperimentIdx = idx)}
			>
				{#if idx === currentExperimentIdx}
					<!-- Make outer rounded corner of active tab -->
					<div class="absolute -bottom-3 -left-3 size-6 rounded-full bg-white"></div>
					<div class="bg-background absolute bottom-0 -left-6 size-6 rounded-full"></div>
					<div class="absolute -right-3 -bottom-3 size-6 rounded-full bg-white"></div>
					<div class="bg-background absolute -right-6 bottom-0 size-6 rounded-full"></div>
				{/if}

				{experiment.title}
			</button>
		{/each}
	</div>
{/snippet}

{#snippet experiment(experiment: Experiment)}
	<div class="divide-background flex flex-col divide-y divide-solid rounded-xl bg-white">
		{@render experimentOverview(experiment)}
		<Files files={experiment.files} />
		{@render samples(experiment)}
		{@render trees(experiment)}
		{@render leafSampleMapping(experiment)}
		{@render evolutionaryModel(experiment)}
		{@render metadata(experiment)}
	</div>
{/snippet}

{#snippet experimentOverview(experiment: Experiment)}
	<div class="flex flex-wrap items-start gap-2 p-4">
		<Tag label="Upload date">{formatDate(experiment.upload_date)}</Tag>
		<Tag label="Origin">{experiment.origin}</Tag>
		<Tag label="DOI"><a href={experiment.doi}>{experiment.doi}</a></Tag>
		<Tag label="License">{experiment.license}</Tag>
	</div>
{/snippet}

{#snippet samples(experiment: Experiment)}
	<div class="flex flex-col gap-4 p-4">
		<h3 class="text-lg font-bold">Samples</h3>

		<div class="flex flex-wrap items-start gap-2">
			<Tag label="Number of samples">{formatNumber(experiment.samples.length)}</Tag>
		</div>
	</div>
{/snippet}

{#snippet trees(experiment: Experiment)}
	<div class="flex flex-col gap-4 p-4">
		<h3 class="text-lg font-bold">Trees</h3>

		<div class="flex flex-wrap items-start gap-2">
			<Tag label="Number of trees">{formatNumber(experiment.number_of_trees)}</Tag>
			<Tag label="Number of tips">{formatNumber(experiment.number_of_tips)}</Tag>
			<Tag label="Ultrametric">{experiment.ultrametric ? 'Yes' : 'No'}</Tag>
			<Tag label="Rooted">{experiment.rooted ? 'Yes' : 'No'}</Tag>
			<Tag label="Tree ESS">{formatNumber(experiment.tree_ess)}</Tag>
			<Tag label="CCD1 entropy">{formatNumber(experiment.ccd1_entropy)}</Tag>
		</div>
	</div>
{/snippet}

{#snippet leafSampleMapping(experiment: Experiment)}
	<div class="flex flex-col gap-4 p-4">
		<h3 class="text-lg font-bold">Leaf-to-sample Mapping</h3>
	</div>
{/snippet}

{#snippet evolutionaryModel(experiment: Experiment)}
	<div class="flex flex-col gap-4 p-4">
		<h3 class="text-lg font-bold">Evolutionary Model</h3>
	</div>
{/snippet}

{#snippet metadata(experiment: Experiment)}
	<div class="flex flex-col gap-4 p-4">
		<h3 class="text-lg font-bold">Other Metadata</h3>
	</div>
{/snippet}
