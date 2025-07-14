export type Experiment = {
	type: 'beast2Experiment';
	title?: string;
	description?: string;
	humanReadableId: string;
	origin: string;
	uploadDate: string;
	license?: string;
	id?: string;
};

export type Paper = {
	doi: string;
	title: string;
	year: number;
	authors: string[];
	abstract: string;
	bibtex: string;
	url?: string;
};

export type File = {
	name: string;
	type:
		| 'beast2Configuration'
		| 'beast2PosteriorLogs'
		| 'beast2PosteriorTrees'
		| 'summaryTree'
		| 'codephyModel'
		| 'phyloDataExperiment'
		| 'unknown';
	version: number;
	sizeBytes: number;
	md5: string;
	isPreview?: boolean;
	localPath?: string;
	remotePath?: string;
};

export type SampleData = {
	type: 'rna' | 'dna' | 'aminoAcids' | 'phasedDiploidDna' | 'traits' | 'unknown';
	length: number;
	data: string;
};

export type ClassificationEntry = {
	classificationId: string;
	scientificName: string;
	idType: 'ncibTaxonomyId' | 'glottologId';
	commonName?: string;
	id?: string;
};

export type Sample = {
	sampleId: string;
	scientificName: string;
	type: 'species' | 'cells' | 'language' | 'unknown';
	classification: ClassificationEntry[];
	data: SampleData[];
	commonName?: string;
	id?: string;
};

export type Trees = {
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
};

export type EvolutionaryModelComponent = {
	name: string;
	description: string;
	type: 'substitutionModel' | 'clockModel' | 'treePrior' | 'treeLikelihood' | 'other';
	documentationUrl: string;
	parameters: Record<string, any>;
};

export type EvolutionaryModel = {
	models: EvolutionaryModelComponent[];
};

export type Metadata = {
	evoDataPipelineVersion: string;
};

export type PaperWithExperiments = {
	paper: Paper;
	experiments: {
		experiment: Experiment;
		files: File[];
		trees: Trees;
		evolutionaryModel: EvolutionaryModel;
		metadata: Metadata;
		samples: Sample[];
	}[];
};

export function convertSchemasToType(
	editableSchemaData: any,
	nonEditableSchemaData: any
): PaperWithExperiments {
	const schemaData = mergeSchemaData(editableSchemaData, nonEditableSchemaData);
	const { paper, ...experimentData } = schemaData;
	return {
		paper,
		experiments: [experimentData]
	} as PaperWithExperiments;
}

function mergeSchemaData(editableSchemaData: any, nonEditableSchemaData: any) {
	return {
		...editableSchemaData,
		...nonEditableSchemaData,
		paper: {
			...editableSchemaData.paper,
			...nonEditableSchemaData.paper
		},
		experiment: {
			...editableSchemaData.experiment,
			...nonEditableSchemaData.experiment
		}
	};
}
