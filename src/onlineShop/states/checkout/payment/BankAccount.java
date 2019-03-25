// #condition BankAccount
package onlineShop.states.checkout.payment;

import io.IO;
import onlineShop.data.PaymentData;
import onlineShop.data.PaymentType;

public class BankAccount implements Runnable {
	private IO io;
	private PaymentData paymentData;
	
	public BankAccount(IO io) {
		this.io = io;
	}

	@Override
	public void run() {
		this.io.writeLine("Enter account number >> ");
		String number = this.io.readLine();
		this.paymentData = new PaymentData(PaymentType.BankAccount, number);
	}
	
	public PaymentData getPaymentData() {
		return this.paymentData;
	}
}
