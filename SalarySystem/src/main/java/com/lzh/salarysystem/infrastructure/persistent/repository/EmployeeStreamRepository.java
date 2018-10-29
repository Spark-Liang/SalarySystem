package com.lzh.salarysystem.infrastructure.persistent.repository;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lzh.salarysystem.domain.entity.Employee;

@Repository
public interface EmployeeStreamRepository extends JpaRepository<Employee, Integer>{
	
	Stream<Employee> findByNameContaining(String name);
}
