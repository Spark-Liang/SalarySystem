package com.lzh.salarysystem.service;

import com.lzh.salarysystem.entity.Employee;

public interface EmployeeService {

	void add(Employee employee);

	void delete(Integer empID);
}
