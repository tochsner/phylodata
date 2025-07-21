export type Model = {
	software: 'beast2';
	name: string;
	authors: string[];
	shortDescription: string;
	mainTags: string[];
	sampleTypes: ('species' | 'genes' | 'cells' | 'pathogens' | 'languages')[];
	dataTypes: ('nucleotides' | 'proteins' | 'snps' | 'traits')[];
	model: 'treePrior' | 'substitutionModel' | 'clockModel' | 'treeLikelihood' | 'mixed' | 'other';
	otherFeatures: (
		| 'structured'
		| 'rateChangesOverTime'
		| 'multispecies coalescent'
		| 'species delimitation'
	)[];
	website?: string;
	paper?: string;
	code?: string;
	tutorials: string[];
	examples: string[];
};

export const MODELS: Model[] = [
	// Previously completed packages
	{
		software: 'beast2',
		name: 'OBAMA',
		authors: ['Remco Bouckaert'],
		shortDescription: 'Site models for amino acid sequences with automatic model averaging.',
		mainTags: ['Proteins', 'Substitution Model'],
		sampleTypes: ['species', 'genes', 'cells', 'pathogens'],
		dataTypes: ['proteins'],
		model: 'substitutionModel',
		otherFeatures: [],
		paper: 'https://doi.org/10.7717/peerj.9460',
		code: 'https://github.com/rbouckaert/obama/',
		tutorials: [
			'https://github.com/rbouckaert/obama/wiki/Installation',
			'https://github.com/rbouckaert/obama/wiki/How-to-use-OBAMA',
			'https://github.com/rbouckaert/obama/wiki/Trouble-shooting',
			'https://www.beast2.org/2020/11/25/OBAMA.html'
		],
		examples: ['https://github.com/rbouckaert/obama/tree/master/examples']
	},
	{
		software: 'beast2',
		name: 'gammaspike',
		authors: [
			'Jordan Douglas',
			'Remco Bouckaert',
			'Simon C. Harris',
			'Charles W. Carter Jr.',
			'Peter R. Wills'
		],
		shortDescription: 'A clock model to test for punctuated equilibrium.',
		mainTags: ['Punctuated Equilibrium', 'Clock Model'],
		sampleTypes: ['species', 'genes', 'cells', 'pathogens', 'languages'],
		dataTypes: ['proteins', 'nucleotides', 'snps', 'traits'],
		model: 'clockModel',
		otherFeatures: [],
		paper: 'https://doi.org/10.1098/rspb.2025.0182',
		code: 'https://github.com/jordandouglas/GammaSpikeModel',
		tutorials: ['https://github.com/jordandouglas/GammaSpikeModel'],
		examples: ['https://github.com/jordandouglas/GammaSpikeModel/tree/main/examples']
	},
	{
		software: 'beast2',
		name: 'Babel',
		authors: ['Remco Bouckaert'],
		shortDescription: 'A package for linguistic analyses.',
		mainTags: ['Linguistics'],
		sampleTypes: ['languages'],
		dataTypes: ['traits'],
		model: 'mixed',
		otherFeatures: [],
		code: 'https://github.com/rbouckaert/Babel',
		tutorials: [
			'https://taming-the-beast.org/tutorials/LanguagePhylogenies/',
			'https://www.beast2.org/2019/05/27/babel-tools.html'
		],
		examples: ['https://github.com/rbouckaert/Babel/tree/master/examples']
	},
	{
		software: 'beast2',
		name: 'StarBeast3',
		authors: ['Jordan Douglas', 'Cinthy L. Jiménez-Silva', 'Remco Bouckaert'],
		shortDescription: 'Infer the species tree and compatible gene trees.',
		mainTags: ['multispecies coalescent', 'ILS'],
		sampleTypes: ['species', 'genes'],
		dataTypes: ['proteins', 'nucleotides', 'snps', 'traits'],
		model: 'treeLikelihood',
		otherFeatures: ['multispecies coalescent'],
		code: 'https://github.com/rbouckaert/starbeast3',
		paper: 'https://doi.org/10.1093/sysbio/syac010',
		tutorials: [
			'https://github.com/rbouckaert/starbeast3#using-starbeast3',
			'https://github.com/rbouckaert/starbeast3/tree/master/workshop',
			'https://www.beast2.org/2022/03/31/starbeast3.html'
		],
		examples: ['https://github.com/rbouckaert/starbeast3/tree/master/examples']
	},
	{
		software: 'beast2',
		name: 'SPEEDEMON',
		authors: ['Jordan Douglas', 'Remco Bouckaert'],
		shortDescription: 'Define boundaries between species.',
		mainTags: ['species delimitation'],
		sampleTypes: ['species', 'genes'],
		dataTypes: ['proteins', 'nucleotides', 'snps', 'traits'],
		model: 'treePrior',
		otherFeatures: ['species delimitation'],
		code: 'https://github.com/rbouckaert/speedemon',
		paper: 'https://doi.org/10.1038/s42003-022-03723-z',
		tutorials: [
			'https://github.com/rbouckaert/speedemon/tree/master/tutorial',
			'https://github.com/rbouckaert/speedemon#preparing-an-xml-file-using-beauti',
			'https://www.beast2.org/2022/08/01/speedemon.html'
		],
		examples: ['https://github.com/rbouckaert/speedemon/tree/master/examples']
	},
	{
		software: 'beast2',
		name: 'phylonco',
		authors: [
			'Kylie Chen',
			'Jiří C Moravec',
			'Alex Gavryushkin',
			'David Welch',
			'Alexei J Drummond'
		],
		shortDescription: 'Model single-cell cancer evolution.',
		mainTags: ['cancer evolution'],
		sampleTypes: ['cells'],
		dataTypes: ['snps', 'nucleotides', 'proteins'],
		model: 'mixed',
		otherFeatures: [],
		code: 'https://github.com/bioDS/beast-phylonco',
		paper: 'https://doi.org/10.1093/molbev/msac143',
		tutorials: ['https://github.com/bioDS/beast-phylonco?tab=readme-ov-file#user-guide'],
		examples: ['https://github.com/bioDS/beast-phylonco/tree/master/examples']
	},
	{
		software: 'beast2',
		name: 'BDMM-Prime',
		authors: ['Timothy G Vaughan', 'Tanja Stadler'],
		shortDescription: 'Use structured and unstructured birth-death models.',
		mainTags: ['birth-death models', 'multiple types'],
		sampleTypes: ['species', 'genes', 'cells', 'pathogens', 'languages'],
		dataTypes: ['nucleotides', 'proteins', 'snps', 'traits'],
		model: 'treePrior',
		otherFeatures: ['structured'],
		code: 'https://github.com/tgvaughan/BDMM-Prime',
		website: 'https://tgvaughan.github.io/BDMM-Prime/',
		paper: 'https://doi.org/10.1093/molbev/msaf130',
		tutorials: ['https://tgvaughan.github.io/BDMM-Prime/#id-1-Getting-Started'],
		examples: ['https://github.com/tgvaughan/BDMM-Prime/tree/master/examples']
	},
	{
		software: 'beast2',
		name: 'TiDeTree',
		authors: ['Timothy G Vaughan', 'Tanja Stadler'],
		shortDescription: 'Use lineage recorder data to estimate time-scaled single-cell trees.',
		mainTags: ['single-cell trees', 'lineage tracing data'],
		sampleTypes: ['cells'],
		dataTypes: ['nucleotides'],
		model: 'mixed',
		otherFeatures: [],
		code: 'https://github.com/seidels/tidetree',
		paper: 'https://doi.org/10.1098/rspb.2022.1844',
		tutorials: ['https://github.com/seidels/tidetree?tab=readme-ov-file#installation'],
		examples: ['https://github.com/seidels/tidetree/tree/main/examples']
	},
	{
		software: 'beast2',
		name: 'bdsky',
		authors: [
			'Tanja Stadler',
			'Denise Kühnert',
			'Sebastian Bonhoeffer',
			'Alexei J. Drummond',
			'Alexandra Gavryushkina',
			'David Welch'
		],
		shortDescription: 'Use birth-death skyline models.',
		mainTags: ['birth-death skyline models'],
		sampleTypes: ['species', 'genes', 'cells', 'pathogens', 'languages'],
		dataTypes: ['nucleotides', 'proteins', 'snps', 'traits'],
		model: 'treePrior',
		otherFeatures: [],
		code: 'https://github.com/BEAST2-Dev/bdsky',
		paper: 'https://doi.org/10.1073/pnas.1207965110',
		tutorials: [
			'https://beast2-dev.github.io/beast-docs/beast2/bdsky/bdskytutorial.html',
			'https://taming-the-beast.org/tutorials/Skyline-plots/',
			'https://phyloworks.org/workshops/Ebola_BEAST2_Exercise.pdf'
		],
		examples: ['https://github.com/BEAST2-Dev/bdsky/tree/master/examples']
	}
];

export const NAME_TO_MODEL = Object.fromEntries(MODELS.map((x) => [x.name, x]));
