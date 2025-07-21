<script lang="ts">
	import Header from '$lib/components/header.svelte';
	import Tag from '$lib/components/tag.svelte';
	import { MODELS } from '$lib/models/models';
	import { titleCase } from '$lib/utils/titleCase';

	const filteredModels = MODELS;

	const getRandomGradient = (seed: string) => {
		const hash = Array.from(seed).reduce((acc, char) => acc + char.charCodeAt(0), 0);
		const hue = hash % 360;

		// This curve reduces saturation for visually intense hues like green
		const baseSaturation = 50 - 20 * Math.cos(((hue - 120) * Math.PI) / 180);

		return 'white';

		return `linear-gradient(45deg, hsl(${hue} ${baseSaturation + 5} 50) 0%, hsl(${hue} ${baseSaturation} 50) 100%)`;
	};
</script>

<Header><h2 class="text-dark text-2xl font-bold">Models</h2></Header>

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
