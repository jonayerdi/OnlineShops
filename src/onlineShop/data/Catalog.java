package onlineShop.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Catalog {
	private static final String ITEMS_FILENAME = "OnlineShopItems.txt";
	
	private Map<String,Double> products;
	
	public Catalog() throws FileNotFoundException {
		this.products = new HashMap<String,Double>();
		Scanner in = new Scanner(new FileInputStream(ITEMS_FILENAME));
		while(in.hasNextLine()) {
			try {
				String[] data = in.nextLine().split("=");
				if(data.length == 2) {
					// Do not allow the underscore '_' character on a product name
					this.products.put(data[0].trim().replace("_", ""), Double.parseDouble(data[1].trim()));
				}
			} finally { }
		}
		in.close();
	}
	
	public Map<String,Double> getProducts() {
		return this.products;
	}
	
	public Set<String> getProductNames() {
		return this.products.keySet();
	}
	
	public Double getProductPrice(String product) {
		return this.products.get(product);
	}
}
