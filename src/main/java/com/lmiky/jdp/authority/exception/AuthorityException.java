package com.lmiky.jdp.authority.exception;
/**
 * 权限异常
 * @author lmiky
 * @date 2013-5-24
 */
public class AuthorityException extends Exception {
	private static final long serialVersionUID = -1799837645221468892L;

	/**
	 * 
	 */
	public AuthorityException() {
		super();
	}
	
	/**
	 * @param message
	 */
	public AuthorityException(String message) {
		super(message);
	}
}
