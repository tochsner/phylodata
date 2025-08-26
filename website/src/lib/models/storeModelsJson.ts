import { MODELS } from './models';

import * as fs from 'fs';

fs.writeFileSync(
	'../cli/phylodata/process/evolutionary_model/models.json',
	JSON.stringify(MODELS, null, 2)
);
