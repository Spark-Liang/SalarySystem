package com.lzh.salarysystem.service.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.lzh.salarysystem.repository.WorkRecordRepository;
import com.lzh.salarysystem.service.WorkRecordService;

public class WorkRecordServiceTest {
	
	@Mock
	private WorkRecordRepository workRecordRepository;
	
	@InjectMocks
	private WorkRecordService SUT = new WorkRecordServiceImpl();
	
}
