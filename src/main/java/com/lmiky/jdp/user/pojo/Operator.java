package com.lmiky.jdp.user.pojo;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * 操作员
 * @author lmiky
 * @date 2014-2-5
 */
@Entity
@Table(name="t_operator")
@PrimaryKeyJoinColumn(name="id")
public class Operator extends User {
	private static final long serialVersionUID = 7428190638595074671L;
	private Set<Role> roles;
	
	/**
	 * @return the roles
	 */
	@ManyToMany(fetch = FetchType.LAZY, cascade ={CascadeType.PERSIST})
	@JoinTable(name = "t_user_role", joinColumns = { @JoinColumn(name = "user_id", updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "role_id", updatable = false) })
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
