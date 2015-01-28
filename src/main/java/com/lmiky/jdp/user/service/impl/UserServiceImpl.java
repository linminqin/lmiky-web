package com.lmiky.jdp.user.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmiky.jdp.database.dao.BaseDAO;
import com.lmiky.jdp.database.model.PropertyCompareType;
import com.lmiky.jdp.database.model.PropertyFilter;
import com.lmiky.jdp.service.exception.ServiceException;
import com.lmiky.jdp.service.impl.BaseServiceImpl;
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
	 * @see com.lmiky.jdp.service.impl.BaseServiceImpl#setDAO(com.lmiky.jdp.database.dao.BaseDAO)
	 */
	@Override
	@Resource(name="userDAO")
	public void setDAO(BaseDAO dao) {
		super.setDAO(dao);
	}
}