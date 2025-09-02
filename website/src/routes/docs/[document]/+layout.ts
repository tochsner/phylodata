export const load = async ({ params }) => {
	const post = await import(`$lib/docs/${params.document}.md`);

	return {
		meta: { ...post.metadata, slug: params.document }
	};
};
