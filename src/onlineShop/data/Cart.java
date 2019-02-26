package onlineShop.data;

import java.util.HashMap;
import java.util.Map;

public class Cart {
	private Map<String,Integer> products;
	
	public Cart() {
		this.products = new HashMap<String,Integer>();
	}
	
	public void addProductToCart(String product) {
		Integer oldValue = this.products.get(product);
		if(oldValue != null) {
			this.products.put(product, oldValue + 1);
		} else {
			this.products.put(product, 1);
		}
	}
	
	public boolean removeProductFromCart(String product) {
		Integer oldValue = this.products.get(product);
		if(oldValue != null) {
			if(oldValue == 1) {
				this.products.remove(product);
			} else {
				this.products.put(product, oldValue - 1);
			}
			return true;
		}
		return false;
	}
	
	public Map<String,Integer> getItems() {
		return this.products;
	}
	
	public int getProductCount(String product) {
		return this.products.getOrDefault(product, 0);
	}
}
