<script lang="ts">
	import { fade } from 'svelte/transition';
	import Code from './code.svelte';
	import Checkbox from './checkbox.svelte';

	let {
		experimentsToDownload = $bindable()
	}: { experimentsToDownload: [string, number][] | undefined } = $props();

	let downloadSpecificTypes = $state(false);
	let configurationFiles = $state(true);
	let posteriorTrees = $state(true);
	let logFiles = $state(true);
	let summaryTrees = $state(true);
	let others = $state(true);

	let downloadPreview = $state(false);

	let code = $derived.by(() => {
		const imports = [];
		let functionName;
		const attributes = [];

		if (experimentsToDownload?.length == 1) {
			imports.push('load_experiment');
			functionName = 'experiment = load_experiment';
			attributes.push(`"${experimentsToDownload[0][0]}"`);
			attributes.push(`version=${experimentsToDownload[0][1]}`);
		} else {
			imports.push('load_experiments');
			functionName = 'experiments = load_experiments';
			imports.push('ExperimentToLoad');
			attributes.push(
				'[\n' +
					experimentsToDownload
						?.map((exp) => `\t\tExperimentToLoad("${exp[0]}", version=${exp[1]})`)
						.join(',\n') +
					'\n\t]'
			);
		}

		if (downloadSpecificTypes) {
			imports.push('FileType');

			const fileTypes = [];

			if (configurationFiles) fileTypes.push('FileType.BEAST2_CONFIGURATION');
			if (posteriorTrees) fileTypes.push('FileType.BEAST2_POSTERIOR_TREES');
			if (logFiles) fileTypes.push('FileType.BEAST2_POSTERIOR_LOGS');
			if (summaryTrees) fileTypes.push('FileType.SUMMARY_TREE');
			if (others) fileTypes.push('FileType.UNKNOWN');

			attributes.push('files_to_download=[\n\t\t' + fileTypes.join(',\n\t\t') + '\n\t]');
		}

		if (downloadPreview) {
			attributes.push('download_only_preview=True');
		}

		let code = 'from phylodata import ';
		code += imports.join(', ');
		code += `\n${functionName}(\n\t`;
		code += attributes.join(',\n\t') + ',';
		code += '\n)';

		return code;
	});
</script>

{#if experimentsToDownload}
	<div
		class="fixed inset-0 z-10 flex items-center justify-center bg-black/50"
		transition:fade={{ duration: 100 }}
	>
		<dialog
			open
			class="max-h-4/5 max-w-4/5 relative flex overflow-y-scroll rounded-2xl bg-white shadow-xl"
		>
			<button
				onclick={() => (experimentsToDownload = undefined)}
				aria-label="Close dialog"
				class="absolute right-0 top-0 cursor-pointer p-2 opacity-80 hover:opacity-40"
			>
				<svg
					xmlns="http://www.w3.org/2000/svg"
					fill="none"
					viewBox="0 0 24 24"
					stroke-width="1.5"
					stroke="currentColor"
					class="text-dark size-8"
				>
					<path stroke-linecap="round" stroke-linejoin="round" d="M6 18 18 6M6 6l12 12" />
				</svg>
			</button>

			<div class="flex flex-1 flex-col gap-8 p-8">
				<h2 class="text-2xl font-bold">
					Use
					{#if experimentsToDownload.length == 1}
						Experiment
					{:else}
						{experimentsToDownload.length} Experiments
					{/if}
				</h2>

				<div class="flex flex-col gap-2">
					<Checkbox name="only_specific_types" bind:checked={downloadSpecificTypes}>
						Only download specific files
					</Checkbox>
					<span class="ml-8 text-sm opacity-70">
						Check this if you only need certain file types.
					</span>

					{#if downloadSpecificTypes}
						<div class="ml-8 flex flex-col gap-1">
							<Checkbox name="BEAST2 configuration files" bind:checked={configurationFiles}>
								BEAST 2 configuration files
							</Checkbox>
							<Checkbox name="MCMC posterior trees" bind:checked={posteriorTrees}>
								MCMC posterior trees
							</Checkbox>
							<Checkbox name="BEAST2 log files" bind:checked={logFiles}>MCMC log files</Checkbox>
							<Checkbox name="Summary tree" bind:checked={summaryTrees}>Summary trees</Checkbox>
							<Checkbox name="Other" bind:checked={others}>Others</Checkbox>
						</div>
					{/if}
				</div>

				<div class="flex flex-col gap-2">
					<Checkbox name="download_previews" bind:checked={downloadPreview}>
						Only download previews
					</Checkbox>
					<span class="ml-8 text-sm opacity-70">
						Check this if you want to download subsampled versions of posterior tree and log files.
						This is useful to test your code locally without having to deal with large datasets.
					</span>
				</div>
			</div>

			<div class="bg-accent-light flex-2 flex flex-col gap-8 overflow-x-scroll rounded-2xl p-8">
				<div class="flex flex-col gap-2">
					<h3 class="font-bold">1. Install the PhyloData library</h3>
					<Code code="pip install phylodata --upgrade" />
					<p class="text-sm opacity-70">Make sure you have Python 3.10 or newer installed.</p>
				</div>

				<div class="flex flex-col gap-2">
					<h3 class="font-bold">2. Run the following python commands</h3>
					<Code {code} />
					<p class="text-sm opacity-70">
						This will download the experiment if needed and gives you easy access to all the
						information about it.
						<a class="text-accent underline" href="/"> Learn more about the library... </a>
					</p>
					<p class="text-sm opacity-70">
						You should put this script into a git repository for reproducability.
						<a class="text-accent underline" href="/"> Learn more about reproducability... </a>
					</p>
				</div>
			</div>
		</dialog>
	</div>
{/if}
