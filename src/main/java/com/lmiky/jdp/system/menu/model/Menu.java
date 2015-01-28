package com.lmiky.jdp.system.menu.model;

import java.io.Serializable;

/**
 * 菜单
 * @author lmiky
 * @date 2014-6-29
 */
public class Menu implements Serializable, Cloneable {
	private static final long serialVersionUID = -2046458146678335353L;
	private String id;
	private String label;
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Menu clone() throws CloneNotSupportedException {
		return (Menu)super.clone();
	}
}
