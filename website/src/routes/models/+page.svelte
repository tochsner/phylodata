<script lang="ts">
	import Header from '$lib/components/header.svelte';
	import { MODELS } from '$lib/models/models';

	const filteredModels = MODELS;

	const getRandomGradient = (seed: string) => {
		const hash = Array.from(seed).reduce((acc, char) => acc + char.charCodeAt(0), 0);
		const hue = hash % 360;

		// This curve reduces saturation for visually intense hues like green
		const baseSaturation = 50 - 20 * Math.cos(((hue - 120) * Math.PI) / 180);

		return `linear-gradient(45deg, hsl(${hue} ${baseSaturation + 5} 55) 0%, hsl(${hue} ${baseSaturation} 50) 100%)`;
	};
</script>

<Header><h2 class="text-dark text-2xl font-bold">Models</h2></Header>

<div class="flex flex-col items-stretch">
	<div class="m-8 grid grid-cols-1 gap-8 sm:grid-cols-2 md:grid-cols-3 xl:col-end-4">
		{#each filteredModels as model}
			<a
				class="flex w-full cursor-pointer flex-col gap-4 rounded-xl p-5 hover:opacity-80"
				style:background={getRandomGradient(model.name)}
				href={`models/${model.name}`}
			>
				<span class="text-xl font-semibold text-white">{model.name}</span>
				<div class="flex flex-wrap gap-3">
					{#each model.mainTags as tag}
						<span class="rounded-full bg-white px-3 py-1 text-xs font-semibold uppercase shadow-sm">
							{tag}
						</span>
					{/each}
				</div>
				<span class="flex-1 text-white">{model.shortDescription}</span>
			</a>
		{/each}
	</div>
</div>
