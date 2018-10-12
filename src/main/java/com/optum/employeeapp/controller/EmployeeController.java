package com.optum.employeeapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.optum.employeeapp.models.CreateEmployeeRequest;
import com.optum.employeeapp.models.Employee;
import com.optum.employeeapp.services.EmployeeService;

@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping(value="/getAllEmployees", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getAllEmployees(){
		return employeeService.getAllEmployeesList();
	}
	
	@RequestMapping(value = "/addEmployee", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void addEmployee(@RequestBody CreateEmployeeRequest req) {
		employeeService.addTheEmployee(req);
	}
	
	@RequestMapping(value = "/getEmployeeDetails/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Employee listUsersInvoices(@PathVariable("name") String name) {
		return employeeService.getEmployeeByName(name);
	}

}
