import { supabase } from '$lib/supabaseClient';
import type { PaperWithExperiments } from '$lib/types';
import type { PageServerLoad } from './$types';

export const load: PageServerLoad = async ({ params }) => {
	const { paperId } = params;

	const { data, error } = await supabase
		.from('papers')
		.select(
			`
    *,
    experiments (
      *,
      files (
        id,
        name,
        type,
        version,
        size_bytes,
        md5
      ),
      samples (
        id,
        scientific_name,
        type,
        classification,
        data
      ),
      evolutionary_models (
        id,
        name,
        model_type,
        documentation_url
      )
    )
  `
		)
		.eq('id', paperId)
		.single();

	if (error) {
		console.error(error);
		throw new Error('No experiment found');
	}

	if (!data) {
		throw new Error('No experiment found');
	}

	return data as PaperWithExperiments;
};
