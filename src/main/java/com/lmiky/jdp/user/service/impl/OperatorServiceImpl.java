package com.lmiky.jdp.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmiky.jdp.database.exception.DatabaseException;
import com.lmiky.jdp.database.model.PropertyCompareType;
import com.lmiky.jdp.database.model.PropertyFilter;
import com.lmiky.jdp.database.model.Sort;
import com.lmiky.jdp.database.pojo.BasePojo;
import com.lmiky.jdp.service.exception.ServiceException;
import com.lmiky.jdp.user.dao.UserDAO;
import com.lmiky.jdp.user.pojo.Operator;
import com.lmiky.jdp.user.pojo.Role;
import com.lmiky.jdp.user.service.OperatorService;

/**
 * 
 * @author lmiky
 * @date 2014-2-6
 */
@Service("operatorService")
public class OperatorServiceImpl extends UserServiceImpl implements OperatorService {

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.user.service.UserService#listUserRoles(java.lang.Long)
	 */
	@Transactional(readOnly=true)
	public List<Role> listUserRoles(Long userId) throws ServiceException {
		List<PropertyFilter> propertyFilters = new ArrayList<PropertyFilter>();
		propertyFilters.add(new PropertyFilter("id", userId, PropertyCompareType.EQ, true, Operator.class));
		List<Sort> sorts = new ArrayList<Sort>();
		sorts.add(new Sort("name", Sort.SORT_TYPE_ASC, Role.class));
		return list(Role.class, propertyFilters, sorts);
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.user.service.UserService#listNoUserRoles(java.lang.Long)
	 */
	@Transactional(readOnly=true)
	public List<Role> listNoUserRoles(Long userId) throws ServiceException {
		try {
			UserDAO userDAO = (UserDAO)getDAO();
			return userDAO.listNoUserRoles(userId);
		} catch (DatabaseException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.service.impl.BaseServiceImpl#add(com.lmiky.jdp.database.pojo.BasePojo)
	 */
	@Override
	public <T extends BasePojo> void add(T pojo) throws ServiceException {
		try {
			super.add(pojo);
			if(pojo instanceof Operator) {
				UserDAO userDAO = (UserDAO)getDAO();
				Set<Role> roles = ((Operator) pojo).getRoles();
				if(roles != null && roles.size() > 0) {
					for(Role role : roles) {
						userDAO.addUserRole(pojo.getId(), role.getId());	//添加中间表数据
					}
				}
			}
		} catch (DatabaseException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.service.impl.BaseServiceImpl#update(com.lmiky.jdp.database.pojo.BasePojo)
	 */
	@Transactional(rollbackFor={Exception.class})
	public <T extends BasePojo> void update(T pojo) throws ServiceException {
		try {
			super.update(pojo);
			if(pojo instanceof Operator) {
				UserDAO userDAO = (UserDAO)getDAO();
				userDAO.deleteUserRole(pojo.getId());	//删除中间表数据
				Set<Role> roles = ((Operator) pojo).getRoles();
				if(roles != null && roles.size() > 0) {
					for(Role role : roles) {
						userDAO.addUserRole(pojo.getId(), role.getId());	//添加中间表数据
					}
				}
			}
		} catch (DatabaseException e) {
			throw new ServiceException(e.getMessage());
		}
	}
}
