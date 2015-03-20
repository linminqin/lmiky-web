package com.lmiky.jdp.tree.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lmiky.jdp.database.model.PropertyCompareType;
import com.lmiky.jdp.database.model.PropertyFilter;
import com.lmiky.jdp.database.model.Sort;
import com.lmiky.jdp.database.pojo.BasePojo;
import com.lmiky.jdp.session.model.SessionInfo;
import com.lmiky.jdp.sort.pojo.BaseSortPojo;
import com.lmiky.jdp.tree.pojo.BaseTreePojo;

/**
 * 树
 * @author lmiky
 * @date 2013-1-24
 */
@Controller
@RequestMapping("/tree")
public class TreeController extends BaseTreeController<BaseTreePojo> {
	
	/**
	 * 加载选择树
	 * @author lmiky
	 * @date 2014-1-24
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @param className
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/loadSelectTree.shtml")
	public String loadSelectTree(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse, @RequestParam(value = "className", required = true) String className) throws Exception {
		try {
			// 判断是否有登陆
			SessionInfo sessionInfo = getSessionInfo(modelMap, request);
			// 检查单点登陆
			checkSso(sessionInfo, modelMap, request);
			Class<? extends BaseTreePojo> treeClass = (Class<? extends BaseTreePojo>) Class.forName(className);
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("parent.id", null, PropertyCompareType.NULL, treeClass));
			List<Sort> sorts = new ArrayList<Sort>();
			sorts.add(new Sort(BaseSortPojo.POJO_FIELD_NAME_SORT, Sort.SORT_TYPE_DESC, treeClass));
			sorts.add(new Sort(BasePojo.POJO_FIELD_NAME_ID, Sort.SORT_TYPE_ASC, treeClass));
			modelMap.put("roots", service.list(treeClass, filters, sorts));
			modelMap.put("className", className);
			return "jdp/tree/selectTree";
		} catch (Exception e) {
			return transactException(e, modelMap, request, resopnse);
		}
	}
	
	/**
	 * 加载树子列表
	 * @author lmiky
	 * @date 2014-1-24
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
}