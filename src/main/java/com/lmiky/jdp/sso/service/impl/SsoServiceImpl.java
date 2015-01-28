package com.lmiky.jdp.sso.service.impl;

import org.apache.commons.lang3.StringUtils;

import com.lmiky.jdp.cache.CacheFactory;
import com.lmiky.jdp.cache.exception.CacheException;
import com.lmiky.jdp.cache.model.ObjectCache;
import com.lmiky.jdp.service.exception.ServiceException;
import com.lmiky.jdp.session.model.SessionInfo;
import com.lmiky.jdp.sso.exception.LoginException;
import com.lmiky.jdp.sso.exception.SsoException;
import com.lmiky.jdp.sso.model.SsoData;
import com.lmiky.jdp.sso.service.SsoService;
import com.lmiky.jdp.user.pojo.User;
import com.lmiky.jdp.user.service.UserService;

/**
 * 单点登陆服务
 * @author lmiky
 * @date 2013-4-23
 */
public class SsoServiceImpl implements SsoService {
	private CacheFactory cacheFactory;
	private ObjectCache cache;
	private String cacheName; // 缓存名称
	private UserService userService;

	/**
	 * @param cacheName
	 */
	public SsoServiceImpl(CacheFactory cacheFactory, String cacheName) {
		this.cacheFactory = cacheFactory;
		this.cacheName = cacheName;
		cache = cacheFactory.getCache(cacheName);
	}
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.sso.service.SsoService#login(java.lang.String, java.lang.String)
	 */
	public User login(String loginName, String password) throws LoginException {
		return login(loginName, password, User.class);
	}
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.sso.service.SsoService#login(java.lang.String, java.lang.String, java.lang.Class)
	 */
	public <T extends User> T login(String loginName, String password, Class<T> userClass) throws LoginException {
		if(StringUtils.isBlank(loginName)) {
			throw new LoginException(LoginException.USERNAME_NULL);
		}
		if(StringUtils.isBlank(password)) {
			throw new LoginException(LoginException.PASSWORD_NULL);
		}
		T user = null;
		try {
			user = userService.findByLoginName(loginName, userClass);
		} catch (ServiceException e) {
			throw new LoginException(e.getMessage());
		}
		if(user == null) {
			throw new LoginException(LoginException.USERNAME_NOTEXIST);
		}
		try {
			if(!user.getPassword().equals(password)) {
				throw new LoginException(LoginException.PASSWORD_ERROR);
			}
			if(user.getValid() != User.VALID_YES) {
				throw new LoginException(LoginException.VALID_NO);
			}
		} catch (Exception e) {
			throw new LoginException(e.getMessage());
		}
		return user;
	}
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.sso.service.SsoService#recordSessionInfo(com.lmiky.jdp.session.model.SessionInfo)
	 */
	public void recordSessionInfo(SessionInfo sessionInfo) throws SsoException {
		if(sessionInfo == null) {
			throw new SsoException(SsoException.SESSIONINFO_NULL);
		}
		SsoData ssoData = new SsoData();
		ssoData.setUserId(sessionInfo.getUserId());
		ssoData.setLoginName(sessionInfo.getLoginName());
		ssoData.setSessionId(sessionInfo.getSessionId());
		try {
			cache.put(sessionInfo.getLoginName(), ssoData);
		} catch (CacheException e) {
			throw new SsoException(e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.sso.service.SsoService#removeSessionInfo(com.lmiky.jdp.session.model.SessionInfo)
	 */
	public void removeSessionInfo(SessionInfo sessionInfo) throws SsoException {
		if(sessionInfo == null || StringUtils.isBlank(sessionInfo.getLoginName())) {
			return;
		}
		try {
			cache.remove(sessionInfo.getLoginName());
		} catch (CacheException e) {
			throw new SsoException(e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.sso.service.SsoService#checkSso(com.lmiky.jdp.session.model.SessionInfo)
	 */
	public void checkSso(SessionInfo sessionInfo) throws SsoException {
		if(sessionInfo == null) {
			throw new SsoException(SsoException.SESSIONINFO_NULL);
		}
		if(StringUtils.isBlank(sessionInfo.getLoginName())) {
			throw new SsoException(SsoException.SESSIONINFO_LOGINNAME_NULL);
		}
		if(StringUtils.isBlank(sessionInfo.getSessionId())) {
			throw new SsoException(SsoException.SESSIONINFO_SESSIONID_NULL);
		}
		SsoData ssoData = null;
		try {
			ssoData = (SsoData) cache.get(sessionInfo.getLoginName());
		} catch (CacheException e) {
			throw new SsoException(e.getMessage());
		}
		if(ssoData != null && !sessionInfo.getSessionId().equals(ssoData.getSessionId())) {
			throw new SsoException(SsoException.LOGIN_IN_OTHER);
		}
	}

	/**
	 * @return the cacheFactory
	 */
	public CacheFactory getCacheFactory() {
		return cacheFactory;
	}

	/**
	 * @param cacheFactory the cacheFactory to set
	 */
	public void setCacheFactory(CacheFactory cacheFactory) {
		this.cacheFactory = cacheFactory;
	}

	/**
	 * @return the cache
	 */
	public ObjectCache getCache() {
		return cache;
	}

	/**
	 * @param cache the cache to set
	 */
	public void setCache(ObjectCache cache) {
		this.cache = cache;
	}

	/**
	 * @return the cacheName
	 */
	public String getCacheName() {
		return cacheName;
	}

	/**
	 * @param cacheName the cacheName to set
	 */
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
