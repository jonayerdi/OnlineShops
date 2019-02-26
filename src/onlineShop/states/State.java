package onlineShop.states;

public enum State {
	Start,
	Catalog,
	ProductDetails,
	// #if Search
	ProductSearch,
	// #endif
	CartContent,
	OrderSummary,
	PaymentChoice,
	// #if BankAccount
	BankAccount,
	// #endif
	// #if ECoins
	ECoins,
	// #endif
	// #if CreditCard
	CreditCard,
	// #endif
}
