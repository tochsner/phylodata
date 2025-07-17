import { supabase } from '$lib/db/supabase';
import type { PaperWithExperiments } from '$lib/types';
import type { PageServerLoad } from './$types';
import type { Actions } from './$types';

export const load: PageServerLoad = async () => ({
	papers: supabase
		.from('papers')
		.select(
			`
			*,
      experiments!experiments_paperDoi_fkey (
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
		.then(({ error, data }) => {
			if (error) {
				console.error(error);
				throw new Error('No experiment found');
			}

			if (data === undefined) {
				throw new Error('No experiment found');
			}

			const transformedData = data.map((d) => ({
				paper: d,
				experiments: d.experiments
			}));
			transformedData.forEach((d) => delete d.paper.experiments);

			return transformedData as PaperWithExperiments[];
		})
});

export const actions = {
	filter: async ({ request }) => {
		const data = await request.formData();
		console.log(data.getAll('samples'));

		return 200;
	}
} satisfies Actions;
