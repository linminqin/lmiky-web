package com.lmiky.jdp.authority.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lmiky.jdp.database.pojo.BasePojo;

/**
 * 权限
 * @author lmiky
 * @date 2013-5-12
 */
@Entity
@Table(name="t_authority")
public class Authority extends BasePojo {
	private static final long serialVersionUID = -3394086011235457556L;
	
	private Long operator;
	private String modulePath;
	private String authorityCode;
	
	/**
	 * @return the operator
	 */
	@Column(name="operator")
	public Long getOperator() {
		return operator;
	}
	/**
	 * @param operator the operator to set
	 */
	public void setOperator(Long operator) {
		this.operator = operator;
	}
	/**
	 * @return the modulePath
	 */
	@Column(name="modulePath")
	public String getModulePath() {
		return modulePath;
	}
	/**
	 * @param modulePath the modulePath to set
	 */
	public void setModulePath(String modulePath) {
		this.modulePath = modulePath;
	}
	/**
	 * @return the authorityCode
	 */
	@Column(name="authorityCode")
	public String getAuthorityCode() {
		return authorityCode;
	}
	/**
	 * @param authorityCode the authorityCode to set
	 */
	public void setAuthorityCode(String authorityCode) {
		this.authorityCode = authorityCode;
	}
}
