package com.lmiky.jdp.system.menu.dao;

import java.util.List;

import com.lmiky.jdp.database.dao.BaseDAO;
import com.lmiky.jdp.database.exception.DatabaseException;

/**
 * @author lmiky
 * @date 2014年8月13日 下午10:18:03
 */
public interface MenuDAO extends BaseDAO {
	
	/**
	 * 获取最近操作菜单ID
	 * @author lmiky
	 * @date 2014年8月13日 下午10:19:27
	 * @param userId 指定用户
	 * @param size	要获取的条数
	 * @return
	 * @throws DatabaseException
	 */
	public List<String> listLatelyOperateMenuId(Long userId, int size) throws DatabaseException;
	
	/**
	 * 获取收藏夹菜单ID
	 * @author lmiky
	 * @date 2014年8月13日 下午10:35:35
	 * @param userId
	 * @return
	 * @throws DatabaseException
	 */
	public List<String> listFavoriteMenuId(Long userId) throws DatabaseException;
}
