export type Model = {
	software: 'beast2';
	name: string;
	authors: string[];
	shortDescription: string;
	tags: string[];
	sampleTypes: ('species' | 'single-cells' | 'pathogens' | 'languages')[];
	dataTypes: ('nucleotides' | 'proteins' | 'snps' | 'traits')[];
	model: 'treePrior' | 'substitutionModel' | 'clockModel' | 'treeLikelihood' | 'mixed' | 'other';
	website?: string;
	paper?: string;
	code?: string;
	tutorials: string[];
	examples: string[];
	namespaces: string[];
};

export const ALL_SAMPLE_TYPES = [
	'species',
	'single-cells',
	'pathogens',
	'languages'
] as Model['sampleTypes'];
export const ALL_DATA_TYPES = ['nucleotides', 'proteins', 'snps', 'traits'] as Model['dataTypes'];

export const MODELS: Model[] = [
	{
		software: 'beast2',
		name: 'OBAMA',
		authors: ['Remco Bouckaert'],
		shortDescription: 'Site models for amino acid sequences with automatic model averaging.',
		tags: ['Proteins'],
		sampleTypes: ['species', 'single-cells', 'pathogens'],
		dataTypes: ['proteins'],
		model: 'substitutionModel',
		paper: 'https://doi.org/10.7717/peerj.9460',
		code: 'https://github.com/rbouckaert/obama/',
		tutorials: [
			'https://github.com/rbouckaert/obama/wiki/Installation',
			'https://github.com/rbouckaert/obama/wiki/How-to-use-OBAMA',
			'https://github.com/rbouckaert/obama/wiki/Trouble-shooting',
			'https://www.beast2.org/2020/11/25/OBAMA.html'
		],
		examples: ['https://github.com/rbouckaert/obama/tree/master/examples'],
		namespaces: ['obama']
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
		tags: ['Punctuated Equilibrium'],
		sampleTypes: ALL_SAMPLE_TYPES,
		dataTypes: ALL_DATA_TYPES,
		model: 'clockModel',
		paper: 'https://doi.org/10.1098/rspb.2025.0182',
		code: 'https://github.com/jordandouglas/GammaSpikeModel',
		tutorials: ['https://github.com/jordandouglas/GammaSpikeModel'],
		examples: ['https://github.com/jordandouglas/GammaSpikeModel/tree/main/examples'],
		namespaces: ['gammaspike']
	},
	{
		software: 'beast2',
		name: 'Babel',
		authors: ['Remco Bouckaert'],
		shortDescription: 'A package for linguistic analyses.',
		tags: ['Linguistics'],
		sampleTypes: ['languages'],
		dataTypes: ['traits'],
		model: 'mixed',
		code: 'https://github.com/rbouckaert/Babel',
		tutorials: [
			'https://taming-the-beast.org/tutorials/LanguagePhylogenies/',
			'https://www.beast2.org/2019/05/27/babel-tools.html'
		],
		examples: ['https://github.com/rbouckaert/Babel/tree/master/examples'],
		namespaces: ['babel']
	},
	{
		software: 'beast2',
		name: 'StarBeast3',
		authors: ['Jordan Douglas', 'Cinthy L. Jiménez-Silva', 'Remco Bouckaert'],
		shortDescription: 'Infer the species tree and compatible gene trees.',
		tags: ['Multispecies Coalescent', 'ILS'],
		sampleTypes: ['species'],
		dataTypes: ALL_DATA_TYPES,
		model: 'treeLikelihood',
		code: 'https://github.com/rbouckaert/starbeast3',
		paper: 'https://doi.org/10.1093/sysbio/syac010',
		tutorials: [
			'https://github.com/rbouckaert/starbeast3#using-starbeast3',
			'https://github.com/rbouckaert/starbeast3/tree/master/workshop',
			'https://www.beast2.org/2022/03/31/starbeast3.html'
		],
		examples: ['https://github.com/rbouckaert/starbeast3/tree/master/examples'],
		namespaces: ['starbeast3']
	},
	{
		software: 'beast2',
		name: 'SPEEDEMON',
		authors: ['Jordan Douglas', 'Remco Bouckaert'],
		shortDescription: 'Define boundaries between species.',
		tags: ['Species Delimitation'],
		sampleTypes: ['species'],
		dataTypes: ALL_DATA_TYPES,
		model: 'treePrior',
		code: 'https://github.com/rbouckaert/speedemon',
		paper: 'https://doi.org/10.1038/s42003-022-03723-z',
		tutorials: [
			'https://github.com/rbouckaert/speedemon/tree/master/tutorial',
			'https://github.com/rbouckaert/speedemon#preparing-an-xml-file-using-beauti',
			'https://www.beast2.org/2022/08/01/speedemon.html'
		],
		examples: ['https://github.com/rbouckaert/speedemon/tree/master/examples'],
		namespaces: ['speedemon']
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
		tags: ['cancer evolution'],
		sampleTypes: ['single-cells'],
		dataTypes: ['snps', 'nucleotides', 'proteins'],
		model: 'mixed',
		code: 'https://github.com/bioDS/beast-phylonco',
		paper: 'https://doi.org/10.1093/molbev/msac143',
		tutorials: ['https://github.com/bioDS/beast-phylonco?tab=readme-ov-file#user-guide'],
		examples: ['https://github.com/bioDS/beast-phylonco/tree/master/examples'],
		namespaces: ['phylonco']
	},
	{
		software: 'beast2',
		name: 'BDMM-Prime',
		authors: ['Timothy G Vaughan', 'Tanja Stadler'],
		shortDescription: 'Use structured and unstructured birth-death models.',
		tags: ['Multiple Types'],
		sampleTypes: ALL_SAMPLE_TYPES,
		dataTypes: ALL_DATA_TYPES,
		model: 'treePrior',
		code: 'https://github.com/tgvaughan/BDMM-Prime',
		website: 'https://tgvaughan.github.io/BDMM-Prime/',
		paper: 'https://doi.org/10.1093/molbev/msaf130',
		tutorials: ['https://tgvaughan.github.io/BDMM-Prime/#id-1-Getting-Started'],
		examples: [],
		namespaces: ['bdmmprime']
	},
	{
		software: 'beast2',
		name: 'TiDeTree',
		authors: ['Timothy G Vaughan', 'Tanja Stadler'],
		shortDescription: 'Use lineage recorder data to estimate time-scaled single-cell trees.',
		tags: ['single-cell trees', 'lineage tracing data'],
		sampleTypes: ['single-cells'],
		dataTypes: ['nucleotides'],
		model: 'mixed',
		code: 'https://github.com/seidels/tidetree',
		paper: 'https://doi.org/10.1098/rspb.2022.1844',
		tutorials: ['https://github.com/seidels/tidetree?tab=readme-ov-file#installation'],
		examples: ['https://github.com/seidels/tidetree/tree/main/examples'],
		namespaces: ['tidetree']
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
		tags: [],
		sampleTypes: ALL_SAMPLE_TYPES,
		dataTypes: ALL_DATA_TYPES,
		model: 'treePrior',
		code: 'https://github.com/BEAST2-Dev/bdsky',
		paper: 'https://doi.org/10.1073/pnas.1207965110',
		tutorials: [
			'https://beast2-dev.github.io/beast-docs/beast2/bdsky/bdskytutorial.html',
			'https://taming-the-beast.org/tutorials/Skyline-plots/',
			'https://phyloworks.org/workshops/Ebola_BEAST2_Exercise.pdf'
		],
		examples: ['https://github.com/BEAST2-Dev/bdsky/tree/master/examples'],
		namespaces: ['bdsky']
	},
	{
		software: 'beast2',
		name: 'BICEPS',
		authors: ['Remco R Bouckaert'],
		shortDescription: 'Use birth-death skyline models more efficiently.',
		tags: [],
		sampleTypes: ALL_SAMPLE_TYPES,
		dataTypes: ALL_DATA_TYPES,
		model: 'treePrior',
		code: 'https://github.com/rbouckaert/biceps/',
		paper: 'https://academic.oup.com/sysbio/article/71/6/1549/6536980',
		tutorials: [
			'https://github.com/rbouckaert/biceps/?tab=readme-ov-file#biceps-tutorial',
			'https://github.com/rbouckaert/biceps/?tab=readme-ov-file#yule-skyline-tutorial',
			'https://www.beast2.org/2022/05/01/bicpes-tree-prior.html',
			'https://beast2-dev.github.io/hmc/hmc/Priors/BICEPS/'
		],
		examples: ['https://github.com/rbouckaert/biceps/tree/master/examples'],
		namespaces: ['biceps']
	},
	{
		software: 'beast2',
		name: 'contraband',
		authors: [
			'Théo Gaboriau',
			'Fábio K. Mendes',
			'Simon Joly',
			'Daniele Silvestro',
			'Nicolas Salamin'
		],
		shortDescription: 'Model the evolution of continuous traits.',
		tags: ['Trait Evolution'],
		sampleTypes: ALL_SAMPLE_TYPES,
		dataTypes: ['traits'],
		model: 'substitutionModel',
		code: 'https://github.com/fkmendes/contraband',
		paper: 'https://besjournals.onlinelibrary.wiley.com/doi/epdf/10.1111/2041-210X.13458',
		tutorials: ['https://taming-the-beast.org/tutorials/contraband-tutorial/'],
		examples: ['https://github.com/fkmendes/contraband/tree/master/examples/testing'],
		namespaces: ['contraband']
	},
	{
		software: 'beast2',
		name: 'SNAPP',
		authors: [
			'David Bryant',
			'Remco Bouckaert',
			'Joseph Felsenstein',
			'Noah A. Rosenberg',
			'Arindam RoyChoudhury'
		],
		shortDescription: 'Infer species trees from SNP or AFLP data.',
		tags: ['Multispecies Coalescent', 'SNP', 'AFLP'],
		sampleTypes: ['species', 'single-cells', 'pathogens'],
		dataTypes: ['snps'],
		model: 'treePrior',
		code: 'https://github.com/BEAST2-Dev/SNAPP',
		paper: 'https://academic.oup.com/mbe/article/29/8/1917/1045283',
		tutorials: [
			'https://www.beast2.org/snapp/',
			'https://www.beast2.org/snapp-faq/',
			'https://github.com/ForBioPhylogenomics/tutorials/blob/main/divergence_time_estimation_with_snp_data/README.md',
			'https://evomics.org/wp-content/uploads/2018/01/BFD-tutorial.pdf',
			'https://taming-the-beast.org/tutorials/BFD_snapper_tutorial/'
		],
		examples: ['https://github.com/BEAST2-Dev/SNAPP/tree/master/examples'],
		namespaces: ['snap']
	},
	{
		software: 'beast2',
		name: 'SNAPPER',
		authors: [
			'Marnus Stoltz',
			'Boris Bauemer',
			'Remco Bouckaert',
			'Colin Fox',
			'Gordon Hiscott',
			'David Bryant'
		],
		shortDescription: 'Efficiently infer species trees from SNP or AFLP data.',
		tags: ['Multispecies Coalescent', 'SNP', 'AFLP'],
		sampleTypes: ['species', 'single-cells', 'pathogens'],
		dataTypes: ['snps'],
		model: 'treePrior',
		code: 'https://github.com/rbouckaert/snapper',
		paper: 'https://academic.oup.com/sysbio/article-abstract/70/1/145/5867924',
		tutorials: [
			'https://github.com/ForBioPhylogenomics/tutorials/tree/main/divergence_time_estimation_with_snp_data',
			'https://github.com/BEAST2-Dev/beast-docs/releases/download/v1.0/snapper-delimitation-tutorial-2021.zip',
			'https://taming-the-beast.org/tutorials/BFD_snapper_tutorial/'
		],
		examples: ['https://github.com/rbouckaert/snapper/tree/master/examples'],
		namespaces: ['snapper']
	},
	{
		software: 'beast2',
		name: 'SpeciesNetwork',
		authors: ['Chi Zhang', 'Huw A Ogilvie', 'Alexei J Drummond', 'Tanja Stadler'],
		shortDescription:
			'Multispecies network coalescent inference of introgression and hybridization.',
		tags: ['Introgression', 'Hybridization'],
		sampleTypes: ['species'],
		dataTypes: ALL_DATA_TYPES,
		model: 'treePrior',
		code: 'https://github.com/zhangchicool/speciesnetwork/tree/master',
		paper: 'https://academic.oup.com/mbe/article/35/2/504/4705834',
		tutorials: [
			'https://github.com/zhangchicool/speciesnetwork/releases/download/v0.13.0/Tutorial.zip',
			'https://evomics.org/wp-content/uploads/2018/01/tutorial-1.pdf'
		],
		examples: ['https://github.com/zhangchicool/speciesnetwork/tree/master/examples'],
		namespaces: ['speciesnetwork']
	},
	{
		software: 'beast2',
		name: 'morph-models',
		authors: ['Remco Bouckaert', 'Alexandra Gavryushkina'],
		shortDescription: 'The MK and MKv models for morphological data.',
		tags: ['Trait Evolution'],
		sampleTypes: ALL_SAMPLE_TYPES,
		dataTypes: ['traits'],
		model: 'substitutionModel',
		code: 'https://github.com/CompEvol/morph-models',
		paper: 'https://academic.oup.com/sysbio/article-abstract/50/6/913/1628902',
		tutorials: ['https://www.beast2.org/morphological-models/'],
		examples: [
			'https://github.com/CompEvol/morph-models/tree/master/examples',
			'https://taming-the-beast.org/tutorials/Total-Evidence-Tutorial/'
		],
		namespaces: ['morphmodels']
	},
	{
		software: 'beast2',
		name: 'GEO_SPHERE',
		authors: ['Remco Bouckaert'],
		shortDescription: 'Model geographical evolution on a sphere.',
		tags: ['Geographical Evolution'],
		sampleTypes: ['species', 'pathogens', 'languages'],
		dataTypes: ALL_DATA_TYPES,
		model: 'substitutionModel',
		code: 'https://github.com/BEAST2-Dev/beast-geo',
		paper: 'https://www.biorxiv.org/content/10.1101/016311v1',
		tutorials: [
			'https://github.com/BEAST2-Dev/beast-geo/releases/download/v1.1.0/phylogeography_s.0.1.2.pdf',
			'https://tgvaughan.github.io/talks/MolEcolBio/'
		],
		examples: ['https://github.com/BEAST2-Dev/beast-geo/tree/master/examples'],
		namespaces: ['sphericalGeo']
	},
	{
		software: 'beast2',
		name: 'break-away',
		authors: ['Remco Bouckaert', 'Claire Bowern', 'Quentin Atkinson'],
		shortDescription:
			'Model geographical evolution where one of the populations stays in the same place.',
		tags: ['Geographical Evolution'],
		sampleTypes: ['species', 'pathogens', 'languages'],
		dataTypes: ALL_DATA_TYPES,
		model: 'substitutionModel',
		code: 'https://github.com/rbouckaert/break-away',
		paper: 'https://www.nature.com/articles/s41559-018-0489-3',
		tutorials: [
			'https://github.com/rbouckaert/break-away/wiki',
			'https://www.beast2.org/2018/03/12/break-away-phylogeography.html'
		],
		examples: ['https://github.com/rbouckaert/break-away/tree/master/examples'],
		namespaces: ['breakaway']
	},
	{
		software: 'beast2',
		name: 'SSM',
		authors: ['Remco Bouckaert', 'Dong Xie'],
		shortDescription: 'Use a wide range of substitution models.',
		tags: ['Substitution Models'],
		sampleTypes: ALL_SAMPLE_TYPES,
		dataTypes: ['nucleotides', 'snps'],
		model: 'substitutionModel',
		code: 'https://github.com/BEAST2-Dev/substmodels',
		paper: 'https://doi.org/10.5281/zenodo.995740',
		tutorials: ['https://github.com/BEAST2-Dev/substmodels?tab=readme-ov-file#installation'],
		examples: [],
		namespaces: ['substmodels']
	},
	{
		software: 'beast2',
		name: 'MGSM',
		authors: ['Remco Bouckaert', 'Peter Lockhart'],
		shortDescription: 'Capture extreme rate differences between lineages.',
		tags: ['Heterotachy'],
		sampleTypes: ALL_SAMPLE_TYPES,
		dataTypes: ALL_DATA_TYPES,
		model: 'substitutionModel',
		code: 'https://github.com/BEAST2-Dev/MGSM',
		paper: 'https://www.biorxiv.org/content/10.1101/018101v1.full.pdf',
		tutorials: ['https://github.com/BEAST2-Dev/MGSM/wiki'],
		examples: [],
		namespaces: ['mgsm']
	},
	{
		software: 'beast2',
		name: 'sampled-ancestors',
		authors: ['Alexandra Gavryushkina', 'David Welch', 'Tanja Stadler', 'Alexei J. Drummond'],
		shortDescription: 'Use the birth-death skyline model with sampled ancestors.',
		tags: ['Sampled Ancestors'],
		sampleTypes: ALL_SAMPLE_TYPES,
		dataTypes: ALL_DATA_TYPES,
		model: 'treePrior',
		code: 'https://github.com/CompEvol/sampled-ancestors',
		paper: 'https://journals.plos.org/ploscompbiol/article?id=10.1371/journal.pcbi.1003919',
		tutorials: [
			'https://www.beast2.org/2014/09/02/sampled-ancestor-trees-in-beast.html',
			'https://beast2-dev.github.io/beast-docs/beast2/FBDDating/tutorial.html',
			'https://www.beast2.org/divergence-dating-with-sampled-ancestors-fbd-model/'
		],
		examples: ['https://github.com/CompEvol/sampled-ancestors/tree/master/examples'],
		namespaces: ['sa']
	},
	{
		software: 'beast2',
		name: 'STACEY',
		authors: ['Graham Jones'],
		shortDescription: 'Infer species delimitations and the species tree.',
		tags: ['Multispecies Coalescent', 'Species Delimitation'],
		sampleTypes: ['species'],
		dataTypes: ALL_DATA_TYPES,
		model: 'treeLikelihood',
		code: 'https://github.com/Graham853/STACEY',
		paper: 'https://link.springer.com/article/10.1007/s00285-016-1034-0',
		tutorials: [
			'http://indriid.com/2014/Rcode-for-XML-for-STACEY.zip',
			'https://www.beast2.org/2015/06/02/species-delimitation-with-beast.html'
		],
		examples: [],
		namespaces: ['stacey']
	},
	{
		software: 'beast2',
		name: 'ClaDS',
		authors: ['Joëlle Barido-Sottani', 'Hélène Morlon'],
		shortDescription: 'Model progressive rate changes along a phylogeny.',
		tags: ['Continuous Rate Changes'],
		sampleTypes: ALL_SAMPLE_TYPES,
		dataTypes: ALL_DATA_TYPES,
		model: 'treePrior',
		code: 'https://bitbucket.org/bjoelle/clads/src/main/',
		paper: 'https://academic.oup.com/sysbio/article/72/5/1180/7158798',
		tutorials: ['https://taming-the-beast.org/tutorials/ClaDS-tutorial/'],
		examples: ['https://bitbucket.org/bjoelle/clads/src/main/examples/'],
		namespaces: ['clads']
	},
	{
		software: 'beast2',
		name: 'MSBD',
		authors: ['Joëlle Barido-Sottani', 'Timothy G Vaughan', 'Tanja Stadler'],
		shortDescription: 'Use structured models with an unknown number of types.',
		tags: ['Multiple Types'],
		sampleTypes: ALL_SAMPLE_TYPES,
		dataTypes: ALL_DATA_TYPES,
		model: 'treePrior',
		code: 'https://bitbucket.org/bjoelle/msbd/',
		paper: 'https://academic.oup.com/sysbio/article/69/5/973/5762626',
		tutorials: ['https://taming-the-beast.org/tutorials/MSBD-tutorial/'],
		examples: ['https://bitbucket.org/bjoelle/msbd/src/main/examples/'],
		namespaces: ['msbd']
	},
	{
		software: 'beast2',
		name: 'MASCOT',
		authors: ['Nicola F Müller', 'David Rasmussen', 'Tanja Stadler'],
		shortDescription: 'Efficiently infer the structured coalescent model.',
		tags: ['Multiple Types'],
		sampleTypes: ALL_SAMPLE_TYPES,
		dataTypes: ALL_DATA_TYPES,
		model: 'treePrior',
		code: 'https://github.com/nicfel/Mascot',
		paper: 'https://doi.org/10.1093/bioinformatics/bty406',
		tutorials: [
			'https://taming-the-beast.org/tutorials/Mascot-Tutorial/',
			'https://github.com/nicfel/MascotSkyline-Tutorial'
		],
		examples: ['https://github.com/nicfel/Mascot/tree/master/examples'],
		namespaces: ['mascot']
	},
	{
		software: 'beast2',
		name: 'Recombination',
		authors: ['Nicola F. Müller', 'Kathryn E. Kistler', 'Trevor Bedford'],
		shortDescription: 'Model recombination events with phylogenetic networks.',
		tags: ['Recombination'],
		sampleTypes: ['species', 'pathogens'],
		dataTypes: ['nucleotides', 'proteins'],
		model: 'treePrior',
		code: 'https://github.com/nicfel/Recombination',
		paper: 'https://www.nature.com/articles/s41467-022-31749-8',
		tutorials: ['https://github.com/nicfel/Recombination-Tutorial'],
		examples: ['https://github.com/nicfel/Recombination/tree/master/examples'],
		namespaces: ['recombination']
	},
	{
		software: 'beast2',
		name: 'CoalRe',
		authors: ['Nicola F. Müller', 'Ugnė Stolz', 'Gytis Dudas', 'Timothy G. Vaughan'],
		shortDescription: 'Model reassortment events with phylogenetic networks.',
		tags: ['Reassortment'],
		sampleTypes: ['species', 'pathogens'],
		dataTypes: ['nucleotides', 'proteins'],
		model: 'treePrior',
		code: 'https://github.com/nicfel/CoalRe/',
		paper: 'https://www.pnas.org/doi/10.1073/pnas.1918304117',
		tutorials: ['https://taming-the-beast.org/tutorials/Reassortment-Tutorial/'],
		examples: [],
		namespaces: ['coalre']
	},
	{
		software: 'beast2',
		name: 'CodonSubstModels',
		authors: ['Dong Xie', 'Remco Bouckaert'],
		shortDescription: 'Use codon-based substitution models.',
		tags: ['Codons'],
		sampleTypes: ALL_SAMPLE_TYPES,
		dataTypes: ['nucleotides'],
		model: 'substitutionModel',
		code: 'https://github.com/BEAST2-Dev/codonsubstmodels',
		tutorials: ['https://github.com/BEAST2-Dev/codonsubstmodels/wiki'],
		examples: ['https://github.com/BEAST2-Dev/codonsubstmodels/tree/master/examples'],
		namespaces: ['codonmodels']
	},
	{
		software: 'beast2',
		name: 'TimTam',
		authors: [
			'Alexander Eugene Zarebski',
			'Louis du Plessis',
			'Kris Varun Parag',
			'Oliver George Pybus'
		],
		shortDescription: 'Combine genetic and epidemiological data to model outbreaks.',
		tags: [],
		sampleTypes: ['pathogens'],
		dataTypes: ['nucleotides', 'proteins', 'snps'],
		website: 'https://aezarebski.github.io/timtam/index.html',
		model: 'treeLikelihood',
		code: 'https://github.com/aezarebski/timtam2',
		paper: 'https://doi.org/10.1371/journal.pcbi.1009805',
		tutorials: ['https://github.com/aezarebski/timtam2/wiki'],
		examples: ['https://github.com/aezarebski/timtam2/tree/main/examples'],
		namespaces: ['timtam']
	},
	{
		software: 'beast2',
		name: 'BEASTvntr',
		authors: ['Arjun Dhawan', 'Remco Bouckaert'],
		shortDescription: 'Model variable number of tandem repeats (VNTRs).',
		tags: ['VNTRs'],
		sampleTypes: ['species', 'pathogens', 'single-cells'],
		dataTypes: ALL_DATA_TYPES,
		model: 'substitutionModel',
		code: 'https://github.com/arjun-1/BEASTvntr',
		paper:
			'https://academic.oup.com/genetics/article-abstract/168/1/383/6059470?redirectedFrom=fulltext',
		tutorials: ['https://github.com/arjun-1/BEASTvntr/tree/master#example'],
		examples: [],
		namespaces: ['vntr']
	},
	{
		software: 'beast2',
		name: 'PIQMEE',
		authors: ['Veronika Boskova', 'Tanja Stadler'],
		shortDescription: 'Increase efficiency when working with duplicate sequences.',
		tags: [],
		sampleTypes: ['species', 'pathogens', 'single-cells'],
		dataTypes: ALL_DATA_TYPES,
		model: 'treeLikelihood',
		code: 'https://github.com/boskovav/piqmee',
		paper: 'https://doi.org/10.1093/molbev/msaa136',
		tutorials: [
			'https://taming-the-beast.org/tutorials/PIQMEE-Tutorial/',
			'https://www.beast2.org/2020/10/06/PIQMEE.html'
		],
		examples: ['https://github.com/boskovav/piqmee/tree/master/examples'],
		namespaces: ['piqmee']
	}
	// {
	// 	software: 'beast2',
	// 	name: '',
	// 	authors: [],
	// 	shortDescription: '',
	// 	tags: [],
	// 	sampleTypes: ALL_SAMPLE_TYPES,
	// 	dataTypes: ALL_DATA_TYPES,
	// 	model: '',
	// 	code: '',
	// 	paper: '',
	// 	tutorials: [],
	// 	examples: [],
	// 	namespaces: []
	// }
];

export const NAME_TO_MODEL = Object.fromEntries(MODELS.map((x) => [x.name, x]));
