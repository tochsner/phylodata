import { OpenAI } from 'openai';
import { OPEN_AI_KEY } from '$env/static/private';

export const embedding_model = 'text-embedding-3-large';
const apiKey = process.env.OPEN_AI_KEY || OPEN_AI_KEY;

const openai = new OpenAI({ apiKey });

export async function extendQuery(query: string) {
	const modelFeatures =
		'Provide features that a model should support in the context of phylogenetic analysis, such as: structured analysis, phylogenetic network, transmission history inference, language tree inference, species delimitation, and more.';

	const completion = await openai.chat.completions.create({
		model: 'gpt-4',
		messages: [
			{
				role: 'system',
				content: modelFeatures
			},
			{
				role: 'user',
				content: `For the following query about phylogenetics: If the query does not obviously refer to a common phylogenetic use case, return an empty string. Otherwise, suggest relevant model features that should be supported: "${query}". If the query does not obviously refer to a common phylogenetic use case, *return an empty string*. Only return a list of at most four keywords separated by commas.`
			}
		]
	});

	const enhancedQuery = `${query}\n (${completion.choices[0].message.content})`;

	return enhancedQuery;
}

export async function computeEmbedding(content: string) {
	const response = await openai.embeddings.create({
		model: embedding_model,
		input: await extendQuery(content)
	});
	return response.data[0].embedding;
}
