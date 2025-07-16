/**
 * Uploads the given file to Wasabi using the presigned upload url. This may be
 * called on the client side.
 */
export async function uploadToWasabi(file: File, presignedUploadUrl: string) {
	const uploadResult = await fetch(presignedUploadUrl, {
		method: 'PUT',
		body: file,
		headers: {
			'Content-Type': file.type
		}
	});

	if (!uploadResult.ok) {
		throw Error('Upload failed.');
	}
}
