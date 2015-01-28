package com.lmiky.jdp.sso.exception;

/**
 * 登陆异常
 * @author lmiky
 * @date 2013-4-23
 */
public class LoginException extends Exception {
	private static final long serialVersionUID = 6725616597164435449L;
	// 错误原因
	public static final String USERNAME_NULL = "userNameIsNull"; // 用户名为空
	public static final String USERNAME_NOTEXIST = "userNameIsNotExist"; // 用户名不存在
	public static final String PASSWORD_NULL = "passwordIsNull";
	public static final String PASSWORD_ERROR = "passwordError";
	public static final String VALID_NO = "notValid";

	/**
	 * 
	 */
	public LoginException() {
		super();
	}

	/**
	 * @param message
	 */
	public LoginException(String message) {
		super(message);
	}
}
