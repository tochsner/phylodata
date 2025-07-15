import { supabase } from '$lib/db/supabase';
import type { PaperWithExperiments } from '$lib/types';
import { transformJoinedDataToPaperWithExperiment } from './transform';

/**
 * Fetches the paper with the given DOI with its experiments from the database.
 */
export async function getPaperWithExperiments(paperDoi: string): Promise<PaperWithExperiments> {
	const { data, error } = await supabase
		.from('papers')
		.select(
			`
			*,
       experiments (
         *,
         files (*),
         trees (*),
         evolutionaryModels (*),
         metadata (*),
         samples (
           *,
           classification:classifications (*),
           sampleData (*)
         )
       )
       `
		)
		.eq('doi', decodeURIComponent(paperDoi))
		.single();

	if (error || !data) {
		console.error(error);
		throw new Error('No experiment found');
	}

	return transformJoinedDataToPaperWithExperiment(data);
}
