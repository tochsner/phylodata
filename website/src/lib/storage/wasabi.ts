import { S3Client, PutObjectCommand, GetObjectCommand } from '@aws-sdk/client-s3';

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

/**
 * Creates a presigned Wasabi download URL for the given key. This should not be called on the
 * client side.
 */
export async function getWasabiDownloadUrl(key: string): Promise<string> {
	const command = new GetObjectCommand({
		Bucket: WASABI_BUCKET,
		Key: key
	});
	const uploadUrl = await getSignedUrl(s3Client, command, { expiresIn: 600 });
	return uploadUrl;
}

/**
 * Creates a presigned Wasabi upload URL for the given key. This should not be called on the
 * client side.
 */
export async function getWasabiUploadUrl(key: string): Promise<string> {
	const command = new PutObjectCommand({
		Bucket: WASABI_BUCKET,
		Key: key
	});
	const uploadUrl = await getSignedUrl(s3Client, command, { expiresIn: 600 });
	return uploadUrl;
}
