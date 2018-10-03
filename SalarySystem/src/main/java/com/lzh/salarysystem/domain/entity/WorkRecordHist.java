package com.lzh.salarysystem.domain.entity;

import java.time.LocalDate;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.lzh.salarysystem.domain.valueobject.WorkRecordInfo;

@Entity
public class WorkRecordHist {
	
	
	/*---------------------------------- Field Start --------------------------------------*/
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Embedded
	private WorkRecordInfo info;
	
	private LocalDate workDate;
	
	/*----------------------------------  Field End  --------------------------------------*/
	
	/*---------------------------------- logic Start --------------------------------------*/
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((workDate == null) ? 0 : workDate.hashCode());
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof WorkRecordHist))
			return false;
		WorkRecordHist other = (WorkRecordHist) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (workDate == null) {
			if (other.workDate != null)
				return false;
		} else if (!workDate.equals(other.workDate))
			return false;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		return true;
	}
	
	/*----------------------------------- logic End ---------------------------------------*/
	
	/*--------------------------- getter and setter Start ---------------------------------*/
	
	public WorkRecordInfo getInfo() {
		return info;
	}

	public void setInfo(WorkRecordInfo workRecordInfo) {
		this.info = workRecordInfo;
	}

	public LocalDate getWorkDate() {
		return workDate;
	}

	public void setWorkDate(LocalDate workDate) {
		this.workDate = workDate;
	}

	public Long getId() {
		return id;
	}
	
	/*---------------------------  getter and setter End  ---------------------------------*/
	
}
