import { titleCase } from './utils/titleCase';
import type { ClassificationEntry, Sample } from './types';

/**
 * @returns whether the given samples contain a NCBI taxonomy ID classification.
 */
export function hasNCBIClassification(samples: Sample[]) {
	for (const sample of samples) {
		for (const classification of sample.classification) {
			if (classification.idType === 'ncbiTaxonomyId') return true;
		}
	}
	return false;
}

/**
 * @returns a list of the main classification strings for the given samples.
 * The main classifications are defined as the most specific classification entry
 * describing at most half of the samples. Returned are the most specific scientific and
 * most specific common names if found.
 */
export function getMainClassifications(samples: Sample[]) {
	const mainClassifications = [];

	const mainScientificClassification = getSpecificMainClassification(
		samples,
		(x) => x.scientificName
	);
	if (mainScientificClassification) mainClassifications.push(mainScientificClassification);

	const mainCommonNameClassification = getSpecificMainClassification(samples, (x) => x.commonName);
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
	const mainClassification = Object.entries(classificationCounts)
		.filter((data) => data[1] >= threshold)
		.map((data) => data[0])[0];

	return mainClassification ? titleCase(mainClassification) : undefined;
}
