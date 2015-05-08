package com.lmiky.jdp.database.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.lmiky.jdp.database.model.Sort;
import com.lmiky.jdp.database.pojo.BasePojo;
import com.lmiky.jdp.util.PropertyUtils;
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
			String[] propertiesNames = request.getParameterValues(Constants.HTTP_PARAM_SORT_ORDERBY_NAME);
			if(propertiesNames != null && propertiesNames.length > 0) {
				
				Map<String, Class<?>> pojoDescriptors = PropertyUtils.getPropertiesClassType(pojoClass);	//实体类属性
				Map<String, Map<String, Class<?>>> fieldCache = new HashMap<String, Map<String, Class<?>>>();	//放置类型为非基本类的属性缓存
				
				for(int i=0; i<propertiesNames.length; i++) {
					String propertyName = propertiesNames[i];
					String value = request.getParameter(Constants.HTTP_PARAM_SORT_TYPE_NAME_PREFIX + propertiesNames[i]);
					if(!StringUtils.isBlank(value)) {
						//是否别的对象的属性
						if(propertyName.indexOf(".") == -1) {
							if (pojoDescriptors.get(propertyName) == null) {	//没有该属性
								continue;
							}
							sortList.add(new Sort(propertyName, value, pojoClass));
						} else {
							String entiryField = propertyName.substring(0, propertyName.indexOf("."));	//实体字段
							String propertyField = propertyName.substring(propertyName.indexOf(".") + 1);	//属性字段
							Class<?> fieldClass = pojoDescriptors.get(entiryField);
							if (fieldClass == null) {
								continue;
							}
							//排序字段都为：一对一/多对一
							//读取缓存
							Map<String, Class<?>> entiryDescriptors = fieldCache.get(entiryField);
							if(entiryDescriptors == null) {
								entiryDescriptors = PropertyUtils.getPropertiesClassType(fieldClass);
								fieldCache.put(entiryField, entiryDescriptors);
							}
							//属性实体类下没有该属性
							if (entiryDescriptors.get(propertyField) == null) {
								continue;
							}
							String propertyFieldName = propertyField;
							Class<?> propertyFieldClass = fieldClass;
							if(fieldClass.equals(pojoClass) || fieldClass.isAssignableFrom(pojoClass)) {	//自身的引用
								propertyFieldName = propertyName;	
								propertyFieldClass = pojoClass;
							}
							sortList.add(new Sort(propertyFieldName, value, propertyFieldClass));
						}
					}
				}
			}
		}
		return sortList;
	}
}
