import { supabase } from '$lib/db/supabase';
import { transformJoinedDataToPaperWithExperiment } from '$lib/db/transform';
import type { PaperWithExperiments } from '$lib/types';
import type { PageServerLoad } from './$types';
import { getEmptyFilter, type ExperimentFilter } from './experimentFilter';

const getPapers = async (filter: ExperimentFilter) => {
	const filterTrees =
		filter.rootedTrees ||
		filter.unrootedTrees ||
		filter.ultrametricTrees ||
		filter.nonUltrametricTrees ||
		filter.searchString;

	const filterEvolutionaryModels =
		filter.evolutionaryModels && filter.evolutionaryModels.length > 0;

	const filterSamples =
		(filter.languages && filter.languages.length > 0) ||
		(filter.species && filter.species.length > 0);

	let query = supabase.from('papers').select(
		`
		*,
		experiments!experiments_paperDoi_fkey (
			*,
			trees${filterTrees ? '!inner' : ''} (*),
			files!inner (*),
			evolutionaryModels${filterEvolutionaryModels ? '!inner' : ''} (*),
			metadata (*)
			${filterSamples ? ',samples!inner (classification:classifications!inner ())' : ''}
		)
   `
	);

	if (filter.searchString) {
		query = query.textSearch('fullText', filter.searchString.replace(' ', '+'));
	}
	if (filter.rootedTrees) {
		query = query.eq('experiments.trees.rooted', true);
	}
	if (filter.unrootedTrees) {
		query = query.eq('experiments.trees.rooted', false);
	}
	if (filter.ultrametricTrees) {
		query = query.eq('experiments.trees.ultrametric', true);
	}
	if (filter.nonUltrametricTrees) {
		query = query.eq('experiments.trees.ultrametric', false);
	}

	if (filter.filesTypes && filter.filesTypes.length > 0) {
		query = query.in('experiments.files.type', filter.filesTypes);
	}

	if (filter.evolutionaryModels && filter.evolutionaryModels.length > 0) {
		query = query.in('experiments.evolutionaryModels.name', filter.evolutionaryModels);
	}

	if (filter.languages && filter.languages.length > 0) {
		query = query.or(
			`scientificName.in.(${filter.languages.join(',')}),commonName.in.(${filter.languages.join(',')})`,
			{ referencedTable: 'experiments.samples.classification' }
		);
	}

	if (filter.species && filter.species.length > 0) {
		query = query.or(
			`scientificName.in.(${filter.species.join(',')}),commonName.in.(${filter.species.join(',')})`,
			{ referencedTable: 'experiments.samples.classification' }
		);
	}

	query = query.order("year", { ascending: false });

	const data = await query;

	if (!data.data || data.data.length === 0) return [];

	const transformedData = data.data
		.map(transformJoinedDataToPaperWithExperiment)
		.filter((paper) => paper.experiments.length > 0);

	return transformedData as PaperWithExperiments[];
};

// we cache all possible samples to avoid making multiple requests to the database
let allPossibleSamples:
	| {
		name: any;
		idType: any;
	}[]
	| undefined;

const getAllPossibleSamples = async () => {
	if (allPossibleSamples) return allPossibleSamples;

	allPossibleSamples = [];

	let from = 0;
	const chunkSize = 1000;

	while (true) {
		const { data, error } = await supabase
			.from('distinctSampleNames')
			.select('name, idType')
			.range(from, from + chunkSize - 1);

		if (error) throw error;
		if (!data || data.length === 0) break;

		allPossibleSamples = allPossibleSamples.concat(data);
		from += chunkSize;
	}

	return allPossibleSamples;
};

export const load: PageServerLoad = async ({ url }) => {
	const experimentsFilter = (JSON.parse(url.searchParams.get('filter') || 'false') ||
		getEmptyFilter()) as ExperimentFilter;

	const possibleSamples = getAllPossibleSamples();

	const possibleLanguages = possibleSamples.then((samples) =>
		samples.filter((sample) => sample.idType === 'glottologId').map((sample) => sample.name)
	);
	const possibleSpecies = possibleSamples.then((samples) =>
		samples.filter((sample) => sample.idType === 'ncbiTaxonomyId').map((sample) => sample.name)
	);

	const possibleEvolutionaryModels = supabase
		.from('distinctEvolutionaryModelNames')
		.select('name')
		.order('name')
		.then(({ data }) => data?.map((entry) => entry.name) || []);

	return {
		experimentsFilter,
		possibleLanguages,
		possibleSpecies,
		possibleEvolutionaryModels,
		papers: getPapers(experimentsFilter)
	};
};
