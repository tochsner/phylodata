<script lang="ts">
	import { type EvolutionaryModel } from './+page.server';

	let { evolutionaryModels }: { evolutionaryModels: EvolutionaryModel[] } = $props();
</script>

<div class="flex flex-col gap-4 p-4">
	<h3 class="text-lg font-bold">Evolutionary Models</h3>

	<div class="divide-background grid grid-cols-2 gap-x-4 gap-y-8">
		{#each evolutionaryModels as model (model.id)}
			<div class="flex items-center gap-3">
				<span class="font-semibold">{model.name}</span>
				<span
					class="rounded-full px-3 py-1 text-sm"
					class:bg-substitution-model={model.model_type === 'substitutionModel'}
					class:bg-clock-model={model.model_type === 'clockModel'}
					class:bg-tree-prior={model.model_type === 'treePrior'}
					class:bg-tree-likelihood={model.model_type === 'treeLikelihood'}
					class:bg-light-gray={model.model_type === 'other'}
				>
					{#if model.model_type === 'substitutionModel'}Substitution Model{/if}
					{#if model.model_type === 'clockModel'}Clock Model{/if}
					{#if model.model_type === 'treePrior'}Tree Prior{/if}
					{#if model.model_type === 'treeLikelihood'}Tree Likelihood{/if}
					{#if model.model_type === 'other'}Other{/if}
				</span>

				<span class="flex-1"></span>

				<a
					class="text-accent cursor-pointer"
					aria-label="go to definition"
					href={model.documentation_url}
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
							d="M17.25 6.75 22.5 12l-5.25 5.25m-10.5 0L1.5 12l5.25-5.25m7.5-3-4.5 16.5"
						/>
					</svg>
				</a>
				<a
					class="text-accent cursor-pointer"
					aria-label="go to documentation"
					href={model.documentation_url}
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
							d="m11.25 11.25.041-.02a.75.75 0 0 1 1.063.852l-.708 2.836a.75.75 0 0 0 1.063.853l.041-.021M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0Zm-9-3.75h.008v.008H12V8.25Z"
						/>
					</svg>
				</a>
			</div>
		{/each}
	</div>
</div>
