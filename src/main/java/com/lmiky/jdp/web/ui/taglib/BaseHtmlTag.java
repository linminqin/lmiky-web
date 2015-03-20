package com.lmiky.jdp.web.ui.taglib;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang3.StringUtils;

/**
 * HTML元素
 * @author lmiky
 * @date 2013-4-18
 */
@SuppressWarnings("serial")
public abstract class BaseHtmlTag extends BaseTag {
	//选中状态
	public static final String CHECKED_VALUE = "checked";
	
	public static final String INPUT_TYPE_TEXT = "text";
	public static final String INPUT_TYPE_SELECT = "select";
	public static final String INPUT_TYPE_RADIO = "radio";
	public static final String INPUT_TYPE_HIDDEN = "hidden";

	
	// 属性
	private String id;
	private String type;
	private String name;
	private String value;
	private String src;
	private String disabled;
	private String readonly;
	private String style;
	private String styleClass;
	private String accept;
	private String accesskey;
	private String align;
	private String alt;
	private String border;
	private String checked;
	private String dir;
	private String width;
	private String height;
	private String ismap;
	private String istyle;
	private String lang;
	private String maxlength;
	private String size;
	private String tabindex;
	private String title;
	private String usemap;

	// 事件
	private String onhelp;
	private String onabort;
	private String onblur;
	private String onchange;
	private String onclick;
	private String ondblclick;
	private String onerror;
	private String onfocus;
	private String onkeydown;
	private String onkeypress;
	private String onkeyup;
	private String onload;
	private String onmousedown;
	private String onmousemove;
	private String onmouseout;
	private String onmouseover;
	private String onmouseup;
	private String onreset;
	private String onresize;
	private String onselect;

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.web.ui.taglib.BaseTag#resetProperties()
	 */
	protected void resetProperties() {
		super.resetProperties();
		this.id = null;
		this.type = null;
		this.name = null;
		this.value = null;
		this.src = null;
		this.disabled = null;
		this.readonly = null;
		this.style = null;
		this.styleClass = null;
		this.accept = null;
		this.accesskey = null;
		this.align = null;
		this.alt = null;
		this.border = null;
		this.checked = null;
		this.dir = null;
		this.width = null;
		this.height = null;
		this.ismap = null;
		this.istyle = null;
		this.lang = null;
		this.maxlength = null;
		this.size = null;
		this.tabindex = null;
		this.title = null;
		this.usemap = null;
		
		this.onhelp = null;
		this.onabort = null;
		this.onblur = null;
		this.onchange = null;
		this.onclick = null;
		this.ondblclick = null;
		this.onerror = null;
		this.onfocus = null;
		this.onkeydown = null;
		this.onkeypress = null;
		this.onkeyup = null;
		this.onload = null;
		this.onmousedown = null;
		this.onmousemove = null;
		this.onmouseout = null;
		this.onmouseover = null;
		this.onmouseup = null;
		this.onreset = null;
		this.onresize = null;
		this.onselect = null;
	}
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.constants.ui.taglib.BaseTag#prepareBeforeDetailTag()
	 */
	protected void prepareBeforeDetailTag() throws JspException {
		super.prepareBeforeDetailTag();
		setTagValue();
		prepareHtmlAttribute();
		prepareEventAttribute();
	}
	
	/**
	 * 设置标签值
	 * @author lmiky
	 * @date 2013-4-18
	 * @return 设入的值，如果没有匹配的值，则返回空字符串
	 */
	protected String setTagValue() {
		if(!INPUT_TYPE_RADIO.equals(getType())) {	//RADIO不需要设值
			String[] valuse = getRequest().getParameterValues(getName());
			if(valuse != null && valuse.length > 0) {
				for(int i=0; i<valuse.length; i++) {
					if(!StringUtils.isBlank(valuse[i])) {
						setValue(valuse[i]);
						return valuse[i];
					}
				}
			}
		}
		return "";
	}

