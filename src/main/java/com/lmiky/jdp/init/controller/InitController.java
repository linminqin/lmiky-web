package com.lmiky.jdp.init.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lmiky.jdp.controller.BaseController;
import com.lmiky.jdp.init.service.InitService;
import com.lmiky.jdp.util.PropertiesUtils;
import com.lmiky.jdp.util.ResponseUtils;

/**
 * 初始化控制器
 * @author lmiky
 * @date 2013-10-13
 */
@Controller
@RequestMapping("/init")
public class InitController extends BaseController {
	private InitService initService;
	private Boolean allowInit = PropertiesUtils.getBooleanContextValue("system.allowInit");
	
	/**
	 * 加载
	 * @author lmiky
	 * @date 2013-10-14
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/load.shtml")
	public String load(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(!allowInit) {
			return null;
		}
		return "jdp/init/load";
	}
	
	/**
	 * 初始化
	 * @author lmiky
	 * @date 2013-10-13
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param name	管理员用户名
	 * @param password	管理员密码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/init.shtml")
	public String init(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, String name, String loginName, String password) throws Exception {
		initService.init(name, loginName, password);
		putMessage(modelMap, "初始化完成!");
		return load(modelMap, request, response);
	}
	
	/**
	 * 修改模块
	 * @author lmiky
	 * @date 2013-12-31
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateModule.shtml")
	public String updateModule(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		initService.updateModule();
		ResponseUtils.write(response, "更新功能完成!");
		return null;
	}
	
	/**
	 * 修改菜单
	 * @author lmiky
	 * @date 2014-1-3
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateMenu.shtml")
	public String updateMenu(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		initService.updateMenu();
		ResponseUtils.write(response, "更新菜单完成!");
		return null;
	}
	
	/**
	 * @return the initService
	 */
	public InitService getInitService() {
		return initService;
	}

	/**
	 * @param initService the initService to set
	 */
	@Resource(name="initService")
	public void setInitService(InitService initService) {
		this.initService = initService;
	}

	/**
	 * @return the allowInit
	 */
	public Boolean getAllowInit() {
		return allowInit;
	}
}
