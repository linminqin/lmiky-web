package com.lmiky.jdp.user.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.lmiky.jdp.database.pojo.BasePojo;

/**
 * 人员
 * @author lmiky
 * @date 2014-2-5
 */ 
@SuppressWarnings("serial")
@MappedSuperclass
public class Person extends BasePojo {
	private String name;
	private String email;
	private String phone;
	private String photo;
	private Date createTime;
	private String description;

	/**
	 * @return the name
	 */
	@Column(name = "name")
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phone
	 */
	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the photo
	 */
	@Column(name = "photo")
	public String getPhoto() {
		return photo;
	}

	/**
	 * @param photo the photo to set
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	/**
	 * @return the createTime
	 */
	@Column(name = "create_time", updatable = false)
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
	 * @return the description
	 */
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}