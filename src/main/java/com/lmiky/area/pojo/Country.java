package com.lmiky.area.pojo;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;


/**
 * 国家
 * @author lmiky
 * @date 2013-10-21
 */
@Entity
@Table(name="t_country")
public class Country extends BaseAreaPojo {
	private static final long serialVersionUID = -6775351246359582097L;
	private Set<Province> provinces;

	/**
	 * @return the provinces
	 */
	@OneToMany(mappedBy="country", fetch=FetchType.LAZY, cascade={CascadeType.ALL})
	@OrderBy("phoneticAlphabet asc, id asc")
	public Set<Province> getProvinces() {
		return provinces;
	}

	/**
	 * @param provinces the provinces to set
	 */
	public void setProvinces(Set<Province> provinces) {
		this.provinces = provinces;
	}
}
