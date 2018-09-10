package com.lzh.salarysystem.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("Hourly")
//@DiscriminatorColumn(name = "EmployeeType")
public class HourlyEmployee extends Employee {
	
	@Column(scale = 10,precision = 4)
	private BigDecimal hourlyRate;
	
	public static final BigDecimal MIN_HOURLY_RATE = new BigDecimal(0);

	public BigDecimal getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(BigDecimal hourlyRate) {
		this.hourlyRate = hourlyRate;
	}
	
}
