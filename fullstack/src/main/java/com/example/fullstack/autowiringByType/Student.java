package com.example.fullstack.autowiringByType;

public class Student {

	private Department department;

	public void setAddress(Department department) {
		this.department= department;
	}
	
	public void display() {
		System.out.println("Employee Department: " + department);
	}
}

