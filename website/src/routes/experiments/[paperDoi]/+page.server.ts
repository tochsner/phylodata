import { supabase } from '$lib/supabaseClient';
import type { PaperWithExperiments } from '$lib/types';
import type { PageServerLoad } from './$types';

export const load: PageServerLoad = async ({ params }) => {
	const { paperDoi } = params;

	const { data, error } = await supabase
		.from('PaperWithExperiments')
		.select(
			`
        *,
        experiments:Experiment (
          *,
          files:File (*),
          samples:Sample (
            *,
            data:SampleData (*),
            classification:ClassificationEntry (*)
          ),
          evolutionaryModels:EvolutionaryModelComponent (*)
        )
      `
		)
		.eq('doi', paperDoi)
		.single();

	if (error) {
		console.error(error);
		throw new Error('No experiment found');
	}

	if (!data) {
		throw new Error('No experiment found');
	}

	return data as PaperWithExperiments;
};