	/**
	 * 预设置HTML标签属性字段
	 * @author lmiky
	 * @date 2013-4-18
	 */
	public void prepareHtmlAttribute() {
		prepareAttribute(handlers, "id", getId());
		prepareAttribute(handlers, "type", getType());
		prepareAttribute(handlers, "value", getValue());
		prepareAttribute(handlers, "src", getSrc());
		prepareAttribute(handlers, "disabled", getDisabled());
		prepareAttribute(handlers, "readonly", getReadonly());
		prepareAttribute(handlers, "style", getStyle());
		prepareAttribute(handlers, "class", getStyleClass());
		prepareAttribute(handlers, "accept", getAccept());
		prepareAttribute(handlers, "accesskey", getAccesskey());
		prepareAttribute(handlers, "align", getAlign());
		prepareAttribute(handlers, "alt", getAlt());
		prepareAttribute(handlers, "border", getBorder());
		prepareAttribute(handlers, "checked", getChecked());
		prepareAttribute(handlers, "dir", getDir());
		prepareAttribute(handlers, "width", getWidth());
		prepareAttribute(handlers, "height", getHeight());
		prepareAttribute(handlers, "ismap", getIsmap());
		prepareAttribute(handlers, "istyle", getIstyle());
		prepareAttribute(handlers, "lang", getLang());
		prepareAttribute(handlers, "maxlength", getMaxlength());
		prepareAttribute(handlers, "name", getName());
		prepareAttribute(handlers, "size", getSize());
		prepareAttribute(handlers, "tabindex", getTabindex());
		prepareAttribute(handlers, "title", getTitle());
		prepareAttribute(handlers, "usemap", getUsemap());
	}
	
	/**
	 * 预设置事件
	 * @author lmiky
	 * @date 2013-4-18
	 */
	public void prepareEventAttribute() {
		prepareAttribute(handlers, "onhelp", getOnhelp());
		prepareAttribute(handlers, "onabort", getOnabort());
		prepareAttribute(handlers, "onblur", getOnblur());
		prepareAttribute(handlers, "onchange", getOnchange());
		prepareAttribute(handlers, "onclick", getOnclick());
		prepareAttribute(handlers, "ondblclick", getOndblclick());
		prepareAttribute(handlers, "onerror", getOnerror());
		prepareAttribute(handlers, "onfocus", getOnfocus());
		prepareAttribute(handlers, "onkeydown", getOnkeydown());
		prepareAttribute(handlers, "onkeypress", getOnkeypress());
		prepareAttribute(handlers, "onkeyup", getOnkeyup());
		prepareAttribute(handlers, "onload", getOnload());
		prepareAttribute(handlers, "onmousedown", getOnmousedown());
		prepareAttribute(handlers, "onmousemove", getOnmousemove());
		prepareAttribute(handlers, "onmouseout", getOnmouseout());
		prepareAttribute(handlers, "onmouseover", getOnmouseover());
		prepareAttribute(handlers, "onmouseup", getOnmouseup());
		prepareAttribute(handlers, "onreset", getOnreset());
		prepareAttribute(handlers, "onresize", getOnresize());
		prepareAttribute(handlers, "onselect", getOnselect());
	}

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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the src
	 */
	public String getSrc() {
		return src;
	}

	/**
	 * @param src the src to set
	 */
	public void setSrc(String src) {
		this.src = src;
	}

	/**
	 * @return the disabled
	 */
	public String getDisabled() {
		return disabled;
	}

	/**
	 * @param disabled the disabled to set
	 */
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	/**
	 * @return the readonly
	 */
	public String getReadonly() {
		return readonly;
	}

	/**
	 * @param readonly the readonly to set
	 */
	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	/**
	 * @return the style
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * @param style the style to set
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * @return the styleClass
	 */
	public String getStyleClass() {
		return styleClass;
	}

	/**
	 * @param styleClass the styleClass to set
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	/**
	 * @return the accept
	 */
	public String getAccept() {
		return accept;
	}

