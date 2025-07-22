<script lang="ts">
	import Header from '$lib/components/header.svelte';
	import { NAME_TO_MODEL } from '$lib/models/models';
	import Markdown from 'svelte-exmarkdown';
	import type { PageProps } from './$types';
	import { page } from '$app/state';
	import Tag from '$lib/components/tag.svelte';
	import { titleCase } from '$lib/utils/titleCase';
	import SampleTypeIcon from '../sampleTypeIcon.svelte';
	import LinkIcon from '$lib/icons/linkIcon.svelte';
	import PaperIcon from '$lib/icons/paperIcon.svelte';
	import CodeIcon from '$lib/icons/codeIcon.svelte';
	import TutorialIcon from '$lib/icons/tutorialIcon.svelte';
	import ExampleIcon from '$lib/icons/exampleIcon.svelte';
	import NoContent from '$lib/components/noContent.svelte';

	const modelName = page.params.model;
	const model = NAME_TO_MODEL[modelName];

	let { data }: PageProps = $props();

	let md = $state('');
	$effect(() => {
		import(`$lib/models/${modelName.toLowerCase()}.md?raw`).then((text) => {
			md = text.default;
		});
	});
</script>

<Header><a class="text-dark cursor-pointer text-2xl font-bold" href="/models">Models</a></Header>

<div class="m-8 flex items-start justify-center gap-8">
	<div class="flex max-w-[800px] flex-1 flex-col items-stretch gap-8">
		{@render content()}
		{@render experiments()}
	</div>

	<div class="flex w-2/5 max-w-[350px] min-w-56 flex-col gap-8">
		{@render tags()}
		{@render links()}
		{@render authors()}
	</div>
</div>

{#snippet content()}
	{#if md}
		<div
			class="prose prose-h1:text-4xl prose-h1:text-accent prose-h2:text-xl w-full max-w-full flex-1 rounded-xl bg-white p-6 shadow-lg shadow-gray-400/5"
		>
			<h1>{model.name}</h1>
			<p>{model.shortDescription}</p>

			<Markdown {md} />
		</div>
	{:else}
		<div class="h-64 max-w-[800px] flex-1 animate-pulse rounded-xl bg-white/70"></div>
	{/if}
{/snippet}

{#snippet experiments()}
	{#await data.papers}
		<div class="h-[200px] w-full animate-pulse rounded-xl bg-white opacity-60"></div>
	{:then papers}
		<div class="gap-3 rounded-xl bg-white p-5 pb-1 shadow-lg shadow-gray-400/5">
			<h3 class="text-accent mb-2 text-xl font-bold">Experiments using {model.name}</h3>

			{#each papers as paper (paper.paper.doi)}
				<a
					class="flex cursor-pointer flex-col py-3 hover:opacity-70"
					href={`/experiments/${encodeURIComponent(paper.paper.doi)}`}
					data-sveltekit-preloadData
				>
					<h3 class="pb-1 text-lg font-bold">
						{paper.paper.title}
					</h3>

					<p class="text-dark text-sm">{paper.paper.authors.join('; ')}</p>
				</a>
			{/each}

			<NoContent items={papers}>There are no experiments use {model.name}.</NoContent>
		</div>
	{/await}
{/snippet}

{#snippet tags()}
	<div class="flex flex-col gap-2 rounded-xl bg-white p-4 shadow-lg shadow-gray-400/5">
		<h3 class="text-accent font-bold">Compatible sample types</h3>

		<div class="flex flex-wrap gap-2">
			{#each model.sampleTypes as type}
				<Tag><SampleTypeIcon {type} classes="text-accent size-5 -ml-1" /> {titleCase(type)}</Tag>
			{/each}
		</div>

		<h3 class="text-accent mt-4 font-bold">Compatible data types</h3>

		<div class="flex flex-wrap gap-2">
			{#each model.dataTypes as type}
				<Tag>{titleCase(type)}</Tag>
			{/each}
		</div>

		{#if 0 < model.otherFeatures.length}
			<h3 class="text-accent mt-4 font-bold">Other features</h3>

			<div class="flex flex-wrap gap-2">
				{#each model.otherFeatures as feature}
					<Tag>{titleCase(feature)}</Tag>
				{/each}
			</div>
		{/if}
	</div>
{/snippet}

{#snippet links()}
	<div class="rounded-xl bg-white p-4 pb-1 shadow-lg shadow-gray-400/5">
		<h3 class="text-accent font-bold">Links</h3>

		{#if model.website}
			<a class="flex items-center gap-2 py-3 hover:opacity-60" href={model.website} target="_blank">
				<LinkIcon classes="text-accent" />
				<span class="line-clamp-1 flex-1 overflow-hidden text-ellipsis">Website</span>
			</a>
		{/if}

		{#if model.paper}
			<a class="flex items-center gap-2 py-3 hover:opacity-60" href={model.paper} target="_blank">
				<PaperIcon classes="text-accent" />
				<span class="line-clamp-1 flex-1 overflow-hidden text-ellipsis">Paper</span>
			</a>
		{/if}

		{#if model.code}
			<a class="flex items-center gap-2 py-3 hover:opacity-60" href={model.code} target="_blank">
				<CodeIcon classes="text-accent" />
				<span class="line-clamp-1 flex-1 overflow-hidden text-ellipsis">Code</span>
			</a>
		{/if}

		{#if 0 < model.tutorials.length}
			<h3 class="text-accent mt-4 font-bold">Tutorials</h3>

			{#each model.tutorials as tutorial}
				<a class="flex items-center gap-2 py-3 hover:opacity-60" href={tutorial} target="_blank">
					<TutorialIcon classes="text-accent" />
					<span class="line-clamp-1 flex-1 overflow-hidden text-ellipsis">{tutorial}</span>
				</a>
			{/each}
		{/if}

		{#if 0 < model.examples.length}
			<h3 class="text-accent mt-4 font-bold">Examples</h3>

			{#each model.examples as example}
				<a class="flex items-center gap-2 py-3 hover:opacity-60" href={example} target="_blank">
					<ExampleIcon classes="text-accent" />
					<span class="line-clamp-1 flex-1 overflow-hidden text-ellipsis">{example}</span>
				</a>
			{/each}
		{/if}
	</div>
{/snippet}

{#snippet authors()}
	<div class="rounded-xl bg-white p-4 shadow-lg shadow-gray-400/5">
		<h3 class="text-accent font-bold">Authors</h3>
		<p>
			{model.authors.join(', ')}
		</p>
	</div>
{/snippet}
