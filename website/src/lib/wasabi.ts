import { S3Client, PutObjectCommand } from '@aws-sdk/client-s3';

import { getSignedUrl } from '@aws-sdk/s3-request-presigner';

import { WASABI_ACCESS_KEY, WASABI_SECRET_KEY, WASABI_BUCKET } from '$env/static/private';

var s3Client = new S3Client({
	endpoint: 'https://s3.eu-central-2.wasabisys.com',
	region: 'eu-central-2',
	credentials: {
		accessKeyId: WASABI_ACCESS_KEY,
		secretAccessKey: WASABI_SECRET_KEY
	}
});

export async function getWasabiUploadUrl(key: string): Promise<string> {
	const command = new PutObjectCommand({
		Bucket: WASABI_BUCKET,
		Key: key
	});
	const uploadUrl = await getSignedUrl(s3Client, command, { expiresIn: 3600 });
	return uploadUrl;
}
