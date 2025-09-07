<script lang="ts">
	import { Datatable, TableHandler, ThSort } from '@vincjo/datatables';
	import { type File } from '$lib/types';
	import Tag from '$lib/components/tag.svelte';
	import { formatNumber } from '$lib/utils/formatter';
	import Pagination from '$lib/components/pagination.svelte';
	import toast from 'svelte-5-french-toast';
	import BEASTXmlIcon from '$lib/icons/beastXMLIcon.svelte';
	import MetadataIcon from '$lib/icons/metadataIcon.svelte';
	import LogsIcon from '$lib/icons/logsIcon.svelte';
	import TreesIcon from '$lib/icons/treesIcon.svelte';
	import FileIcon from '$lib/icons/fileIcon.svelte';
	import Info from '$lib/components/info.svelte';
	import { formatFileSize } from '$lib/utils/fileSizeFormatter';

	let {
		files,
		minimal,
		experimentId
	}: { files: File[]; minimal?: boolean; experimentId?: string } = $props();

	const table = $derived(
		new TableHandler(files, {
			rowsPerPage: 10
		})
	);

	const totalSize = files.map((file) => file.sizeBytes).reduce((acc, val) => acc + val, 0);

	async function openBeast2Configuration(name: string) {
		const presignedFileUrl = await (
			await fetch(`/api/getDownloadLink/${experimentId}/${name}`)
		).text();
		window.open(presignedFileUrl, '_blank');
	}
	async function openSummaryTree(name: string) {
		const presignedFileUrl = await (
			await fetch(`/api/getDownloadLink/${experimentId}/${name}`)
		).text();
		const icyTreeUrl = `https://icytree.org/?url=${presignedFileUrl}`;
		window.open(icyTreeUrl, '_blank');
	}
</script>

<div class="flex flex-col gap-5 p-5">
	<h3 class="text-lg font-bold">Files</h3>

	{#if !minimal}
		<div class="flex flex-wrap items-start gap-2">
			<Tag label="Number of files">{formatNumber(files.length)}</Tag>
			<Tag label="Total size">{formatFileSize(totalSize)}</Tag>
		</div>
	{/if}

	<Datatable {table}>
		<table>
			<thead>
				<tr>
					<ThSort {table} field="type">Type</ThSort>
					<ThSort {table} field="name">Name</ThSort>
					{#if !minimal}
						<ThSort {table} field="sizeBytes">Size</ThSort>
						<th></th>
					{/if}
				</tr>
			</thead>
			<tbody>
				{#each table.rows as row (row.name)}
					<tr>
						<td>
							<div class="flex gap-4 whitespace-nowrap font-semibold">
								{#if row.type === 'beast2Configuration'}
									<BEASTXmlIcon classes="text-accent size-5" />
								{:else if row.type === 'codephyModel' || row.type === 'phyloDataExperiment'}
									<MetadataIcon classes="text-accent size-5" />
								{:else if row.type === 'beast2PosteriorLogs'}
									<LogsIcon classes="text-accent size-5" />
								{:else if row.type === 'posteriorTrees' || row.type === 'summaryTree'}
									<TreesIcon classes="text-accent size-5" />
								{:else}
									<FileIcon classes="text-accent size-5" />
								{/if}

								{#if row.type === 'beast2Configuration'}
									BEAST 2 XML Configuration
								{:else if row.type === 'beast2PosteriorLogs'}
									BEAST 2 Posterior Logs
								{:else if row.type === 'posteriorTrees'}
									BEAST 2 Posterior Trees
								{:else if row.type === 'codephyModel'}
									Codephy Model
								{:else if row.type === 'summaryTree'}
									Summary Tree
								{:else if row.type === 'phyloDataExperiment'}
									PhyloData Experiment
								{:else}
									Other
								{/if}

								{#if row.isPreview}
									(Preview)
								{/if}

								<Info>
									<div class="font-normal">
										{#if row.type === 'beast2Configuration'}
											This file contains the BEAST 2 model specified using XML.
										{:else if row.type === 'beast2PosteriorLogs'}
											This file contains the BEAST 2 posterior logs.
										{:else if row.type === 'posteriorTrees'}
											This file contains the BEAST 2 posterior trees.
										{:else if row.type === 'codephyModel'}
											This file contains a Codephy model description.
										{:else if row.type === 'summaryTree'}
											This file contains a computed summary tree.
										{:else if row.type === 'phyloDataExperiment'}
											This file contains the PhyloData experiment data.
										{:else}
											This file contains some unspecified data.
										{/if}
									</div>
								</Info>
							</div>
						</td>

						<td><span class="wrap-anywhere">{row.name}</span></td>

						{#if !minimal}
							<td class="text-nowrap">{formatFileSize(row.sizeBytes)}</td>
							<td align="right">
								{#if experimentId && (row.type === 'beast2Configuration' || row.type === 'summaryTree')}
									<button
										aria-label="copy-md5-shash"
										class="text-accent flex cursor-pointer gap-1 p-2 font-semibold"
										onclick={() => {
											if (row.type === 'beast2Configuration') {
												openBeast2Configuration(row.name);
											} else if (row.type === 'summaryTree') {
												openSummaryTree(row.name);
											}
										}}
									>
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
											class="icon icon-tabler icons-tabler-outline icon-tabler-eye-search"
											><path stroke="none" d="M0 0h24v24H0z" fill="none" /><path
												d="M10 12a2 2 0 1 0 4 0a2 2 0 0 0 -4 0"
											/><path
												d="M12 18c-.328 0 -.652 -.017 -.97 -.05c-3.172 -.332 -5.85 -2.315 -8.03 -5.95c2.4 -4 5.4 -6 9 -6c3.465 0 6.374 1.853 8.727 5.558"
											/><path d="M18 18m-3 0a3 3 0 1 0 6 0a3 3 0 1 0 -6 0" /><path
												d="M20.2 20.2l1.8 1.8"
											/>
										</svg>
									</button>
								{/if}
							</td>
						{/if}
					</tr>
				{/each}
			</tbody>
		</table>
	</Datatable>

	<Pagination {table} />
</div>
