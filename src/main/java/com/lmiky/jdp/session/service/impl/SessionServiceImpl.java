package com.lmiky.jdp.session.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.lmiky.jdp.session.exception.SessionException;
import com.lmiky.jdp.session.model.SessionInfo;
import com.lmiky.jdp.session.service.SessionService;
import com.lmiky.jdp.user.pojo.User;
import com.lmiky.jdp.web.constants.Constants;

/**
 * @author lmiky
 * @date 2013-4-24
 */
@Service("sessionService")
public class SessionServiceImpl implements SessionService {

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.session.service.SessionService#generateSessionInfo(javax.servlet.http.HttpServletRequest, com.lmiky.jdp.user.pojo.User)
	 */
	public SessionInfo generateSessionInfo(HttpServletRequest request, User user) throws SessionException {
		if(user == null) {
			throw new SessionException(SessionException.USER_NULL);
		}
		SessionInfo sessionInfo = new SessionInfo();
		sessionInfo.setUser(user);
		sessionInfo.setSessionId(request.getSession().getId());
		return sessionInfo;
	}
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.session.service.SessionService#getSessionInfo(javax.servlet.http.HttpServletRequest)
	 */
	public SessionInfo getSessionInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (SessionInfo)session.getAttribute(Constants.SESSION_ATTR_SESSIONINFO);
	}
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.session.service.SessionService#removeSessionInfo(javax.servlet.http.HttpServletRequest)
	 */
	public void removeSessionInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute(Constants.SESSION_ATTR_SESSIONINFO, null);
		session.removeAttribute(Constants.SESSION_ATTR_SESSIONINFO);
	}

}
