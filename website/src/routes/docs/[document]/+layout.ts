export const load = async ({ params }) => {
	const post = await import(`$lib/docs/${params.document}.md`);

	return {
		PostContent: post.default,
		meta: { ...post.metadata, slug: params.document }
	};
};
