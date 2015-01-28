package com.lmiky.jdp.module.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import com.lmiky.jdp.json.util.JsonUtils;
import com.lmiky.jdp.module.controller.ModuleController;
import com.lmiky.jdp.module.pojo.Module;

/**
 * 模块列表
 * @author lmiky
 * @date 2013-5-19
 */
@Component(value="moduleListJsonView")
public class ModuleListJsonView extends AbstractView {

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.view.AbstractView#renderMergedOutputModel(java.util.Map, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Module> modules = (List<Module>)model.get("pojos");
		
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		Map<String, Object> dataMap = null;
		for(Module module : modules) {
			dataMap = new HashMap<String, Object>();
			dataMap.put("id", ModuleController.TREE_LIST_ID_PREFIX_MODULE + module.getId());
			dataMap.put("name", module.getName());
			dataMap.put("moduleType", Module.MODULE_TYPE_MODULE);
			dataMap.put("modulePath", module.getPath());
			dataMap.put("isParent", true);
			dataList.add(dataMap);
		}
		String json = JsonUtils.toJson(dataList);
		response.getWriter().write(json);
	}

}
