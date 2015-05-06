package com.lmiky.jdp.web.ui.taglib;

import javax.servlet.jsp.JspException;

import com.lmiky.jdp.util.BundleUtils;


/**
 * 资源文件
 * @author lmiky
 * @date 2014年8月2日 下午6:14:11
 */
@SuppressWarnings("serial")
public abstract class ResourceTag extends BaseTag {
	private String propertyKey;	//资源文件
	private String key;	//内容key

	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.web.ui.taglib.BaseTag#prepareBeforeWriteHandlers()
	 */
	@Override
	protected void prepareBeforeWriteHandlers() throws JspException {
		super.prepareBeforeWriteHandlers();
		handlers.append(BundleUtils.getStringValue(getPropertyKey(), getKey()));
	}

	/**
	 * @return the propertyKey
	 */
	public String getPropertyKey() {
		return propertyKey;
	}

	/**
	 * @param propertyKey the propertyKey to set
	 */
	public void setPropertyKey(String propertyKey) {
		this.propertyKey = propertyKey;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}


}
