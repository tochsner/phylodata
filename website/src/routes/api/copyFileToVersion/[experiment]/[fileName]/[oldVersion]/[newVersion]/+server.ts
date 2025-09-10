import type { RequestHandler } from './$types';
import { copyWasabiObject } from '$lib/storage/wasabi';

/**
 * Copies the file with the given name from the given old version to the given new version.
 */
export const GET: RequestHandler = async ({ params }) => {
	let { experiment, oldVersion, newVersion, fileName } = params;

	fileName = decodeURIComponent(fileName);

	await copyWasabiObject(
		`${experiment}/${oldVersion}/${fileName}`,
		`${experiment}/${newVersion}/${fileName}`
	);

	return new Response();
};
