<script lang="ts">
	import Header from '$lib/components/header.svelte';
	import Tag from '$lib/components/tag.svelte';
	import { MODELS, type Model } from '$lib/models/models';
	import { titleCase } from '$lib/utils/titleCase';
	import { fade, slide } from 'svelte/transition';
	import SampleTypeIcon from './sampleTypeIcon.svelte';
	import NoContent from '$lib/components/noContent.svelte';
	import Info from '$lib/components/info.svelte';
	import { page } from '$app/stores';
	import { replaceState } from '$app/navigation';
	import { browser } from '$app/environment';
	import modelEmbeddings from '$lib/models/embeddings.json';

	const embeddingThreshold = 0.55;

	async function computeEmbedding(query: string | null) {
		if (!query || !browser) return null;

		const response = await fetch(`api/computeEmbedding/${query}`);
		const data = await response.json();

		return data;
	}
	const modelToEmbedding = new Map<string, number[]>();
	for (const model of modelEmbeddings) {
		modelToEmbedding.set(model.fileName, model.embedding);
	}

	const sampleTypes: {
		value: Model['sampleTypes'][number];
		label: string;
		color: string;
		description: string;
	}[] = [
		{
			value: 'species',
			label: 'Species',
			color: '#FCD443',
			description: 'This could be extinct or extant species.'
		},
		{
			value: 'single-cells',
			label: 'Single-cells',
			color: '#2FA7AB',
			description: 'This could be extinct or extant species.'
		},
		{
			value: 'pathogens',
			label: 'Pathogens',
			color: '#AB2F54',
			description: 'This could be extinct or extant species.'
		},
		{
			value: 'languages',
			label: 'Languages',
			color: '#AB2F54',
			description: 'This could be extinct or extant species.'
		}
	];
	const dataTypes: { value: Model['dataTypes'][number]; label: string }[] = [
		{ value: 'nucleotides', label: 'Nucleotides' },
		{ value: 'proteins', label: 'Proteins' },
		{ value: 'snps', label: 'SNPs' },
		{ value: 'traits', label: 'Traits' }
	];

	let inputSearchQuery = $state<string>($page.url.searchParams.get('query') || '');
	let searchQuery = $state<string | null>($page.url.searchParams.get('query'));

	let selectedSampleTypes = $state<Model['sampleTypes']>(
		JSON.parse($page.url.searchParams.get('sampleTypes') || '[]')
	);
	let selectedDataTypes = $state<Model['dataTypes']>(
		JSON.parse($page.url.searchParams.get('dataTypes') || '[]')
	);

	const updateUrl = async () => {
		if (0 < selectedSampleTypes.length) {
			$page.url.searchParams.set('sampleTypes', JSON.stringify(selectedSampleTypes));
		} else {
			$page.url.searchParams.delete('sampleTypes');
		}

		if (0 < selectedDataTypes.length) {
			$page.url.searchParams.set('dataTypes', JSON.stringify(selectedDataTypes));
		} else {
			$page.url.searchParams.delete('dataTypes');
		}

		if (inputSearchQuery != '') {
			$page.url.searchParams.set('query', inputSearchQuery);
		} else {
			$page.url.searchParams.delete('query');
		}
		searchQuery = inputSearchQuery;

		try {
			replaceState($page.url, $page.state);
		} catch {}
	};

	const filteredModels = $derived.by(async () => {
		let filteredModels = MODELS.filter((model) => {
			if (selectedSampleTypes.length === 0) return true;
			return model.sampleTypes.some((type) => selectedSampleTypes.includes(type));
		})
			.filter((model) => {
				if (selectedDataTypes.length === 0) return true;
				return model.dataTypes.some((type) => selectedDataTypes.includes(type));
			})
			.map((model) => ({
				model,
				matchScore:
					model.sampleTypes.filter((type) => selectedSampleTypes.includes(type)).length /
						model.sampleTypes.length +
					model.dataTypes.filter((type) => selectedDataTypes.includes(type)).length /
						model.dataTypes.length
			}))
			.sort((a, b) => b.matchScore - a.matchScore)
			.map(({ model }) => model)
			.sort((a, b) => a.name.localeCompare(b.name));

		if (searchQuery) {
			const embedding = await computeEmbedding(searchQuery);
			filteredModels = filteredModels
				.sort((a, b) => {
					if (!searchQuery) return 0;
					if (
						a.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
						a.shortDescription.toLowerCase().includes(searchQuery.toLowerCase())
					) {
						return -1;
					}

					const aEmbedding = modelToEmbedding.get(a.name.toLowerCase() + '.md');
					const bEmbedding = modelToEmbedding.get(b.name.toLowerCase() + '.md');
					if (!aEmbedding || !bEmbedding) {
						console.log('Embedding not found for', a.name, b.name);
						return 0;
					}
					return -cosineSimilarity(embedding, aEmbedding) + cosineSimilarity(embedding, bEmbedding);
				})
				.filter((model) => {
					if (!searchQuery) return false;
					if (
						model.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
						model.shortDescription.toLowerCase().includes(searchQuery.toLowerCase())
					) {
						return true;
					}

					const modelEmbedding = modelToEmbedding.get(model.name.toLowerCase() + '.md');
					return (
						!modelEmbedding || cosineSimilarity(embedding, modelEmbedding) > embeddingThreshold
					);
				})
				.slice(0, 6);
		}

		return filteredModels;
	});

	function cosineSimilarity(embedding: number[], aEmbedding: number[]) {
		return aEmbedding.reduce((acc, val, i) => acc + val * embedding[i], 0);
	}
