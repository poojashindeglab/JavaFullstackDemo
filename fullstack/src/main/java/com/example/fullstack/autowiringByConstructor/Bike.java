package com.example.fullstack.autowiringByConstructor;


public class Bike {
	private Specification specification;

	public Bike(Specification specification) {
		super();
		this.specification = specification;
	}

	public void display() {
		System.out.println("Bike Specification: " + specification);
	}
}
