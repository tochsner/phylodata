import { browser } from '$app/environment';

/**
 * Detects the sample types for a given search query.
 */
export async function detectSampleTypes(searchQuery: string | null) {
	if (!searchQuery || !browser) return null;

	const response = await fetch(`api/detectSampleTypes/${searchQuery}`);
	const data = await response.json();

	return data;
}
