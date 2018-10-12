package com.optum.employeeapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.optum.employeeapp.models.Employee;


@Repository
public interface EmployeeInfoRepository extends CrudRepository<Employee, Long> {

	Employee findByName(String name);
	
	@Query("SELECT name FROM Employee")
	List<String> getAllEmployeesNames();
}
