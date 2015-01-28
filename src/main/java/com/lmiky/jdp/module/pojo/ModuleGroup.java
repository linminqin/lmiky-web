package com.lmiky.jdp.module.pojo;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.lmiky.jdp.database.pojo.BasePojo;

/**
 * 模块组
 * @author lmiky
 * @date 2013-5-12
 */
@Entity
@Table(name="t_module_group")
public class ModuleGroup extends BasePojo {
	private static final long serialVersionUID = -1308605978031590177L;
	
	private String name;
	private String path;
	private Set<Module> modules;

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

	/**
	 * @return the modules
	 */
	@OneToMany(mappedBy="group", fetch=FetchType.LAZY, cascade={CascadeType.ALL})
	@OrderBy("id asc")
	public Set<Module> getModules() {
		return modules;
	}

	/**
	 * @param modules the modules to set
	 */
	public void setModules(Set<Module> modules) {
		this.modules = modules;
	}
}
