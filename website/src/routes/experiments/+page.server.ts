import { supabase } from '$lib/supabaseClient';
import type { PaperWithExperiments } from '$lib/types';
import type { PageServerLoad } from './$types';
import type { Actions } from './$types';

export const load: PageServerLoad = async () => ({
	papers: supabase
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
           evolutionaryModels: EvolutionaryModelComponent (*)
         )
       `
		)
		.then(({ error, data }) => {
			if (error) {
				console.error(error);
				throw new Error('No experiment found');
			}

			if (data === undefined) {
				throw new Error('No experiment found');
			}

			return data as PaperWithExperiments[];
		})
});

export const actions = {
	filter: async () => {
		return 200;
	}
} satisfies Actions;
