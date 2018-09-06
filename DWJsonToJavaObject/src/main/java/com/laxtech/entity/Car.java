package com.laxtech.entity;

import java.util.List;
import java.util.Map;

public class Car {
	
	private int id;
	private String description;
	private int price;
	private boolean tested;
	private GPS gps;
	private Camera camera;
	private List<Map<String, Integer>> interiorAccessory;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public boolean isTested() {
		return tested;
	}
	public void setTested(boolean tested) {
		this.tested = tested;
	}
	public GPS getGps() {
		return gps;
	}
	public void setGps(GPS gps) {
		this.gps = gps;
	}
	public Camera getCamera() {
		return camera;
	}
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	public List<Map<String, Integer>> getInteriorAccessory() {
		return interiorAccessory;
	}
	public void setInteriorAccessory(List<Map<String, Integer>> interiorAccessory) {
		this.interiorAccessory = interiorAccessory;
	}

	
	

}
