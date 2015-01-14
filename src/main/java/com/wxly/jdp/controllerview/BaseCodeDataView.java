package com.wxly.jdp.controllerview;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**
 * 带数据集的视图
 * @author lmiky
 * @date 2014年11月3日 下午2:26:15
 */
@Component
public class BaseCodeDataView extends BaseCodeView {
	public static final String KEY_NAME_DATA = "data";

	/*
	 * (non-Javadoc)
	 * @see com.lmiky.jdp.controllerview.BaseJsonView#fill(java.util.Map, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void fill(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response, Map<String, Object> resultMap) throws Exception {
		super.fill(map, request, response, resultMap);
		//填充结果码
		Map<String, Object> dataMap = (Map<String, Object>) map.get(KEY_NAME_DATA);
		if (dataMap != null) {
			fillData(map, request, response, resultMap, dataMap);
		}
	}
	
	/**
	 * 填充数据
	 * @author lmiky
	 * @date 2014年11月3日 下午3:12:23
	 * @param map
	 * @param request
	 * @param response
	 * @param resultMap
	 * @param dataMap
	 * @throws Exception
	 */
	protected void fillData(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response, Map<String, Object> resultMap, Map<String, Object> dataMap) throws Exception {
		resultMap.put(KEY_NAME_DATA, dataMap);
	}
}