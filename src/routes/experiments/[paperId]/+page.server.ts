import { supabase } from '$lib/supabaseClient';
import type { PageServerLoad } from './$types';
export type File = {
	id: number;
	name: string;
	type:
		| 'beast2Configuration'
		| 'beast2PosteriorLogs'
		| 'beast2PosteriorTrees'
		| 'summaryTree'
		| 'codephyModel'
		| 'evoDataExperiment';
	version: number;
	size_bytes: number;
	md5: string;
};

type SampleData = {
	type: 'rna' | 'dna' | 'aminoAcids' | 'phasedDiploidDna' | 'traits';
	length: number;
	data: string;
};

type Sample = {
	id: string;
	scientific_name: string;
	type: 'species' | 'cell' | 'language' | 'other';
	classification: Record<string, unknown>;
	data: SampleData[];
};

type EvolutionaryModel = {
	id: number;
	name: string;
	model_type: 'substitutionModel' | 'clockModel' | 'treePrior' | 'treeLikelihood' | 'other';
	documentation_url: string;
};

export type Experiment = {
	id: number;
	paper_id: number;
	title: string;
	origin: string;
	doi: string;
	upload_date: string;
	license: string;
	number_of_trees: number;
	number_of_tips: number;
	ultrametric: boolean;
	rooted: boolean;
	ccd1_entropy: number;
	tree_ess: number;
	ccd0_map_tree: string;
	hipstr_tree: string;
	average_root_age_years: number;
	leaf_to_sample_map: Record<string, string>;
	pipeline_version: string;
	pipeline_hash: string;
	files: File[];
	samples: Sample[];
	evolutionary_models: EvolutionaryModel[];
};

type PaperWithRelations = {
	id: number;
	title: string;
	authors: string[];
	abstract: string;
	bibtex: string;
	doi: string;
	url: string | null;
	experiments: Experiment[];
};
export const load: PageServerLoad = async ({ params }) => {
	const { paperId } = params;

	const { data, error } = await supabase
		.from('papers')
		.select(
			`
    *,
    experiments (
      *,
      files (
        id,
        name,
        type,
        version,
        size_bytes,
        md5
      ),
      samples (
        id,
        scientific_name,
        type,
        classification,
        data
      ),
      evolutionary_models (
        id,
        name,
        model_type,
        documentation_url
      )
    )
  `
		)
		.eq('id', paperId)
		.single();

	if (error) {
		console.error(error);
		throw new Error('No experiment found');
	}

	if (!data) {
		throw new Error('No experiment found');
	}

	return data as PaperWithRelations;
};
