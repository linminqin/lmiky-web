package com.lmiky.jdp.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;

import com.lmiky.jdp.authority.exception.AuthorityException;
import com.lmiky.jdp.authority.service.AuthorityService;
import com.lmiky.jdp.controller.view.BaseCode;
import com.lmiky.jdp.controller.view.BaseCodeView;
import com.lmiky.jdp.logger.util.LoggerUtils;
import com.lmiky.jdp.module.pojo.Module;
import com.lmiky.jdp.service.exception.ServiceException;
import com.lmiky.jdp.session.exception.SessionException;
import com.lmiky.jdp.session.model.SessionInfo;
import com.lmiky.jdp.session.service.SessionService;
import com.lmiky.jdp.sso.exception.SsoException;
import com.lmiky.jdp.sso.service.SsoService;
import com.lmiky.jdp.system.menu.service.MenuService;
import com.lmiky.jdp.system.menu.util.MenuUtils;
import com.lmiky.jdp.user.pojo.User;
import com.lmiky.jdp.util.UUIDGenerator;
import com.lmiky.jdp.web.constants.Constants;
import com.lmiky.jdp.web.util.WebUtils;


/**
 * 控制器
 * @author lmiky
 * @date 2013-4-15
 */
public abstract class BaseWebController extends BaseController {
	public static final String MESSAGE_INFO_KEY = "messageInfos";
	public static final String ERROR_INFO_KEY = "errorInfos";
	public static final String REDIRECT_TO_LOGIN_REASON_KEY = "redirectToLoginReason";	//跳转到登陆页面重新登陆原因
	
	//请求方式
	public static final String REQUESTTYPE_NORMAL = "normal";	//普通方式
	public static final String REQUESTTYPE_AJAX = "ajax";		//ajax方式
	
	protected SessionService sessionService;
	protected SsoService ssoService;
	protected AuthorityService authorityService;
	protected MenuService menuService;
	private String viewType = Constants.VIEWTYPE;
	protected String loginUrl = Constants.LOGINURL;
	
	/**
	 * 获取加载权限值，如果返回值为空，表示不需要检查权限
	 * @author lmiky
	 * @date 2013-12-30
	 * @param modelMap
	 * @param request
	 * @return
	 */
	protected String getLoadAuthorityCode(ModelMap modelMap, HttpServletRequest request) {
		return "";
	}
	
	/**
	 * 添加提示信息
	 * @author lmiky
	 * @date 2013-4-24
	 * @param modelMap
	 * @param message
	 */
	@SuppressWarnings("unchecked")
	public void putMessage(ModelMap modelMap, String message) {
		List<String> messageInfos = (List<String>) modelMap.get(MESSAGE_INFO_KEY);
		if(messageInfos == null) {
			messageInfos = new ArrayList<String>();
			modelMap.put(MESSAGE_INFO_KEY, messageInfos);
		}
		messageInfos.add(message);
	}
	
	/**
	 * 设置提示信息
	 * @author lmiky
	 * @date 2014-1-23
	 * @param modelMap
	 * @param message
	 */
	@SuppressWarnings("unchecked")
	public void setMessage(ModelMap modelMap, String message) {
		List<String> messageInfos = (List<String>) modelMap.get(MESSAGE_INFO_KEY);
		if(messageInfos == null) {
			messageInfos = new ArrayList<String>();
			modelMap.put(MESSAGE_INFO_KEY, messageInfos);
		} else {
			messageInfos.clear();
		}
		messageInfos.add(message);
	}
	
	/**
	 * 添加错误信息
	 * @author lmiky
	 * @date 2013-5-9
	 * @param modelMap
	 * @param error
	 */
	@SuppressWarnings("unchecked")
	public void putError(ModelMap modelMap, String error) {
		List<String> errorInfos = (List<String>) modelMap.get(ERROR_INFO_KEY);
		if(errorInfos == null) {
			errorInfos = new ArrayList<String>();
			modelMap.put(ERROR_INFO_KEY, errorInfos);
		}
		errorInfos.add(error);
	}
	
