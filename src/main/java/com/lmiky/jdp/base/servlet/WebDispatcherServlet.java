package com.lmiky.jdp.base.servlet;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.lmiky.jdp.web.constants.Constants;
import com.lmiky.jdp.logger.util.LoggerUtils;
import com.lmiky.jdp.service.BaseService;
import com.lmiky.jdp.session.model.SessionInfo;
import com.lmiky.jdp.session.service.SessionService;
import com.lmiky.jdp.system.menu.model.SubMenu;
import com.lmiky.jdp.system.menu.pojo.LatelyOperateMenu;
import com.lmiky.jdp.system.menu.service.MenuParseService;
import com.lmiky.jdp.util.Environment;
import com.lmiky.jdp.web.model.ContinuationRequest;

/**
 * @author lmiky
 * @date 2013-4-25
 */
public class WebDispatcherServlet extends DispatcherServlet {
	private static final long serialVersionUID = -5891758631298574853L;
	private BaseService baseService;
	private SessionService sessionService;
	private MenuParseService menuParseService;

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		baseService = (BaseService) Environment.getBean("baseService");
		sessionService = (SessionService) Environment.getBean("sessionService");
		menuParseService = (MenuParseService) Environment.getBean("menuParseService");
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.servlet.DispatcherServlet#doService(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String continuationId = request.getParameter(Constants.HTTP_PARAM_LOGIN_CONTINUATION);
		SessionInfo sessionInfo = null;
		try {
			sessionInfo = sessionService.getSessionInfo(request);
		} catch (Exception e) {
			LoggerUtils.logException(e);
		}
		if (!StringUtils.isBlank(continuationId) && !(request instanceof ContinuationRequest)) {
			HttpSession session = request.getSession();
			Map parameters = (Map) session.getAttribute(continuationId + Constants.SESSION_ATTR_CONTINUATION_PARAM_SUFFIX);
			if (parameters != null) {
				session.removeAttribute(continuationId + Constants.SESSION_ATTR_CONTINUATION_PARAM_SUFFIX);
				request = new ContinuationRequest(request, parameters); // 构造新的请求
			}
		} else { // 非继续登录之前的操作，防止重复记录
			String subMenuId = request.getParameter(Constants.HTTP_PARAM_SUBMENU_ID);
			if (!StringUtils.isBlank(subMenuId)) {
				SubMenu subMenu = menuParseService.getSubMenu(subMenuId, sessionInfo);
				// 记录最近操作
				if (subMenu != null && sessionInfo != null && sessionInfo.getUserId() != null) {
					if (!SubMenu.TYPE_IFRAME.equals(subMenu.getType())) {
						String latelyOperateMenuId = sessionInfo.getLatelyOperateMenuId();
						// 如果跟上次操作一样，就不重复记录
						if (StringUtils.isBlank(latelyOperateMenuId) || !subMenuId.equals(latelyOperateMenuId)) {
							LatelyOperateMenu latelyOperateMenu = new LatelyOperateMenu();
							latelyOperateMenu.setMenuId(subMenuId);
							latelyOperateMenu.setUserId(sessionInfo.getUserId());
							latelyOperateMenu.setOpeTime(new Date());
							baseService.add(latelyOperateMenu);
							sessionInfo.setLatelyOperateMenuId(subMenuId);
						}
					}
				}
			}
			// 保存参数
			if (sessionInfo != null) {
				// 最近访问历史记录
				sessionInfo.AddUrlParamHistory(request.getRequestURI(), new HashMap(request.getParameterMap()));
			}
		}
		// 设置菜单
		if (sessionInfo != null && (sessionInfo.getTopMenus() == null || sessionInfo.getTopMenus().isEmpty())) {
			sessionInfo.setTopMenus(menuParseService.getTopMenus(sessionInfo));
		}
		super.doService(request, response);
	}

}
