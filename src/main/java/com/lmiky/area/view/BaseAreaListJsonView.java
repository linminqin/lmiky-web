package com.lmiky.area.view;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.lmiky.area.pojo.BaseAreaPojo;
import com.lmiky.jdp.controller.view.BaseCodeDataListView;

/**
 * 地区列表
 * @author lmiky
 * @date 2015年5月26日 下午1:54:08
 */
@Component
public class BaseAreaListJsonView extends BaseCodeDataListView {

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.controller.view.BaseCodeDataListView#buildDataModel(java.util.Map, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public Object buildDataModel(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response, Object listModel) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		BaseAreaPojo areaPojo = (BaseAreaPojo)listModel;
		model.put(BaseAreaPojo.POJO_FIELD_NAME_CODE, areaPojo.getCode());
		model.put(BaseAreaPojo.POJO_FIELD_NAME_NAME, areaPojo.getName());
		model.put(BaseAreaPojo.POJO_FIELD_NAME_ID, areaPojo.getId());
		return model;
	}

	
	
}
