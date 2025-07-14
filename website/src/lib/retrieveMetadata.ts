import { validateNonEditableSchema, validateEditableSchema } from './validateSchemas';

/**
 * Extracts JSON files from a zip file and returns the first one as a parsed object
 * @param blob The zip file as a Blob
 * @returns A Promise that resolves to the parsed JSON object or null if no JSON file is found
 */
export async function retrieveMetadata(files: Blob[]) {
	let editableMetadata;
	let nonEditableMetadata;

	for (const file of files) {
		const jsonText = await file.text();

		try {
			const jsonData = JSON.parse(jsonText);
			if (validateEditableSchema(jsonData)) {
				editableMetadata = jsonData;
			} else if (validateNonEditableSchema(jsonData)) {
				nonEditableMetadata = jsonData;
			}
		} catch {
			continue;
		}
	}

	if (!editableMetadata || !nonEditableMetadata) {
		return undefined;
	}

	return { editableMetadata, nonEditableMetadata };
}
