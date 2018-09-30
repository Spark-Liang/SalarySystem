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
	private Long id;
	
	@Embedded
	private WorkRecordInfo info;
	
	/*--------------------------- Field Start ---------------------------------*/
	
	/*--------------------------- logic Start ---------------------------------*/
	
	public boolean isCurrentWorkRecord() {
		return info.isCurrentWorkRecordInfo();
	}
	
	/*--------------------------- logic end ---------------------------------*/
	
	/*--------------------------- getter and setter Start ---------------------------------*/
	
	public WorkRecordInfo getInfo() {
		return info;
	}

	public void setInfo(WorkRecordInfo workRecordInfo) {
		this.info = workRecordInfo;
	}

	public Long getWorkRecordId() {
		return id;
	}

	/*--------------------------- getter and setter End ---------------------------------*/
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WorkRecord [");
		if (id != null) {
			builder.append("workRecordId=");
			builder.append(id);
			builder.append(", ");
		}
		if (info != null) {
			builder.append("workRecordInfo=");
			builder.append(info);
		}
		builder.append("]");
		return builder.toString();
	}
	
}
