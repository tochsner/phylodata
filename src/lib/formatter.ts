const numberFormatter = new Intl.NumberFormat('de-CH');
const currencyFormatter = new Intl.NumberFormat('de-CH', { style: 'currency', currency: 'CHF' });
const dateTimeFormatter = new Intl.DateTimeFormat('de-CH', {
	dateStyle: 'short',
	timeStyle: 'short'
});
const dateFormatter = new Intl.DateTimeFormat('de-CH', { dateStyle: 'short' });

const formatNumber = (value: number) => {
	return numberFormatter.format(value);
};

const formatCurrency = (value: number) => {
	return currencyFormatter.format(value);
};

const formatDateTime = (value: Date) => {
	return dateTimeFormatter.format(value);
};

const formatDate = (value: Date | string) => {
	if (typeof value === 'string') {
		value = new Date(value);
	}

	return dateFormatter.format(value);
};

export { formatNumber, formatCurrency, formatDate, formatDateTime };
