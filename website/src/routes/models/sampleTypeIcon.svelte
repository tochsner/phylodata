<script lang="ts">
	import CellIcon from '$lib/icons/cellIcon.svelte';
	import GeneIcon from '$lib/icons/geneIcon.svelte';
	import LanguageIcon from '$lib/icons/languageIcon.svelte';
	import PathogenIcon from '$lib/icons/pathogenIcon.svelte';
	import SpeciesIcon from '$lib/icons/speciesIcon.svelte';
	import type { Model } from '$lib/models/models';

	let { type, classes }: { type: Model['sampleTypes'][number]; classes: string } = $props();
</script>

{#if type === 'languages'}
	<LanguageIcon {classes} />
{:else if type === 'species'}
	<SpeciesIcon {classes} />
{:else if type === 'pathogens'}
	<PathogenIcon {classes} />
{:else if type === 'cells'}
	<CellIcon {classes} />
{:else if type === 'genes'}
	<GeneIcon {classes} />
{/if}
