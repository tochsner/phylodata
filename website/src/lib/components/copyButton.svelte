<script lang="ts">
	import ClipboardIcon from '$lib/icons/clipboardIcon.svelte';
	import toast from 'svelte-5-french-toast';

	let { code }: { code: string } = $props();
</script>

<div class="absolute right-1 top-1 flex size-10 items-center justify-center rounded-md p-2">
	<button
		class="cursor-pointer p-1 text-gray-500 hover:text-gray-700"
		onclick={() => {
			navigator.clipboard.writeText(code);
			toast.success('Copied to clipboard.');
		}}
		aria-label="Copy command to clipboard"
	>
		<ClipboardIcon />
	</button>
</div>
