package com.lmiky.jdp.user.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmiky.jdp.database.dao.BaseDAO;
import com.lmiky.jdp.database.exception.DatabaseException;
import com.lmiky.jdp.database.model.PropertyCompareType;
import com.lmiky.jdp.database.model.PropertyFilter;
import com.lmiky.jdp.service.exception.ServiceException;
import com.lmiky.jdp.service.impl.BaseServiceImpl;
import com.lmiky.jdp.user.dao.UserDAO;
import com.lmiky.jdp.user.pojo.User;
import com.lmiky.jdp.user.service.UserService;

/**
 * 用户管理业务实现类
 * @author lmiky
 * @date 2013-4-24
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl implements UserService {

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.user.service.UserService#findByLoginName(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public User findByLoginName(String loginName) throws ServiceException {
		return findByLoginName(loginName, User.class);
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.user.service.UserService#findByLoginName(java.lang.String, java.lang.Class)
	 */
	@Transactional(readOnly=true)
	public <T extends User> T findByLoginName(String loginName,  Class<T> userClass) throws ServiceException {
		return find(userClass, new PropertyFilter(User.POJO_FIELD_NAME_LOGINNAME, loginName, PropertyCompareType.EQ, userClass));
	}
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.user.service.UserService#deleteOperatorUser()
	 */
	@Transactional(rollbackFor={Exception.class})
	public void deleteOperatorUser() throws ServiceException {
		try {
			((UserDAO)getDAO()).deleteOperatorUser();
		} catch (DatabaseException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.user.service.UserService#deleteUserRole(java.lang.Long)
	 */
	@Transactional(rollbackFor={Exception.class})
	public void deleteUserRole(Long userId) throws ServiceException {
		try {
			((UserDAO)getDAO()).deleteUserRole(userId);
		} catch (DatabaseException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.user.service.UserService#addUserRole(java.lang.Long, java.lang.Long)
	 */
	@Transactional(rollbackFor={Exception.class})
	public void addUserRole(Long userId, Long roleId) throws ServiceException {
		try {
			((UserDAO)getDAO()).addUserRole(userId, roleId);
		} catch (DatabaseException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.service.impl.BaseServiceImpl#setDAO(com.lmiky.jdp.database.dao.BaseDAO)
	 */
	@Override
	@Resource(name="userDAO")
	public void setDAO(BaseDAO dao) {
		super.setDAO(dao);
	}
}