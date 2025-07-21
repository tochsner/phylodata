import { supabase } from '$lib/db/supabase';
import { transformJoinedDataToPaperWithExperiment } from '$lib/db/transform';
import type { PaperWithExperiments } from '$lib/types';
import type { PageServerLoad } from './$types';

export const load: PageServerLoad = async ({ params }) => ({
	papers: supabase
		.from('papers')
		.select(
			`
			*,
      experiments!experiments_paperDoi_fkey (
        *,
        evolutionaryModels!inner (*)
      )
       `
		)
		// .eq('experiments.evolutionaryModels.name', params.model)
		.then(({ error, data }) => {
			if (error) {
				console.error(error);
				throw new Error('No experiment found');
			}

			if (data === undefined) {
				throw new Error('No experiment found');
			}

			const transformedData = data.map((joinedData) => ({
				paper: {
					doi: joinedData.doi,
					title: joinedData.title,
					year: joinedData.year,
					authors: joinedData.authors,
					abstract: joinedData.abstract,
					bibtex: joinedData.bibtex,
					url: joinedData.url
				}
			}));

			return transformedData as PaperWithExperiments[];
		})
});
