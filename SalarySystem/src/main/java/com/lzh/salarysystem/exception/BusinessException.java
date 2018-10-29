package com.lzh.salarysystem.exception;

import java.util.Map;

public class BusinessException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String errorCode;
	
	private Map<String, Object> params;

	public BusinessException(ErrorCode errorCode) {
		this(errorCode, null, null, null);
	}
	
	public BusinessException(ErrorCode errorCode , String message) {
		this(errorCode, message, null, null);
	}
	
	public BusinessException(ErrorCode errorCode, Throwable cause) {
		this(errorCode, cause, null);
	}
	
	public BusinessException(ErrorCode errorCode, Map<String, Object> params) {
		this(errorCode,null,params);
	}
	
	public BusinessException(ErrorCode errorCode, Throwable cause, Map<String, Object>params) {
		this(errorCode, null, cause, params);
	}
	
	public BusinessException(ErrorCode errorCode,String message , Throwable cause, Map<String, Object> params) {
		super(message != null? message : errorCode.getDefaultMessage(), cause);
		this.errorCode = errorCode.getCode();
		this.params = params;
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((errorCode == null) ? 0 : errorCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BusinessException))
			return false;
		BusinessException other = (BusinessException) obj;
		if (errorCode == null) {
			if (other.errorCode != null)
				return false;
		} else if (!errorCode.equals(other.errorCode))
			return false;
		return true;
	}
	
	
}
