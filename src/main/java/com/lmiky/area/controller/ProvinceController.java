package com.lmiky.area.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lmiky.area.pojo.Country;
import com.lmiky.area.pojo.Province;

/**
 * 省份
 * @author lmiky
 * @date 2013-10-22
 */
@Controller
@RequestMapping("/province")
public class ProvinceController extends BaseAreaController<Province> {
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.form.controller.FormController#getAddAuthorityCode(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected String getAddAuthorityCode(ModelMap modelMap, HttpServletRequest request) {
		return "jdp_area_manage";
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.form.controller.FormController#getModifyAuthorityCode(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected String getModifyAuthorityCode(ModelMap modelMap, HttpServletRequest request) {
		return "jdp_area_manage";
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.form.controller.FormController#getDeleteAuthorityCode(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected String getDeleteAuthorityCode(ModelMap modelMap, HttpServletRequest request) {
		return "jdp_area_manage";
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.base.controller.BaseController#getLoadAuthorityCode(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected String getLoadAuthorityCode(ModelMap modelMap, HttpServletRequest request) {
		return "jdp_area_manage";
	}

	/**
	 * @author lmiky
	 * @date 2013-10-22
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
	 * 方法说明
	 * @author lmiky
	 * @date 2013-10-22
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save.shtml")
	public String save(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse,
			@RequestParam(value = "id", required = false) Long id) throws Exception {
		return executeSaveArea(modelMap, request, resopnse, id);
	}
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.form.controller.FormController#setPojoProperties(com.lmiky.jdp.database.pojo.BasePojo, org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void setPojoProperties(Province pojo, ModelMap modelMap, HttpServletRequest request) throws Exception {
		super.setPojoProperties(pojo, modelMap, request);
		Long countryId = Long.parseLong(request.getParameter("countryId"));
		Country country = new Country();
		country.setId(countryId);
		pojo.setCountry(country);
	}

	/**
	 * 删除
	 * @author lmiky
	 * @date 2013-10-23
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
}
