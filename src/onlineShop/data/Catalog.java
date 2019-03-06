package onlineShop.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Catalog {
	private static final String ITEMS_FILENAME = "OnlineShopItems.txt";
	
	private List<String> orderedProducts;
	private Map<String,Double> products;
	
	public Catalog() throws FileNotFoundException {
		this.orderedProducts = new ArrayList<String>();
		this.products = new HashMap<String,Double>();
		Scanner in = new Scanner(new FileInputStream(ITEMS_FILENAME));
		while(in.hasNextLine()) {
			try {
				String[] data = in.nextLine().split("=");
				if(data.length == 2) {
					String productName = data[0].trim();
					double productPrice = Double.parseDouble(data[1].trim());
					this.orderedProducts.add(productName);
					this.products.put(productName, productPrice);
				}
			} finally { }
		}
		in.close();
	}
	
	public List<String> getProductNames() {
		return this.orderedProducts;
	}
	
	public Double getProductPrice(String product) {
		return this.products.get(product);
	}
}
