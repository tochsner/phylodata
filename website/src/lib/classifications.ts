import { titleCase } from './titleCase';
import type { ClassificationEntry, Sample } from './types';

export function getMainClassifications(samples: Sample[]) {
	const mainScientificClassification = getSpecificMainClassification(
		samples,
		(x) => x.scientificName
	);
	const mainCommonNameClassification = getSpecificMainClassification(samples, (x) => x.commonName);

	const mainClassifications = [];
	if (mainScientificClassification) mainClassifications.push(mainScientificClassification);
	if (mainCommonNameClassification) mainClassifications.push(mainCommonNameClassification);

	return mainClassifications;
}

function getSpecificMainClassification(
	samples: Sample[],
	predicate: (sample: ClassificationEntry) => string | undefined
) {
	const allClassifications = samples.flatMap((sample) => sample.classification).map(predicate);

	const classificationCounts: Record<string, number> = {};
	for (const classification of allClassifications) {
		if (!classification) continue;
		classificationCounts[classification] = classificationCounts[classification]
			? classificationCounts[classification] + 1
			: 1;
	}

	const threshold = samples.length / 2;
	return titleCase(
		Object.entries(classificationCounts)
			.filter((data) => data[1] >= threshold)
			.map((data) => data[0])[0]
	);
}
