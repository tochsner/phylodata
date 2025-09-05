<script lang="ts">
	import { goto } from '$app/navigation';
	import Header from '$lib/components/header.svelte';
	import MenuIcon from '$lib/icons/menuIcon.svelte';

	let { children, data } = $props();
	const { meta } = $derived(data);

	type NavigationItem = {
		label: string;
		items: {
			label: string;
			url: string;
		}[];
	};

	let mobileNavigation = $state<HTMLDetailsElement>();

	const sidebar = [
		{
			label: 'General',
			items: [
				{
					label: 'Core principles',
					url: 'principles'
				}
			]
		},
		{
			label: 'Java Library',
			items: [
				{
					label: 'First steps',
					url: 'java_first_steps'
				},
				{
					label: 'Loading experiments',
					url: 'java_downloading_experiments'
				},
				{
					label: 'Accessing files',
					url: 'java_files'
				},
				{
					label: 'Dealing with large files',
					url: 'java_large_files'
				},
				{
					label: 'Accessing metadata',
					url: 'java_metadata'
				},
				{
					label: 'Citing experiments',
					url: 'java_citations'
				},
				{
					label: 'Best practices',
					url: 'java_best_practices'
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
				},
				{
					label: 'Citing experiments',
					url: 'python_citations'
				},
				{
					label: 'Best practices',
					url: 'python_best_practices'
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

<div class="flex flex-col gap-4 px-0 py-8 md:flex-row md:px-8">
	{@render navigation()}

	<div class="flex min-w-0 flex-1 justify-center">
		{@render children()}
	</div>
</div>

{#snippet navigation()}
	<div class="hidden min-w-[210px] flex-col gap-8 md:flex">
		{@render links(sidebar)}
	</div>

	<details class="flex flex-col gap-3 px-6 md:hidden" bind:this={mobileNavigation}>
		<summary class="text-accent flex items-center gap-2 font-semibold">
			<MenuIcon />
			Navigation
		</summary>

		<div class="flex flex-col gap-5">
			{@render links(sidebar)}
		</div>
	</details>
{/snippet}

{#snippet links(items: NavigationItem[])}
	{#each items as item}
		<div class="flex flex-col gap-2">
			<span class="font-semibold">{item.label}</span>

			<div class="border-light-gray flex flex-col border-l pl-2">
				{#each item.items as subitem}
					<button
						onclick={() => {
							mobileNavigation?.removeAttribute('open');
							goto(subitem.url);
						}}
						class={[
							'cursor-pointer rounded-md px-2 py-1 text-left',
							subitem.url === meta.slug && 'text-accent font-semibold',
							subitem.url !== meta.slug && 'hover:text-accent'
						]}
					>
						{subitem.label}
					</button>
				{/each}
			</div>
		</div>
	{/each}
{/snippet}
