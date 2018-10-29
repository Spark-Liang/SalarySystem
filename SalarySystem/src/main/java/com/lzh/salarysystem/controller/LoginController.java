package com.lzh.salarysystem.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController extends BaseController{
	public static final String LOGIN_PAGE_URL = "/user/login/page";
	public static final String LOGIN_POP_UP_URL = "/user/login/popup";
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	// 原请求信息的缓存及恢复
	private RequestCache requestCache = new HttpSessionRequestCache();
 
	// 用于重定向
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@RequestMapping(LOGIN_PAGE_URL)
	public String loginPage() {
		return "login/login";
	}
	
	@RequestMapping("/authentication/require")
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public void handleAuthentication(HttpServletRequest request 
			,HttpServletResponse response) throws Exception{
		requestCache.saveRequest(request, response);
		redirectStrategy.sendRedirect(request, response, LOGIN_PAGE_URL);
	}
	
}
