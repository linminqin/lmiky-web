package com.lmiky.jdp.system.menu.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lmiky.jdp.database.pojo.BasePojo;

/**
 * 用户最近操作菜单
 * @author lmiky
 * @date 2013-6-16
 */
@Entity
@Table(name="t_lately_operate_menu")
public class LatelyOperateMenu extends BasePojo {
	private static final long serialVersionUID = 1145492069635567005L;
	private Long userId;
	private Date opeTime;
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
	 * @return the opeTime
	 */
	@Column(name="ope_time")
	public Date getOpeTime() {
		return opeTime;
	}
	/**
	 * @param opeTime the opeTime to set
	 */
	public void setOpeTime(Date opeTime) {
		this.opeTime = opeTime;
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
