package com.lmiky.jdp.web.page.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.lmiky.jdp.database.pojo.BasePojo;
import com.lmiky.jdp.web.constants.Constants;
import com.lmiky.jdp.web.page.model.Page;

/**
 * 分页工具
 * @author lmiky
 * @date 2013-4-17
 */
public class PageUtils {

	/**
	 * 从页面请求中生成分页对象
	 * @author lmiky
	 * @date 2013-4-17
	 * @param request
	 * @return
	 */
	public static <T extends BasePojo> Page<T> generateFromHttpRequest(HttpServletRequest request) {
		Page<T> page = new Page<T>();
		setPropertiesFromHttpRequest(page, request);
		return page;
	}

	/**
	 * 从页面请求中设置分页对象
	 * @author lmiky
	 * @date 2013-4-17
	 * @param page
	 * @param request
	 */
	public static <T extends BasePojo> void setPropertiesFromHttpRequest(Page<T> page, HttpServletRequest request) {
		if(request == null) {
			return;
		}
		String currentPageParam = request.getParameter(Constants.HTTP_PARAM_PAGE_CURRENTPAGE);
		if(!StringUtils.isBlank(currentPageParam)) {
			try {
				page.setCurrentPage(Integer.parseInt(currentPageParam.trim()));
			} catch(NumberFormatException e) {	//处理非数据
				page.setCurrentPage(1);
			}
		}
		page.setAction(request.getParameter(Constants.HTTP_PARAM_PAGE_ACTION));
	}

}
