package com.lzh.salarysystem.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@DiscriminatorValue("Sale")
public class SaleEmployee extends MonthlyEmployee {
	
	@Column(scale = 20,precision = 2)
	private BigDecimal commissionRate;
	
	public static final BigDecimal MIN_COMMISSION_RATE = new BigDecimal(0);
	
	public BigDecimal getCommissionRate() {
		return commissionRate;
	}
	public void setCommissionRate(BigDecimal commissionRate) {
		this.commissionRate = commissionRate;
	}
}
