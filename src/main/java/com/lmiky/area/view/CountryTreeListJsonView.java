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
import com.lmiky.area.pojo.Country;
import com.lmiky.jdp.json.util.JsonUtils;

/**
 * @author lmiky
 * @date 2013-10-22
 */
@Component(value="countryTreeListJsonView")
public class CountryTreeListJsonView extends AbstractView {

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.view.AbstractView#renderMergedOutputModel(java.util.Map, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Country> pojos = (List<Country>)model.get("pojos");
		
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		Map<String, Object> dataMap = null;
		for(Country pojo : pojos) {
			dataMap = new HashMap<String, Object>();
			dataMap.put("id", AreaController.AREA_TYPE_COUNTRY + pojo.getId());
			dataMap.put("realId", pojo.getId());
			dataMap.put("name", pojo.getName());
			dataMap.put("phoneticAlphabet", pojo.getPhoneticAlphabet());
			dataMap.put("areaType", AreaController.AREA_TYPE_COUNTRY);
			dataMap.put("isParent", pojo.getProvinces().isEmpty() ? false : true);
			dataList.add(dataMap);
		}
		String json = JsonUtils.toJson(dataList);
		response.getWriter().write(json);
	}

}
