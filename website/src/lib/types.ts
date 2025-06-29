export type File = {
	name: string;
	type:
		| 'beast2Configuration'
		| 'beast2PosteriorLogs'
		| 'beast2PosteriorTrees'
		| 'codephyModel'
		| 'evoDataExperiment'
		| 'summaryTree'
		| 'unknown';
	version: number;
	sizeBytes: number;
	md5: string;
	localPath?: string | null;
	remotePath?: string | null;
};

type SampleData = {
	type: 'aminoAcids' | 'dna' | 'phasedDiploidDna' | 'rna' | 'traits' | 'unknown';
	length: number;
	data: string;
};

type ClassificationEntry = {
	id?: string;
	classificationId: string;
	scientificName: string;
	idType: 'glottologId' | 'ncibTaxonomyId';
};

export type Sample = {
	id?: string;
	sampleId: string;
	scientificName: string;
	type: 'cells' | 'language' | 'species' | 'unknown';
	classification: ClassificationEntry[];
	data: SampleData[];
};

export type EvolutionaryModelComponent = {
	name: string;
	description: string;
	type: 'clockModel' | 'other' | 'substitutionModel' | 'treeLikelihood' | 'treePrior';
	documentationUrl: string;
	parameters: Record<string, any>;
};

export type Experiment = {
	type: 'beast2Experiment';
	origin: string;
	uploadDate: string;
	title?: string | null;
	description?: string | null;
	license: string;
	id?: string | null;
	files: File[];
	samples: Sample[];
	evolutionaryModels: EvolutionaryModelComponent[];
	// trees
	numberOfTrees: number;
	numberOfTips: number;
	ultrametric: boolean;
	timeTree: boolean;
	rooted: boolean;
	ccd1Entropy: number;
	treeEss: number;
	ccd0MapTree: string;
	hipstrTree: string;
	leafToSampleMap: Record<string, string>;
	averageRootAge: number;
	//metadata
	evoDataPipelineVersion: string;
};

export type PaperWithExperiments = {
	title: string;
	authors: string[];
	abstract: string;
	bibtex: string;
	doi?: string | null;
	id?: string | null;
	url?: string | null;
	experiments: Experiment[];
};

export function convertSchemaToType(schemaData: any): PaperWithExperiments {
	const { experiment, paper, files, samples, trees, evolutionaryModel, metadata } = schemaData;
	return {
		...paper,
		experiments: [
			{
				...experiment,
				files: files,
				samples: samples,
				evolutionaryModels: evolutionaryModel.models,
				...trees,
				...metadata
			}
		]
	} as PaperWithExperiments;
}
