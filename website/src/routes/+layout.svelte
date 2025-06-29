<script lang="ts">
	import '../app.css';
	import { Toaster } from 'svelte-5-french-toast';

	let { children } = $props();
</script>

<Toaster />

<div class="bg-background font-display text-dark min-h-lvh w-lvw">
	{@render children()}
</div>
