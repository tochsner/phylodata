<script lang="ts">
	// @ts-expect-error: old JS package
	import { phylotree } from 'phylotree';

	import { type Experiment } from './+page.server';
	import Tag from '$lib/components/tag.svelte';
	import { formatNumber } from '$lib/formatter';
	import { browser } from '$app/environment';

	let { experiment }: { experiment: Experiment } = $props();

	let trees = {
		'CCD0 MAP Tree': experiment.ccd0_map_tree,
		'Hipstr Tree': experiment.hipstr_tree
	};
	let currentTree = $state('CCD0 MAP Tree' as keyof typeof trees);

	let treeContainer = $state<HTMLElement>();

	$effect(() => {
		if (browser && treeContainer) {
			let tree = new phylotree(trees[currentTree]);
			const renderedTree = tree.render({
				height: 500,
				'left-right-spacing': 'fit-to-size',
				'top-bottom-spacing': 'fit-to-size',
				'draw-size-bubbles': false,
				selectable: false,
				collapsible: false
			});

			// eslint-disable-next-line svelte/no-dom-manipulating
			treeContainer.innerHTML = '';
			// eslint-disable-next-line svelte/no-dom-manipulating
			treeContainer.appendChild(renderedTree.show());
		}
	});
</script>

<div class="flex flex-col gap-4 p-4">
	<h3 class="text-lg font-bold">Trees</h3>

	<div class="flex flex-wrap items-start gap-2">
		<Tag label="Number of trees">{formatNumber(experiment.number_of_trees)}</Tag>
		<Tag label="Number of tips">{formatNumber(experiment.number_of_tips)}</Tag>
		<Tag label="Ultrametric">{experiment.ultrametric ? 'Yes' : 'No'}</Tag>
		<Tag label="Rooted">{experiment.rooted ? 'Yes' : 'No'}</Tag>
		<Tag label="Tree ESS">{formatNumber(experiment.tree_ess)}</Tag>
		<Tag label="CCD1 entropy">{formatNumber(experiment.ccd1_entropy)}</Tag>
	</div>

	<div class="bg-background flex gap-2 self-start rounded-md p-1">
		{#each Object.keys(trees) as tree (tree)}
			{@const selected = tree === currentTree}

			<button
				class="cursor-pointer rounded-md px-4 py-1"
				class:text-dark={!selected}
				class:opacity-60={!selected}
				class:bg-accent={selected}
				class:text-white={selected}
				aria-label={tree}
				onclick={() => (currentTree = tree as keyof typeof trees)}
			>
				{tree}
			</button>
		{/each}
	</div>

	<div id="a" class="pointer-events-none h-[500px] w-full" bind:this={treeContainer}></div>
</div>
