package com.lmiky.jdp.system.menu.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lmiky.jdp.controller.BaseController;
import com.lmiky.jdp.database.model.PropertyCompareType;
import com.lmiky.jdp.database.model.PropertyFilter;
import com.lmiky.jdp.json.util.JsonUtils;
import com.lmiky.jdp.session.exception.SessionException;
import com.lmiky.jdp.session.model.SessionInfo;
import com.lmiky.jdp.system.menu.pojo.MyFavoriteMenu;
import com.lmiky.jdp.system.menu.service.MenuService;
import com.lmiky.jdp.system.menu.util.MenuUtils;
import com.lmiky.jdp.util.PropertiesUtils;
import com.lmiky.jdp.util.ResponseUtils;

/**
 * 菜单
 * @author lmiky
 * @date 2013-6-16
 */
@Controller
@RequestMapping("/sso/system/menu")
public class MenuController extends BaseController {
	public static final String CODE_SUCCESS = "0";
	public static final String CODE_FAIL = "-1";

	public static final String TOP_MENU_ID_MYINDEX = "myIndex";
	public static final String TOP_MENU_LABEL_MYINDEX = "个人主页";

	// 菜单名称前缀
	public static final String MENU_LABEL_LEFTMENU_PREFIX = "system.menu.label.leftMenu.";
	public static final String LEFTMENU_ID_LATELYOPE = "latelyOpe";
	public static final String LEFTMENU_ID_MYFAVORITE = "myFavorite";
	public static final String MENU_LABEL_LEFTMENU_LATELYOPE = PropertiesUtils.getStringContextValue(MENU_LABEL_LEFTMENU_PREFIX
			+ LEFTMENU_ID_LATELYOPE);
	public static final String MENU_LABEL_LEFTMENU_MYFAVORITE = PropertiesUtils.getStringContextValue(MENU_LABEL_LEFTMENU_PREFIX
			+ LEFTMENU_ID_MYFAVORITE);
	
	private MenuService menuService;

	/**
	 * 加载菜单列表
	 * @author lmiky
	 * @date 2013-6-16
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/load.shtml")
	public String load(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "topMenuId", required = false) String topMenuId) throws Exception {
		try {
			// 判断是否有登陆
			SessionInfo sessionInfo = getSessionInfo(modelMap, request);
			// 检查单点登陆
			checkSso(sessionInfo, modelMap, request);
			//sessionInfo.setTopMenus(menuService.getTopMenus(sessionInfo));
			request.setAttribute("welcomeMenu", MenuUtils.getMyWelcomeMenu(modelMap, request, sessionInfo));
			return "index";
		} catch (Exception e) {
			return transactException(e, modelMap, request, response);
		}
	}

	/**
	 * 获取左菜单列表
	 * @author lmiky
	 * @date 2014-1-22
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param topMenuId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listLeftMenus.shtml")
	public String listLeftMenus(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "topMenuId", required = true) String topMenuId) throws Exception {
		try {
			// 判断是否有登陆
			SessionInfo sessionInfo = getSessionInfo(modelMap, request);
			// 检查单点登陆
			checkSso(sessionInfo, modelMap, request);
			modelMap.put("leftMenus", MenuUtils.getLeftMenus(modelMap, request, sessionInfo, topMenuId));
			return "leftMenu";
		} catch (Exception e) {
			return transactException(e, modelMap, request, response);
		}
	}

	/**
	 * 添加到收藏夹
	 * @author lmiky
	 * @date 2013-6-17
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param menuId
	 * @throws Exception
	 */
	@RequestMapping("/addMyFavorite.shtml")
	public void addMyFavorite(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "menuId", required = true) String menuId) throws Exception {
		Map<String, String> retMap = new HashMap<String, String>();
		retMap.put("code", CODE_FAIL);
		try {
			SessionInfo sessionInfo = getSessionInfo(modelMap, request);
			if (service.exist(MyFavoriteMenu.class, new PropertyFilter("userId", sessionInfo.getUserId(), PropertyCompareType.EQ,
					MyFavoriteMenu.class), new PropertyFilter("menuId", menuId, PropertyCompareType.EQ, MyFavoriteMenu.class))) {
				retMap.put("msg", "该菜单已经在您的收藏夹中！");
			} else {
				MyFavoriteMenu myFavoriteMenu = new MyFavoriteMenu();
				myFavoriteMenu.setMenuId(menuId);
				myFavoriteMenu.setUserId(sessionInfo.getUserId());
				myFavoriteMenu.setAddTime(new Date());
				service.add(myFavoriteMenu);
				MenuUtils.addMyFavorite(request, sessionInfo, menuId);
				retMap.put("code", CODE_SUCCESS);
				retMap.put("msg", "收藏成功！");
			}
			ResponseUtils.write(response, JsonUtils.toJson(retMap));
		} catch (Exception e) {
			logException(e, modelMap, request, response);
			if (e instanceof SessionException) {
				retMap.put("msg", "添加失败，登录超时！");
			} else {
				retMap.put("msg", "添加失败，系统错误！");
			}
			ResponseUtils.write(response, JsonUtils.toJson(retMap));
		}
	}

	/**
	 * 移除收藏夹
	 * @author lmiky
	 * @date 2013-6-17
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param menuId
	 * @throws Exception
	 */
	@RequestMapping("/removeMyFavorite.shtml")
	public void removeMyFavorite(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "menuId", required = true) String menuId) throws Exception {
		Map<String, String> retMap = new HashMap<String, String>();
		retMap.put("code", CODE_FAIL);
		try {
			SessionInfo sessionInfo = getSessionInfo(modelMap, request);
			MyFavoriteMenu myFavoriteMenu = service
					.find(MyFavoriteMenu.class, new PropertyFilter("userId", sessionInfo.getUserId(), PropertyCompareType.EQ, MyFavoriteMenu.class),
							new PropertyFilter("menuId", menuId, PropertyCompareType.EQ, MyFavoriteMenu.class));
			if (myFavoriteMenu == null) {
				retMap.put("msg", "该菜单不在您的收藏夹中！");
			} else {
				service.delete(myFavoriteMenu);
				MenuUtils.removeMyFavorite(request, sessionInfo, menuId);
				retMap.put("code", CODE_SUCCESS);
				retMap.put("msg", "取消成功！");
			}
			ResponseUtils.write(response, JsonUtils.toJson(retMap));
		} catch (Exception e) {
			logException(e, modelMap, request, response);
			if (e instanceof SessionException) {
				retMap.put("msg", "移除失败，登录超时！");
			} else {
				retMap.put("msg", "移除失败，系统错误！");
			}
			ResponseUtils.write(response, JsonUtils.toJson(retMap));
		}
	}

	/**
	 * @return the menuService
	 */
	public MenuService getMenuService() {
		return menuService;
	}

	/**
	 * @param menuService the menuService to set
	 */
	@Resource(name="menuService")
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

}
