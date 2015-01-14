package com.wxly.jdp.web.page.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lmiky.jdp.util.PropertiesUtils;
import com.wxly.jdp.constants.Constants;

/**
 * 分页
 * @author lmiky
 * @date 2013-4-15
 */
public class Page<T> implements Serializable {
	private static final long serialVersionUID = -5517269650405864533L;
	//分页动作
	public static final String PAGE_ACTION_PRE = "pre";		//上一页
	public static final String PAGE_ACTION_NEXT = "next";	//下一页
	public static final String PAGE_ACTION_FIRST = "first";	//第一页
	public static final String PAGE_ACTION_LAST = "last";		//最后一页
	public static final String PAGE_ACTION_TURN = "turn";	//跳转
	
	//分页滚动数
	public static final int SLIDER_BEGIN = 2;		//
	public static final int SLIDER_CENTER = 3;		//
	public static final int SLIDER_END = 2;			//
	
	private int currentPage;		//当前页码
	private int pageSize;			//页面显示记录数
	private int itemCount;		//总记录数
	private List<T> items;		//当前显示的记录
	private String action;			//分页动作
	
	public Page() {
		setPageSize(PropertiesUtils.getIntContextValue(Constants.CONTEXT_KEY_PAGE_PAGESIZE));
		setCurrentPage(1);
		setAction(PAGE_ACTION_TURN);
	}
	
	/**
	 * 页面操作
	 * @author lmiky
	 * @date 2013-4-15
	 */
	public void pageAction() {
		if(!StringUtils.isBlank(action)) {
			if(PAGE_ACTION_PRE.equals(action)) {
				setCurrentPage(getCurrentPage() - 1); 
			} else if(PAGE_ACTION_NEXT.equals(action)) {
				setCurrentPage(getCurrentPage() + 1); 
			} else if(PAGE_ACTION_FIRST.equals(action)) {
				setCurrentPage(1); 
			} else if(PAGE_ACTION_LAST.equals(action)) {
				setCurrentPage(getPageCount()); 
			} else if(PAGE_ACTION_TURN.equals(action)) {
			}
		}
	}
	
	/**
	 * 重新设置当前页
	 * @author lmiky
	 * @date 2013-4-17
	 */
	public void resetCurrentPage() {
		if(currentPage <= 0) {
			currentPage = 1;
		} else {
			int pageCount = getPageCount();
			 if(currentPage > pageCount) {
				 currentPage = pageCount;
			 }
		}
	}
	
	/**
	 * 获取记录开始行数
	 * @author lmiky
	 * @date 2013-4-16
	 * @return
	 */
	public int getItemOffset() {
		int offset = (getCurrentPage() - 1) * getPageSize();
		return offset < 0 ? 0 : offset;
	}
	
	/**
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}
	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(int currentPage) {
		if(currentPage < 1) {
			this.currentPage = 1;
		} else if(currentPage > getPageCount()) {
			this.currentPage = currentPage;
		}
	}
	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		if(pageSize < 0) {
			this.pageSize = 0;
		} else {
			this.pageSize = pageSize;
		}
	}
	/**
	 * 获取总页数
	 * @author lmiky
	 * @date 2013-4-15
	 * @return
	 */
	public int getPageCount() {
		int pageCount = (getPageSize() == 0) ? 0 : ((int) Math.ceil((double) getItemCount() / (double) getPageSize()));
		if(pageCount < 0) {
			pageCount = 0;
		}
		return pageCount;
	}
	
	/**
	 * @return the itemCount
	 */
	public int getItemCount() {
		return itemCount;
	}
	/**
	 * @param itemCount the itemCount to set
	 */
	public void setItemCount(int itemCount) {
		if(itemCount < 0) {
			this.itemCount = 0;
		} else {
			this.itemCount = itemCount;
		}
	}
	/**
	 * @return the items
	 */
	public List<T> getItems() {
		if(items == null) {
			items = new ArrayList<T>();
		}
		return items;
	}
	/**
	 * @param items the items to set
	 */
	public void setItems(List<T> items) {
		this.items = items;
	}
	/**
	 * @return the action
	 */
	public String getAction() {
		if(action == null) {
			action = "";
		}
		return action;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}
}
