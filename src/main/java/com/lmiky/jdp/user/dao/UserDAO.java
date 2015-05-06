package com.lmiky.jdp.user.dao;

import java.util.List;

import com.lmiky.jdp.database.dao.BaseDAO;
import com.lmiky.jdp.database.exception.DatabaseException;
import com.lmiky.jdp.user.pojo.Operator;
import com.lmiky.jdp.user.pojo.Role;

/**
 * @author lmiky
 * @date 2014年8月13日 下午10:44:59
 */
public interface UserDAO extends BaseDAO {

	/**
	 * 列出非用户所拥有的角色
	 * @author lmiky
	 * @date 2014-2-6
	 * @param userId
	 * @return
	 * @throws DatabaseException
	 */
	public List<Role> listNoUserRoles(Long userId) throws DatabaseException;
	
	/**
	 * 获取非角色用户
	 * @author lmiky
	 * @date 2013-5-14
	 * @param roleId
	 * @return
	 * @throws DatabaseException
	 */
	public List<Operator> listNoRoleUser(Long roleId) throws DatabaseException;
	
	/**
	 * 删除用户-角色中间表
	 * @author lmiky
	 * @date 2014年9月23日 下午10:22:39
	 * @param userId
	 * @throws DatabaseException
	 */
	public void deleteUserRole(Long userId) throws DatabaseException;
	
	/**
	 * 添加用户-角色中间表
	 * @author lmiky
	 * @date 2014年9月23日 下午10:22:49
	 * @param userId
	 * @param roleId
	 * @throws DatabaseException
	 */
	public void addUserRole(Long userId, Long roleId) throws DatabaseException;
	
	/**
	 * 删除所有操作员用户
	 * @author lmiky
	 * @date 2015年5月6日 下午5:45:07
	 * @throws DatabaseException
	 */
	public void deleteOperatorUser() throws DatabaseException;
}
