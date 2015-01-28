package com.lmiky.jdp.lock.model;

import com.lmiky.jdp.cache.model.CacheData;

/**
 * 加锁数据
 * @author lmiky
 * @date 2013-4-23
 */
public class LockData extends CacheData {
	private static final long serialVersionUID = -8274067336827582637L;
	private Long userId;			//加锁人ID
	private String userName;	//加锁人姓名
	private long lockTime;		//加锁时间
	
	public LockData() {
		
	}
	
	/**
	 * @param userId
	 * @param userName
	 * @param lockTime
	 */
	public LockData(Long userId, String userName, long lockTime) {
		this.userId = userId;
		this.userName = userName;
		this.lockTime = lockTime;
	}
	
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
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the lockTime
	 */
	public long getLockTime() {
		return lockTime;
	}
	/**
	 * @param lockTime the lockTime to set
	 */
	public void setLockTime(long lockTime) {
		this.lockTime = lockTime;
	}
}
