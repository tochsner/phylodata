<script lang="ts">
	let {
		possibleValues,
		searchText = '',
		name,
		placeholder = 'Search...',
		submitOnChange = false,
		selectedValues = $bindable()
	}: {
		possibleValues: { label: string; value: string }[];
		name: string;
		searchText?: string;
		submitOnChange?: boolean;
		placeholder?: string;
		selectedValues: { label: string; value: string }[];
	} = $props();

	import { clickOutside } from '$lib/actions/clickOutside';
	import { fade } from 'svelte/transition';

	let input = $state<HTMLInputElement>();
	let isOpen = $state(false);
	let filteredValues = $derived(
		possibleValues.filter((item) => item.label.toLowerCase().includes(searchText.toLowerCase()))
	);

	const openDropdown = () => (isOpen = true);
	const closeDropdown = () => (isOpen = false);

	const selectItem = (item: { label: string; value: string }) => {
		selectedValues = [...selectedValues, item];
		searchText = '';

		closeDropdown();
	};

	const removeItem = (value: string) => {
		selectedValues = selectedValues.filter((v) => v.value !== value);
	};

	const handleKeydown = (e: KeyboardEvent) => {
		if (e.key === 'Escape') {
			closeDropdown();
		} else if (e.key === 'ArrowDown' && isOpen && filteredValues.length > 0) {
			e.preventDefault();
			const firstOption = document.querySelector('.option-item') as HTMLElement;
			if (firstOption) firstOption.focus();
		}
	};

	const submit = (element: HTMLElement) => {
		if (submitOnChange) {
			input?.form?.requestSubmit();
		}

		return {
			destroy: () => {
				if (submitOnChange) {
					input?.form?.requestSubmit();
				}
			}
		};
	};

	const handleOptionKeydown = (e: KeyboardEvent, item: { label: string; value: string }) => {
		if (e.key === 'Enter' || e.key === ' ') {
			e.preventDefault();
			selectItem(item);
		} else if (e.key === 'Escape') {
			closeDropdown();
			input?.focus();
		} else if (e.key === 'ArrowDown') {
			e.preventDefault();
			const currentElement = e.currentTarget as HTMLElement;
			const nextElement = currentElement.nextElementSibling as HTMLElement;
			if (nextElement) nextElement.focus();
		} else if (e.key === 'ArrowUp') {
			e.preventDefault();
			const currentElement = e.currentTarget as HTMLElement;
			const prevElement = currentElement.previousElementSibling as HTMLElement;
			if (prevElement) {
				prevElement.focus();
			} else {
				input?.focus();
			}
		}
	};
</script>

<div class="relative w-full" use:clickOutside={closeDropdown}>
	<div class="relative">
		<input
			type="text"
			bind:this={input}
			bind:value={searchText}
			{placeholder}
			onfocus={openDropdown}
			onkeydown={handleKeydown}
			class="border-accent/80 w-full rounded-md border py-2 pl-3 pr-10 placeholder:italic"
		/>
		<svg
			xmlns="http://www.w3.org/2000/svg"
			fill="none"
			viewBox="0 0 24 24"
			stroke-width="1.5"
			stroke="currentColor"
			class="text-accent pointer-events-none absolute right-2 top-[9px] size-5"
		>
			<path
				stroke-linecap="round"
				stroke-linejoin="round"
				d="m21 21-5.197-5.197m0 0A7.5 7.5 0 1 0 5.196 5.196a7.5 7.5 0 0 0 10.607 10.607Z"
			/>
		</svg>
	</div>

	{#if isOpen}
		<div
			transition:fade={{ duration: 150 }}
			class="absolute z-10 mt-1 max-h-60 w-full overflow-auto rounded-md bg-white py-1 shadow-lg focus:outline-none"
		>
			{#if filteredValues.length === 0}
				<div class="px-4 py-2 text-sm">No samples found.</div>
			{:else}
				{#each filteredValues as item (item.value)}
					<div
						aria-label={item.label}
						role="combobox"
						class="hover:bg-accent-light cursor-pointer px-4 py-2 text-sm"
						tabindex="0"
						onclick={() => selectItem(item)}
						onkeydown={(e) => handleOptionKeydown(e, item)}
					>
						{item.label}
					</div>
				{/each}
			{/if}
		</div>
	{/if}

	{#if selectedValues.length > 0}
		<div class="mt-2 flex flex-wrap gap-2">
			{#each selectedValues as selectedValue}
				<div class="bg-accent-light flex items-center gap-1 rounded-lg py-1 text-sm">
					<span class="flex-1 pl-3">
						{selectedValue.label}
					</span>
					<button
						type="button"
						class="cursor-pointer p-1 pr-3 hover:opacity-60"
						onclick={() => removeItem(selectedValue.value)}
						aria-label={`Remove ${selectedValue.value}}`}
					>
						<svg
							xmlns="http://www.w3.org/2000/svg"
							fill="none"
							viewBox="0 0 24 24"
							stroke-width="1.5"
							stroke="currentColor"
							class="size-5"
						>
							<path
								stroke-linecap="round"
								stroke-linejoin="round"
								d="m14.74 9-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 0 1-2.244 2.077H8.084a2.25 2.25 0 0 1-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 0 0-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 0 1 3.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 0 0-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 0 0-7.5 0"
							/>
						</svg>
					</button>
				</div>
			{/each}
		</div>
	{/if}

	{#each selectedValues as selectedValue (selectedValue.value)}
		<input type="hidden" {name} value={selectedValue.value} use:submit />
	{/each}
</div>
