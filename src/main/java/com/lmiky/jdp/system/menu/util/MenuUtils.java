package com.lmiky.jdp.system.menu.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;

import com.lmiky.jdp.session.exception.SessionException;
import com.lmiky.jdp.session.model.SessionInfo;
import com.lmiky.jdp.system.menu.controller.MenuController;
import com.lmiky.jdp.system.menu.model.LeftMenu;
import com.lmiky.jdp.system.menu.model.SubMenu;
import com.lmiky.jdp.system.menu.model.TopMenu;
import com.lmiky.jdp.system.menu.service.MenuParseService;
import com.lmiky.jdp.system.menu.service.MenuService;
import com.lmiky.jdp.util.Environment;
import com.lmiky.jdp.util.PropertiesUtils;
import com.lmiky.jdp.web.constants.Constants;
import com.lmiky.jdp.web.util.WebUtils;

/**
 * 菜单工具类
 * @author lmiky
 * @date 2013-12-8
 */
public class MenuUtils {
	public static final String SESSION_KEY_MYFAVORITE = "menu_myFavorite_";
	private static Integer latelyOperateMenuNum = PropertiesUtils.getIntValue(Constants.PROPERTIES_KEY_WEB_FILE, "menu.latelyOperateMenuNum");

	/**
	 * 添加收藏夹信息
	 * @author lmiky
	 * @date 2013-12-8
	 * @param request
	 * @param sessionInfo
	 * @param menuId
	 */
	public static void addMyFavorite(HttpServletRequest request, SessionInfo sessionInfo, String menuId) {
		setMyFavorite(request, sessionInfo, menuId, true);
	}

	/**
	 * 移除收藏夹信息
	 * @author lmiky
	 * @date 2013-12-8
	 * @param request
	 * @param sessionInfo
	 * @param menuId
	 */
	public static void removeMyFavorite(HttpServletRequest request, SessionInfo sessionInfo, String menuId) {
		setMyFavorite(request, sessionInfo, menuId, false);
	}

	/**
	 * 设置收藏夹信息
	 * @author lmiky
	 * @date 2013-12-8
	 * @param request
	 * @param sessionInfo
	 * @param menuId
	 * @param value
	 */
	public static void setMyFavorite(HttpServletRequest request, SessionInfo sessionInfo, String menuId, Boolean value) {
		sessionInfo.setMenuFavoriteInfo(SESSION_KEY_MYFAVORITE + menuId, value);
	}

	/**
	 * 获取顶级菜单
	 * @author lmiky
	 * @date 2014-6-28
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param sessionInfo
	 * @param topMenuId
	 * @return
	 * @throws Exception
	 */
	public static TopMenu getTopMenu(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, SessionInfo sessionInfo, String topMenuId) throws Exception {
		TopMenu topMenu = null;
		List<TopMenu> topMenus = getMenuParseService().getTopMenus(sessionInfo);
		for (TopMenu tm : topMenus) {
			if (topMenuId.equals(tm.getId())) {
				topMenu = tm;
			}
		}
		// 个人主页
		if (topMenu == null) {
			topMenu = new TopMenu();
			topMenu.setId(MenuController.TOP_MENU_ID_MYINDEX);
			topMenu.setLabel(MenuController.TOP_MENU_LABEL_MYINDEX);
		}
		return topMenu;
	}

	/**
	 * 获取左菜单列表
	 * @author lmiky
	 * @date 2014-6-28
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param sessionInfo
	 * @param topMenuId
	 * @return
	 * @throws Exception
	 */
	public static List<LeftMenu> getLeftMenus(ModelMap modelMap, HttpServletRequest request, SessionInfo sessionInfo, String topMenuId) throws Exception {
		List<LeftMenu> leftMenuList = new ArrayList<LeftMenu>();
		if (MenuController.TOP_MENU_ID_MYINDEX.equals(topMenuId)) {
			leftMenuList = getMyIndexMenus(modelMap, request, sessionInfo, topMenuId);
		} else {
			leftMenuList = getMenuParseService().getLeftMenus(topMenuId, sessionInfo);
		}
		return leftMenuList;
	}

	/**
	 * 获取左菜单列表
	 * @author lmiky
	 * @date 2014-6-28
	 * @param modelMap
	 * @param request
	 * @param sessionInfo
	 * @param topMenu 如果为空，则获取个人主页菜单列表
	 * @return
	 * @throws Exception
	 */
	public static List<LeftMenu> getLeftMenus(ModelMap modelMap, HttpServletRequest request, SessionInfo sessionInfo, TopMenu topMenu) throws Exception {
		List<LeftMenu> leftMenuList = new ArrayList<LeftMenu>();
		if (topMenu == null || MenuController.TOP_MENU_ID_MYINDEX.equals(topMenu.getId())) {
			leftMenuList = getMyIndexMenus(modelMap, request, sessionInfo, MenuController.TOP_MENU_ID_MYINDEX);
		} else {
			leftMenuList = topMenu.getLeftMenus();
		}
		return leftMenuList;
	}

