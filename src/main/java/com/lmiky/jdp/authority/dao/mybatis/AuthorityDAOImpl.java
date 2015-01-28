package com.lmiky.jdp.authority.dao.mybatis;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lmiky.jdp.authority.dao.AuthorityDAO;
import com.lmiky.jdp.authority.pojo.Authority;
import com.lmiky.jdp.database.dao.mybatis.BaseDAOImpl;
import com.lmiky.jdp.database.exception.DatabaseException;
import com.lmiky.jdp.service.exception.ServiceException;
import com.lmiky.jdp.system.menu.pojo.LatelyOperateMenu;
import com.lmiky.jdp.user.pojo.Role;

/**
 * 权限dao
 * @author lmiky
 * @date 2014-8-13 下午5:03:53
 */
@Repository("authorityDAO")
public class AuthorityDAOImpl extends BaseDAOImpl implements AuthorityDAO {

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.authority.dao.AuthorityDAO#listAuthorizedOperator(java.lang.String)
	 */
	public List<Role> listAuthorizedOperator(String modulePath) throws DatabaseException {
		try {
			Map<String, Object> params = generateParameterMap(LatelyOperateMenu.class);
			params.put("modulePath", modulePath);
			return sqlSessionTemplate.selectList(Authority.class.getName() + ".listAuthorizedOperator", params);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.authority.dao.AuthorityDAO#listUnauthorizedOperator(java.lang.String)
	 */
	@Override
	public List<Role> listUnauthorizedOperator(String modulePath) throws DatabaseException {
		try {
			Map<String, Object> params = generateParameterMap(LatelyOperateMenu.class);
			params.put("modulePath", modulePath);
			return sqlSessionTemplate.selectList(Authority.class.getName() + ".listUnauthorizedOperator", params);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.authority.dao.AuthorityDAO#checkAuthority(java.lang.String, java.lang.Long)
	 */
	@Override
	public boolean checkAuthority(String authorityCode, Long userId) throws ServiceException {
		try {
			Map<String, Object> params = generateParameterMap(LatelyOperateMenu.class);
			params.put("authorityCode", authorityCode);
			params.put("userId", userId);
			Integer result = sqlSessionTemplate.selectOne(Authority.class.getName() + ".checkAuthority", params);
			return result > 0;
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}
