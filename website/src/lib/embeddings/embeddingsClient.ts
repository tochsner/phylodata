import modelEmbeddings from '$lib/models/embeddings.json';
import { browser } from '$app/environment';

/**
 * Computes an embedding for a given search query using the model embeddings.
 */
export async function computeEmbedding(searchQuery: string | null) {
	if (!searchQuery || !browser) return null;

	const response = await fetch(`api/computeEmbedding/${searchQuery}`);
	const data = await response.json();

	return data;
}

const modelsToEmbedding = new Map<string, number[]>();
for (const model of modelEmbeddings) {
	modelsToEmbedding.set(model.fileName, model.embedding);
}

/**
 * Returns the embedding for a given model name.
 */
export function getModelEmbedding(modelName: string): number[] | undefined {
	return modelsToEmbedding.get(modelName.toLowerCase() + '.md');
}

/**
 * Returns a similarity score between the query and reference embeddings.
 * The score is a number between -1 and 1, where 1 indicates a perfect match
 * and -1 indicates no similarity.
 */
export function embeddingSimilarity(queryEmbedding: number[], referenceEmbedding: number[]) {
	return referenceEmbedding.reduce((acc, val, i) => acc + val * queryEmbedding[i], 0);
}
