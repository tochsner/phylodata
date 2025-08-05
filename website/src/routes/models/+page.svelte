<script lang="ts">
	import Header from '$lib/components/header.svelte';
	import Tag from '$lib/components/tag.svelte';
	import { MODELS, type Model } from '$lib/models/models';
	import { titleCase } from '$lib/utils/titleCase';
	import { fade } from 'svelte/transition';
	import SampleTypeIcon from './sampleTypeIcon.svelte';
	import NoContent from '$lib/components/noContent.svelte';
	import Info from '$lib/components/info.svelte';
	import { page } from '$app/stores';
	import { replaceState } from '$app/navigation';

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
			value: 'cells',
			label: 'Single cells',
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
			value: 'genes',
			label: 'Genes',
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

	let selectedSampleTypes = $state<Model['sampleTypes']>(
		JSON.parse($page.url.searchParams.get('sampleTypes') || '[]')
	);
	let selectedDataTypes = $state<Model['dataTypes']>(
		JSON.parse($page.url.searchParams.get('dataTypes') || '[]')
	);

	const updateUrl = () => {
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

		try {
			replaceState($page.url, $page.state);
		} catch {}
	};

	const filteredModels = $derived(
		MODELS.filter((model) => {
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
	);
</script>

<svelte:head>
	<title>PhyloData Models</title>
	<meta name="description" content="Explore a wide range of models for your phylogenetic data." />
</svelte:head>

<Header>
	<h2 class="text-dark text-2xl font-bold">Models</h2>
	<h3 class="text-dark text-center text-lg font-semibold">Discover models for your data:</h3>

	<div class="flex justify-center gap-4 pt-4">
		<div
			class="flex max-w-1/2 flex-col items-stretch overflow-clip rounded-xl bg-white/60 shadow-lg shadow-gray-300/10 lg:max-w-1/3"
		>
			<span class="w-full rounded-b-xl bg-white p-2 text-center font-bold">Sample Type</span>

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
			class="flex max-w-1/2 flex-col items-stretch overflow-clip rounded-xl bg-white/60 shadow-lg shadow-gray-300/10 lg:max-w-1/3"
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
</Header>

<div class="flex flex-col items-stretch">
	<div class="m-8 grid grid-cols-1 gap-6 sm:grid-cols-2 md:grid-cols-3 xl:col-end-4">
		{#each filteredModels as model}
			<a
				class="flex w-full cursor-pointer flex-col gap-4 rounded-xl p-5 shadow-lg shadow-gray-400/10 duration-100 hover:scale-[102%] hover:opacity-90"
				href={`models/${model.name}`}
				style="background: linear-gradient(45deg, hsl(96 25 45) 0%, hsl(96 30 55) 100%);"
				transition:fade={{ duration: 100 }}
			>
				<span class="text-xl font-semibold text-white">{model.name}</span>
				<div class="mx-[-2px] flex gap-3 text-black">
					{#each model.mainTags as tag}
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

		<NoContent items={filteredModels}>There are no matching models.</NoContent>
	</div>
</div>
