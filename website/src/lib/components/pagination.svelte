<script lang="ts">
	let { table } = $props();
</script>

{#if 1 < table.pages.length}
	<aside class="flex flex-wrap justify-center gap-2">
		{#each table.pages as page (page)}
			<button
				class:active={page === table.currentPage}
				class:cursor-pointer={page !== table.currentPage}
				class:bg-background={page === table.currentPage}
				class="border-background rounded-sm border px-3 py-1"
				onclick={() => table.setPage(page)}
			>
				{page ?? '...'}
			</button>
		{/each}
	</aside>
{/if}
