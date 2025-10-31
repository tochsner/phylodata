<script lang="ts">
	import Header from '$lib/components/header.svelte';
	import type { PageProps } from './$types';
	import { goto } from '$app/navigation';
	import { fade } from 'svelte/transition';
	import { page } from '$app/state';
	import type { PaperWithXml } from './+page.server';
	import NoContent from '$lib/components/noContent.svelte';

	let { data }: PageProps = $props();
	let { papers } = $derived(data);

	let searchQuery = $state<string>(decodeURIComponent(page.params.query || ''));
	let activeSearch = $derived(page.params.query && page.params.query.replace(/\s/g, '').length > 0);

	let loadedPapers = $state<PaperWithXml[]>([]);
	let nextPaperIdxToCheck = $state<number>(0);
	let matches = $state<PaperWithXml[]>([]);
	let isSearching = $state<boolean>(false);

	$effect(() => {
		papers.then(async (papers) => {
			loadedPapers = papers;
			nextPaperIdxToCheck = 0;
			matches = [];
			isSearching = false;
			await loadNextMatches();
		});
	});

	const loadNextMatches = async () => {
		if (!activeSearch) return;
		if (nextPaperIdxToCheck >= loadedPapers.length) return;

		isSearching = true;
		const targetNumberOfMatches = matches.length + 2;

		while (matches.length < targetNumberOfMatches && nextPaperIdxToCheck < loadedPapers.length) {
			const paperToCheck = loadedPapers[nextPaperIdxToCheck++];

			for (const exp of paperToCheck.experiments) {
				for (const file of exp.files) {
					const presignedFileUrl = await (
						await fetch(`/api/getDownloadLink/${file.humanReadableId}/${file.name}`)
					).text();
					const response = await fetch(presignedFileUrl);
					const text = await response.text();
					const lines = text.split('\n');

					file.presignedUrl = presignedFileUrl;
					file.matches = [];

					for (let i = 0; i < lines.length; i++) {
						const line = lines[i];

						if (line.toLowerCase().includes(searchQuery.toLowerCase())) {
							if (i === 0)
								file.matches.push({
									lineNumberStart: 0,
									lineNumberMatch: 0,
									lines: lines.slice(0, 5)
								});
							else
								file.matches.push({
									lineNumberStart: i - 3,
									lineNumberMatch: i,
									lines: lines.slice(i - 3, i + 4)
								});
						}
					}

					if (file.matches.length > 0) {
						matches.push(paperToCheck);
					}
				}
			}
		}

		isSearching = false;
	};
</script>

<svelte:head>
	<title>Experiments – PhyloData</title>
	<meta
		name="description"
		content="Explore a wide range of phylogenetic experiments on PhyloData."
	/>
</svelte:head>

<Header>
	<div class="class flex flex-1 flex-col gap-6">
		<h2 class="text-dark flex-1 text-2xl font-bold">Search in BEAST 2 XML files</h2>
		{@render searchBar()}
	</div>
</Header>

