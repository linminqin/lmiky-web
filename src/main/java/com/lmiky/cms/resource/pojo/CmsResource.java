package com.lmiky.cms.resource.pojo;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.lmiky.cms.directory.pojo.CmsDirectory;
import com.lmiky.jdp.database.pojo.BasePojo;
import com.lmiky.jdp.user.pojo.User;

/**
 * 资源
 * @author lmiky
 * @date 2014-1-5
 */ 
@Entity
@Table(name="t_cms_resource")
public class CmsResource extends BasePojo {
	private static final long serialVersionUID = -5493636462667145928L;
	//状态
	public static final int STATE_CREATE = 0;	//创建
	public static final int STATE_PUBLISH = 1;	//已发布
	public static final int STATE_UNPUBLISH = 2;	//取消发布
	private String title;
	private String subtitle;
	private String author;
	private User creator;
	private Date createTime;
	private User publisher;
	private Date pubTime;
	private String source;
	private Integer state = STATE_CREATE;
	private Set<CmsResourceContent> resourceContents;
	private CmsDirectory directory;
	private Set<CmsResourcePictureSnapshot> pictureSnapshots;
	
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
	 * @return the createTime
	 */
	@Column(name="createTime")
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	 * @return the creator
	 */
	@ManyToOne(fetch=FetchType.LAZY)  
    @JoinColumn(name="creator") 
	public User getCreator() {
		return creator;
	}
	/**
	 * @param creator the creator to set
	 */
	public void setCreator(User creator) {
		this.creator = creator;
	}
	/**
	 * @return the publisher
	 */
	@ManyToOne(fetch=FetchType.LAZY)  
    @JoinColumn(name="publisher") 
	public User getPublisher() {
		return publisher;
	}
	/**
	 * @param publisher the publisher to set
	 */
	public void setPublisher(User publisher) {
		this.publisher = publisher;
	}
	/**
	 * @return the source
	 */
	@Column(name="source")
	public String getSource() {
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * @return the state
	 */
	@Column(name="state")
	public Integer getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	
	/**
	 * @return the resourceContents
	 */
	@OneToMany(mappedBy="cmsResource", fetch=FetchType.LAZY, cascade={CascadeType.ALL})
	public Set<CmsResourceContent> getResourceContents() {
		return resourceContents;
	}
	/**
	 * @param resourceContents the resourceContents to set
	 */
	public void setResourceContents(Set<CmsResourceContent> resourceContents) {
		this.resourceContents = resourceContents;
	}
	
	/**
	 * 获取内容
	 * @author lmiky
	 * @date 2014-1-5
	 * @return
	 */
	@Transient
	public String getContent() {
		Set<CmsResourceContent> rcs = getResourceContents();
		if(rcs == null || rcs.isEmpty()) {
			return "";
		}
		return rcs.iterator().next().getContent();
	}
	/**
	 * @return the directory
	 */
	@ManyToOne(fetch=FetchType.LAZY)  
    @JoinColumn(name="directoryId")  
	public CmsDirectory getDirectory() {
		return directory;
	}
	/**
	 * @param directory the directory to set
	 */
	public void setDirectory(CmsDirectory directory) {
		this.directory = directory;
	}
	/**
	 * @return the pictureSnapshots
	 */
	@OneToMany(mappedBy="cmsResource", fetch=FetchType.LAZY, cascade={CascadeType.ALL})
	@OrderBy("id asc")
	public Set<CmsResourcePictureSnapshot> getPictureSnapshots() {
		return pictureSnapshots;
	}
	/**
	 * @param pictureSnapshots the pictureSnapshots to set
	 */
	public void setPictureSnapshots(Set<CmsResourcePictureSnapshot> pictureSnapshots) {
		this.pictureSnapshots = pictureSnapshots;
	}
}
