import { fail } from '@sveltejs/kit';
import { insertPaperWithExperiments } from '$lib/db/insertPaperWithExperiments';
import type { PaperWithExperiments } from '$lib/types';
import { getWasabiUploadUrl } from '$lib/storage/wasabi';
import type { Actions } from './$types';

export const actions = {
	default: async (event) => {
		const formData = await event.request.formData();
		const rawPaperData = formData.get('paperData') as string;
		const rawFileNames = formData.get('fileNames') as string;

		if (!rawPaperData) {
			return fail(400);
		}

		const paperData = JSON.parse(rawPaperData) as PaperWithExperiments;
		const result = await insertPaperWithExperiments(paperData);

		if (!result.success || !result.insertedIds) {
			return fail(400);
		}

		const fileNames = JSON.parse(rawFileNames) as string[];
		const uploadUrls = await Promise.all(
			fileNames.map(
				async (name) =>
					await getWasabiUploadUrl(
						`${paperData.experiments[0].experiment.humanReadableId}/${paperData.experiments[0].experiment.version}/${name}`
					)
			)
		);

		return { uploadUrls };
	}
} satisfies Actions;
