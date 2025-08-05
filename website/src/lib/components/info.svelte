<script lang="ts">
	import InfoIcon from '$lib/icons/infoIcon.svelte';
	import type { Snippet } from 'svelte';

	let {
		children,
		classes = 'size-5 text-accent/70',
		trigger
	}: { children: Snippet; classes?: string | (string | boolean)[]; trigger?: Snippet } = $props();

	let popup = $state<HTMLElement>();

	let x = $state();
	let y = $state();
	let scrollY = $state(0);
	let scrollX = $state(0);

	function mouseOver(event: MouseEvent) {
		popup?.showPopover();
		x = event.pageX - scrollX + 10;
		y = event.pageY - scrollY + 20;
	}
	function mouseMove(event: MouseEvent) {
		x = event.pageX - scrollX + 10;
		y = event.pageY - scrollY + 20;
	}
	function mouseLeave(event: MouseEvent) {
		popup?.hidePopover();
	}
</script>

<button
	popovertarget="info"
	data-trigger="hover"
	onmouseover={mouseOver}
	onmousemove={mouseMove}
	onmouseleave={mouseLeave}
	onfocus={() => {}}
>
	{#if trigger}
		{@render trigger()}
	{:else}
		<InfoIcon {classes} />
	{/if}
</button>

<div
	bind:this={popup}
	popover
	id="info"
	style="top: {y}px; left: {x}px;"
	class="asbolute overflow-visible bg-transparent"
>
	<div
		class="mr-[50%] ml-[-50%] rounded-xl border border-gray-300/50 bg-white/60 p-2 shadow-lg shadow-gray-400/20 backdrop-blur-lg"
	>
		{@render children()}
	</div>
</div>

<svelte:window bind:scrollY bind:scrollX />
