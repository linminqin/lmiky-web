package com.lmiky.jdp.user.dao.mybatis;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lmiky.jdp.database.dao.mybatis.BaseDAOImpl;
import com.lmiky.jdp.database.exception.DatabaseException;
import com.lmiky.jdp.database.model.PropertyFilter;
import com.lmiky.jdp.database.pojo.BasePojo;
import com.lmiky.jdp.user.dao.UserDAO;
import com.lmiky.jdp.user.pojo.Operator;
import com.lmiky.jdp.user.pojo.Role;
import com.lmiky.jdp.user.pojo.User;

/**
 * @author lmiky
 * @date 2014年8月13日 下午10:52:30
 */
@Repository("userDAO")
public class UserDAOImpl extends BaseDAOImpl implements UserDAO {

	/*
	 * (non-Javadoc)
	 * @see com.lmiky.jdp.user.dao.UserDAO#listNoUserRoles(java.lang.Long)
	 */
	@Override
	public List<Role> listNoUserRoles(Long userId) throws DatabaseException {
		try {
			Map<String, Object> params = generateParameterMap(Role.class);
			params.put("userId", userId);
			return sqlSessionTemplate.selectList(Role.class.getName() + ".listNoUserRoles", params);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.lmiky.jdp.user.dao.UserDAO#listNoRoleUser(java.lang.Long)
	 */
	@Override
	public List<Operator> listNoRoleUser(Long roleId) throws DatabaseException {
		try {
			Map<String, Object> params = generateParameterMap(Operator.class);
			params.put("roleId", roleId);
			return sqlSessionTemplate.selectList(Operator.class.getName() + ".listNoRoleUser", params);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.lmiky.jdp.user.dao.UserDAO#deleteUserRole(java.lang.Long)
	 */
	@Override
	public void deleteUserRole(Long userId) throws DatabaseException {
		try {
			sqlSessionTemplate.delete(User.class.getName() + ".deleteUserRole", userId);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.lmiky.jdp.user.dao.UserDAO#addUserRole(java.lang.Long, java.lang.Long)
	 */
	@Override
	public void addUserRole(Long userId, Long roleId) throws DatabaseException {
		try {
			Map<String, Object> params = generateParameterMap(User.class);
			params.put("userId", userId);
			params.put("roleId", roleId);
			sqlSessionTemplate.delete(User.class.getName() + ".addUserRole", params);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.database.dao.mybatis.BaseDAOImpl#delete(java.lang.Class, java.util.List)
	 */
	@Override
	public <T extends BasePojo> int delete(Class<T> pojoClass, List<PropertyFilter> propertyFilters) throws DatabaseException {
		try {
			int ret = super.delete(pojoClass, propertyFilters);
			if(User.class.isAssignableFrom(pojoClass)) {	
				super.delete(User.class, propertyFilters);	//删除用户表记录
			}
			return ret;
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.user.dao.UserDAO#deleteOperatorUser()
	 */
	@Override
	public void deleteOperatorUser() throws DatabaseException {
		try {
			sqlSessionTemplate.delete(User.class.getName() + ".deleteOperatorUser");
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}
