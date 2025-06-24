<script lang="ts">
	import type { Snippet } from 'svelte';

	let {
		label,
		children,
		class: classes
	}: { label: string; children: Snippet; class?: string } = $props();
</script>

<div class={`bg-light-gray flex gap-2 rounded-full px-3 py-1 text-xs ${classes}`}>
	<span class=" uppercase">{label}</span>
	<span class=" font-bold">{@render children()}</span>
</div>
