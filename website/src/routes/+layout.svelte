<script lang="ts">
	import Footer from '$lib/components/footer.svelte';
	import { onMount } from 'svelte';
	import '../app.css';
	import toast, { Toaster } from 'svelte-5-french-toast';

	let { children } = $props();

	onMount(() => {
		toast(
			'This website is in development. Not every feature will work. The datasets are non-complete and intended for testing purposes.',
			{
				duration: 5000
			}
		);
	});
</script>

<Toaster />

<div class="bg-background font-display text-dark min-h-lvh w-lvw">
	{@render children()}
	<Footer />
</div>
