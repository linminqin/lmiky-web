package com.wxly.jdp.controllerview;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import com.lmiky.jdp.json.util.JsonUtils;
import com.wxly.jdp.web.util.ResponseUtils;

/**
 * 基本信息视图
 * @author lmiky
 * @date 2014-11-3
 */
@Component
public class BaseJsonView extends AbstractView {

	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.servlet.view.AbstractView#renderMergedOutputModel(java.util.Map, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		fill(map, request, response, resultMap);
		String json = JsonUtils.toJson(resultMap);
		ResponseUtils.write(response, json);
	}

	/**
	 * 填充信息
	 * @author lmiky
	 * @date 2014-11-3
	 * @param map
	 * @param request
	 * @param response
	 * @param resultMap
	 * @throws Exception
	 */
	protected void fill(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response, Map<String, Object> resultMap)
			throws Exception {

	}
}
