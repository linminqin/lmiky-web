package com.lmiky.capture.resource.pojo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.lmiky.jdp.database.pojo.BasePojo;

/**
 * 抓取资源内容
 * @author lmiky
 * @date 2013-11-6
 */
@Entity
@Table(name="t_capture_resource_content")
public class CaptureResourceContent extends BasePojo {
	private static final long serialVersionUID = 1070661318133881056L;
	private String content;

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
