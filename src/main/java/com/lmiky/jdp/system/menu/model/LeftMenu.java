package com.lmiky.jdp.system.menu.model;

import java.util.List;

/**
 * 左菜单菜单
 * @author lmiky
 * @date 2013-6-16
 */
public class LeftMenu extends Menu {
	private static final long serialVersionUID = -3891079667547674181L;
	private TopMenu topMenu;
	private List<SubMenu> subMenus;
	
	/**
	 * @return the topMenu
	 */
	public TopMenu getTopMenu() {
		return topMenu;
	}
	/**
	 * @param topMenu the topMenu to set
	 */
	public void setTopMenu(TopMenu topMenu) {
		this.topMenu = topMenu;
	}
	/**
	 * @return the subMenus
	 */
	public List<SubMenu> getSubMenus() {
		return subMenus;
	}
	/**
	 * @param subMenus the subMenus to set
	 */
	public void setSubMenus(List<SubMenu> subMenus) {
		this.subMenus = subMenus;
	}
}
