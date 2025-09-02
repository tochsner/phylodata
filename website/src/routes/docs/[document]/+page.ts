export const load = async ({ params }) => {
	console.log(params.document);
	const post = await import(`$lib/docs/${params.document}.md`);

	return {
		PostContent: post.default,
		meta: { ...post.metadata, slug: params.document }
	};
};
