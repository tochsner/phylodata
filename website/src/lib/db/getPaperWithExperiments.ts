import { supabase } from '$lib/supabase';
import type { PaperWithExperiments } from '$lib/types';
import { transformJoinedDataToPaperWithExperiment } from './transform';

export async function getPaperWithExperiments(paperDoi: string): Promise<PaperWithExperiments> {
	const { data, error } = await supabase
		.from('papers')
		.select(
			`
			*,
       experiments!experiments_paperDoi_fkey (
         *,
         files (*),
         trees (*),
         evolutionaryModels (
           *,
           models:evolutionaryModelComponents (*)
         ),
         metadata (*),
         samples (
           *,
           classification:classificationEntries (*),
           data:sampleData (*)
         )
       )
       `
		)
		.eq('doi', decodeURIComponent(paperDoi))
		.single();

	if (error) {
		console.error(error);
		throw new Error('No experiment found');
	}

	if (!data) {
		throw new Error('No experiment found');
	}

	return transformJoinedDataToPaperWithExperiment(data);
}
