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

export type Sample = {
	id: string;
	scientific_name: string;
	type: 'species' | 'cell' | 'language' | 'other';
	classification: Record<string, unknown>;
	data: SampleData[];
};

export type EvolutionaryModel = {
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

export type PaperWithExperiments = {
	id: number;
	title: string;
	authors: string[];
	abstract: string;
	bibtex: string;
	doi: string;
	url: string | null;
	experiments: Experiment[];
};