	/**
	 * 设置错误信息
	 * @author lmiky
	 * @date 2014-1-23
	 * @param modelMap
	 * @param error
	 */
	@SuppressWarnings("unchecked")
	public void setError(ModelMap modelMap, String error) {
		List<String> errorInfos = (List<String>) modelMap.get(ERROR_INFO_KEY);
		if(errorInfos == null) {
			errorInfos = new ArrayList<String>();
			modelMap.put(ERROR_INFO_KEY, errorInfos);
		} else {
			errorInfos.clear();
		}
		errorInfos.add(error);
	}
	
	/**
	 * 方加载
	 * @author lmiky
	 * @date 2013-10-22
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String executeBaseLoad(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return executeBaseLoad(modelMap, request, response, REQUESTTYPE_NORMAL);
	}
	
	/**
	 * 方加载
	 * @author lmiky
	 * @date 2013-10-22
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param requestTyps 请求方式
	 * @return
	 * @throws Exception
	 */
	public String executeBaseLoad(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, String requestTyp) throws Exception {
		try {
			//判断是否有登陆
			SessionInfo sessionInfo = getSessionInfo(modelMap, request);
			//检查单点登陆
			checkSso(sessionInfo, modelMap, request);
			//检查权限
			checkAuthority(modelMap, request, sessionInfo, getLoadAuthorityCode(modelMap, request));
			Map<String, Object> loadParams = new HashMap<String, Object>();
			setLoadPrams(modelMap, request, response, loadParams);
			return processLoad(modelMap, request, response, loadParams);
		} catch(Exception e) {
			return transactException(e, modelMap, request, response, requestTyp);
		}
	}
	
	/**
	 * 设置加载参数
	 * @author lmiky
	 * @date 2013-10-22
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param loadParams
	 * @throws Exception
	 */
	protected void setLoadPrams(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, Map<String, Object> loadParams) throws Exception {
	}
	
	/**
	 * 处理加载过程
	 * @author lmiky
	 * @date 2013-10-22
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param loadParams
	 * @return
	 * @throws Exception
	 */
	protected String processLoad(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, Map<String, Object> loadParams) throws Exception {
		return "";
	}
	
	/**
	 * 异常处理
	 * @author lmiky
	 * @date 2013-4-24
	 * @param e
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String transactException(Exception e, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return transactException(e, modelMap, request, response, REQUESTTYPE_NORMAL);
	}
	
	/**
	 * 异常处理
	 * @author lmiky
	 * @date 2013-4-24
	 * @param e
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param requestTyp 请求方式
	 * @return
	 * @throws Exception
	 */
	public String transactException(Exception e, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, String requestTyp) throws Exception {
		if(e instanceof SessionException) {
			putError(modelMap, "登陆超时！");
			if(isRedirectRequestType(requestTyp)) {
				return redirectToLogin(modelMap, request, response, "登陆超时！");
			} else {
				return getUnRedirectRequestTypeExceptionReturn(e, modelMap, request, response, requestTyp);
			}
		}
		if(e instanceof SsoException) {
			sessionService.removeSessionInfo(request);	//清除SessionInfo，如果是在别处登录，即使别处已经下线，还是强制必须得重新登录
			putError(modelMap, "当前账号在别处登陆！");
			if(isRedirectRequestType(requestTyp)) {
				return redirectToLogin(modelMap, request, response, "当前账号在别处登陆！");
			} else {
				return getUnRedirectRequestTypeExceptionReturn(e, modelMap, request, response, requestTyp);
			}
		}
		if(e instanceof AuthorityException) {
			putError(modelMap, "没有权限！");
			if(isRedirectRequestType(requestTyp)) {
				return redirectToLogin(modelMap, request, response, "没有权限！");
			} else {
				return getUnRedirectRequestTypeExceptionReturn(e, modelMap, request, response, requestTyp);
			}
		}
		logException(e, modelMap, request, response);
		putError(modelMap, e.getMessage());
		if(isRedirectRequestType(requestTyp)) {
			throw e;
		}
		return getUnRedirectRequestTypeExceptionReturn(e, modelMap, request, response, requestTyp);
	}
	
