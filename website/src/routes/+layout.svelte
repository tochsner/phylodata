<script lang="ts">
	import '../app.css';

	let { children } = $props();
</script>

<div class="bg-background font-display text-dark min-h-lvh w-lvw">
	{@render children()}
</div>
