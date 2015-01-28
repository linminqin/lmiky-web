package com.lmiky.jdp.system.menu.service;

import java.util.List;

import com.lmiky.jdp.session.model.SessionInfo;
import com.lmiky.jdp.system.menu.model.LeftMenu;
import com.lmiky.jdp.system.menu.model.SubMenu;
import com.lmiky.jdp.system.menu.model.TopMenu;

/**
 * 菜单
 * @author lmiky
 * @date 2013-6-16
 */
public interface MenuParseService {
	/**
	 * 初始化
	 * @author lmiky
	 * @date 2013-6-16
	 * @throws Exception
	 */
	public void init() throws Exception;
	
	/**
	 * 解析菜单
	 * @author lmiky
	 * @date 2013-12-31
	 * @throws Exception
	 */
	public void parse() throws Exception;
	
	/**
	 * 解析获取拥有权限的菜单列表
	 * @author lmiky
	 * @date 2013-6-16
	 * @param sessionInfo
	 * @return
	 * @throws Exception
	 */
	public List<TopMenu> getTopMenus(SessionInfo sessionInfo) throws Exception;
	
	/**
	 * 根据ID获取类别菜单
	 * @author lmiky
	 * @date 2014-1-5
	 * @param topMenuId
	 * @param sessionInfo
	 * @return
	 * @throws Exception
	 */
	public TopMenu getTopMenu(String topMenuId, SessionInfo sessionInfo) throws Exception;
	
	/**
	 * 根据顶层菜单ID获取左菜单
	 * @author lmiky
	 * @date 2014-1-22
	 * @param topMenuId
	 * @param sessionInfo 如果不为空，则检查权限，没有权限则返回空值
	 * @return
	 * @throws Exception
	 */
	public List<LeftMenu> getLeftMenus(String topMenuId, SessionInfo sessionInfo) throws Exception;
	
	/**
	 * 根据ID获取子菜单
	 * @author lmiky
	 * @date 2013-6-16
	 * @param subMenuId
	 * @param sessionInfo 如果不为空，则检查权限，没有权限则返回空值
	 * @return
	 * @throws Exception
	 */
	public SubMenu getSubMenu(String subMenuId, SessionInfo sessionInfo) throws Exception;
}
