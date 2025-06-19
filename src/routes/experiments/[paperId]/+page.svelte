<script lang="ts">
	import Header from '$lib/components/header.svelte';
	import Tag from '$lib/components/tag.svelte';
	import type { PageProps } from './$types';
	import { type Experiment } from './+page.server';

	let { data }: PageProps = $props();
</script>

<Header>
	<div class="flex items-baseline gap-4">
		<h2 class="text-dark text-2xl font-bold text-nowrap">Experiments for</h2>
		<h2 class="text-accent text-3xl font-bold">{data.title}</h2>
	</div>
</Header>

<div class="m-8 flex gap-8">
	{@render sidebar()}
	{@render content()}
</div>

{#snippet sidebar()}
	<div class="flex w-1/3 max-w-[330px] flex-col gap-8">
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
	<div class="flex flex-col gap-8">
		{@render paperOverview()}

		{@render experiment(data.experiments[0])}
	</div>
{/snippet}

{#snippet paperOverview()}
	<div class="flex flex-wrap items-start gap-2">
		<Tag label="DOI"><a href={data.doi}>{data.doi}</a></Tag>
		{#if data.url}<Tag label="URL"><a href={data.url}>{data.url}</a></Tag>{/if}
	</div>
{/snippet}

{#snippet experiment(experiment: Experiment)}
	<div class="divide-background flex flex-col divide-y divide-solid rounded-xl bg-white">
		{@render experimentOverview(experiment)}
		{@render files(experiment)}
		{@render samples(experiment)}
		{@render trees(experiment)}
		{@render leafSampleMapping(experiment)}
		{@render evolutionaryModel(experiment)}
		{@render metadata(experiment)}
	</div>
{/snippet}

{#snippet experimentOverview(experiment: Experiment)}
	<div class="flex flex-wrap items-start gap-2 p-4">
		<Tag label="Upload date">{experiment.upload_date}</Tag>
		<Tag label="Origin">{experiment.origin}</Tag>
		<Tag label="DOI"><a href={experiment.doi}>{experiment.doi}</a></Tag>
		<Tag label="License">{experiment.license}</Tag>
	</div>
{/snippet}

{#snippet files(experiment: Experiment)}
	{@const totalSize = experiment.files
		.map((file) => file.size_bytes)
		.reduce((acc, val) => acc + val, 0)}

	<div class="flex flex-col gap-4 p-4">
		<h3 class="text-lg font-bold">Files</h3>

		<div class="flex flex-wrap items-start gap-2">
			<Tag label="Number of files">{experiment.files.length}</Tag>
			<Tag label="Total size">{totalSize} bytes</Tag>
		</div>
	</div>
{/snippet}

{#snippet samples(experiment: Experiment)}
	<div class="flex flex-col gap-4 p-4">
		<h3 class="text-lg font-bold">Samples</h3>

		<div class="flex flex-wrap items-start gap-2">
			<Tag label="Number of samples">{experiment.samples.length}</Tag>
		</div>
	</div>
{/snippet}

{#snippet trees(experiment: Experiment)}
	<div class="flex flex-col gap-4 p-4">
		<h3 class="text-lg font-bold">Trees</h3>

		<div class="flex flex-wrap items-start gap-2">
			<Tag label="Number of trees">{experiment.number_of_trees}</Tag>
			<Tag label="Number of tips">{experiment.number_of_tips}</Tag>
			<Tag label="Ultrametric">{experiment.ultrametric ? 'Yes' : 'No'}</Tag>
			<Tag label="Rooted">{experiment.rooted ? 'Yes' : 'No'}</Tag>
			<Tag label="Tree ESS">{experiment.tree_ess}</Tag>
			<Tag label="CCD1 entropy">{experiment.ccd1_entropy}</Tag>
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
