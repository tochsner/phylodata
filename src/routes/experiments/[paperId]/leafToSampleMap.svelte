<script lang="ts">
	import { Datatable, TableHandler, ThSort } from '@vincjo/datatables';
	import Pagination from '$lib/components/pagination.svelte';

	let { leafToSampleMap }: { leafToSampleMap: Record<string, string> } = $props();

	const table = new TableHandler(
		Object.entries(leafToSampleMap).map(([k, v]) => ({
			leaf: k,
			sample: v
		})),
		{
			rowsPerPage: 10
		}
	);
</script>

<div class="flex flex-col gap-4 p-4">
	<h3 class="text-lg font-bold">Leaf-to-Sample Mapping</h3>

	<Datatable {table}>
		<table>
			<thead>
				<tr>
					<ThSort {table} field="leaf">Leaf Name</ThSort>
					<ThSort {table} field="sample">Sample</ThSort>
				</tr>
			</thead>
			<tbody>
				{#each table.rows as row (row.leaf)}
					<tr>
						<td>{row.leaf}</td>
						<td>{row.sample}</td>
					</tr>
				{/each}
			</tbody>
		</table>
	</Datatable>

	<Pagination {table} />
</div>
