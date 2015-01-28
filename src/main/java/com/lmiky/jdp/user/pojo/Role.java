package com.lmiky.jdp.user.pojo;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.lmiky.jdp.database.pojo.BasePojo;

/**
 * 角色
 * @author lmiky
 * @date 2013-5-7
 */
@Entity
@Table(name="t_role")
public class Role extends BasePojo {
	private static final long serialVersionUID = -6262397836385864159L;
	private String name;
	private Set<Operator> users;

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
	 * @return the users
	 */
	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
	public Set<Operator> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(Set<Operator> users) {
		this.users = users;
	}
}
