import AWS from 'aws-sdk';

import { WASABI_ACCESS_KEY, WASABI_SECRET_KEY } from '$env/static/private';

var wasabiEndpoint = new AWS.Endpoint('s3.wasabisys.com');
var s3 = new AWS.S3({
	endpoint: wasabiEndpoint,
	accessKeyId: WASABI_ACCESS_KEY,
	secretAccessKey: WASABI_SECRET_KEY
});
