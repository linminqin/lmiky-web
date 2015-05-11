package com.lmiky.area.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;

import com.lmiky.area.pojo.BaseAreaPojo;
import com.lmiky.jdp.form.controller.FormController;
import com.lmiky.jdp.form.model.ValidateError;
import com.lmiky.jdp.form.util.ValidateUtils;

/**
 * @author lmiky
 * @date 2015年5月11日 上午10:00:47
 */
public abstract class BaseAreaController<T extends BaseAreaPojo> extends FormController<T> {
	
	/**
	 * @author lmiky
	 * @date 2015年5月11日 上午10:03:14
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String executeSaveArea(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse,
			@RequestParam(value = "id", required = false) Long id) throws Exception {
		modelMap.put("flag", "refresh");
		return executeSave(modelMap, request, resopnse, id);
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.form.controller.FormController#validateInput(com.lmiky.jdp.database.pojo.BasePojo, java.lang.String, org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public  List<ValidateError> validateInput(T pojo, String openMode, ModelMap modelMap, HttpServletRequest request) throws Exception {
		List<ValidateError> validateErrors = super.validateInput(pojo, openMode, modelMap, request);
		ValidateUtils.validateRequired(request, BaseAreaPojo.POJO_FIELD_NAME_NAME, "名称", validateErrors);
		ValidateUtils.validateRequired(request, BaseAreaPojo.POJO_FIELD_NAME_CODE, "编码", validateErrors);
		return validateErrors;
	}
	
	/**
	 * @author lmiky
	 * @date 2015年5月11日 上午10:04:40
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String executeDeleteArea(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse, @RequestParam(value = "id", required = false) Long id) throws Exception {
		modelMap.put("flag", "refresh");
		executeDelete(modelMap, request, resopnse, id);
		return executeLoad(modelMap, request, resopnse, null);
	}
}
