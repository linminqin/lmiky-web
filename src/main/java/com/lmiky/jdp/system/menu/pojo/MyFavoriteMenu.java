package com.lmiky.jdp.system.menu.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lmiky.jdp.database.pojo.BasePojo;

/**
 * 我的收藏菜单
 * @author lmiky
 * @date 2013-6-17
 */
@Entity
@Table(name="t_my_favorite_menu")
public class MyFavoriteMenu extends BasePojo {
	private static final long serialVersionUID = 7212289860594068144L;
	
	private Long userId;
	private Date addTime;
	private String menuId;
	
	/**
	 * @return the userId
	 */
	@Column(name="user_id")
	public Long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * @return the addTime
	 */
	@Column(name="add_time")
	public Date getAddTime() {
		return addTime;
	}
	/**
	 * @param addTime the addTime to set
	 */
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	/**
	 * @return the menuId
	 */
	@Column(name="menu_id")
	public String getMenuId() {
		return menuId;
	}
	/**
	 * @param menuId the menuId to set
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
}
