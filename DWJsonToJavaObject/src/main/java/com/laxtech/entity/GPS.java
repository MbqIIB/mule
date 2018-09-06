package com.laxtech.entity;

public class GPS {
	private String description;
	private int price;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "GPS [description=" + description + ", price=" + price + "]";
	}
	
}