</script>

<svelte:head>
	<title>PhyloData Models</title>
	<meta name="description" content="Explore a wide range of models for your phylogenetic data." />
</svelte:head>

<Header>
	<h2 class="text-dark text-2xl font-bold">Models</h2>

	<div class="flex w-full justify-center gap-16">
		<div>
			<h3 class="text-dark text-center text-lg font-semibold">Discover models for your data:</h3>

			<div class="flex w-full gap-4 pt-4">
				<div
					class="flex flex-col items-stretch overflow-clip rounded-xl bg-white/60 shadow-lg shadow-gray-300/10"
				>
					<span class="rounded-b-xl bg-white p-2 text-center font-bold">Sample Type</span>

					<div class="grid grid-cols-1 gap-2 p-4 md:grid-cols-2">
						{#each sampleTypes as type}
							{@const selected = selectedSampleTypes.includes(type.value)}

							<button
								class={[
									'flex cursor-pointer items-center gap-1 rounded-lg py-2 pr-4 pl-3 duration-100 hover:scale-[103%]',
									!selected && 'bg-accent-light',
									selected && 'bg-accent  text-white'
								]}
								onclick={() => {
									if (selectedSampleTypes.includes(type.value))
										selectedSampleTypes = selectedSampleTypes.filter((x) => x != type.value);
									else selectedSampleTypes = [...selectedSampleTypes, type.value];
									updateUrl();
								}}
							>
								<SampleTypeIcon
									type={type.value}
									classes={selected ? `size-5 text-white` : `size-5 text-accent`}
								/>

								{type.label}

								<Info classes={['size-5', !selected && 'text-accent/70', selected && 'text-white']}>
									<span class="text-sm">{type.description}</span>
								</Info>
							</button>
						{/each}
					</div>
				</div>

				<div
					class="flex flex-col items-stretch overflow-clip rounded-xl bg-white/60 shadow-lg shadow-gray-300/10"
				>
					<span class="w-full rounded-b-xl bg-white p-2 text-center font-bold">Data Type</span>

					<div class="grid grid-cols-1 gap-2 p-4 md:grid-cols-2">
						{#each dataTypes as type}
							{@const selected = selectedDataTypes.includes(type.value)}

							<button
								class={[
									'flex cursor-pointer items-center gap-1 rounded-lg py-2 pr-4 pl-3 duration-100 hover:scale-[103%]',
									!selected && 'bg-accent-light',
									selected && 'bg-accent  text-white'
								]}
								onclick={() => {
									if (selectedDataTypes.includes(type.value))
										selectedDataTypes = selectedDataTypes.filter((x) => x != type.value);
									else selectedDataTypes = [...selectedDataTypes, type.value];
									updateUrl();
								}}
							>
								{type.label}
							</button>
						{/each}
					</div>
				</div>
			</div>
		</div>

		<div class="flex flex-col items-center">
			<div class="w-[1px] flex-1 bg-white/60"></div>
			<span class="text-dark/60 pt-2 pb-3 italic">or</span>
			<div class="w-[1px] flex-1 bg-white/60"></div>
		</div>

		<div class="flex-1">
			<h3 class="text-dark text-center text-lg font-semibold">Describe your use case:</h3>

			<div class="flex justify-center gap-2 pt-4">
				<textarea
					class="h-24 w-full resize-none rounded-md border border-white bg-white py-2 pr-10 pl-3 placeholder:italic focus:bg-white"
					bind:value={inputSearchQuery}
					placeholder="I study bacterial evolution with a focus on horizontal gene transfer."
				></textarea>

				<button
					class="bg-accent cursor-pointer rounded-full px-3 text-white duration-100 hover:scale-[102%] hover:opacity-80"
					aria-label="search-model"
					onclick={() => {
						updateUrl();
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
						class="icon icon-tabler icons-tabler-outline icon-tabler-search"
						><path stroke="none" d="M0 0h24v24H0z" fill="none" /><path
							d="M10 10m-7 0a7 7 0 1 0 14 0a7 7 0 1 0 -14 0"
						/><path d="M21 21l-6 -6" /></svg
					>
				</button>

				{#if inputSearchQuery != ''}
					<button
						class="text-accent cursor-pointer rounded-full bg-white px-3 duration-100 hover:scale-[102%] hover:opacity-80"
						aria-label="search-model"
						onclick={() => {
							inputSearchQuery = '';
							updateUrl();
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
		</div>
	</div></Header
>

<div class="flex flex-col items-stretch">
	{#await filteredModels}
		<div class="m-8 grid grid-cols-1 gap-6 sm:grid-cols-2 md:grid-cols-3 xl:col-end-4">
			{#each Array(6) as _}
				<div
					class="flex h-[220px] w-full animate-pulse cursor-pointer flex-col gap-4 rounded-xl bg-white p-5 opacity-60"
				></div>
			{/each}
		</div>
	{:then filteredModels}
		<div class="m-8 grid grid-cols-1 gap-6 sm:grid-cols-2 md:grid-cols-3 xl:col-end-4">
			{#each filteredModels as model}
				<a
					class="flex w-full cursor-pointer flex-col gap-4 rounded-xl p-5 shadow-lg shadow-gray-400/10 duration-100 hover:scale-[102%] hover:opacity-90"
					href={`models/${decodeURIComponent(model.name)}`}
					style="background: linear-gradient(45deg, hsl(96 25 45) 0%, hsl(96 30 55) 100%);"
					transition:fade={{ duration: 100 }}
				>
					<span class="text-xl font-semibold text-white">{model.name}</span>
					<div class="mx-[-2px] flex gap-3 text-black">
						{#each model.tags as tag}
							<Tag class="bg-white">
								{titleCase(tag)}
							</Tag>
						{/each}
					</div>
					<span class="line-clamp-2 h-12 text-ellipsis text-white">{model.shortDescription}</span>
					<div class="flex-1"></div>

					<div class="-mx-5 -mb-5 flex justify-between gap-2 rounded-b-xl bg-white/50 px-5 py-2">
						<span class="text-accent text-sm font-semibold uppercase">{model.software}</span>

						<div class="flex gap-2">
							{#each model.sampleTypes as sampleType}
								<SampleTypeIcon type={sampleType} classes="text-accent size-5" />
							{/each}
						</div>
					</div>
				</a>
			{/each}
		</div>
		<NoContent items={filteredModels}>There are no matching models.</NoContent>
	{/await}
</div>
