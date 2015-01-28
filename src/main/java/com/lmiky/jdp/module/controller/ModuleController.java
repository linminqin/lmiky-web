package com.lmiky.jdp.module.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lmiky.jdp.controller.BaseController;
import com.lmiky.jdp.database.model.PropertyCompareType;
import com.lmiky.jdp.database.model.PropertyFilter;
import com.lmiky.jdp.database.model.Sort;
import com.lmiky.jdp.module.pojo.Function;
import com.lmiky.jdp.module.pojo.Module;
import com.lmiky.jdp.module.pojo.ModuleGroup;
import com.lmiky.jdp.session.model.SessionInfo;

/**
 * 模块
 * @author lmiky
 * @date 2013-5-19
 */
@Controller
@RequestMapping("/module")
public class ModuleController extends BaseController {
	//树列表前缀
	public static final String TREE_LIST_ID_PREFIX_SYSTEM = "s_";
	public static final String TREE_LIST_ID_PREFIX_GROUP = "g_";
	public static final String TREE_LIST_ID_PREFIX_MODULE = "m_";
	public static final String TREE_LIST_ID_PREFIX_FUNCTION = "f_";
	
	/**
	 * 获取树列表
	 * @author lmiky
	 * @date 2013-5-19
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/treeList.shtml")
	public String treeList(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse) throws Exception {
		try {
			//判断是否有登陆
			SessionInfo sessionInfo = getSessionInfo(modelMap, request);
			//检查单点登陆
			checkSso(sessionInfo, modelMap, request);
			//检查权限
			//获取类别
			String moduleType = request.getParameter("moduleType");
			String moduleId = request.getParameter("id");
			if(Module.MODULE_TYPE_GROUP.equals(moduleType)) {
				List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
				filters.add(new PropertyFilter("id", Long.parseLong(moduleId.substring(TREE_LIST_ID_PREFIX_GROUP.length())), PropertyCompareType.EQ, ModuleGroup.class));
				List<Module> modules = service.list(Module.class, filters, null);
				modelMap.put("pojos", modules);
				return "moduleListJsonView";
			} else if(Module.MODULE_TYPE_MODULE.equals(moduleType)) {
				Long mId = Long.parseLong(moduleId.substring(TREE_LIST_ID_PREFIX_MODULE.length()));
				List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
				filters.add(new PropertyFilter("id", mId, PropertyCompareType.EQ, Module.class));
				List<Sort> sorts = new ArrayList<Sort>();
				sorts.add(new Sort("sort", Sort.SORT_TYPE_ASC, Function.class));
				List<Function> functions = service.list(Function.class, filters, sorts);
				modelMap.put("pojos", functions);
				return "functionListJsonView";
			}
		} catch(Exception e) {
			//TODO 如果是登陆或者权限异常,用脚本让页面跳转到登陆页面	
			logException(e, modelMap, request, resopnse);
		}
		return null;
	}
}
