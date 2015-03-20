package com.lmiky.jdp.tree.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;

import com.lmiky.jdp.database.model.PropertyCompareType;
import com.lmiky.jdp.database.model.PropertyFilter;
import com.lmiky.jdp.database.model.Sort;
import com.lmiky.jdp.database.pojo.BasePojo;
import com.lmiky.jdp.form.controller.FormController;
import com.lmiky.jdp.form.model.ValidateError;
import com.lmiky.jdp.form.util.ValidateUtils;
import com.lmiky.jdp.session.model.SessionInfo;
import com.lmiky.jdp.sort.pojo.BaseSortPojo;
import com.lmiky.jdp.tree.pojo.BaseTreePojo;
import com.lmiky.jdp.web.constants.Constants;

/**
 * 树
 * @author lmiky
 * @date 2013-4-15
 */
public abstract class BaseTreeController<T extends BaseTreePojo> extends FormController<T> {
	public static final int ROOT_NODE_ID = 0;
	
	/**
	 * 加载树
	 * @author lmiky
	 * @date 2014-1-3
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @return
	 * @throws Exception
	 */
	public String executeLoadTree(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse) throws Exception {
		try {
			// 判断是否有登陆
			SessionInfo sessionInfo = getSessionInfo(modelMap, request);
			// 检查单点登陆
			checkSso(sessionInfo, modelMap, request);
			//检查权限
			checkAuthority(modelMap, request, sessionInfo, getLoadAuthorityCode(modelMap, request));
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("parent.id", null, PropertyCompareType.NULL, pojoClass));
			List<Sort> sorts = new ArrayList<Sort>();
			sorts.add(new Sort(BaseSortPojo.POJO_FIELD_NAME_SORT, Sort.SORT_TYPE_DESC, pojoClass));
			sorts.add(new Sort(BasePojo.POJO_FIELD_NAME_ID, Sort.SORT_TYPE_ASC, pojoClass));
			List<?> roots = service.list(pojoClass, filters, sorts);
			modelMap.put("roots", roots);
			appendListAttribute(modelMap, request, resopnse);
			String modulePath = getModulePath(modelMap, request);
			modelMap.put(Constants.HTTP_PARAM_MODULE_PATH, modulePath);
			return getExecuteTreeListRet(modelMap, request, modulePath);
		} catch (Exception e) {
			return transactException(e, modelMap, request, resopnse);
		}
	}
	
	/**
	 * 加载树列表返回结果
	 * @author lmiky
	 * @date 2014-1-24
	 * @param modelMap
	 * @param request
	 * @param modulePath
	 * @return
	 */
	public String getExecuteTreeListRet(ModelMap modelMap, HttpServletRequest request, String modulePath) {
		return getExecuteListRet(modelMap, request, modulePath);
	}
	
	/**
	 * 执行树列表加载
	 * @author lmiky
	 * @date 2014-1-4
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public String executeTreeList(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse, Long parentId) throws Exception {
		try {
			// 判断是否有登陆
			SessionInfo sessionInfo = getSessionInfo(modelMap, request);
			// 检查单点登陆
			checkSso(sessionInfo, modelMap, request);
			//检查权限
			checkAuthority(modelMap, request, sessionInfo, getLoadAuthorityCode(modelMap, request));
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			List<Sort> sorts = new ArrayList<Sort>();
			sorts.add(new Sort(BaseSortPojo.POJO_FIELD_NAME_SORT, Sort.SORT_TYPE_DESC, pojoClass));
			sorts.add(new Sort(BasePojo.POJO_FIELD_NAME_ID, Sort.SORT_TYPE_ASC, pojoClass));
			if(parentId == null) {
				filters.add(new PropertyFilter("parent.id", null, PropertyCompareType.NULL, pojoClass));
				modelMap.put("nodes", service.list(pojoClass, filters, sorts));
			} else {
				filters.add(new PropertyFilter("parent.id", parentId, PropertyCompareType.EQ, pojoClass));
				modelMap.put("nodes", service.list(pojoClass, filters, sorts));
			}
			return "treeListJsonView";
		} catch (Exception e) {
			logException(e, modelMap, request, resopnse);
		}
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lmiky.jdp.form.controller.FormController#validateInput(com.lmiky.jdp.database.pojo.BasePojo, java.lang.String, org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	public List<ValidateError> validateInput(BaseTreePojo pojo, String openMode, ModelMap modelMap, HttpServletRequest request) throws Exception {
		List<ValidateError> validateErrors = new ArrayList<ValidateError>();
		ValidateUtils.validateRequired(request, "name", "名称", validateErrors);
		return validateErrors;
	}
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.form.controller.FormController#appendLoadAttribute(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.String, com.lmiky.jdp.database.pojo.BasePojo)
	 */
	@Override
	protected void appendLoadAttribute(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse, String openMode, T pojo)
			throws Exception {
		super.appendLoadAttribute(modelMap, request, resopnse, openMode, pojo);
		String parentId = request.getParameter("parentId");
		if(!StringUtils.isBlank(parentId)) {
			modelMap.put("parentId", parentId);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.lmiky.jdp.form.controller.FormController#setPojoProperties(com.lmiky.jdp.database.pojo.BasePojo, org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void setPojoProperties(T pojo, ModelMap modelMap, HttpServletRequest request) throws Exception {
		super.setPojoProperties(pojo, modelMap, request);
		String parentIdStr = request.getParameter("parentId");
		//非顶层节点
		if(!StringUtils.isBlank(parentIdStr)) {
			Long parentId = Long.parseLong(parentIdStr);
			T parent = service.find(pojoClass, parentId);
			pojo.setParent(parent);
		}
	}
}