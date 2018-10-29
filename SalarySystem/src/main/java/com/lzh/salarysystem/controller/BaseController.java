package com.lzh.salarysystem.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

public class BaseController {
	
	@ModelAttribute
	public void constructBasePath(Model model ,HttpServletRequest request) {
		String path = request.getContextPath();
		String bastPath = 
				request.getScheme() + "://" +
				request.getServerName() + ":" + 
				request.getServerPort() + path + "/";
		model.addAttribute("path", path);
		model.addAttribute("basePath", bastPath);
	}
}
