package com.lmiky.cms.resource.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmiky.cms.resource.pojo.CmsResource;
import com.lmiky.cms.resource.pojo.CmsResourcePictureSnapshot;
import com.lmiky.jdp.database.model.PropertyCompareType;
import com.lmiky.jdp.database.model.PropertyFilter;
import com.lmiky.jdp.database.pojo.BasePojo;
import com.lmiky.jdp.service.exception.ServiceException;
import com.lmiky.jdp.service.impl.BaseServiceImpl;

/**
 * 文章
 * @author lmiky
 * @date 2014年8月3日 下午6:08:38
 */
@Service("cmsResourceService")
public class CmsResourceServiceImpl extends BaseServiceImpl {

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.service.impl.BaseServiceImpl#save(com.lmiky.jdp.database.pojo.BasePojo)
	 */
	@Override
	@Transactional(rollbackFor={Exception.class})
	public <T extends BasePojo> void save(T pojo) throws ServiceException {
		if(pojo.getId() != null && pojo instanceof CmsResource) {	//对象已存在
			PropertyFilter propertyFilter = new PropertyFilter();
			propertyFilter.setCompareClass(CmsResourcePictureSnapshot.class);
			propertyFilter.setCompareType(PropertyCompareType.EQ);
			propertyFilter.setPropertyName("cmsResource.id");
			propertyFilter.setPropertyValue(pojo.getId());
			//删除旧的图片快照
			delete(CmsResourcePictureSnapshot.class, propertyFilter);
		}
		super.save(pojo);
	}
}