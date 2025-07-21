<script lang="ts">
	import Header from '$lib/components/header.svelte';
	import { NAME_TO_MODEL } from '$lib/models/models';
	import Markdown from 'svelte-exmarkdown';
	import type { PageProps } from './$types';
	import { page } from '$app/state';
	import Tag from '$lib/components/tag.svelte';
	import { titleCase } from '$lib/utils/titleCase';
	import { getMainClassifications } from '$lib/classifications';

	const modelName = page.params.model;
	const model = NAME_TO_MODEL[modelName];

	let { data }: PageProps = $props();

	let md = $state('');
	$effect(() => {
		import(`$lib/models/${modelName.toLowerCase()}.md?raw`).then((text) => {
			md = text.default;
		});
	});

	const getRandomGradient = (seed: string) => {
		const hash = Array.from(seed).reduce((acc, char) => acc + char.charCodeAt(0), 0);
		const hue = hash % 360;

		// This curve reduces saturation for visually intense hues like green
		const baseSaturation = 50 - 20 * Math.cos(((hue - 120) * Math.PI) / 180);

		return `hsl(${hue} ${baseSaturation + 5} 55)`;
	};
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
		</div>
	{/await}
{/snippet}

{#snippet tags()}
	<div class="flex flex-col gap-2 rounded-xl bg-white p-4 shadow-lg shadow-gray-400/5">
		<h3 class="text-accent font-bold">Compatible sample types</h3>

		<div class="flex flex-wrap gap-2">
			{#each model.sampleTypes as type}
				<Tag>{titleCase(type)}</Tag>
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
				<svg
					xmlns="http://www.w3.org/2000/svg"
					fill="none"
					viewBox="0 0 24 24"
					stroke-width="1.5"
					stroke="currentColor"
					class="text-accent size-5"
				>
					<path
						stroke-linecap="round"
						stroke-linejoin="round"
						d="M12 21a9.004 9.004 0 0 0 8.716-6.747M12 21a9.004 9.004 0 0 1-8.716-6.747M12 21c2.485 0 4.5-4.03 4.5-9S14.485 3 12 3m0 18c-2.485 0-4.5-4.03-4.5-9S9.515 3 12 3m0 0a8.997 8.997 0 0 1 7.843 4.582M12 3a8.997 8.997 0 0 0-7.843 4.582m15.686 0A11.953 11.953 0 0 1 12 10.5c-2.998 0-5.74-1.1-7.843-2.918m15.686 0A8.959 8.959 0 0 1 21 12c0 .778-.099 1.533-.284 2.253m0 0A17.919 17.919 0 0 1 12 16.5c-3.162 0-6.133-.815-8.716-2.247m0 0A9.015 9.015 0 0 1 3 12c0-1.605.42-3.113 1.157-4.418"
					/>
				</svg>

				<span class="line-clamp-1 flex-1 overflow-hidden text-ellipsis">Website</span>
			</a>
		{/if}

		{#if model.paper}
			<a class="flex items-center gap-2 py-3 hover:opacity-60" href={model.paper} target="_blank">
				<svg
					xmlns="http://www.w3.org/2000/svg"
					fill="none"
					viewBox="0 0 24 24"
					stroke-width="1.5"
					stroke="currentColor"
					class="text-accent size-5"
				>
					<path
						stroke-linecap="round"
						stroke-linejoin="round"
						d="M19.5 14.25v-2.625a3.375 3.375 0 0 0-3.375-3.375h-1.5A1.125 1.125 0 0 1 13.5 7.125v-1.5a3.375 3.375 0 0 0-3.375-3.375H8.25m2.25 0H5.625c-.621 0-1.125.504-1.125 1.125v17.25c0 .621.504 1.125 1.125 1.125h12.75c.621 0 1.125-.504 1.125-1.125V11.25a9 9 0 0 0-9-9Z"
					/>
				</svg>

				<span class="line-clamp-1 flex-1 overflow-hidden text-ellipsis">Paper</span>
			</a>
		{/if}

		{#if model.code}
			<a class="flex items-center gap-2 py-3 hover:opacity-60" href={model.code} target="_blank">
				<svg
					xmlns="http://www.w3.org/2000/svg"
					fill="none"
					viewBox="0 0 24 24"
					stroke-width="1.5"
					stroke="currentColor"
					class="text-accent size-5"
				>
					<path
						stroke-linecap="round"
						stroke-linejoin="round"
						d="M17.25 6.75 22.5 12l-5.25 5.25m-10.5 0L1.5 12l5.25-5.25m7.5-3-4.5 16.5"
					/>
				</svg>

				<span class="line-clamp-1 flex-1 overflow-hidden text-ellipsis">Code</span>
			</a>
		{/if}

		{#if 0 < model.tutorials.length}
			<h3 class="text-accent mt-4 font-bold">Tutorials</h3>

			{#each model.tutorials as tutorial}
				<a class="flex items-center gap-2 py-3 hover:opacity-60" href={tutorial} target="_blank">
					<svg
						xmlns="http://www.w3.org/2000/svg"
						fill="none"
						viewBox="0 0 24 24"
						stroke-width="1.5"
						stroke="currentColor"
						class="text-accent size-5"
					>
						<path
							stroke-linecap="round"
							stroke-linejoin="round"
							d="M4.26 10.147a60.438 60.438 0 0 0-.491 6.347A48.62 48.62 0 0 1 12 20.904a48.62 48.62 0 0 1 8.232-4.41 60.46 60.46 0 0 0-.491-6.347m-15.482 0a50.636 50.636 0 0 0-2.658-.813A59.906 59.906 0 0 1 12 3.493a59.903 59.903 0 0 1 10.399 5.84c-.896.248-1.783.52-2.658.814m-15.482 0A50.717 50.717 0 0 1 12 13.489a50.702 50.702 0 0 1 7.74-3.342M6.75 15a.75.75 0 1 0 0-1.5.75.75 0 0 0 0 1.5Zm0 0v-3.675A55.378 55.378 0 0 1 12 8.443m-7.007 11.55A5.981 5.981 0 0 0 6.75 15.75v-1.5"
						/>
					</svg>

					<span class="line-clamp-1 flex-1 overflow-hidden text-ellipsis">{tutorial}</span>
				</a>
			{/each}
		{/if}

		{#if 0 < model.examples.length}
			<h3 class="text-accent mt-4 font-bold">Examples</h3>

			{#each model.examples as example}
				<a class="flex items-center gap-2 py-3 hover:opacity-60" href={example} target="_blank">
					<svg
						xmlns="http://www.w3.org/2000/svg"
						fill="none"
						viewBox="0 0 24 24"
						stroke-width="1.5"
						stroke="currentColor"
						class="text-accent size-5"
					>
						<path
							stroke-linecap="round"
							stroke-linejoin="round"
							d="M14.25 9.75 16.5 12l-2.25 2.25m-4.5 0L7.5 12l2.25-2.25M6 20.25h12A2.25 2.25 0 0 0 20.25 18V6A2.25 2.25 0 0 0 18 3.75H6A2.25 2.25 0 0 0 3.75 6v12A2.25 2.25 0 0 0 6 20.25Z"
						/>
					</svg>

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
