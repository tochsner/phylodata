<script lang="ts">
	import Header from '$lib/components/header.svelte';
</script>

<svelte:head>
	<title>PhyloData – Bayesian Phylogenetics Experiments and Tools</title>
	<meta
		name="description"
		content="Discover curated Bayesian phylogenetics experiments, browse models, and get started with the PhyloData Python and Java libraries."
	/>
	<meta name="robots" content="index,follow" />
</svelte:head>

<Header>
	<div class="mx-auto w-full max-w-[600px] p-8">
		<p class="text-dark text-center text-lg font-bold italic leading-relaxed md:text-3xl">
			Discover <span class="text-accent">curated Bayesian phylogenetics experiments</span>
			and use them in your research
		</p>
	</div>
</Header>

<div class="flex flex-col items-center gap-8 p-4 md:p-8">
	<div class="grid max-w-[800px] grid-cols-1 gap-6 md:grid-cols-2 xl:grid-cols-4">
		<a
			href="/experiments"
			class="flex w-full cursor-pointer flex-col gap-4 rounded-xl p-5 shadow-lg shadow-gray-400/10 duration-100 hover:scale-[102%] hover:opacity-90"
			style="background: linear-gradient(45deg, hsl(96 25 45) 0%, hsl(96 30 55) 100%);"
		>
			<h3 class="flex items-center gap-2 text-xl font-bold text-white">
				<svg
					xmlns="http://www.w3.org/2000/svg"
					width="24"
					height="24"
					viewBox="0 0 24 24"
					fill="none"
					stroke="currentColor"
					stroke-width="2"
					stroke-linecap="round"
					stroke-linejoin="round"
					class="size-6"
					><path stroke="none" d="M0 0h24v24H0z" fill="none" /><path
						d="M6 20a2 2 0 1 0 -4 0a2 2 0 0 0 4 0z"
					/><path d="M16 4a2 2 0 1 0 -4 0a2 2 0 0 0 4 0z" /><path
						d="M16 20a2 2 0 1 0 -4 0a2 2 0 0 0 4 0z"
					/><path d="M11 12a2 2 0 1 0 -4 0a2 2 0 0 0 4 0z" /><path
						d="M21 12a2 2 0 1 0 -4 0a2 2 0 0 0 4 0z"
					/><path d="M5.058 18.306l2.88 -4.606" /><path d="M10.061 10.303l2.877 -4.604" /><path
						d="M10.065 13.705l2.876 4.6"
					/><path d="M15.063 5.7l2.881 4.61" /></svg
				>
				Experiments
			</h3>
			<p class="text-white">Browse and discover experiments from published papers.</p>
		</a>

		<a
			href="/models"
			class="flex w-full cursor-pointer flex-col gap-4 rounded-xl p-5 shadow-lg shadow-gray-400/10 duration-100 hover:scale-[102%] hover:opacity-90"
			style="background: linear-gradient(45deg, hsl(96 25 45) 0%, hsl(96 30 55) 100%);"
		>
			<h3 class="flex items-center gap-2 text-xl font-bold text-white">
				<svg
					xmlns="http://www.w3.org/2000/svg"
					width="24"
					height="24"
					viewBox="0 0 24 24"
					fill="none"
					stroke="currentColor"
					stroke-width="2"
					stroke-linecap="round"
					stroke-linejoin="round"
					class="icon icon-tabler icons-tabler-outline icon-tabler-blocks"
					><path stroke="none" d="M0 0h24v24H0z" fill="none" /><path
						d="M14 4a1 1 0 0 1 1 -1h5a1 1 0 0 1 1 1v5a1 1 0 0 1 -1 1h-5a1 1 0 0 1 -1 -1z"
					/><path
						d="M3 14h12a2 2 0 0 1 2 2v3a2 2 0 0 1 -2 2h-10a2 2 0 0 1 -2 -2v-10a2 2 0 0 1 2 -2h3a2 2 0 0 1 2 2v12"
					/></svg
				>
				Models
			</h3>
			<p class="text-white">Discover BEAST 2 packages for your data and use case.</p>
		</a>

		<a
			href="/docs/java_first_steps"
			class="flex w-full cursor-pointer flex-col gap-4 rounded-xl p-5 shadow-lg shadow-gray-400/10 duration-100 hover:scale-[102%] hover:opacity-90"
			style="background: linear-gradient(45deg, hsl(96 25 45) 0%, hsl(96 30 55) 100%);"
		>
			<h3 class="flex items-center gap-2 text-xl font-bold text-white">
				<svg
					xmlns="http://www.w3.org/2000/svg"
					width="24"
					height="24"
					viewBox="0 0 24 24"
					fill="none"
					stroke="currentColor"
					stroke-width="2"
					stroke-linecap="round"
					stroke-linejoin="round"
					class="icon icon-tabler icons-tabler-outline icon-tabler-code"
					><path stroke="none" d="M0 0h24v24H0z" fill="none" /><path d="M7 8l-4 4l4 4" /><path
						d="M17 8l4 4l-4 4"
					/><path d="M14 4l-4 16" /></svg
				>

				Java library
			</h3>
			<p class="text-white">Use the experiments in your research with our Java library.</p>
		</a>

		<a
			href="/docs/python_first_steps"
			class="flex w-full cursor-pointer flex-col gap-4 rounded-xl p-5 shadow-lg shadow-gray-400/10 duration-100 hover:scale-[102%] hover:opacity-90"
			style="background: linear-gradient(45deg, hsl(96 25 45) 0%, hsl(96 30 55) 100%);"
		>
			<h3 class="flex items-center gap-2 text-xl font-bold text-white">
				<svg
					xmlns="http://www.w3.org/2000/svg"
					width="24"
					height="24"
					viewBox="0 0 24 24"
					fill="none"
					stroke="currentColor"
					stroke-width="2"
					stroke-linecap="round"
					stroke-linejoin="round"
					class="icon icon-tabler icons-tabler-outline icon-tabler-brand-python"
					><path stroke="none" d="M0 0h24v24H0z" fill="none" /><path
						d="M12 9h-7a2 2 0 0 0 -2 2v4a2 2 0 0 0 2 2h3"
					/><path d="M12 15h7a2 2 0 0 0 2 -2v-4a2 2 0 0 0 -2 -2h-3" /><path
						d="M8 9v-4a2 2 0 0 1 2 -2h4a2 2 0 0 1 2 2v5a2 2 0 0 1 -2 2h-4a2 2 0 0 0 -2 2v5a2 2 0 0 0 2 2h4a2 2 0 0 0 2 -2v-4"
					/><path d="M11 6l0 .01" /><path d="M13 18l0 .01" /></svg
				>
				Python library
			</h3>
			<p class="text-white">Use the experiments in your research with our Python library.</p>
		</a>
	</div>
</div>
