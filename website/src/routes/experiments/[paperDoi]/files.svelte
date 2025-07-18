<script lang="ts">
	import { Datatable, TableHandler, ThSort } from '@vincjo/datatables';
	import { type File } from '$lib/types';
	import Tag from '$lib/components/tag.svelte';
	import { formatNumber } from '$lib/utils/formatter';
	import Pagination from '$lib/components/pagination.svelte';
	import toast from 'svelte-5-french-toast';

	let { files, minimal }: { files: File[]; minimal?: boolean } = $props();

	const table = new TableHandler(files, {
		rowsPerPage: 10
	});

	const totalSize = files.map((file) => file.sizeBytes).reduce((acc, val) => acc + val, 0);
</script>

<div class="flex flex-col gap-4 p-4">
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
					<th></th>
					<ThSort {table} field="type">Type</ThSort>
					<ThSort {table} field="name">Name</ThSort>
					{#if !minimal}
						<ThSort {table} field="sizeBytes">Size</ThSort>
						<th></th>
					{/if}
				</tr>
			</thead>
			<tbody>
				{#each table.rows as row (row.md5)}
					<tr>
						<td>
							{#if row.type === 'beast2Configuration' || row.type === 'codephyModel' || row.type === 'phyloDataExperiment'}
								<svg
									xmlns="http://www.w3.org/2000/svg"
									fill="none"
									viewBox="0 0 24 24"
									stroke-width="1.5"
									stroke="currentColor"
									class="text-accent mx-auto size-5"
								>
									<path
										stroke-linecap="round"
										stroke-linejoin="round"
										d="M10.5 6h9.75M10.5 6a1.5 1.5 0 1 1-3 0m3 0a1.5 1.5 0 1 0-3 0M3.75 6H7.5m3 12h9.75m-9.75 0a1.5 1.5 0 0 1-3 0m3 0a1.5 1.5 0 0 0-3 0m-3.75 0H7.5m9-6h3.75m-3.75 0a1.5 1.5 0 0 1-3 0m3 0a1.5 1.5 0 0 0-3 0m-9.75 0h9.75"
									/>
								</svg>
							{:else if row.type === 'beast2PosteriorLogs'}
								<svg
									xmlns="http://www.w3.org/2000/svg"
									fill="none"
									viewBox="0 0 24 24"
									stroke-width="1.5"
									stroke="currentColor"
									class="text-accent mx-auto size-5"
								>
									<path
										stroke-linecap="round"
										stroke-linejoin="round"
										d="M3 13.125C3 12.504 3.504 12 4.125 12h2.25c.621 0 1.125.504 1.125 1.125v6.75C7.5 20.496 6.996 21 6.375 21h-2.25A1.125 1.125 0 0 1 3 19.875v-6.75ZM9.75 8.625c0-.621.504-1.125 1.125-1.125h2.25c.621 0 1.125.504 1.125 1.125v11.25c0 .621-.504 1.125-1.125 1.125h-2.25a1.125 1.125 0 0 1-1.125-1.125V8.625ZM16.5 4.125c0-.621.504-1.125 1.125-1.125h2.25C20.496 3 21 3.504 21 4.125v15.75c0 .621-.504 1.125-1.125 1.125h-2.25a1.125 1.125 0 0 1-1.125-1.125V4.125Z"
									/>
								</svg>
							{:else if row.type === 'beast2PosteriorTrees' || row.type === 'summaryTree'}
								<svg
									class="mx-auto size-5"
									viewBox="0 0 16 16"
									fill="none"
									xmlns="http://www.w3.org/2000/svg"
								>
									<path
										d="M8.41598 0.222978C8.37032 0.1545 8.30846 0.0983518 8.23589 0.0595182C8.16332 0.0206846 8.08229 0.000366211 7.99998 0.000366211C7.91768 0.000366211 7.83664 0.0206846 7.76407 0.0595182C7.6915 0.0983518 7.62964 0.1545 7.58398 0.222978L4.58398 4.72298C4.53388 4.79827 4.50513 4.88575 4.50081 4.97609C4.49649 5.06643 4.51675 5.15625 4.55944 5.23598C4.60213 5.31571 4.66565 5.38237 4.74323 5.42886C4.82081 5.47535 4.90954 5.49993 4.99998 5.49998H5.09798L3.07598 8.73498C3.02868 8.81066 3.0025 8.89763 3.00015 8.98685C2.99781 9.07608 3.01939 9.1643 3.06266 9.24236C3.10593 9.32043 3.1693 9.38549 3.2462 9.43079C3.32311 9.47609 3.41073 9.49998 3.49998 9.49998H3.69098L2.05298 12.776C2.01479 12.8522 1.99671 12.9369 2.00049 13.0221C2.00427 13.1073 2.02976 13.1901 2.07455 13.2626C2.11935 13.3352 2.18195 13.3951 2.25641 13.4366C2.33087 13.4782 2.41472 13.5 2.49998 13.5H6.99998V16H8.99998V13.5H13.5C13.5852 13.5 13.6691 13.4782 13.7436 13.4366C13.818 13.3951 13.8806 13.3352 13.9254 13.2626C13.9702 13.1901 13.9957 13.1073 13.9995 13.0221C14.0033 12.9369 13.9852 12.8522 13.947 12.776L12.31 9.49998H12.501C12.5902 9.49998 12.6779 9.47609 12.7548 9.43079C12.8317 9.38549 12.895 9.32043 12.9383 9.24236C12.9816 9.1643 13.0032 9.07608 13.0008 8.98685C12.9985 8.89763 12.9723 8.81066 12.925 8.73498L10.902 5.49998H11C11.0904 5.49993 11.1792 5.47535 11.2567 5.42886C11.3343 5.38237 11.3978 5.31571 11.4405 5.23598C11.4832 5.15625 11.5035 5.06643 11.4992 4.97609C11.4948 4.88575 11.4661 4.79827 11.416 4.72298L8.41598 0.222978ZM6.43698 4.75798C6.39379 4.67989 6.33049 4.61478 6.25365 4.56942C6.1768 4.52405 6.08922 4.50007 5.99998 4.49998H5.93398L7.99998 1.40098L10.066 4.49998H9.99998C9.91073 4.49998 9.82311 4.52387 9.7462 4.56917C9.6693 4.61447 9.60593 4.67953 9.56266 4.75759C9.51939 4.83566 9.49781 4.92388 9.50015 5.0131C9.5025 5.10232 9.52868 5.18929 9.57598 5.26498L11.598 8.49998H11.5C11.4147 8.49998 11.3309 8.52179 11.2564 8.56333C11.182 8.60487 11.1193 8.66476 11.0746 8.73731C11.0298 8.80986 11.0043 8.89266 11.0005 8.97784C10.9967 9.06302 11.0148 9.14775 11.053 9.22398L12.69 12.5H3.30898L4.94698 9.22398C4.98518 9.14775 5.00325 9.06302 4.99948 8.97784C4.9957 8.89266 4.9702 8.80986 4.92541 8.73731C4.88062 8.66476 4.81802 8.60487 4.74356 8.56333C4.66909 8.52179 4.58525 8.49998 4.49998 8.49998H4.40198L6.42398 5.26498C6.47116 5.18931 6.49725 5.1024 6.49954 5.01325C6.50182 4.9241 6.48023 4.83597 6.43698 4.75798Z"
										fill="#54763D"
									/>
								</svg>
							{:else}
								<svg
									xmlns="http://www.w3.org/2000/svg"
									fill="none"
									viewBox="0 0 24 24"
									stroke-width="1.5"
									stroke="currentColor"
									class="text-accent mx-auto size-5"
								>
									<path
										stroke-linecap="round"
										stroke-linejoin="round"
										d="M19.5 14.25v-2.625a3.375 3.375 0 0 0-3.375-3.375h-1.5A1.125 1.125 0 0 1 13.5 7.125v-1.5a3.375 3.375 0 0 0-3.375-3.375H8.25m2.25 0H5.625c-.621 0-1.125.504-1.125 1.125v17.25c0 .621.504 1.125 1.125 1.125h12.75c.621 0 1.125-.504 1.125-1.125V11.25a9 9 0 0 0-9-9Z"
									/>
								</svg>
							{/if}
						</td>

						<td class="font-semibold">
							{#if row.type === 'beast2Configuration'}
								Beast 2 XML Configuration
							{:else if row.type === 'beast2PosteriorLogs'}
								Beast 2 Posterior Logs
							{:else if row.type === 'beast2PosteriorTrees'}
								Beast 2 Posterior Trees
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
