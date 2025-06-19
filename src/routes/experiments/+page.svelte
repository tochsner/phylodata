<script>
	import Header from '$lib/components/header.svelte';
</script>

<Header>
	<h2 class="text-dark text-2xl font-bold">Experiments</h2>

	<!-- Search bar -->
	<div class="flex items-center space-x-2">
		<div class="relative">
			<input
				type="text"
				placeholder="Search for experiments..."
				class="focus:ring-accent w-80 rounded-md border border-gray-300 bg-white px-4 py-2 placeholder:italic focus:border-transparent focus:ring-2 focus:outline-none"
			/>
		</div>
		<button
			class="bg-accent cursor-pointer rounded-md p-2 text-white hover:opacity-60"
			onclick={() => {}}
			aria-label="Search"
		>
			<svg class="h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
				<path
					stroke-linecap="round"
					stroke-linejoin="round"
					stroke-width="2"
					d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
				></path>
			</svg>
		</button>
	</div>
</Header>
