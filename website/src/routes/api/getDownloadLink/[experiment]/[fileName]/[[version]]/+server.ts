import type { RequestHandler } from './$types';
import { getWasabiDownloadUrl, getWasabiSubfolders } from '$lib/storage/wasabi';

/**
 * Creates a temporary presigned download URL for the given experiment, version and file name.
 * If no version is given, the most recent one is used.
 */
export const GET: RequestHandler = async ({ params }) => {
	let { experiment, version, fileName } = params;

	fileName = decodeURIComponent(fileName);

	if (!version) {
		version = await getMostRecentExperimentVersion(experiment);
	}

	const presignedDownloadUrl = await getWasabiDownloadUrl(`${experiment}/${version}/${fileName}`);
	return new Response(presignedDownloadUrl);
};

async function getMostRecentExperimentVersion(experiment: string): Promise<string> {
	const versionStrings = await getWasabiSubfolders(experiment);
	return versionStrings.sort((a, b) => {
		const numA = parseInt(a.replace(/[^0-9]/g, ''));
		const numB = parseInt(b.replace(/[^0-9]/g, ''));
		return numB - numA;
	})[0];
}
