package com.lmiky.cms.directory.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmiky.cms.directory.pojo.CmsDirectory;
import com.lmiky.cms.resource.pojo.CmsResource;
import com.lmiky.jdp.database.model.PropertyCompareType;
import com.lmiky.jdp.database.model.PropertyFilter;
import com.lmiky.jdp.database.pojo.BasePojo;
import com.lmiky.jdp.service.exception.ServiceException;
import com.lmiky.jdp.tree.service.impl.TreeServiceImpl;

/**
 * cms目录业务管理
 * @author lmiky
 * @date 2014-1-11
 */
@Service("cmsDirectoryService")
public class CmsDirectoryServiceImpl extends TreeServiceImpl {

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.tree.service.impl.TreeServiceImpl#delete(com.lmiky.jdp.database.pojo.BasePojo)
	 */
	@Override
	@Transactional(rollbackFor={Exception.class})
	public <T extends BasePojo> void delete(T pojo) throws ServiceException {
		delete(CmsResource.class, new PropertyFilter("id", pojo.getId(), PropertyCompareType.EQ, CmsDirectory.class));
		super.delete(pojo);
	}

}
