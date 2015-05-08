package com.lmiky.jdp.web.ui.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.commons.lang3.StringUtils;

import com.lmiky.jdp.util.DateUtils;
import com.lmiky.jdp.util.UUIDGenerator;
import com.lmiky.jdp.web.constants.Constants;
import com.lmiky.jdp.web.util.WebUtils;

/**
 * 属性过滤标签
 * @author lmiky
 * @date 2013-4-18
 */
public class HtmlPropertyFilterTag extends BaseHtmlTag {
	private static final long serialVersionUID = -5249478406848045429L;
	public static final String INPUT_TYPE_DATE = "date";
	public static final String INPUT_TYPE_DATETIME = "dateTime";
	public static final String INPUT_TYPE_BEGINDATE = "beginDate";
	public static final String INPUT_TYPE_ENDDATE = "endDate";

	private String inputType; // 输入方式
	private String propertyName; // 属性名
	private String compareType; // 比较方式

	private String label;
	private String labelStyle;
	private String labelClass;

	public HtmlPropertyFilterTag() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * @see com.lmiky.jdp.web.ui.taglib.BaseHtmlTag#resetProperties()
	 */
	protected void resetProperties() {
		super.resetProperties();
		this.propertyName = null;
		this.compareType = null;
		this.inputType = null;
		this.label = null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lmiky.jdp.constants.ui.taglib.BaseTag#prepareBeforeDetailTag()
	 */
	@Override
	protected void prepareBeforeDetailTag() throws JspException {
		setName(WebUtils.buildPropertyFilterName(propertyName, compareType));
		if (INPUT_TYPE_SELECT.equals(inputType)) {
			setType(null);
		} else if (INPUT_TYPE_RADIO.equals(inputType)) {
			setType(inputType);
		} else if (INPUT_TYPE_HIDDEN.equals(inputType)) {
			setType(inputType);
		}else { // 默认
			setType(INPUT_TYPE_TEXT);
		}
		if (INPUT_TYPE_RADIO.equals(inputType) && StringUtils.isBlank(getId())) {
			setId(UUIDGenerator.generateString());
			HttpServletRequest request = getRequest();
			String chekcdValue = request.getParameter(getName());
			// 设置RADIO选中项
			if ((!StringUtils.isBlank(getValue()) && getValue().equals(chekcdValue)) || (StringUtils.isBlank(getValue()) && StringUtils.isBlank(chekcdValue))) { 
				setChecked(CHECKED_VALUE);
			} else {
				setChecked(null);
			}
		}
		
		super.prepareBeforeDetailTag();
	}

	/*
	 * (non-Javadoc)
	 * @see com.lmiky.jdp.web.ui.taglib.BaseTag#prepareBeforeWriteHandlers()
	 */
	@Override
	protected void prepareBeforeWriteHandlers() throws JspException {
		super.prepareBeforeWriteHandlers();
		if (INPUT_TYPE_SELECT.equals(inputType)) {
			detailSelectType();
		} else { // 默认text
			detailTextType();
		}
	}

	/**
	 * 处理text类型
	 * @author lmiky
	 * @date 2013-5-31
	 */
	private void detailTextType() {
		StringBuffer inputHtml = new StringBuffer("<input  ");
		if (INPUT_TYPE_DATE.equals(inputType) || INPUT_TYPE_BEGINDATE.equals(inputType) || INPUT_TYPE_ENDDATE.equals(inputType) || INPUT_TYPE_DATETIME.equals(inputType)) {
			inputHtml.append(" onFocus=\"WdatePicker({readOnly:true, dateFmt:'");
			if (INPUT_TYPE_DATE.equals(inputType) || INPUT_TYPE_BEGINDATE.equals(inputType) || INPUT_TYPE_ENDDATE.equals(inputType)) {
				inputHtml.append(Constants.CONTEXT_KEY_FORMAT_DATE_VALUE);
				if (INPUT_TYPE_BEGINDATE.equals(inputType)) {
					inputHtml.append(" ").append(DateUtils.getBeginDateTime());
				} else if (INPUT_TYPE_ENDDATE.equals(inputType)) {
					inputHtml.append(" ").append(DateUtils.getEndDateTime());
				}
			} else {
				inputHtml.append(Constants.CONTEXT_KEY_FORMAT_DATETIME_VALUE);
			}
			inputHtml.append("'})\" ");
		}
		handlers.insert(0, inputHtml.toString());
		handlers.append(" />");
		if (INPUT_TYPE_RADIO.equals(inputType)) {
			handlers.append("<label for=\"").append(getId()).append("\" ");
			if(!StringUtils.isBlank(getLabelStyle())) {
				handlers.append(" style=\"").append(getLabelStyle()).append("\" ");
			}
			if(!StringUtils.isBlank(getLabelClass())) {
				handlers.append(" class=\"").append(getLabelClass()).append("\" ");
			}
			handlers.append(" >").append(getLabel()).append("</label>");
		}
	}

	/**
	 * 处理select类型
	 * @author lmiky
	 * @date 2013-5-31
	 */
	private void detailSelectType() {
		handlers.insert(0, "<select  ");
		handlers.append(">");

	}

	/*
	 * (non-Javadoc)
	 * @see com.lmiky.jdp.web.ui.taglib.BaseTag#doAfterBody()
	 */
	@Override
	public int doAfterBody() throws JspException {
		handlers = new StringBuffer();
		// 设置SELECT选中项
		if (INPUT_TYPE_SELECT.equals(inputType)) {
			handlers.append("</select>");
			if (!StringUtils.isBlank(getValue())) {
				handlers.append("<script type=\"text/javascript\">");
				handlers.append("$(document).ready(function() {");
				handlers.append("$(\"select[name='").append(getName()).append("']\").val(\"").append(getValue()).append("\");");
				handlers.append("});");
				handlers.append("</script>");
			}
		}
		outputTag();
		return SKIP_BODY;
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
	 * @return the inputType
	 */
	public String getInputType() {
		return inputType;
	}

	/**
	 * @param inputType the inputType to set
	 */
	public void setInputType(String inputType) {
		this.inputType = inputType;
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

	/**
	 * @return the labelStyle
	 */
	public String getLabelStyle() {
		return labelStyle;
	}

	/**
	 * @param labelStyle the labelStyle to set
	 */
	public void setLabelStyle(String labelStyle) {
		this.labelStyle = labelStyle;
	}

	/**
	 * @return the labelClass
	 */
	public String getLabelClass() {
		return labelClass;
	}

	/**
	 * @param labelClass the labelClass to set
	 */
	public void setLabelClass(String labelClass) {
		this.labelClass = labelClass;
	}

}
