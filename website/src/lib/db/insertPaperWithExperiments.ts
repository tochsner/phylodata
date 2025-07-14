import { supabase } from '../supabase';
import type { PaperWithExperiments } from '../types';

interface InsertResult {
	success: boolean;
	error?: string;
	insertedIds?: {
		paperDoi: string;
		experimentIds: string[];
	};
}

/**
 * Inserts a PaperWithExperiments object into the database with rollback support
 * @param paperWithExperiments - The data to insert
 * @returns Promise<InsertResult> - Result of the operation
 */
export async function insertPaperWithExperiments(
	paperWithExperiments: PaperWithExperiments
): Promise<InsertResult> {
	const insertedIds: string[] = [];
	const experimentIds: string[] = [];

	try {
		const { data: paperData, error: paperError } = await supabase
			.from('papers')
			.insert({
				...paperWithExperiments.paper
			})
			.select();

		if (paperError) {
			throw new Error(`Failed to insert paper: ${paperError.message}`);
		}

		for (const experimentData of paperWithExperiments.experiments) {
			const experiment = experimentData.experiment;

			const experimentId = experiment.id || crypto.randomUUID();
			experimentIds.push(experimentId);

			const { error: experimentError } = await supabase.from('experiments').insert({
				...experiment,
				id: experimentId,
				paperDoi: paperWithExperiments.paper.doi
			});

			if (experimentError) {
				throw new Error(`Failed to insert experiment: ${experimentError.message}`);
			}

			if (experimentData.files && experimentData.files.length > 0) {
				const filesData = experimentData.files.map((file) => ({
					...file,
					experimentId: experimentId
				}));

				const { error: filesError } = await supabase.from('files').insert(filesData);

				if (filesError) {
					throw new Error(`Failed to insert files: ${filesError.message}`);
				}
			}

			if (experimentData.trees) {
				const { error: treesError } = await supabase.from('trees').insert({
					...experimentData.trees,
					experimentId: experimentId
				});

				if (treesError) {
					throw new Error(`Failed to insert trees: ${treesError.message}`);
				}
			}

			if (experimentData.evolutionaryModel) {
				const { data: evolutionaryModelData, error: evolutionaryModelError } = await supabase
					.from('evolutionaryModels')
					.insert({
						experimentId: experimentId
					})
					.select();

				if (evolutionaryModelError) {
					throw new Error(`Failed to insert evolutionary model: ${evolutionaryModelError.message}`);
				}

				const evolutionaryModelId = evolutionaryModelData[0].id;

				if (
					experimentData.evolutionaryModel.models &&
					experimentData.evolutionaryModel.models.length > 0
				) {
					const componentsData = experimentData.evolutionaryModel.models.map((component) => ({
						...component,
						evolutionaryModelId: evolutionaryModelId
					}));

					const { error: componentsError } = await supabase
						.from('evolutionaryModelComponents')
						.insert(componentsData);

					if (componentsError) {
						throw new Error(
							`Failed to insert evolutionary model components: ${componentsError.message}`
						);
					}
				}
			}

			if (experimentData.metadata) {
				const { error: metadataError } = await supabase.from('metadata').insert({
					...experimentData.metadata,
					experimentId: experimentId
				});

				if (metadataError) {
					throw new Error(`Failed to insert metadata: ${metadataError.message}`);
				}
			}

			if (experimentData.samples && experimentData.samples.length > 0) {
				for (const sample of experimentData.samples) {
					const sampleId = sample.id || crypto.randomUUID();

					const { classification, data, ...sampleProps } = sample;
					const { error: sampleError } = await supabase.from('samples').insert({
						...sampleProps,
						id: sampleId,
						experimentId: experimentId
					});

					if (sampleError) {
						throw new Error(`Failed to insert sample: ${sampleError.message}`);
					}

					if (classification && classification.length > 0) {
						const classificationData = classification.map((classificationEntry) => ({
							...classificationEntry,
							id: classificationEntry.id || crypto.randomUUID(),
							sampleId: sampleId
						}));

						const { error: classificationError } = await supabase
							.from('classifications')
							.insert(classificationData);

						if (classificationError) {
							throw new Error(
								`Failed to insert classification entries: ${classificationError.message}`
							);
						}
					}

					if (data && data.length > 0) {
						const sampleDataEntries = data.map((dataEntry) => ({
							...dataEntry,
							sampleId: sampleId
						}));

						const { error: sampleDataError } = await supabase
							.from('sampleData')
							.insert(sampleDataEntries);

						if (sampleDataError) {
							throw new Error(`Failed to insert sample data: ${sampleDataError.message}`);
						}
					}
				}
			}
		}

		return {
			success: true,
			insertedIds: {
				paperDoi: paperWithExperiments.paper.doi,
				experimentIds: experimentIds
			}
		};
	} catch (error) {
		await rollbackInsert(paperWithExperiments.paper.doi, experimentIds);

		return {
			success: false,
			error: error instanceof Error ? error.message : 'Unknown error occurred'
		};
	}
}

/**
 * Rollback function to clean up inserted data on failure
 * @param paperDoi - The DOI of the paper to rollback
 * @param experimentIds - Array of experiment IDs to rollback
 */
async function rollbackInsert(paperDoi: string, experimentIds: string[]): Promise<void> {
	try {
		// Delete in reverse dependency order

		// Delete all data related to experiments
		for (const experimentId of experimentIds) {
			// Get sample IDs for this experiment
			const { data: samples } = await supabase
				.from('samples')
				.select('id')
				.eq('experimentId', experimentId);

			if (samples && samples.length > 0) {
				const sampleIds = samples.map((sample) => sample.id);

				// Delete sample data
				await supabase.from('sampleData').delete().in('sampleId', sampleIds);

				// Delete classification entries
				await supabase.from('classificationEntries').delete().in('sampleId', sampleIds);

				// Delete samples
				await supabase.from('samples').delete().eq('experimentId', experimentId);
			}

			// Get evolutionary model IDs for this experiment
			const { data: evolutionaryModels } = await supabase
				.from('evolutionaryModels')
				.select('id')
				.eq('experimentId', experimentId);

			if (evolutionaryModels && evolutionaryModels.length > 0) {
				const evolutionaryModelIds = evolutionaryModels.map((model) => model.id);

				// Delete evolutionary model components
				await supabase
					.from('evolutionaryModelComponents')
					.delete()
					.in('evolutionaryModelId', evolutionaryModelIds);

				// Delete evolutionary models
				await supabase.from('evolutionaryModels').delete().eq('experimentId', experimentId);
			}

			// Delete other experiment-related data
			await supabase.from('metadata').delete().eq('experimentId', experimentId);
			await supabase.from('trees').delete().eq('experimentId', experimentId);
			await supabase.from('files').delete().eq('experimentId', experimentId);

			// Delete the experiment itself
			await supabase.from('experiments').delete().eq('id', experimentId);
		}

		// Finally, delete the paper
		await supabase.from('papers').delete().eq('doi', paperDoi);
	} catch (rollbackError) {
		console.error('Error during rollback:', rollbackError);
	}
}
