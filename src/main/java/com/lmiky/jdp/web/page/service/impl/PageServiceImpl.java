package com.lmiky.jdp.web.page.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmiky.jdp.database.dao.BaseDAO;
import com.lmiky.jdp.database.model.PropertyFilter;
import com.lmiky.jdp.database.model.Sort;
import com.lmiky.jdp.database.pojo.BasePojo;
import com.lmiky.jdp.web.page.model.Page;
import com.lmiky.jdp.web.page.service.PageService;

/**
 * 分页
 * @author lmiky
 * @date 2013-4-15
 */
@Service("pageService")
public class PageServiceImpl implements PageService {
	protected BaseDAO baseDAO;
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.web.page.service.PageService#fillPage(java.lang.Class)
	 */
	@Transactional(readOnly=true)
	public <T extends BasePojo> Page<T> fillPage(Class<T> pojoClass) {
		return fillPage(pojoClass, new Page<T>());
	}
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.web.page.service.PageService#fillPage(java.lang.Class, com.lmiky.jdp.web.page.model.Page)
	 */
	@Transactional(readOnly=true)
	public <T extends BasePojo> Page<T> fillPage(Class<T> pojoClass, Page<T> page) {
		return fillPage(pojoClass, page, null, null);
	}
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.web.page.service.PageService#fillPage(java.lang.Class, com.lmiky.jdp.web.page.model.Page, java.util.List, java.util.List)
	 */
	@Transactional(readOnly=true)
	public <T extends BasePojo> Page<T> fillPage(Class<T> pojoClass, Page<T> page, List<PropertyFilter> filters, List<Sort>sorts) {
		int itemCount = getItemCount(pojoClass, page, filters);
		page.setItemCount(itemCount);
		page.resetCurrentPage();
		if(itemCount > 0) {	//如果没记录，则减少操作
			page.setItems(listItems(pojoClass, page, filters, sorts));
		} else {
			page.setItems(new ArrayList<T>());
		}
		return page;
	}
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.web.page.service.PageService#getItemCount(java.lang.Class, com.lmiky.jdp.web.page.model.Page, java.util.List)
	 */
	@Transactional(readOnly=true)
	public <T extends BasePojo> int getItemCount(Class<T> pojoClass, Page<T> page, List<PropertyFilter> filters) { 
		return baseDAO.count(pojoClass, filters);
	}
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.web.page.service.PageService#listItems(java.lang.Class, com.lmiky.jdp.web.page.model.Page, java.util.List, java.util.List)
	 */
	@Transactional(readOnly=true)
	public <T extends BasePojo> List<T> listItems(Class<T> pojoClass, Page<T> page, List<PropertyFilter> filters, List<Sort>sorts) { 
		return getDAO().list(pojoClass, filters, sorts, page.getItemOffset(), page.getPageSize());
	}
	
	/**
	 * 获取DAO对象
	 * @author lmiky
	 * @date 2013-4-16
	 * @return
	 */
	public BaseDAO getDAO() {
		return baseDAO;
	}

	/**
	 * 设置DAO对象
	 * @author lmiky
	 * @date 2013-4-16
	 * @param baseDAO
	 */
	@Resource(name="baseDAO")
	public void setDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
}
