package com.lmiky.jdp.system.menu.service;

import java.util.List;

import com.lmiky.jdp.service.BaseService;
import com.lmiky.jdp.service.exception.ServiceException;

/**
 * @author lmiky
 * @date 2014年8月13日 下午10:28:09
 */
public interface MenuService extends BaseService {

	/**
	 * 获取最近操作菜单ID
	 * @author lmiky
	 * @date 2014年8月13日 下午10:19:27
	 * @param userId 指定用户
	 * @param size	要获取的条数
	 * @return
	 * @throws ServiceException
	 */
	public List<String> listLatelyOperateMenuId(Long userId, int size) throws ServiceException;

	/**
	 * 获取收藏夹菜单ID
	 * @author lmiky
	 * @date 2014年8月13日 下午10:35:35
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public List<String> listFavoriteMenuId(Long userId) throws ServiceException;
}
