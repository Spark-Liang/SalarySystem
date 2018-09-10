package com.lzh.salarysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lzh.salarysystem.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

}
