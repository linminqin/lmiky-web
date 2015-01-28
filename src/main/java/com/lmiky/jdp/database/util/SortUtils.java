package com.lmiky.jdp.database.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.lmiky.jdp.database.model.Sort;
import com.lmiky.jdp.database.pojo.BasePojo;
import com.lmiky.jdp.web.constants.Constants;

/**
 * 排序工具类
 * @author lmiky
 * @date 2013-4-17
 */
public class SortUtils {

	/**
	 * 从http请求中生成属性过滤条件
	 * @author lmiky
	 * @date 2013-4-17
	 * @param request
	 * @param pojoClass
	 * @return
	 */
	public static <T extends BasePojo> List<Sort> generateFromHttpRequest(HttpServletRequest request, Class<T> pojoClass) {
		List<Sort> sortList = new ArrayList<Sort>();
		if(request != null && pojoClass != null) {
			String[] propertiesName = request.getParameterValues(Constants.HTTP_PARAM_SORT_ORDERBY_NAME);
			if(propertiesName != null && propertiesName.length > 0) {
				for(int i=0; i<propertiesName.length; i++) {
					String value = request.getParameter(Constants.HTTP_PARAM_SORT_TYPE_NAME_PREFIX + propertiesName[i]);
					if(!StringUtils.isBlank(value)) {
						sortList.add(new Sort(propertiesName[i], value, pojoClass));
					}
				}
			}
		}
		return sortList;
	}
}
