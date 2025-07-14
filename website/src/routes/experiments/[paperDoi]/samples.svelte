<script lang="ts">
	import { Datatable, TableHandler, Th, ThSort } from '@vincjo/datatables';
	import { type Sample } from '$lib/types';
	import Tag from '$lib/components/tag.svelte';
	import { formatNumber } from '$lib/utils/formatter';
	import Pagination from '$lib/components/pagination.svelte';
	import { getMainClassifications } from '$lib/classifications';
	import toast from 'svelte-5-french-toast';

	let { samples }: { samples: Sample[] } = $props();

	const table = new TableHandler(samples, {
		rowsPerPage: 10
	});

	const mainClassifications = $derived(getMainClassifications(samples));
</script>

<div class="flex flex-col gap-4 p-4">
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
					<th></th>
					<ThSort {table} field="scientificName">Name</ThSort>
					<Th>Data</Th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				{#each table.rows as row (row.sampleId)}
					<tr>
						<td>
							{#if row.type === 'species' || row.type === 'cells'}
								<svg
									width="20"
									height="20"
									viewBox="0 0 20 20"
									fill="none"
									xmlns="http://www.w3.org/2000/svg"
								>
									<g clip-path="url(#clip0_73_15661)">
										<g clip-path="url(#clip1_73_15661)">
											<path
												d="M18.1038 7.30722C17.8492 7.30722 17.5879 7.29317 17.3217 7.26486C17.1408 7.24554 17.0099 7.0833 17.0292 6.90241C17.0486 6.7213 17.2139 6.59221 17.3915 6.60977C18.9745 6.77969 20.3257 6.39244 21.1966 5.52178C21.3252 5.39313 21.5336 5.39313 21.6622 5.52178C21.7909 5.65042 21.7909 5.85898 21.6622 5.9874C20.7995 6.85016 19.5549 7.30722 18.1038 7.30722Z"
												fill="#54763D"
											/>
											<path
												d="M7.75519 19.7575C7.67089 19.7575 7.58659 19.7254 7.52227 19.6611C7.39362 19.5325 7.39362 19.3241 7.52227 19.1955C8.76964 17.9479 8.99839 15.8377 8.14969 13.4056C7.27902 10.9091 7.4755 8.64744 8.68885 7.20029C9.78453 5.8932 11.566 5.36391 13.7025 5.70967C13.8821 5.73865 14.0041 5.90791 13.9749 6.08749C13.9458 6.26706 13.7758 6.38737 13.5974 6.35993C11.698 6.05236 10.1342 6.50152 9.19334 7.62333C8.13278 8.88827 7.97911 10.9165 8.77184 13.1884C9.70682 15.8687 9.42121 18.2278 7.98811 19.6609C7.92379 19.7254 7.83949 19.7575 7.75519 19.7575Z"
												fill="#54763D"
											/>
											<path
												d="M11.292 14.1507C10.9118 14.1507 10.516 14.119 10.1068 14.0554C9.92718 14.0273 9.80402 13.8589 9.83212 13.6793C9.86 13.4995 10.029 13.3781 10.2082 13.4045C12.0907 13.6986 13.6408 13.244 14.5727 12.1261C15.6273 10.8608 15.7786 8.83536 14.988 6.56892C14.053 3.88845 14.3387 1.52936 15.7718 0.096484C15.9004 -0.0321613 16.1087 -0.0321613 16.2374 0.096484C16.366 0.225129 16.366 0.433684 16.2374 0.56211C14.99 1.80949 14.7613 3.91962 15.6097 6.35203C16.4786 8.84239 16.285 11.1007 15.0787 12.5481C14.2023 13.599 12.8829 14.1507 11.292 14.1507Z"
												fill="#54763D"
											/>
											<path
												d="M2.32941 14.3322C2.24511 14.3322 2.16081 14.3001 2.09648 14.2358C1.96784 14.1071 1.96784 13.8988 2.09648 13.7702C3.20446 12.6622 4.90912 12.2306 6.89653 12.5548C7.07589 12.5842 7.19773 12.7533 7.16853 12.9329C7.13912 13.1124 6.96942 13.2341 6.7905 13.2049C5.01823 12.916 3.51641 13.2817 2.56233 14.2358C2.49801 14.3001 2.41371 14.3322 2.32941 14.3322Z"
												fill="#54763D"
											/>
											<path
												d="M15.5503 11.3625C15.466 11.3625 15.3817 11.3304 15.3174 11.2661L10.4949 6.44346C10.3663 6.31503 10.3663 6.10626 10.4949 5.97783C10.6236 5.84919 10.8319 5.84919 10.9605 5.97783L15.7832 10.8005C15.9119 10.9291 15.9119 11.1375 15.7832 11.2661C15.7187 11.3302 15.6346 11.3625 15.5503 11.3625Z"
												fill="#54763D"
											/>
											<path
												d="M13.0349 13.8771C12.9506 13.8771 12.8663 13.8451 12.802 13.7808L7.9793 8.95811C7.85065 8.82968 7.85065 8.62091 7.9793 8.49248C8.10794 8.36383 8.31628 8.36383 8.44492 8.49248L13.2676 13.3151C13.3962 13.4438 13.3962 13.6521 13.2676 13.7808C13.2033 13.8449 13.1192 13.8771 13.0349 13.8771Z"
												fill="#54763D"
											/>
											<path
												d="M20.0251 6.97894C19.9408 6.97894 19.8565 6.94689 19.7922 6.88256L14.8738 1.96397C14.7452 1.83554 14.7452 1.62677 14.8738 1.49834C15.0025 1.36969 15.2108 1.36969 15.3395 1.49834L20.2578 6.41694C20.3865 6.54536 20.3865 6.75414 20.2578 6.88256C20.1937 6.94689 20.1094 6.97894 20.0251 6.97894Z"
												fill="#54763D"
											/>
											<path
												d="M8.39644 18.6093C8.31214 18.6093 8.22784 18.5773 8.16352 18.5129L3.24492 13.5943C3.11628 13.4657 3.11628 13.2574 3.24492 13.1287C3.37357 13.0001 3.5819 13.0001 3.71055 13.1287L8.62915 18.0471C8.75779 18.1757 8.75779 18.3841 8.62915 18.5127C8.56482 18.577 8.48074 18.6093 8.39644 18.6093Z"
												fill="#54763D"
											/>
										</g>
									</g>
									<defs>
										<clipPath id="clip0_73_15661">
											<rect width="20" height="20" fill="white" />
										</clipPath>
										<clipPath id="clip1_73_15661">
											<rect width="20" height="20" fill="white" />
										</clipPath>
									</defs>
								</svg>
							{:else if row.type === 'language'}
								<svg
									width="18"
									height="18"
									viewBox="0 0 18 18"
									fill="none"
									xmlns="http://www.w3.org/2000/svg"
								>
									<path
										d="M7.75 16.5L12.125 7.125L16.5 16.5M9 14H15.25M1.5 3.68417C3.15867 3.47777 4.82854 3.37451 6.5 3.375M6.5 3.375C7.43333 3.375 8.36083 3.40667 9.27833 3.47M6.5 3.375V1.5M9.27833 3.47C8.31333 7.88167 5.40833 11.5667 1.5 13.585M9.27833 3.47C10.025 3.52083 10.7658 3.5925 11.5 3.68417M7.67583 10.7633C6.30914 9.37385 5.22594 7.73166 4.48667 5.92833"
										stroke="#54763D"
										stroke-width="1.5"
										stroke-linecap="round"
										stroke-linejoin="round"
									/>
								</svg>
							{:else}
								<svg
									width="15"
									height="18"
									viewBox="0 0 15 18"
									fill="none"
									xmlns="http://www.w3.org/2000/svg"
								>
									<path
										d="M13.5 11V8.8125C13.5 8.06658 13.2037 7.35121 12.6762 6.82376C12.1488 6.29632 11.4334 6 10.6875 6H9.4375C9.18886 6 8.9504 5.90123 8.77459 5.72541C8.59877 5.5496 8.5 5.31114 8.5 5.0625V3.8125C8.5 3.06658 8.20368 2.35121 7.67624 1.82376C7.14879 1.29632 6.43342 1 5.6875 1H4.125M6 1H1.9375C1.42 1 1 1.42 1 1.9375V16.3125C1 16.83 1.42 17.25 1.9375 17.25H12.5625C13.08 17.25 13.5 16.83 13.5 16.3125V8.5C13.5 6.51088 12.7098 4.60322 11.3033 3.1967C9.89678 1.79018 7.98912 1 6 1Z"
										stroke="#54763D"
										stroke-width="1.5"
										stroke-linecap="round"
										stroke-linejoin="round"
									/>
								</svg>
							{/if}
						</td>

						<td>
							<div class="flex flex-col gap-1">
								<span class="font-semibold capitalize">
									{row.scientificName}
									{#if row.commonName && row.commonName !== row.scientificName}
										(<span class="italic">{row.commonName}</span>)
									{/if}
								</span>
								<span>{row.sampleId}</span>
							</div>
						</td>

						<td>
							<div class="flex flex-col gap-1">
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
												NCIB
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
