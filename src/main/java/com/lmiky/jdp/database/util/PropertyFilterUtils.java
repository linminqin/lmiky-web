package com.lmiky.jdp.database.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;

import com.lmiky.jdp.database.model.PropertyCompareType;
import com.lmiky.jdp.database.model.PropertyFilter;
import com.lmiky.jdp.database.pojo.BasePojo;
import com.lmiky.jdp.logger.util.LoggerUtils;
import com.lmiky.jdp.util.PropertyUtils;
import com.lmiky.jdp.web.constants.Constants;

/**
 * 过滤属性工具类
 * @author lmiky
 * @date 2013-4-17
 */
public class PropertyFilterUtils {

	/**
	 * 从http请求中生成属性过滤条件
	 * @author lmiky
	 * @date 2013-4-17
	 * @param request
	 * @param pojoClass
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T extends BasePojo> List<PropertyFilter> generateFromHttpRequest(HttpServletRequest request, Class<T> pojoClass) {
		List<PropertyFilter> filterList = new ArrayList<PropertyFilter>();
		Map<String, Object[]> collectionCache = new HashMap<String, Object[]>();
		Map<String, Map<String, Class<?>>> noCollectionCache = new HashMap<String, Map<String, Class<?>>>();
		if (request != null && pojoClass != null) {
			Map<String, Class<?>> pojoDescriptors = PropertyUtils.getPropertiesClassType(pojoClass);
			// 获取所有参数名
			Enumeration parameterNames = request.getParameterNames();
			while (parameterNames.hasMoreElements()) {
				try {
					String parameterName = (String) parameterNames.nextElement();
					// 格式为“指定开头格式_参数名_比较方式”
					if (parameterName.startsWith(Constants.HTTP_PARAM_PROPERTYFILTER_NAME_PREFIX)) {
						String[] parameterKeys = parameterName.split("_");
						String propertyValue = request.getParameter(parameterName);
						if (parameterKeys.length == 3 && !StringUtils.isBlank(propertyValue)) {
							String propertyName = parameterKeys[1];
							PropertyCompareType compareType = Enum.valueOf(PropertyCompareType.class, parameterKeys[2]);
							//是否别的对象的属性
							if(propertyName.indexOf(".") == -1) {
								if (pojoDescriptors.get(propertyName) == null) {
									continue;
								}
								filterList.add(new PropertyFilter(propertyName, ConvertUtils.convert(propertyValue, pojoDescriptors.get(propertyName)),
										compareType, pojoClass));
							} else {
								String entiryField = propertyName.substring(0, propertyName.indexOf("."));	//实体字段
								String propertyField = propertyName.substring(propertyName.indexOf(".") + 1);	//属性字段
								Class<?> fieldClass = pojoDescriptors.get(entiryField);
								if (fieldClass == null) {
									continue;
								}
								//如果是一对多/多对多
								if(Collection.class.isAssignableFrom(fieldClass)) {
									Class<?> entityClass = null;
									Map<String, Class<?>> entiryDescriptors = null;
									Object[] cacheObjs = collectionCache.get(entiryField);
									if(cacheObjs == null) {
										//获取字段
										Field field = pojoClass.getDeclaredField(entiryField);
										if (field == null) {
											continue;
										}
										//获取集合的泛型类型
										entityClass = (Class<?>) ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
										entiryDescriptors = PropertyUtils.getPropertiesClassType(entityClass);
										
										//放入缓存
										cacheObjs = new Object[2];
										cacheObjs[0] = entityClass;
										cacheObjs[1] = entiryDescriptors;
										collectionCache.put(entiryField, cacheObjs);
									} else {
										entityClass = (Class<?>)cacheObjs[0];
										entiryDescriptors = (Map<String, Class<?>>)cacheObjs[1];
									}
									if (entiryDescriptors.get(propertyField) == null) {
										continue;
									}
									filterList.add(new PropertyFilter(propertyField, ConvertUtils.convert(propertyValue, entiryDescriptors.get(propertyField)),
											compareType, true, entityClass));
								} else {	//一对一/多对一
									//读取缓存
									Map<String, Class<?>> entiryDescriptors = noCollectionCache.get(entiryField);
									if(entiryDescriptors == null) {
										entiryDescriptors = PropertyUtils.getPropertiesClassType(fieldClass);
										noCollectionCache.put(entiryField, entiryDescriptors);
									}
									if (entiryDescriptors.get(propertyField) == null) {
										continue;
									}
									String propertyFieldName = propertyField;
									Class<?> propertyFieldClass = fieldClass;
									if(fieldClass.equals(pojoClass) || fieldClass.isAssignableFrom(pojoClass)) {	//自身的引用
										propertyFieldName = propertyName;	
										propertyFieldClass = pojoClass;
									}
									filterList.add(new PropertyFilter(propertyFieldName, ConvertUtils.convert(propertyValue, entiryDescriptors.get(propertyField)),
											compareType, propertyFieldClass));
								}
							}
						}
					}
				} catch (Exception e) {
					LoggerUtils.logException(e);
					return null;
				}
			}
		}
		return filterList;
	}
}
