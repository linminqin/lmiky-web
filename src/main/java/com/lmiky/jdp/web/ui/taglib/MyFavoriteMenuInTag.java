package com.lmiky.jdp.web.ui.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import com.lmiky.jdp.database.model.PropertyCompareType;
import com.lmiky.jdp.database.model.PropertyFilter;
import com.lmiky.jdp.session.model.SessionInfo;
import com.lmiky.jdp.system.menu.pojo.MyFavoriteMenu;
import com.lmiky.jdp.system.menu.util.MenuUtils;
import com.lmiky.jdp.web.util.WebUtils;

/**
 * 判断是否在我的收藏菜单中
 * @author lmiky
 * @date 2013-6-17
 */ 
public class MyFavoriteMenuInTag extends MyFavoriteMenuTag {
	private static final long serialVersionUID = -4711526415451705329L;

	public MyFavoriteMenuInTag() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * @see com.lmiky.jdp.web.ui.taglib.BaseTag#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		try {
			HttpServletRequest request = getRequest();
			SessionInfo sessionInfo = WebUtils.getSessionInfo(request);
			if (sessionInfo == null) {
				return SKIP_BODY;
			}
			String menuId = getMenuId();
			Boolean isFavorite = sessionInfo.isInMenuFavorite(MenuUtils.SESSION_KEY_MYFAVORITE + menuId);
			if(isFavorite == null) {
				isFavorite = baseService.exist(MyFavoriteMenu.class, new PropertyFilter("userId", sessionInfo.getUserId(), PropertyCompareType.EQ, MyFavoriteMenu.class), new PropertyFilter("menuId", menuId, PropertyCompareType.EQ, MyFavoriteMenu.class));
			}
			MenuUtils.setMyFavorite(request, sessionInfo, menuId, isFavorite);
			if(!isFavorite) {
				return SKIP_BODY;
			}
			return EVAL_BODY_INCLUDE;
		} catch (Exception e) {
			throw new JspException(e.getMessage());
		}
	}

}