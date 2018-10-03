package com.lzh.salarysystem.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lzh.salarysystem.common.util.ValidatorHelper;
import com.lzh.salarysystem.domain.entity.Employee;
import com.lzh.salarysystem.exception.EmployeeCantFoundById;
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
	@Transactional
	public void add(Employee employee) {
		validatorHelper.getValidator(ValidateEmployeeBeforeAdd.class).validate(employee);
		employeeRepository.save(employee);
	}

	@Override
	@Transactional
	public void delete(Integer empID) {
		Employee employeeToDelete = employeeRepository.findOne(empID);
		if(employeeToDelete == null) {
			throw new EmployeeCantFoundById();
		}
		employeeRepository.delete(employeeToDelete);
	}

}
