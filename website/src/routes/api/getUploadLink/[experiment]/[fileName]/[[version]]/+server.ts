import type { RequestHandler } from './$types';
import { getWasabiUploadUrl, getWasabiSubfolders } from '$lib/storage/wasabi';

/**
 * Creates a temporary presigned upload URL for the given experiment, version and file name.
 * If no version is given, the most recent one is used.
 * If the given version is -1, the next version will be used.
 */
export const GET: RequestHandler = async ({ params }) => {
	let { experiment, version, fileName } = params;

	fileName = decodeURIComponent(fileName);

	if (!version) {
		version = await getMostRecentExperimentVersion(experiment);
	} else if (version === '-1') {
		version = await getMostRecentExperimentVersion(experiment);
		version = (parseInt(version) + 1).toString();
	}

	const presignedUploadUrl = await getWasabiUploadUrl(`${experiment}/${version}/${fileName}`);
	return new Response(presignedUploadUrl);
};

async function getMostRecentExperimentVersion(experiment: string): Promise<string> {
	const versionStrings = await getWasabiSubfolders(experiment);
	return versionStrings.sort((a, b) => {
		const numA = parseInt(a.replace(/[^0-9]/g, ''));
		const numB = parseInt(b.replace(/[^0-9]/g, ''));
		return numB - numA;
	})[0];
}