	/**
	 * 获取个人主页菜单列表
	 * @author lmiky
	 * @date 2014-6-28
	 * @param modelMap
	 * @param request
	 * @param sessionInfo
	 * @param topMenuId
	 * @return
	 * @throws Exception
	 */
	public static List<LeftMenu> getMyIndexMenus(ModelMap modelMap, HttpServletRequest request, SessionInfo sessionInfo, String topMenuId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", sessionInfo.getUserId());

		List<LeftMenu> leftMenuList = new ArrayList<LeftMenu>();
		LeftMenu leftMenu = new LeftMenu();
		leftMenu.setId(MenuController.LEFTMENU_ID_LATELYOPE);
		leftMenu.setLabel(MenuController.MENU_LABEL_LEFTMENU_LATELYOPE);
		// 最近操作菜单
		String menuForm = request.getParameter(Constants.HTTP_PARAM_MENU_FROM);
		boolean isFormLatelyMenu = (MenuController.TOP_MENU_ID_MYINDEX + "-" + MenuController.LEFTMENU_ID_LATELYOPE).equals(menuForm); // 是否来源于最近操作菜单
		if (sessionInfo.getLatelyOpeMenus() != null && !sessionInfo.getLatelyOpeMenus().isEmpty() && isFormLatelyMenu) {
			leftMenu.setSubMenus(sessionInfo.getLatelyOpeMenus());
		} else {
			// 如果按时间降序排序，mysql会被distinct干扰,无法获取想要的结果，只能按id降序排序
			List<String> subMenuIds = getMenuService().listLatelyOperateMenuId(sessionInfo.getUserId(), latelyOperateMenuNum.intValue());
			List<SubMenu> opeMenus = new ArrayList<SubMenu>();
			for (String subMenuId : subMenuIds) {
				SubMenu subMenu = getMenuParseService().getSubMenu(subMenuId, sessionInfo);
				if (subMenu != null) {
					opeMenus.add(subMenu);
				}
			}
			leftMenu.setSubMenus(opeMenus);
			sessionInfo.setLatelyOpeMenus(opeMenus);
		}
		leftMenuList.add(leftMenu);
		// 我的收藏菜单
		leftMenu = new LeftMenu();
		leftMenu.setId(MenuController.LEFTMENU_ID_MYFAVORITE);
		leftMenu.setLabel(MenuController.MENU_LABEL_LEFTMENU_MYFAVORITE);
		List<String> subMenuIds = getMenuService().listFavoriteMenuId(sessionInfo.getUserId());
		List<SubMenu> favoriteMenus = new ArrayList<SubMenu>();
		for (String subMenuId : subMenuIds) {
			SubMenu subMenu = getMenuParseService().getSubMenu(subMenuId, sessionInfo);
			if (subMenu != null) {
				favoriteMenus.add(subMenu);
			}
		}
		leftMenu.setSubMenus(favoriteMenus);
		leftMenuList.add(leftMenu);
		return leftMenuList;
	}

