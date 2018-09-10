package com.lzh.salarysystem.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "Employee")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "EmployeeType")
@DiscriminatorValue("Base")
public class Employee {
	
	public static final int MAX_ADDRESS_LENGTH = 1024;

	public static final int MAX_NAME_LENGTH = 32;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer empID;
	
	@Column(length = MAX_NAME_LENGTH,nullable = false)
	private String name;
	
	@Column(length = MAX_ADDRESS_LENGTH,nullable = false)
	private String address;

	public Integer getEmpID() {
		return empID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
