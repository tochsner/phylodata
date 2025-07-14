import { fail } from '@sveltejs/kit';
import { insertPaperWithExperiments } from '$lib/db';
import type { PaperWithExperiments } from '$lib/types';
import { getWasabiUploadUrl } from '$lib/wasabi';
import type { Actions } from './$types';

export const actions = {
	default: async (event) => {
		const formData = await event.request.formData();
		const rawPaperData = formData.get('paperData') as string;
		const numUploadFiles = Number(formData.get('numUploadFiles') as string);

		if (!rawPaperData) {
			return fail(400);
		}

		// const paperData = JSON.parse(rawPaperData) as PaperWithExperiments;
		// const result = await insertPaperWithExperiments(paperData);

		// if (!result.success) {
		// 	return fail(400);
		// }

		// const { paperDoi, experimentId } = result;
		// const key = `${paperDoi}/${experimentId}`;

		const uploadUrls = await Promise.all(
			new Array(numUploadFiles).map(async () => await getWasabiUploadUrl(key))
		);

		return { uploadUrls };
	}
} satisfies Actions;
