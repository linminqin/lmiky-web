package com.lmiky.jdp.web.page.service;

import java.util.List;

import com.lmiky.jdp.database.model.PropertyFilter;
import com.lmiky.jdp.database.model.Sort;
import com.lmiky.jdp.database.pojo.BasePojo;
import com.lmiky.jdp.web.page.model.Page;

/**
 * 分页
 * @author lmiky
 * @date 2013-4-15
 */
public interface PageService {
	
	/**
	 * 填充视图
	 * @author lmiky
	 * @date 2013-4-16
	 * @param pojoClass
	 */
	public <T extends BasePojo> Page<T> fillPage(Class<T> pojoClass);
	
	/**
	 * 方法说明
	 * @author lmiky
	 * @date 2013-4-16
	 * @param pojoClass
	 * @param page
	 */
	public <T extends BasePojo> Page<T> fillPage(Class<T> pojoClass, Page<T> page);
	
	/**
	 * 填充视图
	 * @author lmiky
	 * @date 2013-4-16
	 * @param pojoClass
	 * @param page
	 * @param filters	过滤条件
	 * @param sorts	排序
	 */
	public <T extends BasePojo> Page<T> fillPage(Class<T> pojoClass, Page<T> page, List<PropertyFilter> filters, List<Sort>sorts);
}
