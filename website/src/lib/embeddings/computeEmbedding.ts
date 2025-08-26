import { OpenAI } from 'openai';
import { OPEN_AI_KEY } from '$env/static/private';

export const embedding_model = 'text-embedding-3-large';
const apiKey = process.env.OPEN_AI_KEY || OPEN_AI_KEY;

const openai = new OpenAI({ apiKey });

/**
 * Compute an embedding for the given search query using OpenAI's embedding model.
 */
export async function computeEmbedding(searchQuery: string) {
	const response = await openai.embeddings.create({
		model: embedding_model,
		input: await extendQuery(searchQuery)
	});
	return response.data[0].embedding;
}

/**
 * Extends the given search query with a description of the applications of a matching model.
 * This is done to increase the similarity of the query with a matching model's description.
 */
export async function extendQuery(query: string) {
	const modelFeatures = `You are an expert in Bayesian phylogenetics.

	You develop a BEAST 2 package for a given use case. Suggest features that your package should support.
	Describe the package by listing potential applications of your package. List at most five of such applications.

	# Example 1

	Given use case: "I want to group multiple samples into distinct species."

	Use the package when you:

	- Need to **define species boundaries** from genomic data
	- Working with **multilocus sequence data** or **SNP data**
	- Want to account for **different evolutionary histories** across genomic regions
	- Want to allow **speciation rates to vary through time**

	# Example 2

	Given use case: "I study infectious disease outbreaks given epidemic and genetic data."

	Use the package when you:

	- Combining phylogenetic sequences with time series of confirmed cases for joint inference
  - Analyzing large genomic datasets or real-time analysis from infectious disease outbreaks that require computationally tractable methods
  - Estimating R₀ and prevalence from combined genetic and epidemiological sources

  # Example 3

	Given use case: "I study old polynesian languages."

	Use the package when you:

	- Working with linguistic data in BEAST2
  - Analyzing cognate data (homologous word forms from common ancestors)
  - Studying grammatical features across languages
  - Creating language phylogenies with temporal information
  - Working with binary presence/absence data for linguistic traits
  - Need to account for ascertainment bias in linguistic datasets
	`;

	const completion = await openai.chat.completions.create({
		model: 'gpt-4o',
		messages: [
			{
				role: 'system',
				content: modelFeatures
			},
			{
				role: 'user',
				content: `For the following use case: "${query}".`
			}
		],
		top_p: 0.1
	});

	const enhancedQuery = `${query}\n\n ${completion.choices[0].message.content}`;
	return enhancedQuery;
}
