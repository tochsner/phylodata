import { mdsvex } from 'mdsvex';
import { preprocessMeltUI, sequence } from '@melt-ui/pp';
import adapter from '@sveltejs/adapter-auto';
import { vitePreprocess } from '@sveltejs/vite-plugin-svelte';
import hint from 'remark-hint';

/** @type {import('@sveltejs/kit').Config}*/
const config = {
	preprocess: [
		mdsvex({
			extensions: ['.md'],
			remarkPlugins: [hint]
		}),
		sequence([vitePreprocess(), preprocessMeltUI()])
	],
	kit: {
		adapter: adapter()
	},
	extensions: ['.svelte', '.svx', '.md']
};

export default config;
