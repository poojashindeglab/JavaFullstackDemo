package com.example.fullstack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.example.fullstack.autowiring.Employee;
import com.example.fullstack.autowiringByType.Student;
import com.example.fullstack.coupling.Provider;
import com.example.fullstack.coupling.UserDataBaseProvider;
import com.example.fullstack.coupling.UserManager;
import com.example.fullstack.coupling.WebServiceProvider;

public class FullstackApplication {

	public static void main(String[] args) {
		System.out.println("-----Spring Framework Examples-----");
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationBeanContext.xml");
		MyBean bean = (MyBean) context.getBean("mybean");
		System.out.println(bean);
		
		Car car = (Car) context.getBean("car");
		car.display();
		Bike bike = context.getBean("bike", Bike.class);
		bike.display();
		
		
		/////////loose Coupling/////////
		System.out.println("-----Loose Coupling Example-----");
		Provider provider = new WebServiceProvider();
		UserManager manager = new UserManager(provider);
		System.out.println(manager.displayUserInfo());
		
		Provider dataProvider = new UserDataBaseProvider();
		UserManager dataManager = new UserManager(dataProvider);
		System.out.println(dataManager.displayUserInfo());
		
		
		
		
		
		
		
		//With IOC//
		System.out.println("-----IOC Example-----");
		ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationIoclooseCoupling.xml");
		UserManager userManagerNewDataBaseProvider = (UserManager) appContext.getBean("userManagerNewDataBaseProvider");
		System.out.println(userManagerNewDataBaseProvider.displayUserInfo());
		UserManager userManagerWebServiceProvider = (UserManager) appContext.getBean("userManagerWebServiceProvider");
		System.out.println(userManagerWebServiceProvider.displayUserInfo());
		UserManager userManagerDataBaseProvider = (UserManager) appContext.getBean("userManagerDataBaseProvider");
		System.out.println(userManagerDataBaseProvider.displayUserInfo());
		
		
		//Autowired example//
		System.out.println("-----Autowired Example-----");
		ApplicationContext autowiredContext = new ClassPathXmlApplicationContext("applicationContextAutoWire.xml");
		Employee employeeByName = (Employee) autowiredContext.getBean("employee");
		employeeByName.display();
		
		//Autowired by type//
        Student studentByType = (Student) autowiredContext.getBean("studentByType");
        studentByType.display();
        
        //Autowired by constructor//
        com.example.fullstack.autowiringByConstructor.Bike bikeByConstructor = (com.example.fullstack.autowiringByConstructor.Bike) autowiredContext.getBean("bike");
        bikeByConstructor.display();
        
        //Component Scan//
        System.out.println("-----Component Scan Example-----");
        ApplicationContext componentScanContext = new ClassPathXmlApplicationContext("applicationContextComponentScan.xml");
        com.example.fullstack.componentscan.Employee componentScanEmployee = (com.example.fullstack.componentscan.Employee) componentScanContext.getBean("employee");
        System.out.println(componentScanEmployee.toString());
	}

}
