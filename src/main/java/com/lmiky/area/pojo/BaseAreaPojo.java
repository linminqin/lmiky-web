package com.lmiky.area.pojo;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.lmiky.jdp.database.pojo.BasePojo;
import com.lmiky.jdp.util.StringUtils;


/**
 * 地区父类
 * @author lmiky
 * @date 2013-10-21
 */
@SuppressWarnings("serial")
@MappedSuperclass
public class BaseAreaPojo extends BasePojo {
	
	private String name;
	private String phoneticAlphabet;
	
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
		setPhoneticAlphabet(StringUtils.getChineseFirstLetterl(name));
	}
	/**
	 * @return the phoneticAlphabet
	 */
	@Column(name = "phoneticAlphabet")
	public String getPhoneticAlphabet() {
		return phoneticAlphabet;
	}
	/**
	 * @param phoneticAlphabet the phoneticAlphabet to set
	 */
	public void setPhoneticAlphabet(String phoneticAlphabet) {
		this.phoneticAlphabet = phoneticAlphabet;
	}
}
