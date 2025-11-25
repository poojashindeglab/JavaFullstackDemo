package com.example.fullstack;

public class Specification {

	String model;
	String make;
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	@Override
	public String toString() {
		return "Specification [model=" + model + ", make=" + make + "]";
	}
	
}
