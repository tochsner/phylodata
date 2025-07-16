import {
	S3Client,
	PutObjectCommand,
	GetObjectCommand,
	ListObjectsV2Command
} from '@aws-sdk/client-s3';

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

/**
 * Returns the subfolders in the folder with the given key.
 * (Note that like s3, Wasabi does not technically have folder but just prefixes.
 * This returns all common prefixes delimited by an "/".)
 */
export async function getWasabiSubfolders(key: string): Promise<string[]> {
	const command = new ListObjectsV2Command({
		Bucket: WASABI_BUCKET,
		Prefix: `${key}/`,
		Delimiter: '/'
	});
	const response = await s3Client.send(command);
	const keys =
		response.CommonPrefixes?.map((obj) => {
			let prefix = obj.Prefix;
			// prefix is "key/name/". we only return "name"
			return prefix?.substring(key.length + 1, prefix.length - 1);
		}).filter((obj) => obj != undefined) || [];
	return keys;
}
