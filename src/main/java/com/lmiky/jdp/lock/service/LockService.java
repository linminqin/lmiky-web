package com.lmiky.jdp.lock.service;

import com.lmiky.jdp.lock.exception.LockException;


/**
 * 锁
 * @author lmiky
 * @date 2013-4-23
 */
public interface LockService {
	/**
	 * 加锁记录,如果已经被加锁且加锁人不是当前用户,则抛出异常,如果已经被加锁且加锁人是当前用户,则重新设定加锁时间
	 * @author lmiky
	 * @date 2013-4-23
	 * @param lockTarget
	 * @param userId
	 * @param userName
	 * @throws LockException
	 */
	public void lock(String lockTarget, Long userId, String userName) throws LockException;
	
	/**
	 * 解锁
	 * @author lmiky
	 * @date 2013-4-23
	 * @param lockTarget
	 * @param userId
	 * @throws LockException
	 */
	public void unlock(String lockTarget, Long userId) throws LockException;
	
	/**
	 * 检查记录是否被人加锁
	 * @author lmiky
	 * @date 2013-4-23
	 * @param lockTarget
	 * @return
	 * @throws LockException
	 */
	public boolean isLock(String lockTarget) throws LockException;
	
	/**
	 * 检查记录是否被指定的用户加锁，如果是，则重新设置加锁时间
	 * @author lmiky
	 * @date 2013-4-23
	 * @param lockTarget
	 * @param userId
	 * @return
	 * @throws LockException
	 */
	public boolean isLockByUser(String lockTarget, Long userId) throws LockException;
}
