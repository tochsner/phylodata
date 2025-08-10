<script lang="ts">
	import { Datatable, TableHandler, Th, ThSort } from '@vincjo/datatables';
	import { type Sample } from '$lib/types';
	import Tag from '$lib/components/tag.svelte';
	import { formatNumber } from '$lib/utils/formatter';
	import Pagination from '$lib/components/pagination.svelte';
	import { getMainClassifications } from '$lib/classifications';
	import toast from 'svelte-5-french-toast';
	import GeneIcon from '$lib/icons/geneIcon.svelte';
	import LanguageIcon from '$lib/icons/languageIcon.svelte';
	import FileIcon from '$lib/icons/fileIcon.svelte';
	import Info from '$lib/components/info.svelte';

	let { samples }: { samples: Sample[] } = $props();

	const table = new TableHandler(samples, {
		rowsPerPage: 10
	});

	const mainClassifications = $derived(getMainClassifications(samples));
</script>

<div class="flex flex-col gap-5 p-5">
	<h3 class="text-lg font-bold">Samples</h3>

	<div class="flex flex-wrap items-start gap-2">
		<Tag label="Number of samples">{formatNumber(samples.length)}</Tag>

		{#each mainClassifications as classification}
			<Tag label="Contains">{classification}</Tag>
		{/each}
	</div>

	<Datatable {table}>
		<table>
			<thead>
				<tr>
					<ThSort {table} field="scientificName">Name</ThSort>
					<Th>Data</Th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				{#each table.rows as row (row.sampleId)}
					<tr>
						<td>
							<div class="flex items-center gap-4">
								{#if row.type === 'species' || row.type === 'cells'}
									<GeneIcon classes="size-5 text-accent" />
								{:else if row.type === 'language'}
									<LanguageIcon classes="size-5 text-accent" />
								{:else}
									<FileIcon classes="size-5 text-accent" />
								{/if}

								<div class="flex flex-1 flex-col gap-1">
									<span class="flex items-center font-semibold capitalize">
										{row.scientificName}
										{#if row.commonName && row.commonName !== row.scientificName}
											(<span class="italic">{row.commonName}</span>)
										{/if}

										{#if 0 < row.classification.length}
											<Info classes="text-accent size-5 ml-2">
												<div class="flex flex-col items-center gap-1 font-normal">
													{#each row.classification as classification}
														<span>{classification.scientificName}</span>
													{/each}
												</div>
											</Info>
										{/if}
									</span>
									<span class="flex items-center gap-1">
										{row.sampleId}
										<Info classes="text-dark/50 size-4">This is the BEAST2 taxon ID.</Info>
									</span>
								</div>
							</div>
						</td>

						<td>
							<div class="flex flex-col gap-2">
								{#each row.sampleData as data, idx (idx)}
									{#if data.type === 'dna'}
										<span>DNA ({formatNumber(data.length)}nt)</span>
									{:else if data.type === 'rna'}
										<span>RNA ({formatNumber(data.length)}nt)</span>
									{:else if data.type === 'aminoAcids'}
										<span>Amino Acids ({formatNumber(data.length)}aa)</span>
									{:else if data.type === 'phasedDiploidDna'}
										<span>Phased Diploid DNA ({formatNumber(data.length)}nt)</span>
									{:else if data.type === 'traits'}
										<span>Traits ({formatNumber(data.length)})</span>
									{:else if data.type === 'unknown'}
										<span>Traits ({formatNumber(data.length)})</span>
									{/if}
								{/each}
							</div>
						</td>

						<td align="right">
							<div class="flex flex-col gap-1">
								{#each row.sampleData as data, idx (idx)}
									<div class="flex justify-end gap-2">
										{#if (data.type === 'dna' || data.type === 'rna' || data.type === 'aminoAcids') && row.classification.at(0)?.classificationId}
											<a
												aria-label="download"
												class="text-accent cursor-pointer p-2 font-semibold"
												href={`https://www.ncbi.nlm.nih.gov/Taxonomy/Browser/wwwtax.cgi?mode=Info&id=${row.classification.at(0)?.classificationId}`}
												target="_blank"
											>
												NCBI
											</a>
										{/if}

										{#if row.type === 'language' && row.classification.at(0)?.classificationId}
											<a
												aria-label="download"
												class="text-accent cursor-pointer p-2 font-semibold"
												href={`https://glottolog.org/resource/languoid/id/${row.classification.at(0)?.classificationId}`}
												target="_blank"
											>
												GLOTTOLOG
											</a>
										{/if}

										<button
											aria-label="download"
											class="cursor-pointer"
											onclick={() => {
												navigator.clipboard.writeText(data.data);
												toast.success('Copied data to clipboard');
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
										</button>
									</div>
								{/each}
							</div>
						</td>
					</tr>
				{/each}
			</tbody>
		</table>
	</Datatable>

	<Pagination {table} />
</div>
