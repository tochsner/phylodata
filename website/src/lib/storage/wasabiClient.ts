export async function uploadToWasabi(file: File, uploadUrl: string) {
	const formData = new FormData();
	formData.append('file', file);

	const uploadResult = await fetch(uploadUrl, {
		method: 'PUT',
		body: formData
	});

	if (!uploadResult.ok) {
		throw Error('Upload failed.');
	}
}
