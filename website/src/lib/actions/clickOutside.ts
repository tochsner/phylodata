// Action to handle clicks outside of an element
export function clickOutside(node: HTMLElement, handler: () => void) {
	const handleClick = (event: MouseEvent) => {
		if (node && !node.contains(event.target as Node) && !event.defaultPrevented) {
			handler();
		}
	};

	document.addEventListener('click', handleClick, true);

	return {
		destroy() {
			document.removeEventListener('click', handleClick, true);
		},
		update(newHandler: () => void) {
			handler = newHandler;
		}
	};
}
