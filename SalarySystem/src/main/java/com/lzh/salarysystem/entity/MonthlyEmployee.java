package com.lzh.salarysystem.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("Monthly")
//@DiscriminatorColumn(name = "EmployeeType")
public class MonthlyEmployee extends Employee{
	
	@Column(scale = 20,precision = 4)
	private BigDecimal monthlySalary;
	
	public static final BigDecimal MIN_MONTH_SALARY = new BigDecimal(0);
	
	public BigDecimal getMonthlySalary() {
		return monthlySalary;
	}
	public void setMonthlySalary(BigDecimal monthlySalary) {
		this.monthlySalary = monthlySalary;
	}

}
