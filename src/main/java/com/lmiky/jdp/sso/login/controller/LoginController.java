package com.lmiky.jdp.sso.login.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lmiky.jdp.controller.BaseWebController;
import com.lmiky.jdp.logger.model.OperateType;
import com.lmiky.jdp.logger.util.LoggerUtils;
import com.lmiky.jdp.service.exception.ServiceException;
import com.lmiky.jdp.session.model.SessionInfo;
import com.lmiky.jdp.sso.exception.LoginException;
import com.lmiky.jdp.user.pojo.Operator;
import com.lmiky.jdp.user.pojo.User;
import com.lmiky.jdp.util.CookieUtils;
import com.lmiky.jdp.web.constants.Constants;

/**
 * 类说明
 * @author lmiky
 * @date 2013-4-23
 */
@Controller
@RequestMapping("/sso/login")
public class LoginController extends BaseWebController {
	public static final String COOKIE_NAME_LOGINNAME = "loginName";

	/**
	 * 加载登陆信息
	 * @author lmiky
	 * @date 2013-4-23
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/load.shtml")
	public String load(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		//跳转到登陆原因
		String loginReason = request.getParameter(REDIRECT_TO_LOGIN_REASON_KEY);
		if(!StringUtils.isBlank(loginReason)) {
			loginReason = URLDecoder.decode(request.getParameter(REDIRECT_TO_LOGIN_REASON_KEY), "UTF-8");
			putError(modelMap, loginReason);
		}
		String loginName = CookieUtils.getCookie(request, COOKIE_NAME_LOGINNAME);
		if(!StringUtils.isBlank(loginName)) {
			modelMap.put("loginName", loginName);
			modelMap.put("rememberLoginName", true);
		}
		return "jdp/sso/login/load";
	}

	/**
	 * 登陆
	 * @author lmiky
	 * @date 2013-4-23
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @param userName
	 * @param password
	 * @return
	 */
	@RequestMapping("/login.shtml")
	public String login(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "loginName", required = false) String loginName, @RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "rememberLoginName", required = false) boolean rememberLoginName) {
		try {
			//获取登陆用户信息
			User user = ssoService.login(loginName, password, Operator.class);
			SessionInfo sessionInfo = sessionService.generateSessionInfo(request, user);
			ssoService.recordSessionInfo(sessionInfo);
			HttpSession session = request.getSession();
			//设置session信息
			session.setAttribute(Constants.SESSION_ATTR_SESSIONINFO, sessionInfo);
			//记录日志
			LoggerUtils.save(request, null, null, sessionInfo.getUserId(), sessionInfo.getUserName(), OperateType.OPE_TYPE_LOGIN, this.getClass().getName(), null, service);
			//记录cookie
			if(rememberLoginName) {
				CookieUtils.addCookie(response, COOKIE_NAME_LOGINNAME, loginName);
			} else {
				CookieUtils.removeCookie(response, COOKIE_NAME_LOGINNAME);
			}
			//是否继续之前的操作
			String continuationId = request.getParameter(Constants.HTTP_PARAM_LOGIN_REDIRECT);
			if(!StringUtils.isBlank(continuationId)) {
				String redirectUrl = (String)session.getAttribute(continuationId + Constants.SESSION_ATTR_CONTINUATION_URL_SUFFIX);
				if(!StringUtils.isBlank(redirectUrl)) {
					session.removeAttribute(continuationId + Constants.SESSION_ATTR_CONTINUATION_URL_SUFFIX);
					return "redirect:" + com.lmiky.jdp.util.StringUtils.addUrlParameter(redirectUrl, Constants.HTTP_PARAM_LOGIN_CONTINUATION, continuationId);
				}
			}
			return "redirect:/";	//根目录
		} catch(Exception e) {
			modelMap.put("loginName", loginName);
			modelMap.put("rememberLoginName", rememberLoginName);
			logException(e, modelMap, request, response);
			if(e instanceof LoginException) {
				String error = e.getMessage();
				if(LoginException.USERNAME_NULL.equals(error)) {
					error = "用户名不能为空";
				} else if(LoginException.PASSWORD_NULL.equals(error)) {
					error = "密码不能为空";
				} else if(LoginException.USERNAME_NOTEXIST.equals(error)) {
					error = "用户名不存在";
				} else if(LoginException.PASSWORD_ERROR.equals(error)) {
					error = "密码错误";
				}else if(LoginException.VALID_NO.equals(error)) {
					error = "用户不可用";
				}
				putError(modelMap, error);
			} else {
				putError(modelMap, e.getMessage());
			}
		}
		return "jdp/sso/login/load";
	}
	
	
	/**
	 * 退出
	 * @author lmiky
	 * @date 2013-6-1
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/logout.shtml")
	public String logout(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		//清除session信息
		SessionInfo sessionInfo = null;
		try {
			sessionInfo = getSessionInfo(modelMap, request);
			ssoService.removeSessionInfo(sessionInfo);
		} catch (Exception e) {
			logException(e, modelMap, request, response);
		}
		sessionService.removeSessionInfo(request);
		request.getSession().invalidate();
		try {
			LoggerUtils.save(request, null, null, sessionInfo.getUserId(), sessionInfo.getUserName(), OperateType.OPE_TYPE_LOGOUT, this.getClass().getName(), null, service);
		} catch (ServiceException e) {
			logException(e, modelMap, request, response);
		}
		return load(modelMap, request, response);
	}
}
