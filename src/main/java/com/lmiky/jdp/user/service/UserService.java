package com.lmiky.jdp.user.service;


import com.lmiky.jdp.service.BaseService;
import com.lmiky.jdp.service.exception.ServiceException;
import com.lmiky.jdp.user.pojo.User;

/**
 * 用户管理
 * @author lmiky
 * @date 2013-4-24
 */
public interface UserService extends BaseService {
	
	/**
	 * 根据登陆账号获取用户
	 * @author lmiky
	 * @date 2013-4-24
	 * @param loginName
	 * @return
	 * @throws ServiceException
	 */
	public User findByLoginName(String loginName) throws ServiceException;
	
	/**
	 * 根据登陆账号获取用户
	 * @author lmiky
	 * @date 2014-2-6
	 * @param loginName
	 * @param userClass 用户类别
	 * @return
	 * @throws ServiceException
	 */
	public <T extends User> T findByLoginName(String loginName,  Class<T> userClass) throws ServiceException;
	
	/**
	 * 删除所有操作员用户
	 * @author lmiky
	 * @date 2015年5月6日 下午5:45:07
	 * @throws ServiceException
	 */
	public void deleteOperatorUser() throws ServiceException;
	
	/**
	 * 删除用户-角色中间表
	 * @author lmiky
	 * @date 2015年5月7日 下午6:10:31
	 * @param userId
	 * @throws ServiceException
	 */
	public void deleteUserRole(Long userId) throws ServiceException;
	
	/**
	 * 添加用户-角色中间表
	 * @author lmiky
	 * @date 2015年5月7日 下午6:10:25
	 * @param userId
	 * @param roleId
	 * @throws ServiceException
	 */
	public void addUserRole(Long userId, Long roleId) throws ServiceException;
	
}
