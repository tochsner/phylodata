import { validateNonEditableSchema, validateEditableSchema } from './schema/validateSchemas';

/**
 * Extracts the JSON metadata from the given list of files. Resolves to undefined if the files
 * do not contain both the editable and non-editable metadata.
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
		} catch (e) {
			continue;
		}
	}

	if (!editableMetadata || !nonEditableMetadata) {
		return undefined;
	}

	return { editableMetadata, nonEditableMetadata };
}
