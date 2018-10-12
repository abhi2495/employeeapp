package com.optum.employeeapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

import com.keyholesoftware.troublemaker.client.EnableTroubleMaker;



@SpringBootApplication
@EnableDiscoveryClient
@EnableTroubleMaker
@ComponentScan("com.optum.employeeapp")
public class EmployeeappApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeappApplication.class, args);
	}
	 @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        return application.sources(EmployeeappApplication.class);
	    }
}
