import Ajv from 'ajv';
import AjvFormats from 'ajv-formats';

import editableSchema from './editableSchema.json';
import nonEditableSchema from './nonEditableSchema.json';

const ajv = new Ajv();
AjvFormats(ajv);

const validateEditable = ajv.compile(editableSchema);
export function validateEditableSchema(data: any): boolean {
	return validateEditable(data);
}

const validateNonEditable = ajv.compile(nonEditableSchema);
export function validateNonEditableSchema(data: any): boolean {
	return validateNonEditable(data);
}
