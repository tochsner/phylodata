<script lang="ts">
	import { enhance } from '$app/forms';
	import Header from '$lib/components/header.svelte';
	import Spinner from '$lib/components/spinner.svelte';
	import { convertSchemasToType, type PaperWithExperiments } from '$lib/types';
	import { retrieveMetadata } from '$lib/retrieveMetadata';
	import EvolutionaryModels from '../experiments/[paperDoi]/evolutionaryModels.svelte';
	import Files from '../experiments/[paperDoi]/files.svelte';
	import Samples from '../experiments/[paperDoi]/samples.svelte';
	import Trees from '../experiments/[paperDoi]/trees.svelte';
	import { uploadToWasabi } from '$lib/storage/wasabiClient';
	import toast from 'svelte-5-french-toast';
	import Paper from './paper.svelte';
	import Code from '$lib/components/code.svelte';

	let currentStep = $state(1);
	let files: File[] = $state([]);

	let uploadedObject = $state<PaperWithExperiments>();

	let isProcessing = $state(false);

	function handleFileSelect(e: Event) {
		const fileList = (<HTMLInputElement>e.target)?.files || [];
		files = Array.from(fileList);
	}

	async function selectFiles() {
		try {
			const metadata = await retrieveMetadata(files);

			if (!metadata) {
				toast.error('The files do not contain valid JSON metadata files.');
				return;
			}

			const { editableMetadata, nonEditableMetadata } = metadata;
			uploadedObject = convertSchemasToType(editableMetadata, nonEditableMetadata);

			goToStep(4);
		} catch {}
	}

	function goToStep(step: number) {
		currentStep = step;
	}
</script>

<svelte:head>
	<title>New Experiment – PhyloData</title>
	<meta name="description" content="Submit your experiment to PhyloData." />
</svelte:head>

<Header><h2 class="text-dark text-2xl font-bold">Add experiment</h2></Header>

<div class="container mx-auto max-w-4xl px-4 py-8">
	<div class="mb-8 rounded-2xl bg-white p-6 shadow-lg shadow-gray-400/10">
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
					Install the PhyloData library which will help you process your experiment files.
				</p>

				<Code code="pip install phylodata --upgrade" />

				<p class="my-6 text-sm text-gray-600">Make sure you have Python 3.10 or newer installed.</p>

				<button
					class="bg-accent cursor-pointer rounded-md px-6 py-3 font-medium text-white transition-opacity duration-100 hover:scale-[102%] hover:opacity-80"
					onclick={() => goToStep(2)}
				>
					Continue
				</button>
			</div>
		{:else if currentStep === 2}
			<div class="step-content mb-6">
				<h4 class="mb-3 text-lg font-medium">Step 2: Process experiment files</h4>
				<p class="mb-4">
					Navigate to the directory with your experiment files and run the following command:
				</p>

				<Code code="phylodata process" />

				<p class="my-6">
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
						class="bg-accent cursor-pointer rounded-md px-6 py-3 font-medium text-white transition-opacity duration-100 hover:scale-[102%] hover:opacity-80"
						onclick={() => goToStep(3)}
					>
						Continue
					</button>
				</div>
			</div>
		{:else if currentStep === 3}
			<div class="step-content">
				<h4 class="mb-3 text-lg font-medium">Step 3: Upload Experiment Files</h4>
				<p class="mb-4">Upload the files in the created folder:</p>

				<div class="mb-6 rounded-lg border-2 border-dashed border-gray-300 text-center">
					<input type="file" id="file-upload" class="hidden" multiple onchange={handleFileSelect} />
					<label for="file-upload" class="block size-full cursor-pointer p-8">
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
						<p class="mb-2 text-sm text-gray-600">Click to select the files</p>
					</label>
				</div>

				{#if files.length > 0}
					<div class="mb-6">
						<h5 class="mb-2 font-medium">Selected Files:</h5>
						<ul class="text-sm">
							{#each files as file}
								<li class="mb-2 flex justify-between rounded bg-gray-50 px-3 py-2">
									<span>{file.name}</span>
									<span class="text-gray-500">{(file.size / 1000 / 1000).toFixed(2)} MB</span>
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
						class="bg-accent cursor-pointer rounded-md px-6 py-3 font-medium text-white transition-opacity duration-100 hover:scale-[102%] hover:opacity-80 disabled:opacity-50"
						onclick={() => {
							selectFiles();
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
					<div class="divide-background -m-4 my-6 flex flex-col divide-y divide-solid text-sm">
						<Paper paper={uploadedObject.paper} />
						<Files files={uploadedObject.experiments[0].files} minimal />
						<Samples samples={uploadedObject.experiments[0].samples} />
						<Trees trees={uploadedObject.experiments[0].trees} />
						<EvolutionaryModels
							evolutionaryModels={uploadedObject.experiments[0].evolutionaryModel}
						/>
					</div>
				{/if}

				<div class="mb-6 rounded-lg border border-gray-100 bg-gray-50 p-4">
					<h5 class="mb-2 font-medium text-gray-700">If you find an error:</h5>
					<ol class="list-decimal pl-5 text-sm text-gray-600">
						<li class="mb-2">
							Open the <code class="font-mono text-sm">editable_phylodata_metadata.json</code> file in
							the created folder.
						</li>
						<li class="mb-2">Make any necessary corrections.</li>
						<li class="mb-2">
							Validate if the JSON file is valid using <code class="font-mono text-sm">
								phylodata validate /path/to/editable_phylodata_metadata.json
							</code>.
						</li>
						<li class="mb-2">Upload the files again.</li>
					</ol>
					<button
						class="rounded-md border border-gray-300 px-6 py-3 font-medium text-gray-700 transition-colors hover:bg-gray-50"
						onclick={() => {
							files = [];
							goToStep(3);
						}}
					>
						Upload files again
					</button>
				</div>

				<form
					class="flex space-x-4"
					method="POST"
					use:enhance={({ formData }) => {
						formData.append('paperData', JSON.stringify(uploadedObject));
						formData.append('fileNames', JSON.stringify(files.map((file) => file.name)));
						isProcessing = true;

						return async ({ update, result }) => {
							if (result.type !== 'success') {
								isProcessing = false;
								toast.error('Upload failed. Try again.');
								return await update();
							}

							const uploadUrls = result.data?.uploadUrls as string[];
							if (uploadUrls) {
								try {
									Promise.all(
										files.map(async (file, idx) => await uploadToWasabi(file, uploadUrls[idx]))
									);
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
						class="bg-accent flex cursor-pointer items-center gap-2 rounded-md px-6 py-3 font-medium text-white transition-opacity duration-100 hover:scale-[102%] hover:opacity-80"
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
					Congratulations! Check out <a href="/experiments" class="text-accent underline">
						all existing experiments
					</a>.
				</p>
			</div>
		{/if}
	</div>
</div>
