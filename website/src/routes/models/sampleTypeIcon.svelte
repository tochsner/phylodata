<script lang="ts">
	import Info from '$lib/components/info.svelte';
	import CellIcon from '$lib/icons/cellIcon.svelte';
	import GeneIcon from '$lib/icons/geneIcon.svelte';
	import LanguageIcon from '$lib/icons/languageIcon.svelte';
	import PathogenIcon from '$lib/icons/pathogenIcon.svelte';
	import SpeciesIcon from '$lib/icons/speciesIcon.svelte';
	import type { Model } from '$lib/models/models';

	let { type, classes }: { type: Model['sampleTypes'][number]; classes: string } = $props();
</script>

{#if type === 'languages'}
	<Info>
		{#snippet trigger()}
			<LanguageIcon {classes} />
		{/snippet}
		Languages
	</Info>
{:else if type === 'species'}
	<Info>
		{#snippet trigger()}
			<SpeciesIcon {classes} />
		{/snippet}
		Species
	</Info>
{:else if type === 'pathogens'}
	<Info>
		{#snippet trigger()}
			<PathogenIcon {classes} />
		{/snippet}
		Pathogens
	</Info>
{:else if type === 'single-cells'}
	<Info>
		{#snippet trigger()}
			<CellIcon {classes} />
		{/snippet}
		Cells
	</Info>
{/if}
