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
	
	public Employee() {}

	public Employee(Integer empID) {
		this.id = empID;
	}
	
	/*---------------------------------- Field Start --------------------------------------*/
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = MAX_NAME_LENGTH,nullable = false)
	private String name;
	
	@Column(length = MAX_ADDRESS_LENGTH,nullable = false)
	private String address;
	
	/*----------------------------------  Field End  --------------------------------------*/
	
	/*---------------------------------- logic Start --------------------------------------*/
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	/*----------------------------------- logic End ---------------------------------------*/
	
	/*--------------------------- getter and setter Start ---------------------------------*/
	
	public Integer getId() {
		return id;
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
	
	/*---------------------------  getter and setter End  ---------------------------------*/
	
	
}
