package com.example.fullstack.componentscan.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		Employee employee = context.getBean("employee",Employee.class);
		System.out.println(employee.toString());
	}
}
