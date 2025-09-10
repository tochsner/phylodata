import type { RequestHandler } from './$types';

import { getWasabiDownloadUrl, getWasabiSubfolders } from '$lib/storage/wasabi';
import { convertSchemasToType } from '$lib/types';
import { insertPaperWithExperiments } from '$lib/db/insertPaperWithExperiments';
import { error } from '@sveltejs/kit';

/**
 * Updates the DB entries for the given experiment based on the
 * files on Wasabi.
 */
export const GET: RequestHandler = async ({ params }) => {
	let { experimentId } = params;

	const currentVersion = await getMostRecentExperimentVersion(experimentId);

	const editableDownloadUrl = await getWasabiDownloadUrl(
		`${experimentId}/${currentVersion}/editable_phylodata_metadata.json`
	);
	const nonEditableDownloadUrl = await getWasabiDownloadUrl(
		`${experimentId}/${currentVersion}/non_editable_phylodata_metadata`
	);

	const editableMetadata = await (await fetch(editableDownloadUrl)).json();
	const nonEditableMetadata = await (await fetch(nonEditableDownloadUrl)).json();

	const mergedMetadata = convertSchemasToType(editableMetadata, nonEditableMetadata);

	const result = await insertPaperWithExperiments(mergedMetadata);

	if (!result.success || !result.insertedIds) {
		console.log(result);
		return error(400);
	}

	return new Response(JSON.stringify({ success: true }));
};

async function getMostRecentExperimentVersion(experiment: string): Promise<string> {
	const versionStrings = await getWasabiSubfolders(experiment);
	return versionStrings.sort((a, b) => {
		const numA = parseInt(a.replace(/[^0-9]/g, ''));
		const numB = parseInt(b.replace(/[^0-9]/g, ''));
		return numB - numA;
	})[0];
}