	/**
	 * 非调整请求方式异常返回结果
	 * @author lmiky
	 * @date 2014-1-11
	 * @param e
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param requestTyp 请求方式
	 * @return
	 */
	public String getUnRedirectRequestTypeExceptionReturn(Exception e, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, String requestTyp) {
		modelMap.put(BaseCodeView.KEY_NAME_CODE, BaseCode.CODE_ERROR);
		return "baseCodeView";
	}
	
	/**
	 * 是否调整方式请求
	 * @author lmiky
	 * @date 2014-1-11
	 * @param requestTyp
	 * @return
	 */
	protected boolean isRedirectRequestType(String requestTyp) {
		if(StringUtils.isBlank(requestTyp)) {
			return true;
		}
		if(REQUESTTYPE_AJAX.equals(requestTyp)) {	//ajax方式
			return false;
		}
		return true;
	}
	
	/**
	 * 记录异常日志
	 * @author lmiky
	 * @date 2013-5-6
	 * @param e
	 * @param modelMap
	 * @param request
	 * @param response
	 */
	public void logException(Exception e, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		LoggerUtils.logException(e);
	}
	
	/**
	 * 跳转到登陆页面
	 * @author lmiky
	 * @date 2013-4-24
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param reason
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public String redirectToLogin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, String reason) throws UnsupportedEncodingException {
		HttpSession session = request.getSession();
		String continuation = UUIDGenerator.generateString();
		HashMap parameters = (request.getParameterMap()==null) ? new HashMap() : new HashMap(request.getParameterMap());
		String url = request.getRequestURL().toString();
		String queryString = request.getQueryString();
		if(!StringUtils.isBlank(queryString)) {
			url += "?" + queryString;
		}
		session.setAttribute(continuation + Constants.SESSION_ATTR_CONTINUATION_URL_SUFFIX, url);
		session.setAttribute(continuation + Constants.SESSION_ATTR_CONTINUATION_PARAM_SUFFIX, parameters);
		if(!StringUtils.isBlank(reason)) {
			//modelMap.put(REDIRECT_TO_LOGIN_REASON_KEY, URLEncoder.encode(reason, "UTF-8"));
			modelMap.put(REDIRECT_TO_LOGIN_REASON_KEY, URLEncoder.encode(reason, "UTF-8"));
		}
		return "redirect:" + loginUrl + "?" + Constants.HTTP_PARAM_LOGIN_REDIRECT + "=" + continuation;
	}
	
	/**
	 * 获取会话信息
	 * @author lmiky
	 * @date 2013-4-24
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws SessionException 
	 */
	public SessionInfo getSessionInfo(ModelMap modelMap, HttpServletRequest request) throws SessionException {
		SessionInfo sessionInfo = WebUtils.getSessionInfo(request);
		if(sessionInfo == null) {
			throw new SessionException(SessionException.SESSION_NULL);
		}
		return sessionInfo;
	}
	
	/**
	 * 检查单点登陆
	 * @author lmiky
	 * @date 2013-4-24
	 * @param sessionInfo
	 * @param modelMap
	 * @param request
	 * @throws SsoException
	 */
	public void checkSso(SessionInfo sessionInfo, ModelMap modelMap, HttpServletRequest request) throws SsoException {
		ssoService.checkSso(sessionInfo);
	}
	
