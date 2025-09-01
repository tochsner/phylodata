import type { PaperWithExperiments } from '$lib/types';

const MAX_CHARACTERS = 2_000;

/**
 * Trims all sample data to have a maximal data length for efficiency.
 */
export function prunePaperWithExperiments(paper: PaperWithExperiments) {
	for (const experiment of paper.experiments) {
		for (const sample of experiment.samples) {
			for (const data of sample.sampleData) {
				data.data = data.data.slice(0, MAX_CHARACTERS);
			}
		}
	}
}
