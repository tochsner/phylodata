<script lang="ts">
	import type { Paper } from '$lib/types';

	let { paper }: { paper: Paper } = $props();
</script>

<div class="flex flex-col gap-4 p-4">
	<h3 class="text-lg font-bold">Paper</h3>

	<div class="flex flex-col gap-1">
		<h4 class="text-accent text-sm font-semibold">Title</h4>
		<span>{paper.title}</span>
	</div>

	<div class="flex flex-col gap-1">
		<h4 class="text-accent text-sm font-semibold">DOI</h4>
		<span>{paper.doi}</span>
	</div>

	<div class="flex flex-col gap-1">
		<h4 class="text-accent text-sm font-semibold">Authors</h4>
		<span>{paper.authors.join(', ')}</span>
	</div>

	<div class="flex flex-col gap-1">
		<h4 class="text-accent text-sm font-semibold">Abstract</h4>
		<span class="whitespace-pre-wrap text-justify">{paper.abstract}</span>
	</div>

	<div class="flex flex-col gap-1">
		<h4 class="text-accent text-sm font-semibold">Year</h4>
		<span>{paper.year}</span>
	</div>
</div>
