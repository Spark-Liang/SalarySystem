package com.lzh.salarysystem.service;

public interface Validator<T> {
	void validate(T target);
}
