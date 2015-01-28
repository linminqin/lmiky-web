package com.lmiky.jdp.lock.exception;
/**
 * 锁异常
 * @author lmiky
 * @date 2013-4-23
 */
public class LockException extends Exception {
	private static final long serialVersionUID = -1662973709206373148L;
	private String lockUserName;		//拥有锁的用户姓名

	/**
	 * 
	 */
	public LockException() {
		super();
	}
	
	/**
	 * @param message
	 */
	public LockException(String message) {
		super(message);
	}
	
	/**
	 * @param message
	 * @param lockUserName
	 */
	public LockException(String message, String lockUserName) {
		super(message);
		this.lockUserName = lockUserName;
	}

	/**
	 * @return the lockUserName
	 */
	public String getLockUserName() {
		return lockUserName;
	}

	/**
	 * @param lockUserName the lockUserName to set
	 */
	public void setLockUserName(String lockUserName) {
		this.lockUserName = lockUserName;
	}
}
