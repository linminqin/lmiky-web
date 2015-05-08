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
	
	/**
	 * 获取记录数
	 * @author lmiky
	 * @date 2015年5月8日 上午9:50:36
	 * @param pojoClass
	 * @param page
	 * @param filters
	 * @return
	 */
	public <T extends BasePojo> int getItemCount(Class<T> pojoClass, Page<T> page, List<PropertyFilter> filters);
	
	/**
	 * 获取记录列表
	 * @author lmiky
	 * @date 2015年5月8日 上午9:47:21
	 * @param pojoClass
	 * @param page
	 * @param filters
	 * @param sorts
	 * @return
	 */
	public <T extends BasePojo> List<T> listItems(Class<T> pojoClass, Page<T> page, List<PropertyFilter> filters, List<Sort>sorts);
}
