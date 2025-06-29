import type { Sample } from './types';

export function getCommonClassifications(samples: Sample[]) {
	const allClassifications = samples
		.flatMap((sample) => sample.classification)
		.map((classification) => classification.scientificName);

	const classificationCounts: Record<string, number> = {};
	for (const classification of allClassifications) {
		classificationCounts[classification] = classificationCounts[classification]
			? classificationCounts[classification] + 1
			: 1;
	}

	const threshold = samples.length / 2;
	return Object.entries(classificationCounts)
		.filter((data) => data[1] >= threshold)
		.map((data) => data[0]);
}
