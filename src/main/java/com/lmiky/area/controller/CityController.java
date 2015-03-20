package com.lmiky.area.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lmiky.area.pojo.City;
import com.lmiky.area.pojo.Province;
import com.lmiky.jdp.form.controller.FormController;
import com.lmiky.jdp.form.model.ValidateError;
import com.lmiky.jdp.form.util.ValidateUtils;

/**
 * 地市
 * @author lmiky
 * @date 2013-10-24
 */
@Controller
@RequestMapping("/city")
public class CityController extends FormController<City> {

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
	public String save(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse,
			@RequestParam(value = "id", required = false) Long id) throws Exception {
		modelMap.put("flag", "refresh");
		return executeSave(modelMap, request, resopnse, id);
	}
	
	/* (non-Javadoc)
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

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.form.controller.FormController#validateInput(com.lmiky.jdp.database.pojo.BasePojo, java.lang.String, org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public List<ValidateError> validateInput(City pojo, String openMode, ModelMap modelMap, HttpServletRequest request) throws Exception {
		List<ValidateError> validateErrors = super.validateInput(pojo, openMode, modelMap, request);
		ValidateUtils.validateRequired(request, "name", "名称", validateErrors);
		return validateErrors;
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
		modelMap.put("flag", "refresh");
		executeDelete(modelMap, request, resopnse, id);
		return executeLoad(modelMap, request, resopnse, null);
	}
}
