package com.lzh.salarysystem.service;

import java.util.Date;

public interface WorkRecordService {
	
	void logWorkStart(Integer empID);

	void logWorkEnd(Integer empID);

	void finishCurrentDateWorkRecord(Date workDate);
	
}
