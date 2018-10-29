package com.lzh.salarysystem.exception;

public interface ErrorCodeConstants {
	
	ErrorCode USERNAME_ISNULL = new ErrorCode("E00001", "input username is null");
	ErrorCode USERNAME_IS_DUPLICATED = new ErrorCode("E00002", "input username is duplicated");
	ErrorCode PASSWORD_ISNULL = new ErrorCode("E00003", "input password is null");
}
