<script>
	let { children } = $props();
</script>

<div class="bg-accent-light flex flex-col rounded-b-3xl">
	<div class="flex w-full items-center justify-between px-8 py-6">
		<!-- Logo -->
		<div class="flex items-center space-x-8">
			<h1 class="text-4xl font-bold text-gray-800">
				<span class="text-accent">Phylo</span><span>Data</span>
			</h1>

			<span class="text-accent/20 text-4xl font-extralight">/</span>

			<!-- Navigation -->
			<nav class="flex space-x-8">
				<a href="/experiments" class="font-semibold hover:opacity-70">Experiments</a>
				<a href="/models" class="font-semibold hover:opacity-70">Models</a>
			</nav>
		</div>

		<!-- Right side buttons -->
		<div class="flex items-center space-x-8">
			<a
				href="/new-experiment"
				class="bg-accent flex cursor-pointer items-center space-x-2 rounded-md px-5 py-3 font-semibold text-white hover:opacity-70"
			>
				<svg
					xmlns="http://www.w3.org/2000/svg"
					fill="none"
					viewBox="0 0 24 24"
					stroke-width="1.5"
					stroke="currentColor"
					class="size-6"
				>
					<path
						stroke-linecap="round"
						stroke-linejoin="round"
						d="M12 9v6m3-3H9m12 0a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z"
					/>
				</svg>

				<span>Add experiment</span>
			</a>
		</div>
	</div>

	<div class="bg-accent/20 h-[1px] w-full"></div>

	<div class="px-8 py-6">
		{@render children()}
	</div>
</div>