	/**
	 * @param accept the accept to set
	 */
	public void setAccept(String accept) {
		this.accept = accept;
	}

	/**
	 * @return the accesskey
	 */
	public String getAccesskey() {
		return accesskey;
	}

	/**
	 * @param accesskey the accesskey to set
	 */
	public void setAccesskey(String accesskey) {
		this.accesskey = accesskey;
	}

	/**
	 * @return the align
	 */
	public String getAlign() {
		return align;
	}

	/**
	 * @param align the align to set
	 */
	public void setAlign(String align) {
		this.align = align;
	}

	/**
	 * @return the alt
	 */
	public String getAlt() {
		return alt;
	}

	/**
	 * @param alt the alt to set
	 */
	public void setAlt(String alt) {
		this.alt = alt;
	}

	/**
	 * @return the border
	 */
	public String getBorder() {
		return border;
	}

	/**
	 * @param border the border to set
	 */
	public void setBorder(String border) {
		this.border = border;
	}

	/**
	 * @return the checked
	 */
	public String getChecked() {
		return checked;
	}

	/**
	 * @param checked the checked to set
	 */
	public void setChecked(String checked) {
		this.checked = checked;
	}

	/**
	 * @return the dir
	 */
	public String getDir() {
		return dir;
	}

	/**
	 * @param dir the dir to set
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}

	/**
	 * @return the width
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * @return the ismap
	 */
	public String getIsmap() {
		return ismap;
	}

	/**
	 * @param ismap the ismap to set
	 */
	public void setIsmap(String ismap) {
		this.ismap = ismap;
	}

	/**
	 * @return the istyle
	 */
	public String getIstyle() {
		return istyle;
	}

	/**
	 * @param istyle the istyle to set
	 */
	public void setIstyle(String istyle) {
		this.istyle = istyle;
	}

	/**
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * @param lang the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * @return the maxlength
	 */
	public String getMaxlength() {
		return maxlength;
	}

	/**
	 * @param maxlength the maxlength to set
	 */
	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * @return the tabindex
	 */
	public String getTabindex() {
		return tabindex;
	}

	/**
	 * @param tabindex the tabindex to set
	 */
	public void setTabindex(String tabindex) {
		this.tabindex = tabindex;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the usemap
	 */
	public String getUsemap() {
		return usemap;
	}

	/**
	 * @param usemap the usemap to set
	 */
	public void setUsemap(String usemap) {
		this.usemap = usemap;
	}

	/**
	 * @return the onhelp
	 */
	public String getOnhelp() {
		return onhelp;
	}

	/**
	 * @param onhelp the onhelp to set
	 */
	public void setOnhelp(String onhelp) {
		this.onhelp = onhelp;
	}

	/**
	 * @return the onabort
	 */
	public String getOnabort() {
		return onabort;
	}

	/**
	 * @param onabort the onabort to set
	 */
	public void setOnabort(String onabort) {
		this.onabort = onabort;
	}

	/**
	 * @return the onblur
	 */
	public String getOnblur() {
		return onblur;
	}

	/**
	 * @param onblur the onblur to set
	 */
	public void setOnblur(String onblur) {
		this.onblur = onblur;
	}

	/**
	 * @return the onchange
	 */
	public String getOnchange() {
		return onchange;
	}

	/**
	 * @param onchange the onchange to set
	 */
	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	/**
	 * @return the onclick
	 */
	public String getOnclick() {
		return onclick;
	}

	/**
	 * @param onclick the onclick to set
	 */
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	/**
	 * @return the ondblclick
	 */
	public String getOndblclick() {
		return ondblclick;
	}

	/**
	 * @param ondblclick the ondblclick to set
	 */
	public void setOndblclick(String ondblclick) {
		this.ondblclick = ondblclick;
	}

	/**
	 * @return the onerror
	 */
	public String getOnerror() {
		return onerror;
	}

	/**
	 * @param onerror the onerror to set
	 */
	public void setOnerror(String onerror) {
		this.onerror = onerror;
	}

	/**
	 * @return the onfocus
	 */
	public String getOnfocus() {
		return onfocus;
	}

	/**
	 * @param onfocus the onfocus to set
	 */
	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}

