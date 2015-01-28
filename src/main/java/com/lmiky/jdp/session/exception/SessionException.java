package com.lmiky.jdp.session.exception;
/**
 * Session异常
 * @author lmiky
 * @date 2013-4-24
 */
public class SessionException extends Exception {
	private static final long serialVersionUID = -6165070103618374945L;
	
	public static final String SESSION_NULL = "noSession";
	public static final String USER_NULL = "noUser";
	
	/**
	 * 
	 */
	public SessionException() {
		super();
	}
	
	/**
	 * @param message
	 */
	public SessionException(String message) {
		super(message);
	}
}
