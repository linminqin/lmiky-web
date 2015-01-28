package com.lmiky.jdp.lock.service.impl;

import com.lmiky.jdp.cache.CacheFactory;
import com.lmiky.jdp.cache.exception.CacheException;
import com.lmiky.jdp.cache.model.ObjectCache;
import com.lmiky.jdp.lock.exception.LockException;
import com.lmiky.jdp.lock.model.LockData;
import com.lmiky.jdp.lock.service.LockService;

/**
 * 锁
 * @author lmiky
 * @date 2013-4-23
 */
public class LockServiceImpl implements LockService {
	private final String CACHE_HEAD = "lock.";

	private CacheFactory cacheFactory;
	private ObjectCache cache;
	private String cacheName; // 缓存名称
	private int maxLockMinutes; // 最长加锁时间,以分钟为单位
	private long maxLockMillis; // 最长加锁时间,以毫秒为单位

	private static Object mutex = new Object();

	public LockServiceImpl(CacheFactory cacheFactory, String cacheName, int maxLockMinutes) {
		this.cacheFactory = cacheFactory;
		this.cacheName = cacheName;
		this.maxLockMinutes = maxLockMinutes;
		cache = cacheFactory.getCache(cacheName);
		maxLockMillis = maxLockMinutes * 60000;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lmiky.jdp.lock.service.LockService#lock(java.lang.String, java.lang.Long, java.lang.String)
	 */
	public void lock(String lockTarget, Long userId, String userName) throws LockException {
		synchronized (mutex) {
			try {
				String cacheKey = CACHE_HEAD + lockTarget;
				LockData lockData = (LockData) cache.get(cacheKey);
				if (lockData == null) { // 未被加锁
					lockData = new LockData(userId, userName, System.currentTimeMillis());
					cache.put(cacheKey, lockData);
				} else if (!lockData.getUserId().equals(userId) && System.currentTimeMillis() - lockData.getLockTime() < maxLockMillis) { // 已经加锁，并且还未超时
					throw new LockException("locked by " + lockData.getUserName(), lockData.getUserName());
				} else { // 被自己加锁或者加锁超时
					lockData.setUserId(userId);
					lockData.setUserName(userName);
					lockData.setLockTime(System.currentTimeMillis());
				}
			} catch (CacheException e) {
				throw new LockException(e.getMessage());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.lmiky.jdp.lock.service.LockService#unlock(java.lang.String, java.lang.Long)
	 */
	public void unlock(String lockTarget, Long userId) throws LockException {
		synchronized (mutex) {
			try {
				String key = CACHE_HEAD + lockTarget;
				LockData lockData = (LockData) cache.get(key);
				if (lockData != null) {
					if (lockData.getUserId().equals(userId)) {
						cache.remove(key);
					}
				}
			} catch (CacheException e) {
				throw new LockException(e.getMessage());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.lmiky.jdp.lock.service.LockService#isLock(java.lang.String)
	 */
	public boolean isLock(String lockTarget) throws LockException {
		synchronized (mutex) {
			try {
				String key = CACHE_HEAD + lockTarget;
				LockData lockData = (LockData) cache.get(key);
				if (lockData == null) { // 未被加锁
					return false;
				} else if(System.currentTimeMillis() - lockData.getLockTime() > maxLockMillis) { // 超时
					return false;
				}
				return true;
			} catch (CacheException e) {
				throw new LockException(e.getMessage());
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.lock.service.LockService#isLockByUser(java.lang.String, java.lang.Long)
	 */
	public boolean isLockByUser(String lockTarget, Long userId) throws LockException {
		synchronized (mutex) {
			try {
				String key = CACHE_HEAD + lockTarget;
				LockData lockData = (LockData) cache.get(key);
				if (lockData == null) { // 未被加锁
					return false;
				} else if (!lockData.getUserId().equals(userId)) {
					return false;
				}
				lockData.setLockTime(System.currentTimeMillis());
				return true;
			} catch (CacheException e) {
				throw new LockException(e.getMessage());
			}
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
	 * @return the maxLockMinutes
	 */
	public int getMaxLockMinutes() {
		return maxLockMinutes;
	}

	/**
	 * @param maxLockMinutes the maxLockMinutes to set
	 */
	public void setMaxLockMinutes(int maxLockMinutes) {
		this.maxLockMinutes = maxLockMinutes;
	}

	/**
	 * @return the maxLockMillis
	 */
	public long getMaxLockMillis() {
		return maxLockMillis;
	}
}
