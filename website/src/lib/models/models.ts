type Model = {
	software: 'beast2';
	name: string;
	authors: string[];
	shortDescription: string;
	mainTags: string[];
	sampleTypes: ('species' | 'genes' | 'cells' | 'pathogens' | 'languages')[];
	dataTypes: ('nucleotides' | 'proteins' | 'snps' | 'traits')[];
	model: 'treePrior' | 'substitutionModel' | 'clockModel' | 'treeLikelihood' | 'mixed' | 'other';
	otherFeatures: ('structured' | 'rateChangesOverTime')[];
	website?: string;
	paper?: string;
	code?: string;
	tutorials: string[];
	examples: string[];
};

export const MODELS: Model[] = [
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
	}
];

export const NAME_TO_MODEL = Object.fromEntries(MODELS.map((x) => [x.name, x]));
