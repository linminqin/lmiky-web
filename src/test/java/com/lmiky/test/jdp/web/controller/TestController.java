package com.lmiky.test.jdp.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 测试
 * @author lmiky
 * @date 2013-4-14
 */
@Controller
@RequestMapping("/test")
public class TestController {
	
	protected String getViewNamePrefix() {
		return this.getClass().getSimpleName().toLowerCase().replace("controller", "") + "/";
	}
	
	/**
	 * 测试
	 * @author lmiky
	 * @date 2013-4-14
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/test.shtml")
	public ModelAndView test(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("viewPref", getViewNamePrefix());
    	return new ModelAndView(getViewNamePrefix() + "test");
    }
}
