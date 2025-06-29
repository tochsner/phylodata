import Ajv from 'ajv';
import AjvFormats from 'ajv-formats';

import schema from './schema.json';

const ajv = new Ajv();
AjvFormats(ajv);
const validate = ajv.compile(schema);

export function validateSchema(data: any): boolean {
	return validate(data);
}
