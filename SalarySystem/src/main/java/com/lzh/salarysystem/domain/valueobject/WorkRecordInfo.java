package com.lzh.salarysystem.domain.valueobject;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.lzh.salarysystem.domain.entity.HourlyEmployee;

@Embeddable
public class WorkRecordInfo {
	/*---------------------------------- Field Start --------------------------------------*/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empID", updatable = false ,nullable = false)
	private HourlyEmployee employee;
	
	@Column(nullable = false)
	private LocalTime startTime;
	
	private LocalTime endTime;
	
	/*----------------------------------  Field End  --------------------------------------*/
	
	/*---------------------------------- logic Start --------------------------------------*/
	
	public boolean isCurrentWorkRecordInfo() {
		return this.startTime != null 
				&& this.endTime == null;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employee == null) ? 0 : employee.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
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
		WorkRecordInfo other = (WorkRecordInfo) obj;
		if (employee == null) {
			if (other.employee != null)
				return false;
		} else if (!employee.equals(other.employee))
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		return true;
	}
	
	/*----------------------------------- logic End ---------------------------------------*/
	
	/*--------------------------- getter and setter Start ---------------------------------*/
	
	public HourlyEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(HourlyEmployee employee) {
		this.employee = employee;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}
	
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	
	/*---------------------------  getter and setter End  ---------------------------------*/
}
