package com.lmiky.jdp.user.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lmiky.jdp.form.controller.FormController;
import com.lmiky.jdp.form.model.ValidateError;
import com.lmiky.jdp.form.util.ValidateUtils;
import com.lmiky.jdp.service.BaseService;
import com.lmiky.jdp.user.pojo.Role;

/**
 * 角色
 * @author lmiky
 * @date 2013-5-14
 */
@Controller
@RequestMapping("/role")
public class RoleController extends FormController<Role> {

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.form.controller.FormController#getAddAuthorityCode(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected String getAddAuthorityCode(ModelMap modelMap, HttpServletRequest request) {
		return "jdp_user_role_add";
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.form.controller.FormController#getModifyAuthorityCode(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected String getModifyAuthorityCode(ModelMap modelMap, HttpServletRequest request) {
		return "jdp_user_role_modify";
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.form.controller.FormController#getDeleteAuthorityCode(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected String getDeleteAuthorityCode(ModelMap modelMap, HttpServletRequest request) {
		return "jdp_user_role_delete";
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.base.controller.BaseController#getLoadAuthorityCode(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected String getLoadAuthorityCode(ModelMap modelMap, HttpServletRequest request) {
		return "jdp_user_role_load";
	}

	/**
	 * @author lmiky
	 * @date 2013-5-14
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list.shtml")
	public String list(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse) throws Exception {
		return executeList(modelMap, request, resopnse);
	}
	
	/**
	 * @author lmiky
	 * @date 2013-5-14
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
	 * @date 2013-5-14
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save.shtml")
	public String save(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse, @RequestParam(value = "id", required = false) Long id) throws Exception {
		return executeSave(modelMap, request, resopnse, id);
	}
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.form.controller.FormController#validateInput(com.lmiky.jdp.database.pojo.BasePojo, java.lang.String, org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public List<ValidateError> validateInput(Role pojo, String openMode, ModelMap modelMap, HttpServletRequest request) throws Exception {
		List<ValidateError> errors = super.validateInput(pojo, openMode, modelMap, request);
		ValidateUtils.validateRequired(request, "name", "名称", errors);
		return errors;
	}
	
	/**
	 * @author lmiky
	 * @date 2013-5-14
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete.shtml")
	public String delete(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse, @RequestParam(value = "id", required = false) Long id) throws Exception {
		return executeDelete(modelMap, request, resopnse, id);
	}

	/**
	 * 批量删除
	 * @author lmiky
	 * @date 2013-6-24
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/batchDelete.shtml")
	public String batchDelete(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse, @RequestParam(value = "batchDeleteId", required = false) Long[] ids) throws Exception {
		return executeBatchDelete(modelMap, request, resopnse, ids);
	}
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.base.controller.BaseController#getService()
	 */
	@Resource(name="roleService")
	public void setService(BaseService service) {
		this.service = service;
	}
}
