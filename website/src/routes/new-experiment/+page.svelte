<script lang="ts">
	import { enhance } from '$app/forms';
	import Header from '$lib/components/header.svelte';
	import Spinner from '$lib/components/spinner.svelte';
	import { convertSchemaToType, type PaperWithExperiments } from '$lib/types';
	import { retrieveJSON } from '$lib/zip';
	import type { PageProps } from './$types';
	import EvolutionaryModels from '../experiments/[paperId]/evolutionaryModels.svelte';
	import Files from '../experiments/[paperId]/files.svelte';
	import Samples from '../experiments/[paperId]/samples.svelte';
	import Trees from '../experiments/[paperId]/trees.svelte';
	import { uploadToWasabi } from '$lib/wasabiClient';
	import toast from 'svelte-5-french-toast';

	let currentStep = $state(1);
	let files: File[] = $state([]);

	let uploadedObject = $state<PaperWithExperiments>();

	let isProcessing = $state(false);

	function handleFileSelect(e: Event) {
		const fileList = (<HTMLInputElement>e.target)?.files || [];
		files = Array.from(fileList);
	}

	async function uploadFiles() {
		try {
			const json = await retrieveJSON(files[0]);
			uploadedObject = convertSchemaToType(json);

			goToStep(4);
		} catch {
			toast.error('The ZIP file does not contain a valid JSON schema.');
		}
	}

	function goToStep(step: number) {
		currentStep = step;
	}
</script>

<Header><h2 class="text-dark text-2xl font-bold">Add experiment</h2></Header>

