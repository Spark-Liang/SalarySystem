package com.lzh.salarysystem.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.lzh.salarysystem.service.impl.WorkRecordServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class WorkRecordService_logWorkStart {

	protected WorkRecordService SUT = new WorkRecordServiceImpl();
	
	@Test
	public void can_log_work_start_when_given_the_right_empID(){
		
	}
}
