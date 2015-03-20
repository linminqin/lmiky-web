package com.lmiky.capture.resource.pojo;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.lmiky.jdp.database.pojo.BasePojo;

/**
 * 抓取资源
 * @author lmiky
 * @date 2013-11-6
 */
@Entity
@Table(name="t_capture_resource")
public class CaptureResource extends BasePojo {
	private static final long serialVersionUID = 8552555711119829436L;
	
	private String title;
	private String subtitle;
	private String author;
	private Date pubTime;
	private CaptureResourceContent content;
	
	/**
	 * @return the title
	 */
	@Column(name="title")
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the subtitle
	 */
	@Column(name="subtitle")
	public String getSubtitle() {
		return subtitle;
	}
	/**
	 * @param subtitle the subtitle to set
	 */
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	/**
	 * @return the author
	 */
	@Column(name="author")
	public String getAuthor() {
		return author;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * @return the pubTime
	 */
	@Column(name="pubTime")
	public Date getPubTime() {
		return pubTime;
	}
	/**
	 * @param pubTime the pubTime to set
	 */
	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
	}
	/**
	 * @return the content
	 */
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="contentId")
	public CaptureResourceContent getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(CaptureResourceContent content) {
		this.content = content;
	}
	
	/**
	 * 获取内容
	 * @author lmiky
	 * @date 2013-11-6
	 * @return
	 */
	@Transient
	public String getContentBody() {
		if(content != null) {
			return content.getContent();
		}
		return "";
	}
}
