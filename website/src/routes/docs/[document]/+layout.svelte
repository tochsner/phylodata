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
					label: 'Core Principles',
					url: 'principles'
				}
			]
		},
		{
			label: 'Python Library',
			items: [
				{
					label: 'First steps',
					url: 'python_first_steps'
				},
				{
					label: 'Loading experiments',
					url: 'python_downloading_experiments'
				},
				{
					label: 'Accessing files',
					url: 'python_files'
				},
				{
					label: 'Dealing with large files',
					url: 'python_large_files'
				},
				{
					label: 'Accessing metadata',
					url: 'python_metadata'
				},
				{
					label: 'Uploading experiments',
					url: 'python_uploading_experiments'
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
	<div class="flex flex-col gap-8">
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
