import { OpenAI } from 'openai';
import { zodTextFormat } from 'openai/helpers/zod';
import { z } from 'zod';

import { OPEN_AI_KEY } from '$env/static/private';

export const embedding_model = 'text-embedding-3-large';
const apiKey = OPEN_AI_KEY;

const openai = new OpenAI({ apiKey });

/**
 * Returns the relevant sample data types ("species", "pathogens", "single-cells", "languages")
 * for the given query.
 */
export async function detectSampleTypes(query: string) {
	const modelFeatures = `You are an expert in Bayesian phylogenetics.

	For a given phylogenetic use case, you determine the types of samples that are relevant.

	Return a list containing the relevant sample data types. If unsure, be generous and include
	all realistic types.

	The possible sample data types are:
	- "species": for any analysis including species. this is the most general type and could apply to macroevolutionary studies, multispecies coalescent studies, studies of bacteria, and more.
	- "pathogens": for any analysis including pathogens. In particular, this is everything that is related to virus transmission, epidemiology, and more.
	- "single-cells": for any analysis including single-cells. This is mainly for developmental studies including sequence data of single-cells of the same organism.
	- "languages": for any analysis including languages.
	`;

	const SampleType = z.object({
		sampleTypes: z.array(
			z.union([
				z.literal('species'),
				z.literal('pathogens'),
				z.literal('single-cells'),
				z.literal('languages')
			])
		)
	});

	const completion = await openai.responses.parse({
		model: 'gpt-4o',
		input: [
			{
				role: 'system',
				content: modelFeatures
			},
			{
				role: 'user',
				content: `For the following use case: "${query}".`
			}
		],
		temperature: 0.1,
		top_p: 0.1,
		text: {
			format: zodTextFormat(SampleType, 'sampleTypeExtraction')
		}
	});

	return completion.output_parsed?.sampleTypes || [];
}
