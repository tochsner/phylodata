import { getPaperWithExperiments } from '$lib/db/getPaperWithExperiments';
import type { PageServerLoad } from './$types';

export const load: PageServerLoad = async ({ params }) => {
	const { paperDoi } = params;
	return await getPaperWithExperiments(paperDoi);
};
