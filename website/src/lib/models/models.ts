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
	paper: string;
	code?: string;
	tutorials: string[];
	examples: string[];
};

const MODELS: Model[] = [
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
	}
];
