package ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.IO;

public class Menu<T> {
	public static final String ASTERISKS = "**************************************************";
	
	private IO io;
	private String title;
	private List<String> textLines;
	private List<T> orderedKeys;
	private Map<T,String> entries;
	
	public Menu(IO io, String title) {
		this.io = io;
		this.title = title;
		this.textLines = new ArrayList<String>();
		this.orderedKeys = new ArrayList<T>();
		this.entries = new HashMap<T, String>();
	}
	
	public void addText(String text) {
		this.textLines.add(text);
	}
	
	public void addEntry(T key, String value) throws Exception {
		if(this.entries.containsKey(key)) {
			throw new Exception("Duplicate menu entry");
		}
		this.textLines.add((this.orderedKeys.size()+1) + ". " + value);
		this.entries.put(key, value);
		this.orderedKeys.add(key);
	}
	
	public T show() {
		String titleSidesAsterisks = repeat("*", (ASTERISKS.length() - this.title.length()) / 2);
		T selectedKey = null;
		int selectedChoice;
		do {
			this.io.writeLine(ASTERISKS);
			this.io.writeLine(titleSidesAsterisks + this.title + titleSidesAsterisks);
			this.io.writeLine(ASTERISKS);
			for(String line : this.textLines) {
				this.io.writeLine(line);
			}
			this.io.writeLine(ASTERISKS);
			this.io.write("Your choice >> ");
			try {
				selectedChoice = Integer.parseInt(this.io.readLine()) - 1;
				selectedKey = this.orderedKeys.get(selectedChoice);
			} catch(Exception e) {
			} finally {
				if(selectedKey == null) {
					this.io.writeLine("Please enter a valid choice");
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
