package com.lmiky.area.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import com.lmiky.area.controller.AreaController;
import com.lmiky.area.pojo.Province;
import com.lmiky.jdp.json.util.JsonUtils;

/**
 * @author lmiky
 * @date 2013-10-22
 */
@Component(value="provinceTreeListJsonView")
public class ProvinceTreeListJsonView extends AbstractView {

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.view.AbstractView#renderMergedOutputModel(java.util.Map, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Province> pojos = (List<Province>)model.get("pojos");
		
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		Map<String, Object> dataMap = null;
		for(Province pojo : pojos) {
			dataMap = new HashMap<String, Object>();
			dataMap.put("id", AreaController.AREA_TYPE_PROVINCE + pojo.getId());
			dataMap.put("realId", pojo.getId());
			dataMap.put("name", pojo.getName());
			dataMap.put("phoneticAlphabet", pojo.getPhoneticAlphabet());
			dataMap.put("areaType", AreaController.AREA_TYPE_PROVINCE);
			dataMap.put("isParent", pojo.getCities().isEmpty() ? false : true);
			dataList.add(dataMap);
		}
		String json = JsonUtils.toJson(dataList);
		response.getWriter().write(json);
	}

}
