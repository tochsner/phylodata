<script lang="ts">
	import Header from '$lib/components/header.svelte';
	import Tag from '$lib/components/tag.svelte';
	import { MODELS, type Model } from '$lib/models/models';
	import { titleCase } from '$lib/utils/titleCase';

	const getRandomGradient = (seed: string) => {
		const hash = Array.from(seed).reduce((acc, char) => acc + char.charCodeAt(0), 0);
		const hue = hash % 360;

		// This curve reduces saturation for visually intense hues like green
		const baseSaturation = 50 - 20 * Math.cos(((hue - 120) * Math.PI) / 180);

		return 'white';

		return `linear-gradient(45deg, hsl(${hue} ${baseSaturation + 5} 50) 0%, hsl(${hue} ${baseSaturation} 50) 100%)`;
	};

	const sampleTypes: { value: Model['sampleTypes'][number]; label: string }[] = [
		{ value: 'species', label: 'Species' },
		{ value: 'cells', label: 'Single cells' },
		{ value: 'pathogens', label: 'Pathogens' },
		{ value: 'genes', label: 'Genes' },
		{ value: 'languages', label: 'Languages' }
	];
	const dataTypes: { value: Model['dataTypes'][number]; label: string }[] = [
		{ value: 'nucleotides', label: 'Nucleotides' },
		{ value: 'proteins', label: 'Proteins' },
		{ value: 'snps', label: 'SNPs' },
		{ value: 'traits', label: 'Traits' }
	];

	let selectedSampleTypes = $state<Model['sampleTypes']>([]);
	let selectedDataTypes = $state<Model['dataTypes']>([]);

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

<Header>
	<h2 class="text-dark text-2xl font-bold">Models</h2>
	<h3 class="text-dark text-center text-lg font-semibold">Discover models for your data</h3>

	<div class="flex justify-center gap-4 pt-4">
		<div class="max-w-1/3 items-stretch rounded-xl bg-white/50 p-4">
			<span class="font-semibold">Sample Type</span>

			<div class="mt-3 grid grid-cols-1 gap-2 md:grid-cols-2">
				{#each sampleTypes as type}
					<button
						class="border-accent text-accent cursor-pointer rounded-lg border px-3 py-1 duration-100"
						class:bg-white={!selectedSampleTypes.includes(type.value)}
						class:text-accent={!selectedSampleTypes.includes(type.value)}
						class:bg-accent={selectedSampleTypes.includes(type.value)}
						class:text-white={selectedSampleTypes.includes(type.value)}
						onclick={() => {
							if (selectedSampleTypes.includes(type.value))
								selectedSampleTypes = selectedSampleTypes.filter((x) => x != type.value);
							else selectedSampleTypes = [...selectedSampleTypes, type.value];
						}}
					>
						{type.label}
					</button>
				{/each}
			</div>
		</div>

		<div class="max-w-1/3 items-stretch rounded-xl bg-white/50 p-4">
			<span class="font-semibold">Data Type</span>

			<div class="mt-2 grid grid-cols-1 gap-2 md:grid-cols-2">
				{#each dataTypes as type}
					<button
						class="border-accent text-accent cursor-pointer rounded-lg border px-3 py-1 duration-100"
						class:bg-white={!selectedDataTypes.includes(type.value)}
						class:text-accent={!selectedDataTypes.includes(type.value)}
						class:bg-accent={selectedDataTypes.includes(type.value)}
						class:text-white={selectedDataTypes.includes(type.value)}
						onclick={() => {
							if (selectedDataTypes.includes(type.value))
								selectedDataTypes = selectedDataTypes.filter((x) => x != type.value);
							else selectedDataTypes = [...selectedDataTypes, type.value];
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
	<div class="m-8 grid grid-cols-1 gap-8 sm:grid-cols-2 md:grid-cols-3 xl:col-end-4">
		{#each filteredModels as model}
			<a
				class="flex w-full cursor-pointer flex-col gap-4 rounded-xl p-5 shadow-lg shadow-gray-400/10 hover:opacity-80"
				style:background={getRandomGradient(model.name)}
				href={`models/${model.name}`}
			>
				<span class="text-accent text-xl font-semibold">{model.name}</span>
				<div class="mx-[-2px] flex gap-3">
					{#each model.mainTags as tag}
						<Tag>
							{titleCase(tag)}
						</Tag>
					{/each}
				</div>
				<span class="line-clamp-2 h-12 text-ellipsis">{model.shortDescription}</span>
			</a>
		{/each}
	</div>
</div>
