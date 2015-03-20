package com.lmiky.jdp.web.constants;

import com.lmiky.jdp.util.PropertiesUtils;

/**
 * 常量
 * @author lmiky
 * @date 2013-4-16
 */
public class Constants extends com.lmiky.jdp.constants.Constants {

	// ****************************************properties文件****************************************//
	// 配置文件
	public static final String PROPERTIES_KEY_WEB_FILE = "config/web";	//WEB配置
	
	public static final String SYSTEM_URI_PATTERN = PropertiesUtils.getStringValue(Constants.PROPERTIES_KEY_WEB_FILE, "system.url.pattern").toLowerCase();
	
	// ****************************************properties文件****************************************//
	
	//**********************************************Http参数**********************************************//
	
	public static final String HTTP_PARAM_PROPERTYFILTER_NAME_PREFIX = "propertyFilter_";	//过滤条件参数名前缀
	
	public static final String HTTP_PARAM_SORT_ORDERBY_NAME = "sort_orderBy";	//排序参数名
	public static final String HTTP_PARAM_SORT_TYPE_NAME_PREFIX  = "sort_type_";	//排序方式参数名前缀
	
	//分页
	public static final String CONTEXT_KEY_PAGE_PAGESIZE = "page.pageSize";	//分页数
	
	public static final String HTTP_PARAM_PAGE_CURRENTPAGE = "page_currentPage";	//当前页
	public static final String HTTP_PARAM_PAGE_ACTION = "page_action";	//动作
	
	public static final String HTTP_PARAM_LOGIN_REDIRECT = "redirect";	//登陆
	public static final String HTTP_PARAM_LOGIN_CONTINUATION = "continuation";	//登陆

	public static final String HTTP_PARAM_MODULE_PATH= "modulePath";	//模块路劲
	public static final String HTTP_PARAM_SUBMENU_ID= "subMenuId";	//子菜单ID
	public static final String HTTP_PARAM_TOPMENU_ID= "topMenuId";	//顶层菜单ID
	public static final String HTTP_PARAM_MENU_FROM= "menuFrom";	//菜单来源
	
	//**********************************************Http参数**********************************************//
	
	//**********************************************session属性**********************************************//
	
	public static final String SESSION_ATTR_SESSIONINFO = "sessionInfo";		//session信息
	
	public static final String SESSION_ATTR_CONTINUATION_URL_SUFFIX = ".url";
	public static final String SESSION_ATTR_CONTINUATION_PARAM_SUFFIX = ".parameters";
	
	//**********************************************session属性**********************************************//
}