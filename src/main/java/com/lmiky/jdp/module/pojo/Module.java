package com.lmiky.jdp.module.pojo;

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

import com.lmiky.jdp.database.pojo.BasePojo;

/**
 * 模块
 * @author lmiky
 * @date 2013-5-12
 */
@Entity
@Table(name = "t_module")
public class Module extends BasePojo {
	private static final long serialVersionUID = -3173260146797921645L;

	public static final String MODULE_PATH_SYSTEM = "system";

	// 模块类别
	public static final String MODULE_TYPE_SYSTEM = "moduleType_system";
	public static final String MODULE_TYPE_GROUP = "moduleType_group";
	public static final String MODULE_TYPE_MODULE = "moduleType_module";
	public static final String MODULE_TYPE_FUNCTION = "moduleType_function";

	private String name;
	private String path;
	private ModuleGroup group;
	private Set<Function> functions;

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
	 * @return the path
	 */
	@Column(name = "path")
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
	 * @return the group
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "groupId", updatable = false)
	public ModuleGroup getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(ModuleGroup group) {
		this.group = group;
	}

	/**
	 * @return the functions
	 */
	@OneToMany(mappedBy = "module", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@OrderBy("sort asc, id asc")
	public Set<Function> getFunctions() {
		return functions;
	}

	/**
	 * @param functions the functions to set
	 */
	public void setFunctions(Set<Function> functions) {
		this.functions = functions;
	}
}
