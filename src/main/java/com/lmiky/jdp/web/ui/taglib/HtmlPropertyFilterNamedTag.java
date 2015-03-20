package com.lmiky.jdp.web.ui.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.lmiky.jdp.web.constants.Constants;


/**
 * 条件命名
 * @author lmiky
 * @date 2014-1-20
 */ 
public class HtmlPropertyFilterNamedTag extends BaseTag {
	private static final long serialVersionUID = 1036702552299217916L;
	private String propertyName; // 属性名
	private String compareType; // 比较方式

	/*
	 * (non-Javadoc)
	 * @see com.lmiky.jdp.web.ui.taglib.BaseTag#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		try {
			getPageContext().getOut().write(Constants.HTTP_PARAM_PROPERTYFILTER_NAME_PREFIX + propertyName + "_" + compareType.toUpperCase());
			return EVAL_BODY_INCLUDE;
		} catch (IOException e) {
			e.printStackTrace();
			throw new JspException(e);
		}
	}

	/**
	 * @return the compareType
	 */
	public String getCompareType() {
		return compareType;
	}

	/**
	 * @param compareType the compareType to set
	 */
	public void setCompareType(String compareType) {
		this.compareType = compareType;
	}

	/**
	 * @return the propertyName
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * @param propertyName the propertyName to set
	 */
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
}
