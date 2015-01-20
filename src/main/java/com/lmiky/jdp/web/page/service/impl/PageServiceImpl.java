package com.lmiky.jdp.web.page.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	private BaseDAO baseDAO;
	
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
		page.setItemCount(baseDAO.count(pojoClass, filters));
		page.resetCurrentPage();
		page.setItems(getDAO().list(pojoClass, filters, sorts, page.getItemOffset(), page.getPageSize()));
		return page;
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
	@Autowired
	public void setDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
}
