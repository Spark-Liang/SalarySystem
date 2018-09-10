package com.lzh.salarysystem.common.util;

import com.lzh.salarysystem.service.Validator;

public interface ValidatorHelper {
	<T> Validator<T> getValidator(Class<? extends Validator<T>> clazz);
}
