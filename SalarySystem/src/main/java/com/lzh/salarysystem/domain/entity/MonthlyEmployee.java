package com.lzh.salarysystem.domain.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Monthly")
public class MonthlyEmployee extends Employee{
	
	public static final BigDecimal MIN_MONTH_SALARY = new BigDecimal(0);
	
	public MonthlyEmployee() {}

	public MonthlyEmployee(Integer empID) {
		super(empID);
	}
	
	/*---------------------------------- Field Start --------------------------------------*/
	
	@Column(precision = 20,scale = 4)
	private BigDecimal monthlySalary;
	
	/*----------------------------------  Field End  --------------------------------------*/
	
	/*---------------------------------- logic Start --------------------------------------*/
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((monthlySalary == null) ? 0 : monthlySalary.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof MonthlyEmployee))
			return false;
		MonthlyEmployee other = (MonthlyEmployee) obj;
		if (monthlySalary == null) {
			if (other.monthlySalary != null)
				return false;
		} else if (!monthlySalary.equals(other.monthlySalary))
			return false;
		return true;
	}
	
	/*----------------------------------- logic End ---------------------------------------*/
	
	/*--------------------------- getter and setter Start ---------------------------------*/
	
	public BigDecimal getMonthlySalary() {
		return monthlySalary;
	}
	public void setMonthlySalary(BigDecimal monthlySalary) {
		this.monthlySalary = monthlySalary;
	}

	/*---------------------------  getter and setter End  ---------------------------------*/
	
	
	
}
