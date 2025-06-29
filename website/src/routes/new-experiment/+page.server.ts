import { uploadFileToWasabi } from '$lib/wasabi';
import { fail } from '@sveltejs/kit';
import type { Actions } from './$types';

export const actions = {
	default: async (event) => {
		const formData = await event.request.formData();
		const file = formData.get('file') as File;
		const title = formData.get('title') as string;

		if (!file || !title) {
			return fail(400);
		}

		try {
			await uploadFileToWasabi(file, title);
		} catch {
			return fail(400);
		}
	}
} satisfies Actions;
