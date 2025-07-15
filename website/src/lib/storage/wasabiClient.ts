/**
 * Uploads the given file to Wasabi using the presigned upload url. This may be
 * called on the client side.
 */
export async function uploadToWasabi(file: File, presignedUploadUrl: string) {
	const formData = new FormData();
	formData.append('file', file);

	const uploadResult = await fetch(presignedUploadUrl, {
		method: 'PUT',
		body: formData
	});

	if (!uploadResult.ok) {
		throw Error('Upload failed.');
	}
}
