package com.lmiky.jdp.module.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.lmiky.jdp.sort.pojo.BaseSortPojo;

/**
 * 功能
 * @author lmiky
 * @date 2013-5-12
 */
@Entity
@Table(name="t_function")
public class Function extends BaseSortPojo {
	private static final long serialVersionUID = 6905027190507202928L;
	
	private String name;
	private Module module;
	private String authorityCode;
	
	/**
	 * @return the name
	 */
	@Column(name="name")
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
	 * @return the module
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="moduleId", updatable = false)
	public Module getModule() {
		return module;
	}
	/**
	 * @param module the module to set
	 */
	public void setModule(Module module) {
		this.module = module;
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
