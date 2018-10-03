package com.lzh.salarysystem.domain.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class TimeCard {
	
	public TimeCard() {};
	
	public TimeCard(Employee employee, LocalDate workDate, Integer hours) {
		super();
		this.key.employee = employee;
		this.key.workDate = workDate;
		this.hours = hours;
	}
	
	@Embeddable
	public static class TimeCardKey implements Serializable{

		private static final long serialVersionUID = 1L;
		
		public TimeCardKey() {}
		
		public TimeCardKey(Employee employee, LocalDate workDate) {
			super();
			this.employee = employee;
			this.workDate = workDate;
		}

		/*---------------------------------- Field Start --------------------------------------*/
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "embID",updatable = false,nullable = false)
		private Employee employee;
		
		private LocalDate workDate;
		
		/*----------------------------------  Field End  --------------------------------------*/
		
		/*---------------------------------- logic Start --------------------------------------*/
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((employee == null) ? 0 : employee.hashCode());
			result = prime * result + ((workDate == null) ? 0 : workDate.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof TimeCardKey))
				return false;
			TimeCardKey other = (TimeCardKey) obj;
			if (employee == null) {
				if (other.employee != null)
					return false;
			} else if (!employee.equals(other.employee))
				return false;
			if (workDate == null) {
				if (other.workDate != null)
					return false;
			} else if (!workDate.equals(other.workDate))
				return false;
			return true;
		}
		
		/*----------------------------------- logic End ---------------------------------------*/
		
		/*--------------------------- getter and setter Start ---------------------------------*/
		
		public Employee getEmployee() {
			return employee;
		}

		public LocalDate getWorkDate() {
			return workDate;
		}
		
		/*---------------------------  getter and setter End  ---------------------------------*/
	}

	/*---------------------------------- Field Start --------------------------------------*/
	@EmbeddedId
	private TimeCardKey key = new TimeCardKey();
	
	@Column(nullable = false,updatable = false)
	private Integer hours;

	/*----------------------------------  Field End  --------------------------------------*/
	
	/*---------------------------------- logic Start --------------------------------------*/
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key.employee == null) ? 0 : key.employee.hashCode());
		result = prime * result + ((hours == null) ? 0 : hours.hashCode());
		result = prime * result + ((key.workDate == null) ? 0 : key.workDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TimeCard))
			return false;
		TimeCard other = (TimeCard) obj;
		if (key.employee == null) {
			if (other.key.employee != null)
				return false;
		} else if (!key.employee.equals(other.key.employee))
			return false;
		if (hours == null) {
			if (other.hours != null)
				return false;
		} else if (!hours.equals(other.hours))
			return false;
		if (key.workDate == null) {
			if (other.key.workDate != null)
				return false;
		} else if (!key.workDate.equals(other.key.workDate))
			return false;
		return true;
	}
	
	/*----------------------------------- logic End ---------------------------------------*/
	
	/*--------------------------- getter and setter Start ---------------------------------*/
	
	public Employee getEmployee() {
		return key.employee;
	}

	public void setEmployee(Employee employee) {
		this.key.employee = employee;
	}

	public LocalDate getWorkDate() {
		return key.workDate;
	}

	public void setWorkDate(LocalDate workDate) {
		this.key.workDate = workDate;
	}

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}
	
	/*---------------------------  getter and setter End  ---------------------------------*/
}
