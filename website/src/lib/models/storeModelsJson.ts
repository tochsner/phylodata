import { MODELS } from './models';

import * as fs from 'fs';

fs.writeFileSync(
	'../python/phylodata/process/evolutionary_model/models.json',
	JSON.stringify(MODELS, null, 2)
);
