<script lang="ts">
	import type { Experiment } from '$lib/types';

	let { experiment }: { experiment: Experiment } = $props();
</script>

{#if experiment.title || experiment.description}
	<div class="flex flex-col gap-4 p-4">
		<h3 class="text-lg font-bold">Experiment</h3>

		{#if experiment.title}
			<div class="flex flex-col gap-1">
				<h4 class="text-accent text-sm font-semibold">Title</h4>
				<span>{experiment.title}</span>
			</div>
		{/if}

		{#if experiment.description}
			<div class="flex flex-col gap-1">
				<h4 class="text-accent text-sm font-semibold">Description</h4>
				<span>{experiment.description}</span>
			</div>
		{/if}
	</div>
{/if}
