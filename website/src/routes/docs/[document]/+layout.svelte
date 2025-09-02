<script lang="ts">
	import Header from '$lib/components/header.svelte';

	let { children, data } = $props();
	const { meta } = $derived(data);

	type NavigationItem = {
		label: string;
		items: {
			label: string;
			url: string;
		}[];
	};

	const sidebar = [
		{
			label: 'General',
			items: [
				{
					label: 'Introduction',
					url: 'introduction'
				},
				{
					label: 'Core Principles',
					url: 'principles'
				}
			]
		}
	] as NavigationItem[];
</script>

<svelte:head>
	<title>{meta.title}</title>
	<meta name="description" content={meta.description} />
</svelte:head>

<Header>
	<h2 class="text-dark flex-1 text-2xl font-bold">Documentation</h2>
</Header>

<div class="flex p-8">
	<div class="fixed flex flex-col gap-8">
		{@render links(sidebar)}
	</div>

	<div class="flex flex-1 justify-center">
		{@render children()}
	</div>
</div>

{#snippet links(items: NavigationItem[])}
	{#each items as item}
		<div class="flex flex-col gap-2">
			<span class="font-semibold">{item.label}</span>

			<div class="border-light-gray flex flex-col border-l pl-2">
				{#each item.items as subitem}
					<a
						href={subitem.url}
						class={[
							'rounded-md px-2 py-1',
							subitem.url === meta.slug && 'text-accent font-semibold',
							subitem.url !== meta.slug && 'hover:text-accent'
						]}
					>
						{subitem.label}
					</a>
				{/each}
			</div>
		</div>
	{/each}
{/snippet}
