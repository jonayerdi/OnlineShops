package onlineShop.data;

public class PaymentData {
	public final PaymentType type;
	public final String number;
	public PaymentData(PaymentType type, String number) {
		this.type = type;
		this.number = number;
	}
}
