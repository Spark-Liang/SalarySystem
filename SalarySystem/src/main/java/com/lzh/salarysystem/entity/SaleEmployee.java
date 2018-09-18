package com.lzh.salarysystem.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Sale")
public class SaleEmployee extends MonthlyEmployee {
	
	public static final BigDecimal MIN_COMMISSION_RATE = new BigDecimal(0);
	
	public SaleEmployee() {}
	
	public SaleEmployee(Integer empID) {
		super(empID);
	}
	
	/*---------------------------------- Field Start --------------------------------------*/
	
	@Column(scale = 20,precision = 2)
	private BigDecimal commissionRate;
	
	/*----------------------------------  Field End  --------------------------------------*/
	
	/*---------------------------------- logic Start --------------------------------------*/
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((commissionRate == null) ? 0 : commissionRate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof SaleEmployee))
			return false;
		SaleEmployee other = (SaleEmployee) obj;
		if (commissionRate == null) {
			if (other.commissionRate != null)
				return false;
		} else if (!commissionRate.equals(other.commissionRate))
			return false;
		return true;
	}
	
	/*----------------------------------- logic End ---------------------------------------*/
	
	/*--------------------------- getter and setter Start ---------------------------------*/
	
	public BigDecimal getCommissionRate() {
		return commissionRate;
	}
	public void setCommissionRate(BigDecimal commissionRate) {
		this.commissionRate = commissionRate;
	}

	/*---------------------------  getter and setter End  ---------------------------------*/
	
}
