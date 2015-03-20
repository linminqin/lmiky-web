package com.lmiky.area.pojo;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * 省份
 * @author lmiky
 * @date 2013-10-21
 */
@Entity
@Table(name="t_province")
public class Province extends BaseAreaPojo {
	private static final long serialVersionUID = 7914575913111694327L;
	private Country country;
	private Set<City> cities;
	
	/**
	 * @return the country
	 */
	@ManyToOne(fetch=FetchType.LAZY)  
    @JoinColumn(name="country", updatable = false) 
	public Country getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(Country country) {
		this.country = country;
	}
	/**
	 * @return the cities
	 */
	@OneToMany(mappedBy="province", fetch=FetchType.LAZY, cascade={CascadeType.ALL})
	@OrderBy("phoneticAlphabet asc, id asc")
	public Set<City> getCities() {
		return cities;
	}
	/**
	 * @param cities the cities to set
	 */
	public void setCities(Set<City> cities) {
		this.cities = cities;
	}
}
