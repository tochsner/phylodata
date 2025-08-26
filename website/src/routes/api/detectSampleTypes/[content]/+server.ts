import type { RequestHandler } from './$types';
import { detectSampleTypes } from '$lib/sampleTypes/detectSampleTypes';

/** Detects the sample types for the given search query. */
export const GET: RequestHandler = async ({ params }) => {
	let { content } = params;

	const embedding = await detectSampleTypes(decodeURIComponent(content));

	return new Response(JSON.stringify(embedding));
};
