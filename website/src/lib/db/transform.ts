import type { PaperWithExperiments } from '$lib/types';

export function transformJoinedDataToPaperWithExperiment(joinedData: any): PaperWithExperiments {
	return {
		paper: {
			doi: joinedData.doi,
			title: joinedData.title,
			year: joinedData.year,
			authors: joinedData.authors,
			abstract: joinedData.abstract,
			bibtex: joinedData.bibtex,
			url: joinedData.url
		},
		// @ts-ignore
		experiments: joinedData.experiments.map((exp) => ({
			experiment: {
				type: exp.type,
				version: exp.version,
				title: exp.title,
				description: exp.description,
				humanReadableId: exp.humanReadableId,
				origin: exp.origin,
				uploadDate: exp.uploadDate,
				license: exp.license,
				id: exp.id
			},
			files: exp.files,
			trees: exp.trees[0],
			evolutionaryModels: exp.evolutionaryModels,
			metadata: exp.metadata,
			samples: exp.samples
		}))
	} as PaperWithExperiments;
}
