package ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import input.InputReader;

public class Menu {
	public static final String ASTERISKS = "**************************************************";
	
	private InputReader in;
	private String title;
	private List<String> textLines;
	private List<String> orderedKeys;
	private Map<String,String> entries;
	
	public Menu(InputReader in, String title) {
		this.in = in;
		this.title = title;
		this.textLines = new ArrayList<String>();
		this.orderedKeys = new ArrayList<String>();
		this.entries = new HashMap<String, String>();
	}
	
	public void addText(String text) {
		this.textLines.add(text);
	}
	
	public void addEntry(String key, String value) throws Exception {
		if(this.entries.containsKey(key)) {
			throw new Exception("Duplicate menu entry");
		}
		this.textLines.add(this.orderedKeys.size() + ". " + value);
		this.entries.put(key, value);
		this.orderedKeys.add(key);
	}
	
	public String show() {
		String titleSidesAsterisks = repeat("*", (ASTERISKS.length() - this.title.length()) / 2);
		String selectedKey = null;
		int selectedChoice;
		do {
			System.out.println(ASTERISKS);
			System.out.println(titleSidesAsterisks + this.title + titleSidesAsterisks);
			System.out.println(ASTERISKS);
			for(String line : this.textLines) {
				System.out.println(line);
			}
			System.out.println(ASTERISKS);
			System.out.print("Your choice >> ");
			try {
				selectedChoice = Integer.parseInt(this.in.nextLine()) - 1;
				selectedKey = this.orderedKeys.get(selectedChoice);
			} catch(Exception e) {
			} finally {
				if(selectedKey == null) {
					System.out.println("Please enter a valid choice");
				}
			}
		} while(selectedKey == null);
		return selectedKey;
	}
	
	public static String repeat(String base, int count) {
		String result = "";
		for(int i = 0 ; i < count ; i++) {
			result += base;
		}
		return result;
	}
}
