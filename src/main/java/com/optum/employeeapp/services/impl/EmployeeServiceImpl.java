package com.optum.employeeapp.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optum.employeeapp.dao.EmployeeInfoRepository;
import com.optum.employeeapp.models.CreateEmployeeRequest;
import com.optum.employeeapp.models.Employee;
import com.optum.employeeapp.services.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private EmployeeInfoRepository employeeInfoRepository;

	

	@Override
	public void addTheEmployee(CreateEmployeeRequest req) {
		Employee emp = new Employee(req.getName(), req.getAge(), req.getAddress());
		employeeInfoRepository.save(emp);
	}

	@Override
	public Employee getEmployeeByName(String name) {
		return employeeInfoRepository.findByName(name);
	}

	@Override
	public List<String> getAllEmployeesList() {
		return employeeInfoRepository.getAllEmployeesNames();
	}

}
