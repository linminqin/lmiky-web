package com.lmiky.jdp.module.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmiky.jdp.database.model.PropertyCompareType;
import com.lmiky.jdp.database.model.PropertyFilter;
import com.lmiky.jdp.module.pojo.Function;
import com.lmiky.jdp.module.pojo.Module;
import com.lmiky.jdp.module.pojo.ModuleGroup;
import com.lmiky.jdp.module.service.ModuleService;
import com.lmiky.jdp.service.exception.ServiceException;
import com.lmiky.jdp.service.impl.BaseServiceImpl;

/**
 * @author lmiky
 * @date 2013-12-31
 */
@Service("moduleService")
public class ModuleServiceImpl extends BaseServiceImpl implements ModuleService {

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.module.service.ModuleService#listFunctionByModulePath(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Function> listFunctionByModulePath(String modulePath, String moduleType) throws ServiceException {
		List<Function> functions = new ArrayList<Function>();
		if(Module.MODULE_TYPE_SYSTEM.equals(moduleType)) {
			functions = list(Function.class);
		} else if(Module.MODULE_TYPE_GROUP.equals(moduleType)) {
			PropertyFilter propertyFilter = new PropertyFilter();
			propertyFilter.setCompareClass(ModuleGroup.class);
			propertyFilter.setCompareType(PropertyCompareType.EQ);
			propertyFilter.setPropertyName("path");
			propertyFilter.setPropertyValue(modulePath);
			functions = this.list(Function.class, propertyFilter);
		} else if(Module.MODULE_TYPE_MODULE.equals(moduleType)) {
			PropertyFilter propertyFilter = new PropertyFilter();
			propertyFilter.setCompareClass(Module.class);
			propertyFilter.setCompareType(PropertyCompareType.EQ);
			propertyFilter.setPropertyName("path");
			propertyFilter.setPropertyValue(modulePath);
			functions = this.list(Function.class, propertyFilter);
		} else if(Module.MODULE_TYPE_FUNCTION.equals(moduleType)) {
			PropertyFilter propertyFilter = new PropertyFilter();
			propertyFilter.setCompareClass(Function.class);
			propertyFilter.setCompareType(PropertyCompareType.EQ);
			propertyFilter.setPropertyName("authorityCode");
			propertyFilter.setPropertyValue(modulePath);
			functions = this.list(Function.class, propertyFilter);
		}
		return functions;
	}

}
