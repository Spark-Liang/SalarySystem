package com.lzh.salarysystem.security;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
public class TargetControllerForTest {
	
	@RequestMapping("test/test_security_request")
	@ResponseBody
	public Object testTarget() {
		
		return "success";
	}
}
