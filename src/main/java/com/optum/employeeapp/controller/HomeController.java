package com.optum.employeeapp.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {
	

	
	@RequestMapping("/")
	public String message(){
		return "Hello from employeeapp";
	}

}
