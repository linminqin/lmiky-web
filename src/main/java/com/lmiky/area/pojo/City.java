package com.lmiky.area.pojo;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * 地市
 * @author lmiky
 * @date 2013-10-21
 */
@Entity
@Table(name="t_city")
public class City extends BaseAreaPojo {
	private static final long serialVersionUID = 1206169788541078983L;
	private Province province;
	
	/**
	 * @return the province
	 */
	@ManyToOne(fetch=FetchType.LAZY)  
    @JoinColumn(name="province", updatable = false) 
	public Province getProvince() {
		return province;
	}
	/**
	 * @param province the province to set
	 */
	public void setProvince(Province province) {
		this.province = province;
	}
}