<div class="flex flex-col items-stretch gap-8 p-4 md:p-8">
	{#each matches as match}
		{@render matchingPaper(match)}
	{/each}
	{#if activeSearch && (isSearching || nextPaperIdxToCheck === 0)}
		<div class="h-48 w-full animate-pulse rounded-xl bg-white/70"></div>
	{/if}

	{#if activeSearch && !isSearching}
		<NoContent items={matches}>No matches found</NoContent>
	{/if}

	{#if activeSearch && !isSearching && nextPaperIdxToCheck < loadedPapers.length}
		<div class="flex w-full items-center justify-center">
			<button
				class="border-accent text-accent flex cursor-pointer items-center space-x-4 rounded-full border px-6 py-3 font-semibold duration-100 hover:scale-[102%] hover:opacity-80"
				onclick={() => {
					loadNextMatches();
				}}
			>
				Load more
			</button>
		</div>
	{/if}
</div>

{#snippet searchBar()}
	<div class="ustify-center hidden h-12 w-full gap-2 md:flex">
		<input
			class="w-full flex-1 resize-none rounded-md border border-white bg-white py-2 pl-3 pr-10 placeholder:italic focus:bg-white"
			bind:value={searchQuery}
			placeholder="Search in BEAST 2 XML files..."
			onkeydown={(e) => {
				if (e.key === 'Enter' && !e.shiftKey) {
					e.preventDefault();
					goto(`/experiments/searchXml/${encodeURIComponent(searchQuery)}`, {
						invalidateAll: true,
						replaceState: true
					});
				}
			}}
		/>

		<button
			class="bg-accent cursor-pointer rounded-full px-3 py-0 text-white duration-100 hover:scale-[102%] hover:opacity-80"
			aria-label="search-model"
			onclick={() => {
				goto(`/experiments/searchXml/${encodeURIComponent(searchQuery)}`, {
					invalidateAll: true,
					replaceState: true
				});
			}}
			title="Search for experiments"
		>
			<svg
				width="24"
				height="24"
				viewBox="0 0 24 24"
				fill="none"
				xmlns="http://www.w3.org/2000/svg"
				stroke="currentColor"
			>
				<g clip-path="url(#clip0_331_5)">
					<path
						d="M4 3L7.55532 8.33299"
						stroke-width="1.77766"
						stroke-linecap="round"
						stroke-linejoin="round"
					/>
					<path
						d="M4 8.33299L7.55532 3"
						stroke-width="1.77766"
						stroke-linecap="round"
						stroke-linejoin="round"
					/>
					<path
						d="M17.332 3V8.33299H19.9985"
						stroke-width="1.77766"
						stroke-linecap="round"
						stroke-linejoin="round"
					/>
					<path
						d="M10.2188 8.33299V3L12.4408 5.66649L14.6629 3V8.33299"
						stroke-width="1.77766"
						stroke-linecap="round"
						stroke-linejoin="round"
					/>
					<path
						d="M14 17.5C14 18.163 14.2634 18.7989 14.7322 19.2678C15.2011 19.7366 15.837 20 16.5 20C17.163 20 17.7989 19.7366 18.2678 19.2678C18.7366 18.7989 19 18.163 19 17.5C19 16.837 18.7366 16.2011 18.2678 15.7322C17.7989 15.2634 17.163 15 16.5 15C15.837 15 15.2011 15.2634 14.7322 15.7322C14.2634 16.2011 14 16.837 14 17.5Z"
						stroke-width="2"
						stroke-linecap="round"
						stroke-linejoin="round"
					/>
					<path
						d="M18.5 19.5L21 22"
						stroke-width="2"
						stroke-linecap="round"
						stroke-linejoin="round"
					/>
					<path d="M19 11V13" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
					<path
						d="M12 21H7C6.46957 21 5.96086 20.7893 5.58579 20.4142C5.21071 20.0391 5 19.5304 5 19V11"
						stroke-width="2"
						stroke-linecap="round"
						stroke-linejoin="round"
					/>
				</g>
				<defs>
					<clipPath id="clip0_331_5">
						<rect width="24" height="24" fill="white" />
					</clipPath>
				</defs>
			</svg>
		</button>

		{#if activeSearch}
			<button
				class="text-accent cursor-pointer rounded-full bg-white px-3 duration-100 hover:scale-[102%] hover:opacity-80"
				aria-label="search-model"
				onclick={() => {
					searchQuery = '';
					goto(`/experiments/searchXml`, { invalidateAll: true, replaceState: true });
				}}
				transition:fade
			>
				<svg
					xmlns="http://www.w3.org/2000/svg"
					width="24"
					height="24"
					viewBox="0 0 24 24"
					fill="none"
					stroke="currentColor"
					stroke-width="2"
					stroke-linecap="round"
					stroke-linejoin="round"
					class="icon icon-tabler icons-tabler-outline icon-tabler-x"
					><path stroke="none" d="M0 0h24v24H0z" fill="none" /><path d="M18 6l-12 12" /><path
						d="M6 6l12 12"
					/></svg
				>
			</button>
		{/if}
	</div>
{/snippet}

{#snippet matchingPaper(match: PaperWithXml)}
	{#each match.experiments as exp}
		{#each exp.files as file}
			{#if file.matches && 0 < file.matches?.length}
				<div
					class="flex w-full flex-col gap-4 overflow-clip rounded-xl bg-white shadow-lg shadow-gray-400/15"
					transition:fade={{ duration: 100 }}
				>
					<a
						style="background: linear-gradient(45deg, hsl(96 25 45) 0%, hsl(96 30 55) 100%);"
						class="flex cursor-pointer flex-col gap-1 px-4 py-3"
						href={`/experiments/${encodeURIComponent(match.doi)}`}
					>
						<span class="text-lg font-semibold text-white">{match.title}</span>
						{#if exp.title}
							<span class="text-sm text-white">{exp.title}</span>
						{/if}
					</a>

					<div class="flex flex-col gap-4">
						<div class="flex items-center gap-3 px-4">
							<span class="flex-1 font-semibold">
								{file.name}

								{#if file.matches?.length && file.matches?.length > 0}
									({file.matches?.length} matches)
								{:else}
									(1 match)
								{/if}
							</span>

							<button
								aria-label="copy-md5-shash"
								class="text-accent flex cursor-pointer gap-1 p-2 font-semibold hover:scale-[105%] hover:opacity-80"
								onclick={() => {
									window.open(file.presignedUrl, '_blank');
								}}
							>
								<svg
									xmlns="http://www.w3.org/2000/svg"
									width="24"
									height="24"
									viewBox="0 0 24 24"
									fill="none"
									stroke="currentColor"
									stroke-width="2"
									stroke-linecap="round"
									stroke-linejoin="round"
									class="icon icon-tabler icons-tabler-outline icon-tabler-eye-search"
									><path stroke="none" d="M0 0h24v24H0z" fill="none" /><path
										d="M10 12a2 2 0 1 0 4 0a2 2 0 0 0 -4 0"
									/><path
										d="M12 18c-.328 0 -.652 -.017 -.97 -.05c-3.172 -.332 -5.85 -2.315 -8.03 -5.95c2.4 -4 5.4 -6 9 -6c3.465 0 6.374 1.853 8.727 5.558"
									/><path d="M18 18m-3 0a3 3 0 1 0 6 0a3 3 0 1 0 -6 0" /><path
										d="M20.2 20.2l1.8 1.8"
									/>
								</svg>
							</button>
						</div>

						{#each file.matches as match}
							<div
								class="border-light-gray bg-background flex flex-col overflow-x-scroll border-b border-t"
							>
								{#each match.lines as line, idx}
									<div
										class={[
											'flex gap-0 px-4',
											match.lineNumberMatch === match.lineNumberStart + idx && 'bg-accent/20'
										]}
									>
										<span class="text-dark/50 w-11 min-w-11 whitespace-nowrap font-mono text-sm">
											{match.lineNumberStart + idx + 1}
										</span>
										<span class="text-dark whitespace-pre font-mono text-sm">{line}</span>
									</div>
								{/each}
							</div>
						{/each}
					</div>
				</div>
			{/if}
		{/each}
	{/each}
{/snippet}
