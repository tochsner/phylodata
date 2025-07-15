import type { RequestHandler } from './$types';
import { getWasabiDownloadUrl } from '$lib/storage/wasabi';
import { json } from '@sveltejs/kit';

export const GET: RequestHandler = async ({ params }) => {
	const { experiment, version } = params;

	const editableUrl = await getWasabiDownloadUrl(
		`${experiment}/${version}/editable_phylodata_metadata.json`
	);
	const nonEditableUrl = await getWasabiDownloadUrl(
		`${experiment}/${version}/non_editable_phylodata_metadata.json`
	);

	return json({
		editableUrl,
		nonEditableUrl
	});
};
