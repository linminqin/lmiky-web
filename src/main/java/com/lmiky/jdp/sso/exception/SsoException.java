package com.lmiky.jdp.sso.exception;

/**
 * 单点登陆异常
 * @author lmiky
 * @date 2013-4-23
 */
public class SsoException extends Exception {
	private static final long serialVersionUID = 1233204931993925827L;
	
	public static final String SESSIONINFO_NULL = "noSessionInfo";
	public static final String SESSIONINFO_LOGINNAME_NULL = "noLoginName";
	public static final String SESSIONINFO_SESSIONID_NULL = "noSessionId";
	public static final String LOGIN_IN_OTHER = "loginInOther";

	/**
	 * 
	 */
	public SsoException() {
		super();
	}

	/**
	 * @param message
	 */
	public SsoException(String message) {
		super(message);
	}
}
