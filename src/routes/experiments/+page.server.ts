import { supabase } from '$lib/supabaseClient';
import type { PaperWithExperiments } from '$lib/types';
import type { PageServerLoad } from './$types';
import type { Actions } from './$types';

export const load: PageServerLoad = async () => {
	const { data, error } = await supabase.from('papers').select(
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
	);

	if (error) {
		console.error(error);
		throw new Error('No experiment found');
	}

	if (data === undefined) {
		throw new Error('No experiment found');
	}

	return { papers: data } as { papers: PaperWithExperiments[] };
};

export const actions = {
	filter: async () => {
		console.log('Filter data');
		return 200;
	}
} satisfies Actions;
