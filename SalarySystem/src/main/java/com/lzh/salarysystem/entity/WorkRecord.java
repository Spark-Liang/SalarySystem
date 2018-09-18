package com.lzh.salarysystem.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class WorkRecord {
	
	/*--------------------------- Field Start ---------------------------------*/
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long workRecordId;
	
	@Embedded
	private WorkRecordInfo workRecordInfo;
	
	/*--------------------------- Field Start ---------------------------------*/
	
	/*--------------------------- logic Start ---------------------------------*/
	
	public boolean isCurrentWorkRecord() {
		return workRecordInfo.isCurrentWorkRecordInfo();
	}
	
	/*--------------------------- logic end ---------------------------------*/
	
	/*--------------------------- getter and setter Start ---------------------------------*/
	
	public WorkRecordInfo getWorkRecordInfo() {
		return workRecordInfo;
	}

	public void setWorkRecordInfo(WorkRecordInfo workRecordInfo) {
		this.workRecordInfo = workRecordInfo;
	}

	public Long getWorkRecordId() {
		return workRecordId;
	}

	/*--------------------------- getter and setter End ---------------------------------*/
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WorkRecord [");
		if (workRecordId != null) {
			builder.append("workRecordId=");
			builder.append(workRecordId);
			builder.append(", ");
		}
		if (workRecordInfo != null) {
			builder.append("workRecordInfo=");
			builder.append(workRecordInfo);
		}
		builder.append("]");
		return builder.toString();
	}
	
}
