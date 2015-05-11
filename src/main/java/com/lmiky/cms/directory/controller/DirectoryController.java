package com.lmiky.cms.directory.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lmiky.cms.directory.pojo.CmsDirectory;
import com.lmiky.jdp.controller.BaseWebController;
import com.lmiky.jdp.controller.view.BaseCode;
import com.lmiky.jdp.controller.view.BaseCodeView;
import com.lmiky.jdp.form.model.ValidateError;
import com.lmiky.jdp.form.util.ValidateUtils;
import com.lmiky.jdp.service.BaseService;
import com.lmiky.jdp.tree.controller.BaseTreeController;
import com.lmiky.jdp.web.constants.Constants;

/**
 * 栏目
 * @author lmiky
 * @date 2014-1-3
 */
@Controller
@RequestMapping("/cms/directory")
public class DirectoryController extends BaseTreeController<CmsDirectory> {

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.form.controller.FormController#getAddAuthorityCode(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected String getAddAuthorityCode(ModelMap modelMap, HttpServletRequest request) {
		return "cms_directory_add";
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.form.controller.FormController#getModifyAuthorityCode(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected String getModifyAuthorityCode(ModelMap modelMap, HttpServletRequest request) {
		return "cms_directory_modify";
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.form.controller.FormController#getDeleteAuthorityCode(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected String getDeleteAuthorityCode(ModelMap modelMap, HttpServletRequest request) {
		return "cms_directory_delete";
	}
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.base.controller.BaseController#getLoadAuthorityCode(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected String getLoadAuthorityCode(ModelMap modelMap, HttpServletRequest request) {
		return "cms_directory_load";
	}

	/**
	 * 加载页面
	 * @author lmiky
	 * @date 2014-7-15
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadPage.shtml")
	public String loadPage(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse) throws Exception {
		try {
			modelMap.put(Constants.HTTP_PARAM_MODULE_PATH, getModulePath(modelMap, request));
			//设置菜单
			setMenuInfo(modelMap, request);
			return "cms/directory/load";
		} catch (Exception e) {
			return transactException(e, modelMap, request, resopnse);
		}
	}
	
	/**
	 * @author lmiky
	 * @date 2014-1-3
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadTree.shtml")
	public String loadTree(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse) throws Exception {
		return executeLoadTree(modelMap, request, resopnse);
	}
	
	/**
	 * @author lmiky
	 * @date 2014-1-4
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/treeList.shtml")
	public String treeList(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse, @RequestParam(value = "id", required = false) Long id) throws Exception {
		return executeTreeList(modelMap, request, resopnse, id);
	}
	
	/**
	 * @author lmiky
	 * @date 2014-1-5
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/load.shtml")
	public String load(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse,
			@RequestParam(value = "id", required = false) Long id) throws Exception {
		return executeLoad(modelMap, request, resopnse, id);
	}

	/**
	 * @author lmiky
	 * @date 2014-1-5
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
		return executeSave(modelMap, request, resopnse, id);
	}


	/**
	 * @author lmiky
	 * @date 2014-1-5
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete.shtml")
	public String delete(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse,
			@RequestParam(value = "id", required = true) Long id) throws Exception {
		return executeDelete(modelMap, request, resopnse, id, BaseWebController.REQUESTTYPE_AJAX);
	}
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.form.controller.FormController#getExecuteDeleteRet(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected String getExecuteDeleteRet(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse) throws Exception {
		modelMap.put(BaseCodeView.KEY_NAME_CODE, BaseCode.CODE_SUCCESS);
		return "baseCodeView";
	}
	

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.form.controller.FormController#validateInput(com.lmiky.jdp.database.pojo.BasePojo, java.lang.String, org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public List<ValidateError> validateInput(CmsDirectory pojo, String openMode, ModelMap modelMap, HttpServletRequest request) throws Exception {
		List<ValidateError> validateErrors = super.validateInput(pojo, openMode, modelMap, request);
		ValidateUtils.validateRequired(request, "name", "姓名", validateErrors);
		return validateErrors;
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.base.controller.BaseController#setService(com.lmiky.jdp.service.BaseService)
	 */
	@Resource(name="cmsDirectoryService")
	public void setService(BaseService service) {
		super.setService(service);
	}
}