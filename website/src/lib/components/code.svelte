<script lang="ts">
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
			<svg
				xmlns="http://www.w3.org/2000/svg"
				fill="none"
				viewBox="0 0 24 24"
				stroke-width="1.5"
				stroke="currentColor"
				class="h-5 w-5"
			>
				<path
					stroke-linecap="round"
					stroke-linejoin="round"
					d="M15.666 3.888A2.25 2.25 0 0013.5 2.25h-3c-1.03 0-1.9.693-2.166 1.638m7.332 0c.055.194.084.4.084.612v0a.75.75 0 01-.75.75H9a.75.75 0 01-.75-.75v0c0-.212.03-.418.084-.612m7.332 0c.646.049 1.288.11 1.927.184 1.1.128 1.907 1.077 1.907 2.185V19.5a2.25 2.25 0 01-2.25 2.25H6.75A2.25 2.25 0 014.5 19.5V6.257c0-1.108.806-2.057 1.907-2.185a48.208 48.208 0 011.927-.184"
				/>
			</svg>
		</button>

		<code class="flex-1 font-mono text-sm whitespace-pre-wrap">{code}</code>
	</div>
</div>
