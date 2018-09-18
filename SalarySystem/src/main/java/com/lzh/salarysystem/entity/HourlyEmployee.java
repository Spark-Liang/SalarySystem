package com.lzh.salarysystem.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Hourly")
public class HourlyEmployee extends Employee {
	
	public static final BigDecimal MIN_HOURLY_RATE = new BigDecimal(0);
	
	public HourlyEmployee() {}
	
	public HourlyEmployee(Integer empID) {
		super(empID);
	}
	
	/*---------------------------------- Field Start --------------------------------------*/
	
	@Column(scale = 10,precision = 4)
	private BigDecimal hourlyRate;
	
	/*----------------------------------  Field End  --------------------------------------*/
	
	/*---------------------------------- logic Start --------------------------------------*/
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((hourlyRate == null) ? 0 : hourlyRate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof HourlyEmployee))
			return false;
		HourlyEmployee other = (HourlyEmployee) obj;
		if (hourlyRate == null) {
			if (other.hourlyRate != null)
				return false;
		} else if (!hourlyRate.equals(other.hourlyRate))
			return false;
		return true;
	}
	
	/*----------------------------------- logic End ---------------------------------------*/
	
	/*--------------------------- getter and setter Start ---------------------------------*/
	
	public BigDecimal getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(BigDecimal hourlyRate) {
		this.hourlyRate = hourlyRate;
	}
	
	/*---------------------------  getter and setter End  ---------------------------------*/
	
}
