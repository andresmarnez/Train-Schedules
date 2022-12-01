package com.andresmarnez.app;

import com.andresmarnez.exceptions.CODE;
import com.andresmarnez.exceptions.TrainException;

import java.util.ArrayList;
import java.util.List;

public class Menu {

	private static Menu root;

	private final String title;
	private String text;
	private Menu previous;
	private List<Menu> nexts;

	public Menu(String title) {
		this.title = title;
	}

	public Menu(String title, Menu previous) {
		this.title = title;
		this.previous = previous;
		this.nexts = new ArrayList<>();
	}

	public Menu(String title, String text, Menu previous) {
		this.title = title;
		this.text = text;
		this.previous = previous;
		this.nexts = new ArrayList<>();
	}

	public static Menu getRoot() {
		return root;
	}

	public static void setRoot(Menu root) {
		Menu.root = root;
	}

	public String getTitle() {
		return title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Menu getPrevious() {
		return previous;
	}

	public void setPrevious(Menu previous) {
		this.previous = previous;
	}

	public Menu getOption(int num) throws TrainException{
		if (num >= 0 && num < nexts.size()) throw new TrainException("Accessing incorrect option of menu.",CODE.NEXT_MENU_FAILURE);
		return  nexts.get(num-1);
	}

	public void addMenu(Menu menu){
		nexts.add(menu);
		menu.setPrevious(this);
	}
}
