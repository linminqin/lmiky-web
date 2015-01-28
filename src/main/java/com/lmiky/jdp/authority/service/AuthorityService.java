package com.lmiky.jdp.authority.service;

import java.util.List;

import com.lmiky.jdp.service.exception.ServiceException;
import com.lmiky.jdp.user.pojo.Role;

/**
 * 权限业务
 * @author lmiky
 * @date 2013-5-20
 */
public interface AuthorityService {

	/**
	 * 获取已授权操作员
	 * @author lmiky
	 * @date 2013-5-20
	 * @param modulePath	模块路径
	 * @return
	 * @throws ServiceException
	 */
	public List<Role> listAuthorizedOperator(String modulePath) throws ServiceException;

	/**
	 * 获取未授权操作员
	 * @author lmiky
	 * @date 2013-5-20
	 * @param modulePath	模块路径
	 * @return
	 * @throws ServiceException
	 */
	public List<Role> listUnauthorizedOperator(String modulePath) throws ServiceException;
	
	/**
	 * 授权
	 * @author lmiky
	 * @date 2013-12-29
	 * @param modulePath	模块路径
	 * @param authorities
	 * @throws ServiceException
	 */
	public void authorize(String modulePath, String moduleType, String[] operatorIds) throws ServiceException;
	
	/**
	 * 检查是否拥有权限
	 * @author lmiky
	 * @date 2013-5-24
	 * @param authorityCode	权限编号
	 * @param userId 用户ID
	 * @return
	 * @throws ServiceException
	 */
	public boolean checkAuthority(String authorityCode, Long userId) throws ServiceException;

}
