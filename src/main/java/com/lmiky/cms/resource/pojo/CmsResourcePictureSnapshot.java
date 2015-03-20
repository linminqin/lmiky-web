package com.lmiky.cms.resource.pojo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.lmiky.jdp.database.pojo.BasePojo;

/**
 * 图片快照
 * @author lmiky
 * @date 2014年7月21日 下午8:48:57
 */
@Entity
@Table(name="t_cms_resource_picture_snapshot")
public class CmsResourcePictureSnapshot extends BasePojo {
	private static final long serialVersionUID = 4928834781706515863L;
	private CmsResource cmsResource;
	private String path;
	
	/**
	 * @return the cmsResource
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resource_id", updatable = false)
	public CmsResource getCmsResource() {
		return cmsResource;
	}
	/**
	 * @param cmsResource the cmsResource to set
	 */
	public void setCmsResource(CmsResource cmsResource) {
		this.cmsResource = cmsResource;
	}
	/**
	 * @return the path
	 */
	@Column(name="path")
	public String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
}
