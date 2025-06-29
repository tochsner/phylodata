import { supabase } from '$lib/supabaseClient';
import type { PaperWithExperiments } from '$lib/types';
import type { PageServerLoad } from './$types';
import type { Actions } from './$types';

export const load: PageServerLoad = async () => {
	const { data, error } = await supabase.from('PaperWithExperiments').select(
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
	);

	if (error) {
		console.error(error);
		throw new Error('No experiment found');
	}

	if (data === undefined) {
		throw new Error('No experiment found');
	}

	return { papers: data } as { papers: PaperWithExperiments[] };
};

export const actions = {
	filter: async () => {
		return 200;
	}
} satisfies Actions;
