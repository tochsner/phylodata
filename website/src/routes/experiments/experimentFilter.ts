import { type File } from '$lib/types';

export type ExperimentFilter = {
	searchString?: string;
	species?: string[];
	languages?: string[];
	evolutionaryModels?: string[];
	filesTypes?: File['type'][];
	rootedTrees?: boolean;
	unrootedTrees?: boolean;
	ultrametricTrees?: boolean;
	nonUltrametricTrees?: boolean;
};

export const getEmptyFilter = (): ExperimentFilter => ({});
