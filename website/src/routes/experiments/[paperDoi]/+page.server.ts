import { supabase } from '$lib/supabase';
import type { PaperWithExperiments } from '$lib/types';
import type { PageServerLoad } from './$types';

export const load: PageServerLoad = async ({ params }) => {
	const { paperDoi } = params;

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

	const transformedData = {
		paper: {
			doi: data.doi,
			title: data.title,
			year: data.year,
			authors: data.authors,
			abstract: data.abstract,
			bibtex: data.bibtex,
			url: data.url
		},
		// @ts-ignore
		experiments: data.experiments.map((exp) => ({
			experiment: {
				type: exp.type,
				title: exp.title,
				description: exp.description,
				humanReadableId: exp.humanReadableId,
				origin: exp.origin,
				uploadDate: exp.uploadDate,
				license: exp.license,
				id: exp.id
			},
			files: exp.files,
			trees: exp.trees[0],
			evolutionaryModel: exp.evolutionaryModels[0],
			metadata: exp.metadata,
			samples: exp.samples
		}))
	};

	console.log(transformedData.experiments[0].evolutionaryModel);

	return transformedData as PaperWithExperiments;
};
