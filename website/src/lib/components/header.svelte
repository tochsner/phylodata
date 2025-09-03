<script>
	let { children } = $props();
</script>

<div class="bg-accent-light flex flex-col items-stretch rounded-b-3xl">
	<div class="flex w-full items-center justify-between px-8 py-6">
		<!-- Logo -->
		<div class="flex flex-1 flex-col gap-2 space-x-8 sm:flex-row sm:items-center">
			<h1 class="text-[2rem] font-bold text-gray-800">
				<span class="text-accent">Phylo</span><span>Data</span>
			</h1>

			<span class="text-accent/20 hidden text-4xl font-extralight sm:block">/</span>

			<!-- Navigation -->
			<nav class="flex flex-col space-x-8 sm:flex-row">
				<a href="/experiments" class="cursor-pointer font-semibold hover:opacity-70">Experiments</a>
				<a href="/models" class="cursor-pointer font-semibold hover:opacity-70">Models</a>
				<a href="/docs/python_first_steps" class="cursor-pointer font-semibold hover:opacity-70"
					>Documentation</a
				>
			</nav>
		</div>

		<!-- Right side buttons -->
		<div class="hidden items-center space-x-8 lg:flex">
			<a
				href="/new-experiment"
				class="bg-accent flex cursor-pointer items-center space-x-2 rounded-full px-5 py-3 font-semibold text-white duration-100 hover:scale-[102%] hover:opacity-80"
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

	<div class="bg-accent/10 h-[1px] w-full"></div>

	<div class="px-8 py-6">
		{@render children()}
	</div>
</div>
