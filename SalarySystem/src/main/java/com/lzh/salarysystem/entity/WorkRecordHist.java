package com.lzh.salarysystem.entity;

import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class WorkRecordHist {
	
	
	/*---------------------------------- Field Start --------------------------------------*/
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Embedded
	private WorkRecordInfo workRecordInfo;
	
	@Temporal(TemporalType.DATE)
	private Date workDate;
	
	/*----------------------------------  Field End  --------------------------------------*/
	
	/*---------------------------------- logic Start --------------------------------------*/
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((workDate == null) ? 0 : workDate.hashCode());
		result = prime * result + ((workRecordInfo == null) ? 0 : workRecordInfo.hashCode());
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
		if (workRecordInfo == null) {
			if (other.workRecordInfo != null)
				return false;
		} else if (!workRecordInfo.equals(other.workRecordInfo))
			return false;
		return true;
	}
	
	/*----------------------------------- logic End ---------------------------------------*/
	
	/*--------------------------- getter and setter Start ---------------------------------*/
	
	public WorkRecordInfo getWorkRecordInfo() {
		return workRecordInfo;
	}

	public void setWorkRecordInfo(WorkRecordInfo workRecordInfo) {
		this.workRecordInfo = workRecordInfo;
	}

	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}

	public Long getId() {
		return id;
	}
	
	/*---------------------------  getter and setter End  ---------------------------------*/
	
}
