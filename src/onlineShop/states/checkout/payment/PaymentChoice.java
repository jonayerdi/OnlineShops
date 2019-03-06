package onlineShop.states.checkout.payment;

import input.InputReader;
import onlineShop.states.State;
import ui.Menu;

public class PaymentChoice implements Runnable {
	private Menu<State> menu;
	private State selection;
	
	public PaymentChoice(InputReader in) throws Exception {
		this.menu = new Menu<State>(in, " PAYMENT CHOICE ");
		// #if BankAccount
		this.menu.addEntry(State.BankAccount, "BANK ACCOUNT");
		// #endif
		// #if ECoins
		this.menu.addEntry(State.ECoins, "E-COINS");
		// #endif
		// #if CreditCard
		this.menu.addEntry(State.CreditCard, "CREDIT CARD");
		// #endif
		this.menu.addEntry(State.Start, "CANCEL ORDER");
	}
	
	@Override
	public void run() {
		this.selection = this.menu.show();
	}
	
	public State getSelection() {
		return selection;
	}
}
