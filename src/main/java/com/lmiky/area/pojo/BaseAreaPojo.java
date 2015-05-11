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
	
	public static final String POJO_FIELD_NAME_NAME = "name";
	public static final String POJO_FIELD_NAME_CODE = "code";
	
	private String name;
	private String phoneticAlphabet;
	private String code;
	
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
	/**
	 * @return the code
	 */
	@Column(name = "code")
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
}
