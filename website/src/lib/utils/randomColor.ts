export function getRandomColor(seed: string) {
	const hash = Array.from(seed).reduce((acc, char) => acc + char.charCodeAt(0), 0);
	const hue = hash % 360;

	// This curve reduces saturation for visually intense hues like green
	const baseSaturation = 50 - 20 * Math.cos(((hue - 120) * Math.PI) / 180);

	return `hsl(${hue} ${baseSaturation + 5} 55)`;
}
