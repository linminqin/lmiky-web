package com.lmiky.jdp.system.menu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmiky.jdp.database.dao.BaseDAO;
import com.lmiky.jdp.database.exception.DatabaseException;
import com.lmiky.jdp.service.exception.ServiceException;
import com.lmiky.jdp.service.impl.BaseServiceImpl;
import com.lmiky.jdp.system.menu.dao.MenuDAO;
import com.lmiky.jdp.system.menu.service.MenuService;

/**
 * @author lmiky
 * @date 2014年8月13日 下午11:01:41
 */
@Service("menuService")
public class MenuServiceImpl extends BaseServiceImpl implements MenuService {
	private MenuDAO menuDAO;

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.system.menu.service.MenuService#listLatelyOperateMenuId(java.lang.Long, int)
	 */
	@Transactional(readOnly=true)
	public List<String> listLatelyOperateMenuId(Long userId, int size) throws ServiceException {
		try {
			return menuDAO.listLatelyOperateMenuId(userId, size);
		} catch (DatabaseException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.system.menu.service.MenuService#listFavoriteMenuId(java.lang.Long)
	 */
	
	@Override
	@Transactional(readOnly=true)
	public List<String> listFavoriteMenuId(Long userId) throws ServiceException {
		try {
			return menuDAO.listFavoriteMenuId(userId);
		} catch (DatabaseException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	/**
	 * @return the menuDAO
	 */
	public MenuDAO getMenuDAO() {
		return menuDAO;
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.service.impl.BaseServiceImpl#setDAO(com.lmiky.jdp.database.dao.BaseDAO)
	 */
	@Resource(name="menuDAO")
	public void setDAO(BaseDAO dao) {
		this.baseDAO = dao;
		this.menuDAO = (MenuDAO)dao;
	}
	
}
