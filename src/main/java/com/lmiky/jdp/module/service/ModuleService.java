package com.lmiky.jdp.module.service;

import java.util.List;

import com.lmiky.jdp.module.pojo.Function;
import com.lmiky.jdp.service.BaseService;
import com.lmiky.jdp.service.exception.ServiceException;

/**
 * 模块业务
 * @author lmiky
 * @date 2013-12-31
 */
public interface ModuleService extends BaseService {
	
	/**
	 * 根据模块路径获取功能列表
	 * @author lmiky
	 * @date 2013-12-31
	 * @param modulePath
	 * @param moduleType
	 * @return
	 * @throws ServiceException
	 */
	public List<Function> listFunctionByModulePath(String modulePath, String moduleType) throws ServiceException;
	
}
