package com.lmiky.cms.resource.pojo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.lmiky.jdp.database.pojo.BasePojo;

/**
 * 资源内容
 * @author lmiky
 * @date 2014-1-5
 */ 
@Entity
@Table(name="t_cms_resource_content")
public class CmsResourceContent extends BasePojo {
	private static final long serialVersionUID = 6407808893561867426L;
	private CmsResource cmsResource;
	private String content;

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
	 * @return the content
	 */
	@Lob
	@Column(name="content")
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
}
