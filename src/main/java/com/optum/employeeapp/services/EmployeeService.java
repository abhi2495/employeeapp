package com.optum.employeeapp.services;

import java.util.List;

import com.optum.employeeapp.models.CreateEmployeeRequest;
import com.optum.employeeapp.models.Employee;

public interface EmployeeService {

	public List<String> getAllEmployeesList();
	
	public void addTheEmployee(CreateEmployeeRequest req);
	
	public Employee getEmployeeByName(String name);
}
