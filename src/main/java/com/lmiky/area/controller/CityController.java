package com.lmiky.area.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lmiky.area.pojo.City;
import com.lmiky.area.pojo.Province;
import com.lmiky.jdp.controller.BaseWebController;
import com.lmiky.jdp.controller.view.BaseCodeDataListView;
import com.lmiky.jdp.database.model.PropertyFilter;
import com.lmiky.jdp.session.model.SessionInfo;

/**
 * 地市
 * @author lmiky
 * @date 2013-10-24
 */
@Controller
@RequestMapping("/city")
public class CityController extends BaseAreaController<City> {

	/*
	 * (non-Javadoc)
	 * @see com.lmiky.jdp.form.controller.FormController#getAddAuthorityCode(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected String getAddAuthorityCode(ModelMap modelMap, HttpServletRequest request) {
		return "jdp_area_manage";
	}

	/*
	 * (non-Javadoc)
	 * @see com.lmiky.jdp.form.controller.FormController#getModifyAuthorityCode(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected String getModifyAuthorityCode(ModelMap modelMap, HttpServletRequest request) {
		return "jdp_area_manage";
	}

	/*
	 * (non-Javadoc)
	 * @see com.lmiky.jdp.form.controller.FormController#getDeleteAuthorityCode(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected String getDeleteAuthorityCode(ModelMap modelMap, HttpServletRequest request) {
		return "jdp_area_manage";
	}

	/*
	 * (non-Javadoc)
	 * @see com.lmiky.jdp.base.controller.BaseController#getLoadAuthorityCode(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected String getLoadAuthorityCode(ModelMap modelMap, HttpServletRequest request) {
		return "jdp_area_manage";
	}

	/**
	 * @author lmiky
	 * @date 2013-10-24
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/load.shtml")
	public String load(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse, @RequestParam(value = "id", required = false) Long id) throws Exception {
		return executeLoad(modelMap, request, resopnse, id);
	}

	/**
	 * @author lmiky
	 * @date 2013-10-24
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save.shtml")
	public String save(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse, @RequestParam(value = "id", required = false) Long id) throws Exception {
		return executeSaveArea(modelMap, request, resopnse, id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lmiky.jdp.form.controller.FormController#setPojoProperties(com.lmiky.jdp.database.pojo.BasePojo, org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void setPojoProperties(City pojo, ModelMap modelMap, HttpServletRequest request) throws Exception {
		super.setPojoProperties(pojo, modelMap, request);
		Long provinceId = Long.parseLong(request.getParameter("provinceId"));
		Province province = new Province();
		province.setId(provinceId);
		pojo.setProvince(province);
	}

	/**
	 * 删除
	 * @author lmiky
	 * @date 2013-10-24
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete.shtml")
	public String delete(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse, @RequestParam(value = "id", required = false) Long id) throws Exception {
		return executeDeleteArea(modelMap, request, resopnse, null);
	}

	/**
	 * 异步获取城市列表
	 * @author lmiky
	 * @date 2015年5月26日 下午1:30:59
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param provinceId
	 * @param provinceCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ajaxList.shtml")
	public String ajaxList(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "provinceId", required = false) Long provinceId,
			@RequestParam(value = "provinceCode", required = false) String provinceCode) throws Exception {
		try {
			// 判断是否有登陆
			SessionInfo sessionInfo = getSessionInfo(modelMap, request);
			// 检查单点登陆
			checkSso(sessionInfo, modelMap, request);
			// 检查权限
			checkAuthority(modelMap, request, sessionInfo, getLoadAuthorityCode(modelMap, request));
			//获取列表
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			if(!StringUtils.isBlank(provinceCode)) {
				filters.add(new PropertyFilter(Province.POJO_FIELD_NAME_CODE, provinceCode, Province.class));
			}
			if(provinceId != null) {
				filters.add(new PropertyFilter(Province.POJO_FIELD_NAME_ID, provinceId, Province.class));
			}
			PropertyFilter[] filterArray = {};
			List<City> citys = service.list(City.class, filters.toArray(filterArray));
			//放入结果集
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("citys", citys);
			modelMap.put(BaseCodeDataListView.KEY_NAME_DATA, dataMap);
			return "baseAreaListJsonView";
		} catch (Exception e) {
			return transactException(e, modelMap, request, response, BaseWebController.REQUESTTYPE_AJAX);
		}
	}
}
