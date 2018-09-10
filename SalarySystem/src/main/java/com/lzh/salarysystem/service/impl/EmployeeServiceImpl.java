package com.lzh.salarysystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lzh.salarysystem.common.util.ValidatorHelper;
import com.lzh.salarysystem.entity.Employee;
import com.lzh.salarysystem.repository.EmployeeRepository;
import com.lzh.salarysystem.service.EmployeeService;
import com.lzh.salarysystem.service.validator.ValidateEmployeeBeforeAdd;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ValidatorHelper validatorHelper;
	
	@Override
	public void add(Employee employee) {
		validatorHelper.getValidator(ValidateEmployeeBeforeAdd.class).validate(employee);
		employeeRepository.save(employee);
	}

}
