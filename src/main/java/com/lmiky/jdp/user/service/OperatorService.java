package com.lmiky.jdp.user.service;

import java.util.List;

import com.lmiky.jdp.service.exception.ServiceException;
import com.lmiky.jdp.user.pojo.Role;

/**
 * 
 * @author lmiky
 * @date 2014-2-6
 */
public interface OperatorService extends UserService {
	
	/**
	 * 列出用户所拥有的角色
	 * @author lmiky
	 * @date 2014-2-6
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public List<Role> listUserRoles(Long userId) throws ServiceException;
	
	/**
	 * 列出非用户所拥有的角色
	 * @author lmiky
	 * @date 2014-2-6
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public List<Role> listNoUserRoles(Long userId) throws ServiceException;
}
