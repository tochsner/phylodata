<script lang="ts">
	let { data } = $props();
	let { PostContent } = $derived(data);
</script>

<div
	class="prose prose-h1:text-4xl prose-h1:text-accent prose-h2:text-2xl prose-h3:text-lg w-full max-w-[800px] rounded-xl bg-white p-6 shadow-lg shadow-gray-400/10"
>
	<PostContent />
</div>
