package com.example.fullstack;

public class Car {

	private Specification specification;

	public Car(Specification specification) {
		super();
		this.specification = specification;
	}

	
	public void display() {
		System.out.println("Car Specification: " + specification);
	}
}

