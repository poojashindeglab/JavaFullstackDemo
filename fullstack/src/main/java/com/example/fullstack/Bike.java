package com.example.fullstack;


public class Bike {

	private Specification specification;

	public void setSpecification(Specification specification) {
		this.specification = specification;
	}

	public void display() {
		System.out.println("Bike Specification: " + specification);
	}
}
