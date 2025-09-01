import { supabase } from '$lib/db/supabase';
import { transformJoinedDataToPaperWithExperiment } from '$lib/db/transform';
import type { PaperWithExperiments } from '$lib/types';
import type { PageServerLoad } from './$types';
import type { Actions } from './$types';

export const load: PageServerLoad = async () => ({
	possibleSamples: supabase
		.from('distinctSampleNames')
		.select('name')
		.order('name')
		.then(({ data }) => data?.map((entry) => entry.name) || []),

	possibleEvolutionaryModels: supabase
		.from('distinctEvolutionaryModelNames')
		.select('name')
		.order('name')
		.then(({ data }) => data?.map((entry) => entry.name) || []),

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
					sampleData (
						id,
						type,
						length,
						sampleId
					)
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

			const transformedData = data.map(transformJoinedDataToPaperWithExperiment);

			return transformedData as PaperWithExperiments[];
		})
});

export const actions = {
	filter: async ({ request }) => {
		const data = await request.formData();

		const fileTypes = data.getAll('fileType') as string[];
		const sampleTypes = data.getAll('sampleType') as string[];
		const samples = data.getAll('samples') as string[];
		const treesTypes = data.getAll('treesType') as string[];
		const evolutionaryModels = data.getAll('evolutionaryModel') as string[];

		let query = supabase.from('papers').select(
			`
			*,
			experiments!experiments_paperDoi_fkey!inner (
				*,
				files!inner (*),
				trees!inner (*),
				evolutionaryModels (*),
				metadata (*),
				samples!inner (
					*,
					classification:classifications!inner (*)
				)
			)`
		);

		if (fileTypes.length > 0) {
			query = query.in('experiments.files.type', fileTypes);
		}
		if (sampleTypes.length > 0) {
			query = query.in('experiments.samples.type', sampleTypes);
		}
		if (samples.length > 0) {
			query = query.or(
				`scientificName.in.(${samples.join(',')}),commonName.in.(${samples.join(',')})`,
				{ referencedTable: 'experiments.samples.classification' }
			);
		}
		if (treesTypes.includes('rooted')) {
			query = query.eq('experiments.trees.rooted', true);
		}
		if (treesTypes.includes('unrooted')) {
			query = query.eq('experiments.trees.rooted', false);
		}
		if (treesTypes.includes('ultrametric')) {
			query = query.eq('experiments.trees.ultrametric', true);
		}
		if (treesTypes.includes('nonUltrametric')) {
			query = query.eq('experiments.trees.ultrametric', false);
		}
		if (evolutionaryModels.length > 0) {
			query = query.in('experiments.evolutionaryModels.name', evolutionaryModels);
		}

		const papers = await query;
		return (
			papers?.data?.map(transformJoinedDataToPaperWithExperiment) || ([] as PaperWithExperiments[])
		);
	}
} satisfies Actions;
