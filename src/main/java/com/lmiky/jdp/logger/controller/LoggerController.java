package com.lmiky.jdp.logger.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lmiky.jdp.database.model.PropertyFilter;
import com.lmiky.jdp.database.model.Sort;
import com.lmiky.jdp.logger.pojo.Logger;
import com.lmiky.jdp.logger.util.LoggerUtils;
import com.lmiky.jdp.util.BundleUtils;
import com.lmiky.jdp.util.DateUtils;
import com.lmiky.jdp.view.controller.ViewController;
import com.lmiky.jdp.web.constants.Constants;
import com.lmiky.jdp.web.page.model.Page;

/**
 * 日志
 * @author lmiky
 * @date 2014-7-16
 */ 
@Controller
 @RequestMapping("/logger")
public class LoggerController extends ViewController<Logger> {
	private String contentFormat;				//内容格式
	private String logdescInclude;				//内容包含符号
	private String logdescIncludeBegin;		//内容包含符号开头
	private String logdescIncludeEnd;		//内容包含符号结尾
	public LoggerController() {
		super();
		contentFormat = BundleUtils.getStringValue(Constants.PROPERTIES_KEY_WEB_FILE, "system.logger.view.content.format");
		logdescInclude = BundleUtils.getStringValue(Constants.PROPERTIES_KEY_WEB_FILE, "system.logger.view.logdesc.include");
		if(StringUtils.isBlank(logdescInclude) || logdescInclude.length() < 2) {
			logdescIncludeBegin = "";
			logdescIncludeEnd = "";
		} else {
			logdescIncludeBegin = logdescInclude.substring(0, 1);
			logdescIncludeEnd = logdescInclude.substring(1);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.base.controller.BaseController#getLoadAuthorityCode(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected String getLoadAuthorityCode(ModelMap modelMap, HttpServletRequest request) {
		return "jdp_logger_load";
	}

	/**
	 * 列表
	 * @author lmiky
	 * @date 2014-7-16
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list.shtml")
	public String list(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse) throws Exception {
		return executeList(modelMap, request, resopnse);
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.web.controller.BaseController#getDefaultSort(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected List<Sort> getDefaultSort(ModelMap modelMap, HttpServletRequest request) {
		List<Sort> sorts = super.getDefaultSort(modelMap, request);
		sorts.add(0, new Sort("logTime", Sort.SORT_TYPE_DESC, Logger.class));
		return sorts;
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.view.controller.ViewController#fillPage(com.lmiky.jdp.web.page.model.Page, org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest, java.util.List, java.util.List, java.util.Map)
	 */
	@Override
	protected Page<Logger> fillPage(Page<Logger> page, ModelMap modelMap, HttpServletRequest request, List<PropertyFilter> propertyFilters,
			List<Sort> sorts, Map<String, Object> otherElements) {
		Page<Logger> retPage = super.fillPage(page, modelMap, request, propertyFilters, sorts, otherElements);
		List<Logger> items = retPage.getItems();
		String contet = "";
		for(Logger logger : items) {
			contet = contentFormat.replaceAll("@userName@", logger.getUserName())	//
					.replaceAll("@logTime@", DateUtils.formatTime(logger.getLogTime()))//
					.replaceAll("@ip@", logger.getIp() == null ? "" : logger.getIp())
					.replaceAll("@opeName@", LoggerUtils.getOpeName(logger.getOpeType()));
			if(!StringUtils.isBlank(logger.getPojoName())) {
				contet = contet.replaceAll("@pojoName@", LoggerUtils.getPojoName(logger.getPojoName()));
			} else {
				contet = contet.replaceAll("@pojoName@", "");
			}
			if(!StringUtils.isBlank(logger.getLogDesc())) {
				contet = contet.replaceAll("@logDesc@", logdescIncludeBegin + logger.getLogDesc() + logdescIncludeEnd);
			} else {
				contet = contet.replaceAll("@logDesc@","");
			}
			logger.setLogDesc(contet);
		}
		return retPage;
	}
	
}