	/**
	 * @return the onkeydown
	 */
	public String getOnkeydown() {
		return onkeydown;
	}

	/**
	 * @param onkeydown the onkeydown to set
	 */
	public void setOnkeydown(String onkeydown) {
		this.onkeydown = onkeydown;
	}

	/**
	 * @return the onkeypress
	 */
	public String getOnkeypress() {
		return onkeypress;
	}

	/**
	 * @param onkeypress the onkeypress to set
	 */
	public void setOnkeypress(String onkeypress) {
		this.onkeypress = onkeypress;
	}

	/**
	 * @return the onkeyup
	 */
	public String getOnkeyup() {
		return onkeyup;
	}

	/**
	 * @param onkeyup the onkeyup to set
	 */
	public void setOnkeyup(String onkeyup) {
		this.onkeyup = onkeyup;
	}

	/**
	 * @return the onload
	 */
	public String getOnload() {
		return onload;
	}

	/**
	 * @param onload the onload to set
	 */
	public void setOnload(String onload) {
		this.onload = onload;
	}

	/**
	 * @return the onmousedown
	 */
	public String getOnmousedown() {
		return onmousedown;
	}

	/**
	 * @param onmousedown the onmousedown to set
	 */
	public void setOnmousedown(String onmousedown) {
		this.onmousedown = onmousedown;
	}

	/**
	 * @return the onmousemove
	 */
	public String getOnmousemove() {
		return onmousemove;
	}

	/**
	 * @param onmousemove the onmousemove to set
	 */
	public void setOnmousemove(String onmousemove) {
		this.onmousemove = onmousemove;
	}

	/**
	 * @return the onmouseout
	 */
	public String getOnmouseout() {
		return onmouseout;
	}

	/**
	 * @param onmouseout the onmouseout to set
	 */
	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}

	/**
	 * @return the onmouseover
	 */
	public String getOnmouseover() {
		return onmouseover;
	}

	/**
	 * @param onmouseover the onmouseover to set
	 */
	public void setOnmouseover(String onmouseover) {
		this.onmouseover = onmouseover;
	}

	/**
	 * @return the onmouseup
	 */
	public String getOnmouseup() {
		return onmouseup;
	}

	/**
	 * @param onmouseup the onmouseup to set
	 */
	public void setOnmouseup(String onmouseup) {
		this.onmouseup = onmouseup;
	}

	/**
	 * @return the onreset
	 */
	public String getOnreset() {
		return onreset;
	}

	/**
	 * @param onreset the onreset to set
	 */
	public void setOnreset(String onreset) {
		this.onreset = onreset;
	}

	/**
	 * @return the onresize
	 */
	public String getOnresize() {
		return onresize;
	}

	/**
	 * @param onresize the onresize to set
	 */
	public void setOnresize(String onresize) {
		this.onresize = onresize;
	}

	/**
	 * @return the onselect
	 */
	public String getOnselect() {
		return onselect;
	}

	/**
	 * @param onselect the onselect to set
	 */
	public void setOnselect(String onselect) {
		this.onselect = onselect;
	}

	/**
	 * @return the onsubmit
	 */
	public String getOnsubmit() {
		return onsubmit;
	}

	/**
	 * @param onsubmit the onsubmit to set
	 */
	public void setOnsubmit(String onsubmit) {
		this.onsubmit = onsubmit;
	}

	/**
	 * @return the onunload
	 */
	public String getOnunload() {
		return onunload;
	}

	/**
	 * @param onunload the onunload to set
	 */
	public void setOnunload(String onunload) {
		this.onunload = onunload;
	}

	private String onsubmit;
	private String onunload;

}
