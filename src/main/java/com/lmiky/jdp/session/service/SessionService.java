package com.lmiky.jdp.session.service;

import javax.servlet.http.HttpServletRequest;

import com.lmiky.jdp.session.exception.SessionException;
import com.lmiky.jdp.session.model.SessionInfo;
import com.lmiky.jdp.user.pojo.User;

/**
 * Session服务
 * @author lmiky
 * @date 2013-4-24
 */
public interface SessionService {
	
	/**
	 * 方法说明
	 * @author lmiky
	 * @date 2013-4-24
	 * @param request
	 * @param user
	 * @return
	 * @throws SessionException
	 */
	public SessionInfo generateSessionInfo(HttpServletRequest request, User user) throws SessionException;
	
	/**
	 * 获取Session信息
	 * @author lmiky
	 * @date 2013-4-24
	 * @param request
	 * @return
	 */
	public SessionInfo getSessionInfo(HttpServletRequest request);

	/**
	 * 移除session信息
	 * @author lmiky
	 * @date 2013-6-1
	 * @param request
	 */
	public void removeSessionInfo(HttpServletRequest request);
}