	/**
	 * 获取模块路劲
	 * @author lmiky
	 * @date 2013-5-13
	 * @param modelMap
	 * @param request
	 * @return
	 */
	public String getModulePath(ModelMap modelMap, HttpServletRequest request) {
		String modulePath = request.getParameter(Constants.HTTP_PARAM_MODULE_PATH);
		if(modulePath == null) {
			modulePath = "";
		}
		return modulePath.trim();
	}
	
	/**
	 * 获取当前模块
	 * @author lmiky
	 * @date 2013-5-23
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	public Module getModule(ModelMap modelMap, HttpServletRequest request) throws ServiceException {
		return WebUtils.getModule(service, getModulePath(modelMap, request));
	}
	
	/**
	 * 检查权限，如果没有权限，则抛出权限异常
	 * @author lmiky
	 * @date 2013-5-24
	 * @param modelMap
	 * @param request
	 * @param sessionInfo
	 * @param authorityCode
	 * @throws ServiceException
	 * @throws AuthorityException
	 */
	public void checkAuthority(ModelMap modelMap, HttpServletRequest request, SessionInfo sessionInfo, String authorityCode) throws ServiceException, AuthorityException {
		if(!StringUtils.isBlank(authorityCode) && !WebUtils.checkAuthority(authorityService, sessionInfo, authorityCode)) {
			throw new AuthorityException("no authority");
		}
	}
	
	/**
	 * 获取当前登录用户ID
	 * @author lmiky
	 * @date 2014-1-12
	 * @param modelMap
	 * @param request
	 * @throws Exception
	 * @return
	 */
	public Long getLoginUserId(ModelMap modelMap, HttpServletRequest request) throws Exception {
		SessionInfo sessionInfo = sessionService.getSessionInfo(request);
		return sessionInfo == null ? null : sessionInfo.getUserId();
	}
	
	/**
	 * 获取当前登录用户
	 * @author lmiky
	 * @date 2014-1-12
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public User getLoginUser(ModelMap modelMap, HttpServletRequest request) throws Exception {
		SessionInfo sessionInfo = sessionService.getSessionInfo(request);
		if(sessionInfo == null) {
			return null;
		}
		Long userId = sessionInfo.getUserId();
		return userId == null ? null : service.find(User.class, userId);
	}
	
	/**
	 * 设置菜单信息
	 * @author lmiky
	 * @date 2014年6月29日 下午1:42:04
	 * @param modelMap
	 * @param request
	 * @throws SessionException
	 * @throws Exception
	 */
	public void setMenuInfo(ModelMap modelMap, HttpServletRequest request) throws SessionException, Exception {
		//子菜单
		modelMap.put("subMenu", MenuUtils.getSubMenu(modelMap, request));
		//获取拥有权限
		modelMap.put("topMenu", MenuUtils.getTopMenu(modelMap, request));
	}
	
	/**
	 * @return the sessionService
	 */
	public SessionService getSessionService() {
		return sessionService;
	}

	/**
	 * @param sessionService the sessionService to set
	 */
	@Resource(name="sessionService")
	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	/**
	 * @return the viewType
	 */
	public String getViewType() {
		return viewType;
	}

	/**
	 * @return the loginUrl
	 */
	public String getLoginUrl() {
		return loginUrl;
	}

	/**
	 * @return the ssoService
	 */
	public SsoService getSsoService() {
		return ssoService;
	}

	/**
	 * @param ssoService the ssoService to set
	 */
	@Resource(name="ssoService")
	public void setSsoService(SsoService ssoService) {
		this.ssoService = ssoService;
	}

	/**
	 * @return the authorityService
	 */
	public AuthorityService getAuthorityService() {
		return authorityService;
	}

	/**
	 * @param authorityService the authorityService to set
	 */
	@Resource(name="authorityService")
	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	/**
	 * @return the menuService
	 */
	public MenuService getMenuService() {
		return menuService;
	}

	/**
	 * @param menuService the menuService to set
	 */
	@Resource(name="menuService")
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
}
