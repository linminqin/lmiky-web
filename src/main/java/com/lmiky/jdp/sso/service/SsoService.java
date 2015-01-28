package com.lmiky.jdp.sso.service;

import com.lmiky.jdp.session.model.SessionInfo;
import com.lmiky.jdp.sso.exception.LoginException;
import com.lmiky.jdp.sso.exception.SsoException;
import com.lmiky.jdp.user.pojo.User;

/**
 * 类说明
 * @author lmiky
 * @date 2013-4-23
 */
public interface SsoService {
	/**
	 * 登陆
	 * @author lmiky
	 * @date 2013-4-23
	 * @param loginName
	 * @param password
	 * @return
	 * @throws LoginException
	 */
	public User login(String loginName, String password) throws LoginException;
	
	/**
	 * 登陆
	 * @author lmiky
	 * @date 2014-2-6
	 * @param loginName
	 * @param password
	 * @param userClass 登陆类别
	 * @return
	 * @throws LoginException
	 */
	public <T extends User> T login(String loginName, String password, Class<T> userClass) throws LoginException;
	
	/**
	 * 记录会话信息
	 * @author lmiky
	 * @date 2013-4-24
	 * @param sessionInfo
	 * @throws SsoException
	 */
	public void recordSessionInfo(SessionInfo sessionInfo) throws SsoException;
	
	/**
	 * 清除会话记录
	 * @author lmiky
	 * @date 2013-6-5
	 * @param sessionInfo
	 * @throws SsoException
	 */
	public void removeSessionInfo(SessionInfo sessionInfo) throws SsoException;
	
	/**
	 * 检查单点登陆
	 * @author lmiky
	 * @date 2013-4-24
	 * @param sessionInfo
	 * @throws SsoException
	 */
	public void checkSso(SessionInfo sessionInfo) throws SsoException;
}