<div class="container mx-auto max-w-4xl px-4 py-8">
	<div class="mb-8 rounded-lg bg-white p-6 shadow-md">
		<div class="mb-8 flex">
			<div class="flex-1">
				<div class="relative">
					<div class="-mx-full absolute top-5 right-0 left-0 h-[2px] bg-gray-200">
						<div
							class={`bg-accent h-full ${currentStep >= 1 ? 'w-full' : 'w-0'} transition-all duration-500`}
						></div>
					</div>
					<div
						class={`relative z-10 mx-auto flex h-10 w-10 items-center justify-center rounded-full ${currentStep >= 1 ? 'bg-accent text-white' : 'bg-gray-200'}`}
					>
						1
					</div>
					<div class="mt-2 text-center">Prepare</div>
				</div>
			</div>
			<div class="flex-1">
				<div class="relative">
					<div class="-mx-full absolute top-5 right-0 left-0 h-[2px] bg-gray-200">
						<div
							class={`bg-accent h-full ${currentStep >= 2 ? 'w-full' : 'w-0'} transition-all duration-500`}
						></div>
					</div>
					<div
						class={`relative z-10 mx-auto flex h-10 w-10 items-center justify-center rounded-full ${currentStep >= 2 ? 'bg-accent text-white' : 'bg-gray-200'}`}
					>
						2
					</div>
					<div class="mt-2 text-center">Process Experiment</div>
				</div>
			</div>
			<div class="flex-1">
				<div class="relative">
					<div class="-mx-full absolute top-5 right-0 left-0 h-[2px] bg-gray-200">
						<div
							class={`bg-accent h-full ${currentStep >= 3 ? 'w-full' : 'w-0'} transition-all duration-500`}
						></div>
					</div>
					<div
						class={`relative z-10 mx-auto flex h-10 w-10 items-center justify-center rounded-full ${currentStep >= 3 ? 'bg-accent text-white' : 'bg-gray-200'}`}
					>
						3
					</div>
					<div class="mt-2 text-center">Upload</div>
				</div>
			</div>
			<div class="flex-1">
				<div class="relative">
					<div class="-mx-full absolute top-5 right-0 left-0 h-[2px] bg-gray-200">
						<div
							class={`bg-accent h-full ${currentStep >= 4 ? 'w-full' : 'w-0'} transition-all duration-500`}
						></div>
					</div>
					<div
						class={`relative z-10 mx-auto flex h-10 w-10 items-center justify-center rounded-full ${currentStep >= 3 ? 'bg-accent text-white' : 'bg-gray-200'}`}
					>
						4
					</div>
					<div class="mt-2 text-center">Validation</div>
				</div>
			</div>
		</div>

		{#if currentStep === 1}
			<div class="step-content mb-6">
				<h4 class="mb-3 text-lg font-medium">Step 1: Install the PhyloData CLI</h4>
				<p class="mb-4">
					Install the PhyloData CLI tool which will help you process your experiment files.
				</p>

				<div class="mb-4 rounded-md bg-gray-50 p-4">
					<div class="flex items-center justify-between">
						<code class="font-mono text-sm">pip install phylodata</code>
						<button
							class="cursor-pointer p-1 text-gray-500 hover:text-gray-700"
							onclick={() => {
								navigator.clipboard.writeText('pip install phylodata');
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
					</div>
				</div>

				<p class="mb-6 text-sm text-gray-600">Make sure you have Python 3.10 or newer installed.</p>

				<button
					class="bg-accent cursor-pointer rounded-md px-6 py-3 font-medium text-white transition-opacity hover:opacity-90"
					onclick={() => goToStep(2)}
				>
					Continue
				</button>
			</div>
		{:else if currentStep === 2}
			<div class="step-content mb-6">
				<h4 class="mb-3 text-lg font-medium">Step 2: Process experiment files</h4>
				<p class="mb-4">
					Navigate to the directory with your experiment filee and run the following command:
				</p>

				<div class="mb-4 rounded-md bg-gray-50 p-4">
					<div class="flex items-center justify-between">
						<code class="font-mono text-sm">phylodata process</code>
						<button
							class="cursor-pointer p-1 text-gray-500 hover:text-gray-700"
							onclick={() => {
								navigator.clipboard.writeText('phylodata process');
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
					</div>
				</div>

				<p class="mb-6">
					The tool will guide you through the process and create a new folder with all necessary
					files including a JSON metadata file.
				</p>

				<div class="flex space-x-4">
					<button
						class="cursor-pointer rounded-md border border-gray-300 px-6 py-3 font-medium text-gray-700 transition-colors hover:bg-gray-50"
						onclick={() => goToStep(1)}
					>
						Back
					</button>
					<button
						class="bg-accent cursor-pointer rounded-md px-6 py-3 font-medium text-white transition-opacity hover:opacity-90"
						onclick={() => goToStep(3)}
					>
						Continue
					</button>
				</div>
			</div>
		{:else if currentStep === 3}
			<div class="step-content">
				<h4 class="mb-3 text-lg font-medium">Step 3: Upload Experiment Files</h4>
				<p class="mb-4">Zip the folder created by the CLI tool and upload it here:</p>

				<div class="mb-6 rounded-lg border-2 border-dashed border-gray-300 p-8 text-center">
					<input
						type="file"
						id="file-upload"
						class="hidden"
						accept=".zip"
						onchange={handleFileSelect}
					/>
					<label for="file-upload" class="block cursor-pointer">
						<div class="mx-auto mb-4">
							<svg
								xmlns="http://www.w3.org/2000/svg"
								fill="none"
								viewBox="0 0 24 24"
								stroke-width="1.5"
								stroke="currentColor"
								class="mx-auto h-12 w-12 text-gray-400"
							>
								<path
									stroke-linecap="round"
									stroke-linejoin="round"
									d="M3 16.5v2.25A2.25 2.25 0 0 0 5.25 21h13.5A2.25 2.25 0 0 0 21 18.75V16.5m-13.5-9L12 3m0 0 4.5 4.5M12 3v13.5"
								/>
							</svg>
						</div>
						<p class="mb-2 text-sm text-gray-600">Click to select zip file or drag and drop</p>
						<p class="text-xs text-gray-500">ZIP files only</p>
					</label>
				</div>

				{#if files.length > 0}
					<div class="mb-6">
						<h5 class="mb-2 font-medium">Selected Files:</h5>
						<ul class="text-sm">
							{#each files as file}
								<li class="mb-2 flex justify-between rounded bg-gray-50 px-3 py-2">
									<span>{file.name}</span>
									<span class="text-gray-500">{(file.size / 1024 / 1024).toFixed(2)} MB</span>
								</li>
							{/each}
						</ul>
					</div>
				{/if}

				<div class="flex space-x-4">
					<button
						class="cursor-pointer rounded-md border border-gray-300 px-6 py-3 font-medium text-gray-700 transition-colors hover:bg-gray-50"
						onclick={() => goToStep(2)}
					>
						Back
					</button>
					<button
						class="bg-accent cursor-pointer rounded-md px-6 py-3 font-medium text-white transition-opacity hover:opacity-90 disabled:opacity-50"
						onclick={() => {
							uploadFiles();
						}}
						disabled={files.length === 0}
					>
						Continue
					</button>
				</div>
			</div>
		{:else if currentStep === 4}
			<div class="step-content mb-6">
				<h4 class="mb-3 text-lg font-medium">Step 4: Validate experiment</h4>
				<p class="mb-4">Check the following data and make sure it is correct.</p>

				{#if uploadedObject}
					<div class="divide-background my-6 flex flex-col divide-y divide-solid text-sm">
						<Files files={uploadedObject.experiments[0].files} />
						<Samples samples={uploadedObject.experiments[0].samples} />
						<Trees experiment={uploadedObject.experiments[0]} />
						<EvolutionaryModels
							evolutionaryModels={uploadedObject.experiments[0].evolutionaryModels}
						/>
					</div>
				{/if}

				<div class="mb-6 rounded-lg border border-gray-100 bg-gray-50 p-4">
					<h5 class="mb-2 font-medium text-gray-700">If you find an error:</h5>
					<ol class="list-decimal pl-5 text-sm text-gray-600">
						<li class="mb-2">Open the JSON file in the created folder.</li>
						<li class="mb-2">Make any necessary corrections.</li>
						<li class="mb-2">
							Validate if the JSON file is valid using <code class="font-mono text-sm"
								>phylodata validate /path/to/file.json</code
							>.
						</li>
						<li class="mb-2">Create a new zip file of the folder.</li>
					</ol>
					<button
						class="rounded-md border border-gray-300 px-6 py-3 font-medium text-gray-700 transition-colors hover:bg-gray-50"
						onclick={() => {
							files = [];
							goToStep(3);
						}}
					>
						Upload the ZIP file again
					</button>
				</div>

				<form
					class="flex space-x-4"
					method="POST"
					enctype="multipart/form-data"
					use:enhance={({ formData }) => {
						formData.append('paperData', JSON.stringify(uploadedObject));
						isProcessing = true;
						return async ({ update, result }) => {
							if (result.type !== 'success') {
								isProcessing = false;
								toast.error('Upload failed. Try again.');
								return await update();
							}

							const uploadUrl = result.data?.uploadUrl as string;
							if (uploadUrl) {
								try {
									uploadToWasabi(files[0], uploadUrl);
								} catch {
									toast.error('Upload failed. Try again.');
								}
							}

							isProcessing = false;
							goToStep(5);
							await update();
						};
					}}
				>
					<button
						class="cursor-pointer rounded-md border border-gray-300 px-6 py-3 font-medium text-gray-700 transition-colors hover:bg-gray-50"
						onclick={() => goToStep(3)}
					>
						Back
					</button>
					<button
						class="bg-accent flex cursor-pointer items-center gap-2 rounded-md px-6 py-3 font-medium text-white transition-opacity hover:opacity-90"
						type="submit"
						disabled={isProcessing}
					>
						Add experiment
						{#if isProcessing}
							<Spinner />
						{/if}
					</button>
				</form>
			</div>
		{:else if currentStep === 5}
			<div class="step-content mb-6">
				<h4 class="mb-3 text-lg font-medium">Successfully added experiment!</h4>
				<p class="mb-4">
					Congratulations! Check out <a href="/experiments" class="text-accent underline"
						>all existing experiments</a
					>.
				</p>
			</div>
		{/if}
	</div>
</div>
