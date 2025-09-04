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
	<h2 class="text-dark flex-1 text-2xl font-bold">PhyloData</h2>
</Header>

<div class="flex flex-col gap-8 p-4 md:p-8">
	<div class="w-full rounded-xl bg-white p-6 shadow-lg shadow-gray-400/10">
		<p class="text-dark/80">
			PhyloData curates real-world <strong>Bayesian phylogenetics experiments</strong>, standardizes
			metadata, and provides simple libraries to find, filter, and reuse data for method development
			and research.
		</p>
	</div>

	<div class="grid grid-cols-1 gap-6 md:grid-cols-2 xl:grid-cols-4">
		<a
			href="/experiments"
			class="flex cursor-pointer flex-col rounded-xl bg-white p-5 shadow-lg shadow-gray-400/10 duration-100 hover:scale-[101%]"
		>
			<h3 class="text-accent pb-2 text-[1.2rem] font-bold">Experiments</h3>
			<p class="text-dark/70">
				Browse curated papers and download experiments with trees, logs, and configurations.
			</p>
		</a>

		<a
			href="/models"
			class="flex cursor-pointer flex-col rounded-xl bg-white p-5 shadow-lg shadow-gray-400/10 duration-100 hover:scale-[101%]"
		>
			<h3 class="text-accent pb-2 text-[1.2rem] font-bold">Models</h3>
			<p class="text-dark/70">Discover evolutionary and inference models for your data.</p>
		</a>

		<a
			href="/docs/python_first_steps"
			class="flex cursor-pointer flex-col rounded-xl bg-white p-5 shadow-lg shadow-gray-400/10 duration-100 hover:scale-[101%]"
		>
			<h3 class="text-accent pb-2 text-[1.2rem] font-bold">Python library</h3>
			<p class="text-dark/70">Install the package and learn how to load experiments.</p>
		</a>

		<a
			href="/docs/java_first_steps"
			class="flex cursor-pointer flex-col rounded-xl bg-white p-5 shadow-lg shadow-gray-400/10 duration-100 hover:scale-[101%]"
		>
			<h3 class="text-accent pb-2 text-[1.2rem] font-bold">Java library</h3>
			<p class="text-dark/70">Add the dependency and access standardized experiment data.</p>
		</a>
	</div>
</div>
