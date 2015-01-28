package com.lmiky.jdp.sso.model;

import com.lmiky.jdp.cache.model.CacheData;

/**
 * 登入信息
 * @author lmiky
 * @date 2013-4-23
 */
public class SsoData  extends CacheData {
	private static final long serialVersionUID = 383115405893097065L;
	private Long userId;
	private String loginName;
	private String sessionId;
	
	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}
	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}
	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}
