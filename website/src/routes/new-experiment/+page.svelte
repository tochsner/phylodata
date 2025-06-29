<script lang="ts">
	import Header from '$lib/components/header.svelte';
	import { supabase } from '$lib/supabaseClient';
	import { convertSchemaToType } from '$lib/types';
	import { retrieveJSON } from '$lib/zip';

	let currentStep = 1;
	let files: File[] = [];
	let uploading = false;
	let uploadComplete = false;
	let uploadError: string | undefined = undefined;
	let progress = 0;

	function handleFileSelect(e: Event) {
		const fileList = (<HTMLInputElement>e.target)?.files || [];
		files = Array.from(fileList);
	}

	async function uploadFiles() {
		if (files.length === 0) {
			uploadError = 'Please select files to upload';
			return;
		}

		const json = await retrieveJSON(files[0]);
		console.log(json);
		console.log(convertSchemaToType(json));
	}

	function goToStep(step: number) {
		currentStep = step;
	}
</script>

<Header><h2 class="text-dark text-2xl font-bold">Add your experiment</h2></Header>

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
		</div>

		{#if currentStep === 1}
			<div class="step-content mb-6">
				<h4 class="mb-3 text-lg font-medium">Step 1: Install the PhyloData CLI</h4>
				<p class="mb-4">
					First, you need to install the PhyloData CLI tool which will help you process your
					experiment files.
				</p>

				<div class="mb-4 rounded-md bg-gray-50 p-4">
					<div class="flex items-center justify-between">
						<code class="font-mono text-sm">pip install phylodata</code>
						<button
							class="cursor-pointer p-1 text-gray-500 hover:text-gray-700"
							on:click={() => navigator.clipboard.writeText('pip install phylodata')}
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

				<p class="mb-6 text-sm text-gray-600">Make sure you have Python 3.13 installed.</p>

				<button
					class="bg-accent rounded-md px-6 py-3 font-medium text-white transition-opacity hover:opacity-90"
					on:click={() => goToStep(2)}
				>
					Continue to Step 2
				</button>
			</div>
		{:else if currentStep === 2}
			<div class="step-content mb-6">
				<h4 class="mb-3 text-lg font-medium">Step 2: Process experiment files</h4>
				<p class="mb-4">
					Now, navigate to the directory where your experiment files are located and run the
					following command:
				</p>

				<div class="mb-4 rounded-md bg-gray-50 p-4">
					<code class="font-mono text-sm">phylodata process</code>
				</div>

				<p class="mb-6">
					The tool will guide you through the process and create a new folder with all necessary
					files including a JSON metadata file.
				</p>

				<div class="flex space-x-4">
					<button
						class="rounded-md border border-gray-300 px-6 py-3 font-medium text-gray-700 transition-colors hover:bg-gray-50"
						on:click={() => goToStep(1)}
					>
						Back
					</button>
					<button
						class="bg-accent rounded-md px-6 py-3 font-medium text-white transition-opacity hover:opacity-90"
						on:click={() => goToStep(3)}
					>
						Continue to Step 3
					</button>
				</div>
			</div>
		{:else if currentStep === 3}
			<div class="step-content">
				<h4 class="mb-3 text-lg font-medium">Step 3: Upload Experiment Files</h4>
				<p class="mb-4">Finally, zip the folder created by the CLI tool and upload it here:</p>

				{#if !uploadComplete}
					<div class="mb-6 rounded-lg border-2 border-dashed border-gray-300 p-8 text-center">
						<input
							type="file"
							id="file-upload"
							class="hidden"
							accept=".zip"
							on:change={handleFileSelect}
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

					{#if uploading}
						<div class="mb-6">
							<div class="h-2.5 w-full rounded-full bg-gray-200">
								<div class="bg-accent h-2.5 rounded-full" style="width: {progress}%"></div>
							</div>
							<p class="mt-2 text-center text-sm">Uploading... {progress}%</p>
						</div>
					{/if}

					{#if uploadError}
						<div class="mb-6 rounded-md bg-red-50 p-3 text-red-700">
							{uploadError}
						</div>
					{/if}

					<div class="flex space-x-4">
						<button
							class="rounded-md border border-gray-300 px-6 py-3 font-medium text-gray-700 transition-colors hover:bg-gray-50"
							on:click={() => goToStep(2)}
						>
							Back
						</button>
						<button
							class="bg-accent rounded-md px-6 py-3 font-medium text-white transition-opacity hover:opacity-90 disabled:opacity-50"
							on:click={uploadFiles}
							disabled={files.length === 0 || uploading}
						>
							{uploading ? 'Uploading...' : 'Upload Files'}
						</button>
					</div>
				{:else}
					<div class="mb-6 rounded-lg bg-green-50 p-6 text-center">
						<svg
							xmlns="http://www.w3.org/2000/svg"
							fill="none"
							viewBox="0 0 24 24"
							stroke-width="1.5"
							stroke="currentColor"
							class="mx-auto mb-4 h-16 w-16 text-green-500"
						>
							<path
								stroke-linecap="round"
								stroke-linejoin="round"
								d="M9 12.75 11.25 15 15 9.75M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z"
							/>
						</svg>
						<h5 class="mb-2 text-xl font-medium text-green-700">Upload Complete!</h5>
						<p class="mb-4 text-green-600">
							Your experiment files have been successfully uploaded.
						</p>
						<a
							href="/experiments"
							class="bg-accent inline-block rounded-md px-6 py-3 font-medium text-white transition-opacity hover:opacity-90"
						>
							View All Experiments
						</a>
					</div>
				{/if}
			</div>
		{/if}
	</div>
</div>
