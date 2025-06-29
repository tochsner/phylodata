import { supabase } from './supabaseClient';
import type { PaperWithExperiments } from './types';

type InsertedIDs = {
	paperId?: string;
	experimentIds: string[];
	fileIds: string[];
	sampleIds: string[];
	sampleDataIds: string[];
	classificationIds: string[];
	modelIds: string[];
};

/**
 * Insert a PaperWithExperiments object into Supabase with rollback on failure
 */
export async function insertPaperWithExperiments(paperData: PaperWithExperiments) {
	// we recall the ids of the new elements to be able to roll back if necessary
	const insertedIds: InsertedIDs = {
		paperId: undefined,
		experimentIds: [],
		fileIds: [],
		sampleIds: [],
		sampleDataIds: [],
		classificationIds: [],
		modelIds: []
	};

	try {
		// insert the paper
		const { data: paper, error: paperError } = await supabase
			.from('PaperWithExperiments')
			.insert({
				...paperData,
				id: undefined,
				experiments: undefined
			})
			.select('id')
			.single();

		if (paperError) throw new Error(`Failed to insert paper: ${paperError.message}`);
		insertedIds.paperId = paper.id;

		for (const experimentData of paperData.experiments) {
			const { files, samples, evolutionaryModels, ...experimentFields } = experimentData;

			// insert experiment
			const { data: experiment, error: experimentError } = await supabase
				.from('Experiment')
				.insert({
					...experimentFields,
					id: undefined,
					paperId: paper.id
				})
				.select('id')
				.single();

			if (experimentError)
				throw new Error(`Failed to insert experiment: ${experimentError.message}`);
			insertedIds.experimentIds.push(experiment.id);

			// insert files (bulk)
			if (files?.length > 0) {
				const { data: fileResults, error: filesError } = await supabase
					.from('File')
					.insert(files.map((file) => ({ ...file, experimentId: experiment.id })))
					.select('id');

				if (filesError) throw new Error(`Failed to insert files: ${filesError.message}`);
				insertedIds.fileIds.push(...fileResults.map((f) => f.id));
			}

			// insert evolutionary models (bulk)
			if (evolutionaryModels?.length > 0) {
				const { data: modelResults, error: modelsError } = await supabase
					.from('EvolutionaryModelComponent')
					.insert(evolutionaryModels.map((model) => ({ ...model, experimentId: experiment.id })))
					.select('id');

				if (modelsError)
					throw new Error(`Failed to insert evolutionary models: ${modelsError.message}`);
				insertedIds.modelIds.push(...modelResults.map((m) => m.id));
			}

			for (const sampleData of samples || []) {
				const { data: sampleDataArray, classification, ...sampleFields } = sampleData;

				// insert sample
				const { data: sample, error: sampleError } = await supabase
					.from('Sample')
					.insert({
						...sampleFields,
						id: undefined,
						experimentId: experiment.id
					})
					.select('id')
					.single();

				if (sampleError) throw new Error(`Failed to insert sample: ${sampleError.message}`);
				insertedIds.sampleIds.push(sample.id);

				// insert sample data (bulk)
				if (sampleDataArray?.length > 0) {
					const { data: sampleDataResults, error: sampleDataError } = await supabase
						.from('SampleData')
						.insert(sampleDataArray.map((data) => ({ ...data, sampleId: sample.id })))
						.select('id');

					if (sampleDataError)
						throw new Error(`Failed to insert sample data: ${sampleDataError.message}`);
					insertedIds.sampleDataIds.push(...sampleDataResults.map((sd) => sd.id));
				}

				// Insert classification entries (bulk)
				if (classification?.length > 0) {
					const { data: classificationResults, error: classificationError } = await supabase
						.from('ClassificationEntry')
						.insert(
							classification.map((entry) => ({
								sampleId: sample.id,
								classificationId: entry.classificationId,
								idType: entry.idType,
								scientificName: entry.scientificName
							}))
						)
						.select('id');

					if (classificationError)
						throw new Error(`Failed to insert classifications: ${classificationError.message}`);
					insertedIds.classificationIds.push(...classificationResults.map((c) => c.id));
				}
			}
		}

		return { success: true, paperId: insertedIds.paperId, experimentId: insertedIds.experimentIds };
	} catch (error) {
		console.error('Error inserting paper with experiments, rolling back...', error);

		await rollbackInserts(insertedIds);

		return { success: false };
	}
}

/**
 * Rollback function to delete all inserted records
 */
async function rollbackInserts(insertedIds: InsertedIDs) {
	const rollbackErrors = [];

	try {
		// Delete in reverse order of dependencies (children first)

		// Delete Classification Entries
		if (insertedIds.classificationIds.length > 0) {
			const { error } = await supabase
				.from('ClassificationEntry')
				.delete()
				.in('id', insertedIds.classificationIds);
			if (error) rollbackErrors.push(`Failed to rollback classifications: ${error.message}`);
		}

		// Delete Sample Data
		if (insertedIds.sampleDataIds.length > 0) {
			const { error } = await supabase
				.from('SampleData')
				.delete()
				.in('id', insertedIds.sampleDataIds);
			if (error) rollbackErrors.push(`Failed to rollback sample data: ${error.message}`);
		}

		// Delete Samples
		if (insertedIds.sampleIds.length > 0) {
			const { error } = await supabase.from('Sample').delete().in('id', insertedIds.sampleIds);
			if (error) rollbackErrors.push(`Failed to rollback samples: ${error.message}`);
		}

		// Delete Evolutionary Model Components
		if (insertedIds.modelIds.length > 0) {
			const { error } = await supabase
				.from('EvolutionaryModelComponent')
				.delete()
				.in('id', insertedIds.modelIds);
			if (error) rollbackErrors.push(`Failed to rollback evolutionary models: ${error.message}`);
		}

		// Delete Files
		if (insertedIds.fileIds.length > 0) {
			const { error } = await supabase.from('File').delete().in('id', insertedIds.fileIds);
			if (error) rollbackErrors.push(`Failed to rollback files: ${error.message}`);
		}

		// Delete Experiments
		if (insertedIds.experimentIds.length > 0) {
			const { error } = await supabase
				.from('Experiment')
				.delete()
				.in('id', insertedIds.experimentIds);
			if (error) rollbackErrors.push(`Failed to rollback experiments: ${error.message}`);
		}

		// Delete Paper (last)
		if (insertedIds.paperId) {
			const { error } = await supabase
				.from('PaperWithExperiments')
				.delete()
				.eq('id', insertedIds.paperId);
			if (error) rollbackErrors.push(`Failed to rollback paper: ${error.message}`);
		}

		if (rollbackErrors.length > 0) {
			console.error('Rollback completed with errors:', rollbackErrors);
		} else {
			console.log('Rollback completed successfully');
		}
	} catch (rollbackError) {
		console.error('Critical error during rollback:', rollbackError);
	}
}
