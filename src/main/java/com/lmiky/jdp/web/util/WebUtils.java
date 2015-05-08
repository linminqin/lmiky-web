package com.lmiky.jdp.web.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;


import com.lmiky.jdp.authority.service.AuthorityService;
import com.lmiky.jdp.database.model.PropertyCompareType;
import com.lmiky.jdp.database.model.PropertyFilter;
import com.lmiky.jdp.module.pojo.Module;
import com.lmiky.jdp.service.BaseService;
import com.lmiky.jdp.service.exception.ServiceException;
import com.lmiky.jdp.session.model.SessionInfo;
import com.lmiky.jdp.session.service.SessionService;
import com.lmiky.jdp.util.Environment;
import com.lmiky.jdp.web.constants.Constants;

/**
 * web工具
 * @author lmiky
 * @date 2013-6-6
 */
public class WebUtils {
	
	/**
	 * 获取模块
	 * @author lmiky
	 * @date 2013-6-6
	 * @param baseService
	 * @param modulePath
	 * @return
	 * @throws ServiceException
	 */
	public static Module getModule(BaseService baseService, String modulePath) throws ServiceException {
		// 先从上下文环境中取
		ServletContext application = Environment.getServletContext();
		String key = "modulePath_" + modulePath;
		Module module = (Module) application.getAttribute(key);
		if (module != null) {
			return module;
		}
		module = baseService.find(Module.class, new PropertyFilter("path", modulePath, PropertyCompareType.EQ, Module.class));
		if (module != null) {
			application.setAttribute(key, module);
		}
		return module;
	}
	
	/**
	 * 检查权限
	 * @author lmiky
	 * @date 2013-6-6
	 * @param authorityService
	 * @param sessionInfo
	 * @param authorityCode
	 * @return
	 * @throws ServiceException
	 */
	public static boolean checkAuthority(AuthorityService authorityService, SessionInfo sessionInfo, String authorityCode) throws ServiceException {
		//先从session缓存中找
		Boolean hasAuthority = sessionInfo.isAuthority(authorityCode);
		if(hasAuthority == null) {
			hasAuthority = authorityService.checkAuthority(authorityCode, sessionInfo.getUserId());
		}
		if(!hasAuthority) {
			sessionInfo.setAuthority(authorityCode, false);
		} else {
			sessionInfo.setAuthority(authorityCode, true);
		}
		return hasAuthority;
	}
	
	/**
	 * 获取SessionInfo
	 * @author lmiky
	 * @date 2013-6-18
	 * @param request
	 * @return
	 */
	public static SessionInfo getSessionInfo(HttpServletRequest request) {
		SessionService sessionService = (SessionService)Environment.getBean("sessionService");
		return sessionService.getSessionInfo(request);
	}
	
	/**
	 * 构建属性参数名称
	 * @author lmiky
	 * @date 2015年5月8日 上午11:30:19
	 * @param propertyName
	 * @param propertyCompareType
	 * @return
	 */
	public static String buildPropertyFilterName(String propertyName, PropertyCompareType propertyCompareType) {
		return buildPropertyFilterName(propertyName, propertyCompareType.getValue());
	}
	
	/**
	 * 构建属性参数名称
	 * @author lmiky
	 * @date 2015年5月8日 上午11:32:09
	 * @param propertyName
	 * @param propertyCompareTypeValue
	 * @return
	 */
	public static String buildPropertyFilterName(String propertyName, String propertyCompareTypeValue) {
		return Constants.HTTP_PARAM_PROPERTYFILTER_NAME_PREFIX + propertyName + "_" + propertyCompareTypeValue.toUpperCase();
	}
	
	/**
	 * 构建属性参数名称，默认比较符号为EQ
	 * @author lmiky
	 * @date 2015年5月8日 上午11:34:38
	 * @param propertyName
	 * @return
	 */
	public static String buildPropertyFilterName(String propertyName) {
		return buildPropertyFilterName(propertyName, PropertyCompareType.EQ);
	}
	
	/**
	 * 构建排序参数名称
	 * @author lmiky
	 * @date 2015年5月8日 下午3:03:11
	 * @param propertyName
	 * @return
	 */
	public static String buildSortName(String propertyName) {
		return Constants.HTTP_PARAM_SORT_TYPE_NAME_PREFIX + propertyName;
	}
}
