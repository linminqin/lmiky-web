package com.lmiky.jdp.system.menu.model;

import java.util.List;

/**
 * 顶层菜单
 * @author lmiky
 * @date 2013-6-16
 */
public class TopMenu extends Menu {
	private static final long serialVersionUID = -7617581225680485852L;
	private List<LeftMenu> leftMenus;
	
	/**
	 * @return the leftMenus
	 */
	public List<LeftMenu> getLeftMenus() {
		return leftMenus;
	}
	/**
	 * @param leftMenus the leftMenus to set
	 */
	public void setLeftMenus(List<LeftMenu> leftMenus) {
		this.leftMenus = leftMenus;
	}
}
