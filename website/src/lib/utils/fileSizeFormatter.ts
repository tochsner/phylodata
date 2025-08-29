import { formatNumber } from './formatter';

export function formatFileSize(sizeBytes: number): string {
	if (sizeBytes < 1000) {
		return `${formatNumber(sizeBytes)} bytes`;
	} else if (sizeBytes < 1_000_000) {
		return `${formatNumber(Math.round(sizeBytes / 1000))} KB`;
	} else if (sizeBytes < 1_000_000_000) {
		return `${formatNumber(Math.round(sizeBytes / 1_000_000))} MB`;
	} else {
		return `${formatNumber(Math.round(sizeBytes / 1_000_000_000))} GB`;
	}
}
