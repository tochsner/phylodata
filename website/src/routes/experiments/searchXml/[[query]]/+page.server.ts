import { supabase } from '$lib/db/supabase';
import type { PageServerLoad } from '../$types';

export type PaperWithXml = {
	title: string;
	year: number;
	authors: string[];
	doi: string;
	experiments: {
		title?: string;
		files: {
			type: string;
			name: string;
			humanReadableId: string;
			matches?: {
				lineNumberStart: number;
				lineNumberMatch: number;
				lines: string[];
			}[];
			presignedUrl?: string;
		}[];
		version: number;
	}[];
};

const getPapers = async () => {
	let data = await supabase
		.from('papers')
		.select(
			`
		title,
		year,
		authors,
		doi,
		experiments!experiments_paperDoi_fkey (
			version,
			files!inner (
				type,
				name,
				humanReadableId
			),
			title
		)
   `
		)
		.eq('experiments.files.type', 'beast2Configuration');

	if (!data.data || data.data.length === 0) return [];

	return data.data as unknown as PaperWithXml[];
};

export const load: PageServerLoad = async ({ url }) => {
	return {
		papers: getPapers()
	};
};
