package com.lmiky.jdp.authority.dao;

import java.util.List;

import com.lmiky.jdp.database.dao.BaseDAO;
import com.lmiky.jdp.database.exception.DatabaseException;
import com.lmiky.jdp.service.exception.ServiceException;
import com.lmiky.jdp.user.pojo.Role;

/**
 * @author lmiky
 * @date 2014-8-13 下午5:06:16
 */
public interface AuthorityDAO extends BaseDAO {

	/**
	 * 获取已授权操作员
	 * @author lmiky
	 * @date 2014-8-13 下午5:06:29
	 * @param modulePath
	 * @return
	 * @throws DatabaseException
	 */
	public List<Role> listAuthorizedOperator(String modulePath) throws DatabaseException;
	
	/**
	 * 获取未授权操作员
	 * @author lmiky
	 * @date 2013-5-20
	 * @param modulePath	模块路径
	 * @return
	 * @throws DatabaseException
	 */
	public List<Role> listUnauthorizedOperator(String modulePath) throws DatabaseException;
	
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
