import type { RequestHandler } from './$types';

import { supabase } from '$lib/db/supabase';

/**
 * Returns the data corresponding to the sample data id.
 */
export const GET: RequestHandler = async ({ params }) => {
	let { sampleDataId } = params;

	const data = await supabase.from('sampleData').select('data').eq('id', sampleDataId).single();

	return new Response(data.data?.data || '');
};
