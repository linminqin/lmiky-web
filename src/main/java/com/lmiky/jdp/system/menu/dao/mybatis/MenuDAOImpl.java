package com.lmiky.jdp.system.menu.dao.mybatis;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lmiky.jdp.database.dao.mybatis.BaseDAOImpl;
import com.lmiky.jdp.database.exception.DatabaseException;
import com.lmiky.jdp.database.model.Sort;
import com.lmiky.jdp.database.pojo.BasePojo;
import com.lmiky.jdp.system.menu.dao.MenuDAO;
import com.lmiky.jdp.system.menu.pojo.LatelyOperateMenu;
import com.lmiky.jdp.system.menu.pojo.MyFavoriteMenu;

/**
 * 系统菜单
 * @author lmiky
 * @date 2014年8月13日 下午10:22:50
 */
@Repository("menuDAO")
public class MenuDAOImpl extends BaseDAOImpl implements MenuDAO {

	/*
	 * (non-Javadoc)
	 * @see com.lmiky.jdp.system.menu.dao.MenuDAO#listLatelyOperateMenuId(java.lang.Long, int)
	 */
	@Override
	public List<String> listLatelyOperateMenuId(Long userId, int size) throws DatabaseException {
		try {
			Map<String, Object> params = generateParameterMap(LatelyOperateMenu.class);
			params.put("userId", userId);
			setSortParameter(params, BasePojo.POJO_FIELD_NAME_ID, Sort.SORT_TYPE_DESC, LatelyOperateMenu.class);
			setPageParameter(params, null, size);
			return sqlSessionTemplate.selectList(LatelyOperateMenu.class.getName() + ".listLatelyOperateMenuId", params);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.system.menu.dao.MenuDAO#listFavoriteMenuId(java.lang.Long)
	 */
	@Override
	public List<String> listFavoriteMenuId(Long userId) throws DatabaseException {
		try {
			Map<String, Object> params = generateParameterMap(MyFavoriteMenu.class);
			params.put("userId", userId);
			setSortParameter(params, BasePojo.POJO_FIELD_NAME_ID, Sort.SORT_TYPE_DESC, MyFavoriteMenu.class);
			return sqlSessionTemplate.selectList(MyFavoriteMenu.class.getName() + ".listFavoriteMenuId", params);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}
