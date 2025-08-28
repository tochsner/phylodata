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

	let { files, minimal }: { files: File[]; minimal?: boolean } = $props();

	const table = new TableHandler(files, {
		rowsPerPage: 10
	});

	const totalSize = files.map((file) => file.sizeBytes).reduce((acc, val) => acc + val, 0);
</script>

<div class="flex flex-col gap-5 p-5">
	<h3 class="text-lg font-bold">Files</h3>

	{#if !minimal}
		<div class="flex flex-wrap items-start gap-2">
			<Tag label="Number of files">{formatNumber(files.length)}</Tag>
			<Tag label="Total size">{formatNumber(totalSize)} bytes</Tag>
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
							<div class="flex gap-4 font-semibold">
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
						<td>{row.name}</td>

						{#if !minimal}
							<td class="text-nowrap">{formatNumber(row.sizeBytes)} bytes</td>
							<td align="right">
								<button
									aria-label="download"
									class="text-accent flex cursor-pointer gap-1 p-2 font-semibold"
									onclick={() => {
										navigator.clipboard.writeText(row.md5);
										toast.success('Copied MD5 hash to clipboard');
									}}
								>
									<svg
										width="20"
										height="20"
										viewBox="0 0 20 20"
										fill="none"
										xmlns="http://www.w3.org/2000/svg"
									>
										<g clip-path="url(#clip0_73_21411)">
											<path
												d="M13.055 3.24C12.9439 2.84692 12.7075 2.50087 12.3816 2.25449C12.0558 2.0081 11.6585 1.87485 11.25 1.875H8.75C7.89167 1.875 7.16667 2.4525 6.945 3.24M13.055 3.24C13.1008 3.40167 13.125 3.57333 13.125 3.75C13.125 3.91576 13.0592 4.07473 12.9419 4.19194C12.8247 4.30915 12.6658 4.375 12.5 4.375H7.5C7.33424 4.375 7.17527 4.30915 7.05806 4.19194C6.94085 4.07473 6.875 3.91576 6.875 3.75C6.875 3.57333 6.9 3.40167 6.945 3.24M13.055 3.24C13.5933 3.28083 14.1283 3.33167 14.6608 3.39333C15.5775 3.5 16.25 4.29083 16.25 5.21417V16.25C16.25 16.7473 16.0525 17.2242 15.7008 17.5758C15.3492 17.9275 14.8723 18.125 14.375 18.125H5.625C5.12772 18.125 4.65081 17.9275 4.29917 17.5758C3.94754 17.2242 3.75 16.7473 3.75 16.25V5.21417C3.75 4.29083 4.42167 3.5 5.33917 3.39333C5.87336 3.33148 6.40875 3.28036 6.945 3.24"
												stroke="#54763D"
												stroke-width="1.5"
												stroke-linecap="round"
												stroke-linejoin="round"
											/>
										</g>
										<defs>
											<clipPath id="clip0_73_21411">
												<rect width="20" height="20" fill="white" />
											</clipPath>
										</defs>
									</svg>
									MD5
								</button>
							</td>
						{/if}
					</tr>
				{/each}
			</tbody>
		</table>
	</Datatable>

	<Pagination {table} />
</div>
