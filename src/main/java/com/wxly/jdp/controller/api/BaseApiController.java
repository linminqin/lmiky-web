package com.wxly.jdp.controller.api;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

import com.lmiky.jdp.exception.BaseCodeException;
import com.lmiky.jdp.service.BaseService;
import com.wxly.jdp.controllerview.BaseCode;
import com.wxly.jdp.controllerview.BaseCodeView;
import com.wxly.jdp.logger.util.LoggerUtils;

/**
 * API控制器
 * @author lmiky
 * @date 2014年11月3日 下午2:20:50
 */
public abstract class BaseApiController {
	protected BaseService baseService;
	
	/**
	 * 异常处理
	 * @author lmiky
	 * @date 2014年11月3日 下午2:35:11
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param e
	 * @throws Exception
	 */
	public void transactException(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
		if(e instanceof BaseCodeException && ((BaseCodeException) e).getCode() != null) {	//自带结果码
			int code = ((BaseCodeException) e).getCode().intValue();
			LoggerUtils.debug(String.format("客户端请求操作失败，失败结果吗：%d", code));
			modelMap.put(BaseCodeView.KEY_NAME_CODE, code);
		} else {
			LoggerUtils.logException(e);
			modelMap.put(BaseCodeView.KEY_NAME_CODE, BaseCode.CODE_ERROR);
		}
	}
	
	/**
	 * @return the baseService
	 */
	public BaseService getBaseService() {
		return baseService;
	}

	/**
	 * @param baseService the baseService to set
	 */
	@Resource(name="baseService")
	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}
}
