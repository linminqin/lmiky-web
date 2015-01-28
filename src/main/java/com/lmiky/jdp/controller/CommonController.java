package com.lmiky.jdp.controller;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lmiky.jdp.util.StringUtils;
import com.lmiky.jdp.web.model.ContinuationRequest;

/**
 * 公共
 * @author lmiky
 * @date 2014-1-15
 */
@Controller
@RequestMapping("/common")
public class CommonController extends BaseController {

	/**
	 * 回退
	 * @author
	 * @date 2014-1-15
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param url
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/back.shtml")
	public String back(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "url", required = true) String url) throws Exception {
		try {
			String decodeUrl = URLDecoder.decode(url, "UTF-8");
			String uri = request.getContextPath() + StringUtils.getRequestURI(decodeUrl);
			Map parameters = getSessionInfo(modelMap, request).getUrlParamHistory(uri);
			if (parameters == null) {
				parameters = new HashMap();
			}
			//调整地址中的参数
			Map<String, String[]> urlParameters = StringUtils.getUrlParameters(decodeUrl);
			Set parameterNames = urlParameters.keySet();
			Iterator parameterIte = parameterNames.iterator();
			while(parameterIte.hasNext()) {
				String parameterName = (String)parameterIte.next();
				parameters.put(parameterName, urlParameters.get(parameterName));
			}
			ContinuationRequest continuationRequest = new ContinuationRequest(request, parameters);
			RequestDispatcher dispatcher = request.getRequestDispatcher(decodeUrl);
			dispatcher.forward(continuationRequest, response);
			return null;
		} catch(Exception e) {
			return transactException(e, modelMap, request, response);
		}
	}

}