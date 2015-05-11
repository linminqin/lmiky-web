package com.lmiky.area.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lmiky.area.pojo.City;
import com.lmiky.area.pojo.Country;
import com.lmiky.area.pojo.Province;
import com.lmiky.jdp.controller.BaseWebController;
import com.lmiky.jdp.database.model.PropertyCompareType;
import com.lmiky.jdp.database.model.PropertyFilter;
import com.lmiky.jdp.database.model.Sort;
import com.lmiky.jdp.session.model.SessionInfo;

/**
 * 地区
 * @author lmiky
 * @date 2013-10-22
 */
@Controller
@RequestMapping("/area")
public class AreaController extends BaseWebController {
	public static final String AREA_TYPE_ROOT = "root";
	public static final String AREA_TYPE_COUNTRY = "country";
	public static final String AREA_TYPE_PROVINCE = "province";
	public static final String AREA_TYPE_CITY = "city";

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.base.controller.BaseController#getLoadAuthorityCode(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected String getLoadAuthorityCode(ModelMap modelMap, HttpServletRequest request) {
		return "jdp_area_manage";
	}
	
	/**
	 * 加载
	 * @author lmiky
	 * @date 2013-10-22
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/load.shtml")
	public String load(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse) throws Exception {
		try {
			return executeBaseLoad(modelMap, request, resopnse);
		} catch (Exception e) {
			return transactException(e, modelMap, request, resopnse);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.lmiky.jdp.base.controller.BaseController#processLoad(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.util.Map)
	 */
	@Override
	protected String processLoad(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse, Map<String, Object> loadParams)
			throws Exception {
		super.processLoad(modelMap, request, resopnse, loadParams);
		//modelMap.put("countries", service.list(Country.class, new Sort("phoneticAlphabet", Sort.SORT_TYPE_ASC, Country.class), new Sort("id", Sort.SORT_TYPE_ASC, Country.class)));
		return "jdp/area/load";
	}

	/**
	 * 获取树列表
	 * @author lmiky
	 * @date 2013-10-22
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/treeList.shtml")
	public String treeList(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse) throws Exception {
		try {
			// 判断是否有登陆
			SessionInfo sessionInfo = getSessionInfo(modelMap, request);
			// 检查单点登陆
			checkSso(sessionInfo, modelMap, request);
			String areaType = request.getParameter("areaType");
			modelMap.put("areaType", areaType);
			String areaId = request.getParameter("id");
			Long id = 0l;
			if(AREA_TYPE_ROOT.equals(areaType)) {
				modelMap.put("pojos", service.list(Country.class, new Sort("phoneticAlphabet", Sort.SORT_TYPE_ASC, Country.class), new Sort("id", Sort.SORT_TYPE_ASC, Country.class)));
				modelMap.put("id", id);
				return "countryTreeListJsonView";
			} else if(AREA_TYPE_COUNTRY.equals(areaType)) {
				id = Long.parseLong(areaId.substring(AREA_TYPE_COUNTRY.length()));
				modelMap.put("pojos", service.list(Province.class, new PropertyFilter("id", id, PropertyCompareType.EQ, Country.class)));
				modelMap.put("id", id);
				return "provinceTreeListJsonView";
			} else if(AREA_TYPE_PROVINCE.equals(areaType)) {
				id = Long.parseLong(areaId.substring(AREA_TYPE_PROVINCE.length()));
				modelMap.put("pojos", service.list(City.class, new PropertyFilter("id", id, PropertyCompareType.EQ, Province.class)));
				modelMap.put("id", id);
				return "cityTreeListJsonView";
			}
		} catch (Exception e) {
			// TODO 如果是登陆或者权限异常,用脚本让页面跳转到登陆页面
			logException(e, modelMap, request, resopnse);
		}
		return null;
	}

}
