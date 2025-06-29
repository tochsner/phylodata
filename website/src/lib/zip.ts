import {
	BlobReader,
	BlobWriter,
	TextReader,
	TextWriter,
	ZipReader,
	ZipWriter
} from '@zip.js/zip.js';
import { validateSchema } from './validateSchema';

/**
 * Extracts JSON files from a zip file and returns the first one as a parsed object
 * @param blob The zip file as a Blob
 * @returns A Promise that resolves to the parsed JSON object or null if no JSON file is found
 */
export async function retrieveJSON(blob: Blob): Promise<any | undefined> {
	const zipReader = new ZipReader(new BlobReader(blob));

	const entries = await zipReader.getEntries();

	for (const entry of entries) {
		if (!entry.filename.toLowerCase().endsWith('.json')) continue;
		if (!entry.getData) continue;

		const jsonText = await entry.getData(new TextWriter());
		const jsonData = JSON.parse(jsonText);

		if (!validateSchema(jsonData)) continue;

		return jsonData;
	}

	return undefined;
}
