<script lang="ts">
	import ClipboardIcon from '$lib/icons/clipboardIcon.svelte';
	import toast from 'svelte-5-french-toast';

	let { code }: { code: string } = $props();
</script>

<div class="bg-background rounded-md p-2">
	<div class="flex items-center gap-2">
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

		<code class="flex-1 font-mono text-sm whitespace-pre-wrap">{code}</code>
	</div>
</div>
