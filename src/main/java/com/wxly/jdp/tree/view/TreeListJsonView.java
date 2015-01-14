package com.wxly.jdp.tree.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import com.lmiky.jdp.json.util.JsonUtils;
import com.lmiky.jdp.tree.pojo.BaseTreePojo;

/**
 * 树列表
 * @author lmiky
 * @date 2014-1-4
 */ 
@Component(value="treeListJsonView")
public class TreeListJsonView extends AbstractView {

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.view.AbstractView#renderMergedOutputModel(java.util.Map, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		fillDateList(model, request, dataList);
		String json = JsonUtils.toJson(dataList);
		response.getWriter().write(json);
	}
	
	/**
	 * 填充数据
	 * @author lmiky
	 * @date 2014-1-4
	 * @param model
	 * @param request
	 * @param dataList
	 */
	@SuppressWarnings("unchecked")
	protected void fillDateList(Map<String, Object> model, HttpServletRequest request, List<Map<String, Object>> dataList) {
		List<BaseTreePojo> treeNodes = (List<BaseTreePojo>)model.get("nodes");
		Map<String, Object> dataMap = null;
		for(BaseTreePojo treeNode : treeNodes) {
			dataMap = new HashMap<String, Object>();
			dataMap.put("id", treeNode.getId());
			dataMap.put("name", treeNode.getName());
			dataMap.put("isParent", treeNode.getLeaf() > 0);
			fillOtherDate(model, request, dataMap, treeNode);
			dataList.add(dataMap);
		}
	}
	
	/**
	 * 填充其他数据
	 * @author lmiky
	 * @date 2014-1-4
	 * @param model
	 * @param request
	 * @param dataMap
	 * @param treeNode
	 */
	protected void fillOtherDate(Map<String, Object> model, HttpServletRequest request, Map<String, Object> dataMap, BaseTreePojo treeNode) {
	}

}
