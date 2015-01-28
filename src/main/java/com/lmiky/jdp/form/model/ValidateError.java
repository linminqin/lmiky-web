package com.lmiky.jdp.form.model;

import java.io.Serializable;

/**
 * 验证异常
 * @author lmiky
 * @date 2013-5-9
 */
public class ValidateError implements Serializable {
	private static final long serialVersionUID = 3335039626772480633L;
	private String fieldName;	//字段名
	private String errorDesc;	//异常说明
	
	public ValidateError() {
		
	}
	
	public ValidateError(String fieldName, String errorDesc) {
		this.fieldName = fieldName;
		this.errorDesc = errorDesc;
	}
	 
	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}
	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	/**
	 * @return the errorDesc
	 */
	public String getErrorDesc() {
		return errorDesc;
	}
	/**
	 * @param errorDesc the errorDesc to set
	 */
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
}
