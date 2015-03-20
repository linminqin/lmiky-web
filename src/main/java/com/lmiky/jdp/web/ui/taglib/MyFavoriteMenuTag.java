package com.lmiky.jdp.web.ui.taglib;


import com.lmiky.jdp.service.BaseService;
import com.lmiky.jdp.util.Environment;

/**
 * 我的收藏菜单
 * @author lmiky
 * @date 2013-6-17
 */ 
public abstract class MyFavoriteMenuTag extends BaseTag {
	private static final long serialVersionUID = -5875283682460630975L;
	protected BaseService baseService;
	private String menuId;

	public MyFavoriteMenuTag() {
		baseService = (BaseService) Environment.getBean("baseService");
	}

	/**
	 * @return the menuId
	 */
	public String getMenuId() {
		return menuId;
	}

	/**
	 * @param menuId the menuId to set
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

}
