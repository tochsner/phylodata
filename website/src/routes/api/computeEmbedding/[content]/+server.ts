import type { RequestHandler } from './$types';
import { computeEmbedding } from '$lib/embeddings/computeEmbedding';

/** Calculates an embedding for the given content and returns it as a JSON list. */
export const GET: RequestHandler = async ({ params }) => {
	let { content } = params;

	const embedding = await computeEmbedding(decodeURIComponent(content));

	return new Response(JSON.stringify(embedding));
};