	/**
	 * 获取我的最近操作link菜单
	 * @author lmiky
	 * @date 2014-6-28
	 * @param modelMap
	 * @param request
	 * @param sessionInfo
	 * @return
	 * @throws Exception
	 */
	public static SubMenu getMyLatelyOpeLinkMenu(ModelMap modelMap, HttpServletRequest request, SessionInfo sessionInfo) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", sessionInfo.getUserId());
		// 最近操作菜单
		// 如果按时间降序排序，mysql会被distinct干扰,无法获取想要的结果，只能按id降序排序
		List<String> subMenuIds = getMenuService().listLatelyOperateMenuId(sessionInfo.getUserId(), latelyOperateMenuNum.intValue());
		for (String subMenuId : subMenuIds) {
			SubMenu subMenu = getMenuParseService().getSubMenu(subMenuId, sessionInfo);
			if (subMenu != null && SubMenu.TYPE_LINK.equals(subMenu.getType())) {
				return subMenu;
			}
		}
		return null;
	}

	/**
	 * 获取进入系统欢迎菜单
	 * @author lmiky
	 * @date 2014-6-28
	 * @param modelMap
	 * @param request
	 * @param sessionInfo
	 * @return
	 * @throws Exception
	 */
	public static SubMenu getMyWelcomeMenu(ModelMap modelMap, HttpServletRequest request, SessionInfo sessionInfo) throws Exception {
		SubMenu welcomeMenu = null;
		// 最近操作菜单
		SubMenu subMenu = getMyLatelyOpeLinkMenu(modelMap, request, sessionInfo);
		if (subMenu != null) {
			welcomeMenu = (SubMenu) subMenu.clone();
			welcomeMenu.setUrl(subMenu.getUrl() + "&" + Constants.HTTP_PARAM_MENU_FROM + "=" + MenuController.TOP_MENU_ID_MYINDEX);
			return welcomeMenu;
		}
		// 收藏夹
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", sessionInfo.getUserId());
		List<String> subMenuIds = getMenuService().listFavoriteMenuId(sessionInfo.getUserId());
		MenuParseService menuParseService = getMenuParseService();
		for (String subMenuId : subMenuIds) {
			SubMenu sm = menuParseService.getSubMenu(subMenuId, sessionInfo);
			if (sm != null && SubMenu.TYPE_LINK.equals(sm.getType())) {
				welcomeMenu = (SubMenu) sm.clone();
				welcomeMenu.setUrl(sm.getUrl() + "&" + Constants.HTTP_PARAM_MENU_FROM + "=" + MenuController.TOP_MENU_ID_MYINDEX + "-" + MenuController.LEFTMENU_ID_LATELYOPE);
				return welcomeMenu;
			}
		}
		// 拥有权限的菜单
		List<TopMenu> topMenus = menuParseService.getTopMenus(sessionInfo);
		for (TopMenu topMenu : topMenus) {
			for (LeftMenu leftMenu : topMenu.getLeftMenus()) {
				for (SubMenu sm : leftMenu.getSubMenus()) {
					if (sm != null && SubMenu.TYPE_LINK.equals(sm.getType())) {
						welcomeMenu = (SubMenu) sm.clone();
						welcomeMenu.setUrl(sm.getUrl() + "&" + Constants.HTTP_PARAM_MENU_FROM + "=" + topMenu.getId() + "-" + sm.getLeftMenu().getId());
						return sm;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 获取当前子菜单ID
	 * @author lmiky
	 * @date 2014-6-29
	 * @param request
	 * @return
	 */
	public static String getSubMenuId(ModelMap modelMap, HttpServletRequest request) {
		String subMenuId = request.getParameter(Constants.HTTP_PARAM_SUBMENU_ID);
		if (subMenuId == null) {
			subMenuId = "";
		}
		return subMenuId.trim();
	}

	/**
	 * 获取当前子菜单
	 * @author lmiky
	 * @date 2014-6-29
	 * @param request
	 * @return
	 * @throws SessionException
	 * @throws Exception
	 */
	public static SubMenu getSubMenu(ModelMap modelMap, HttpServletRequest request) throws SessionException, Exception {
		String subMenuId = getSubMenuId(modelMap, request);
		if (StringUtils.isBlank(subMenuId)) {
			return null;
		}
		return getMenuParseService().getSubMenu(subMenuId, WebUtils.getSessionInfo(request));
	}

	/**
	 * 获取当前顶层菜单ID
	 * @author lmiky
	 * @date 2014-6-29
	 * @param request
	 * @return
	 */
	public static String getTopMenuId(ModelMap modelMap, HttpServletRequest request) {
		String menuForm = request.getParameter(Constants.HTTP_PARAM_MENU_FROM);
		if (menuForm == null) {
			menuForm = "";
		}
		return menuForm.trim().split("-")[0];
	}

	/**
	 * 获取当前顶层菜单
	 * @author lmiky
	 * @date 2014-6-29
	 * @param request
	 * @return
	 * @throws SessionException
	 * @throws Exception
	 */
	public static TopMenu getTopMenu(ModelMap modelMap, HttpServletRequest request) throws SessionException, Exception {
		String topMenuId = getTopMenuId(modelMap, request);
		if (StringUtils.isBlank(topMenuId)) {
			topMenuId = MenuController.TOP_MENU_ID_MYINDEX;
		}
		SessionInfo sessionInfo = WebUtils.getSessionInfo(request);
		if (sessionInfo == null) {
			throw new SessionException(SessionException.SESSION_NULL);
		}
		if (MenuController.TOP_MENU_ID_MYINDEX.equals(topMenuId)) {
			TopMenu topMenu = new TopMenu();
			topMenu.setId(topMenuId);
			topMenu.setLabel(MenuController.TOP_MENU_LABEL_MYINDEX);
			topMenu.setLeftMenus(getLeftMenus(modelMap, request, sessionInfo, topMenu));
			return topMenu;
		}
		for (TopMenu tm : sessionInfo.getTopMenus()) {
			if (topMenuId.equals(tm.getId())) {
				return tm;
			}
		}
		return null;
	}

	/**
	 * 获取菜单解析业务
	 * @author lmiky
	 * @date 2014-6-28
	 * @return
	 */
	private static MenuParseService getMenuParseService() {
		return (MenuParseService) Environment.getBean("menuParseService");
	}

	/**
	 * 获取菜单业务
	 * @author lmiky
	 * @date 2014年8月13日 下午10:31:38
	 * @return
	 */
	private static MenuService getMenuService() {
		return (MenuService) Environment.getBean("menuService");
	}
}
