<script lang="ts">
	import Footer from '$lib/components/footer.svelte';
	import { onMount, mount } from 'svelte';
	import '../app.css';
	import toast, { Toaster } from 'svelte-5-french-toast';
	import { afterNavigate } from '$app/navigation';
	import CopyButton from '$lib/components/copyButton.svelte';

	let { children } = $props();

	onMount(() => {
		toast(
			'This website is in development. Not every feature will work. The datasets are non-complete and intended for testing purposes.',
			{
				duration: 5000
			}
		);
	});

	afterNavigate(() => {
		for (const node of document.querySelectorAll('pre.language-python')) {
			console.log(node);
			mount(CopyButton, {
				target: node,
				props: {
					code: node.textContent ?? ''
				}
			});
		}
	});
</script>

<Toaster />

<div class="bg-background font-display text-dark min-h-lvh w-lvw">
	{@render children()}
	<Footer />
</div>
