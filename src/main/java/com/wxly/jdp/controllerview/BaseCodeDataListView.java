package com.wxly.jdp.controllerview;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**
 * 列表视图
 * @author lmiky
 * @date 2014年11月3日 下午8:05:16
 */
@Component
public class BaseCodeDataListView extends BaseCodeDataView {

	/*
	 * (non-Javadoc)
	 * @see com.wxly.jdp.controllerview.BaseCodeDataView#fillData(java.util.Map, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.util.Map, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void fillData(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response, Map<String, Object> resultMap, Map<String, Object> dataMap) throws Exception {
		Set<String> keys = dataMap.keySet();
		if (!keys.isEmpty()) {
			for (Iterator<String> keyIte = keys.iterator(); keyIte.hasNext();) {
				String listKey = keyIte.next(); // 唯一的一个
				Object data = dataMap.get(listKey);
				if (!List.class.isInstance(data)) { // 列表
					continue;
				} else {
					List<Object> dataList = (List<Object>) data;
					List<Object> datas = new ArrayList<Object>();
					if (dataList != null) {
						for (Object obj : dataList) {
							datas.add(buildDataModel(dataMap, request, response, obj));
						}
					}
					dataMap.put(listKey, datas);
				}
			}
			resultMap.put(KEY_NAME_DATA, dataMap);
		}
	}

	/**
	 * 构建数据实体类
	 * @author lmiky
	 * @date 2014年11月3日 下午8:03:23
	 * @param map
	 * @param request
	 * @param response
	 * @param listModel
	 * @return
	 * @throws Exception
	 */
	public Object buildDataModel(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response, Object listModel) throws Exception {
		return listModel;
	}
}