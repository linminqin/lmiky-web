package com.lmiky.jdp.web.ui.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang3.StringUtils;

/**
 * 标签基类
 * @author lmiky
 * @date 2013-4-18
 */
public abstract class BaseTag extends BodyTagSupport {
	private static final long serialVersionUID = 2686293269642543256L;
	protected StringBuffer handlers = new StringBuffer();
	
	/**
	 * 获取上下文环境
	 * @author lmiky
	 * @date 2013-4-18
	 * @return
	 */
	public PageContext getPageContext() {
		return pageContext;
	}
	
	/**
	 * 获取Request
	 * @author lmiky
	 * @date 2013-4-18
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return (HttpServletRequest)getPageContext().getRequest();
	}
	
	/**
	 * 方法说明
	 * @author lmiky
	 * @date 2013-4-18
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return (HttpServletResponse)getPageContext().getResponse();
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		prepareBeforeDetailTag();
		detailTag();
		prepareBeforeWriteHandlers();
		outputTag();
		return getDoStartTagRet();
	}
	
	/**
	 * 处理标签之前的准备
	 * @author lmiky
	 * @date 2013-4-18
	 * @throws JspException
	 */
	protected void  prepareBeforeDetailTag() throws JspException {
		
	}
	
	/**
	 * 处理标签
	 * @author lmiky
	 * @date 2013-4-18
	 * @throws JspException
	 */
	protected void  detailTag() throws JspException {
		
	}
	
	/**
	 * 输出页面内容之前
	 * @author lmiky
	 * @date 2013-4-18
	 * @throws JspException
	 */
	protected void  prepareBeforeWriteHandlers() throws JspException {
		
	}
	
	/**
	 * 输出标签
	 * @author lmiky
	 * @date 2013-4-18
	 * @throws JspException
	 */
	protected void outputTag() throws JspException {
		try {
			String content = handlers.toString();
			if(content != null && !content.isEmpty()) {
				getPageContext().getOut().write(content);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new JspException(e);
		}
	}
	
	/**
	 * 获取doStartTag返回结果
	 * @author lmiky
	 * @date 2013-4-18
	 * @return
	 */
	protected int getDoStartTagRet() {
		return EVAL_BODY_INCLUDE;
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		prepareBeforeEndTag();
		resetProperties();
		return getDoEndTagRet();
	}
	
	/**
	 * 结束标签之前
	 * @author lmiky
	 * @date 2013-5-31
	 * @throws JspException
	 */
	protected void  prepareBeforeEndTag() throws JspException {
		
	}
	
	/**
	 * 重新设置属性
	 * @author lmiky
	 * @date 2013-4-19
	 */
	protected void resetProperties() {
		handlers = new StringBuffer();
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#release()
	 */
	public void release() {
		resetProperties();
	}
	
	/**
	 * 获取doEndTag返回结果
	 * @author lmiky
	 * @date 2013-4-18
	 * @return
	 */
	protected int getDoEndTagRet() {
		return EVAL_PAGE;
	}

	/**
	 * 获取Response
	 * @author lmiky
	 * @date 2013-4-18
	 * @param handlers
	 * @param name
	 * @param value
	 */
	protected void prepareAttribute(StringBuffer handlers, String name, Object value) {
		if (!StringUtils.isBlank(name) && value != null) {
			handlers.append(" ");
			handlers.append(name);
			handlers.append("=\"");
			handlers.append(value);
			handlers.append("\"");
		}
	}

	/**
	 * @return the handlers
	 */
	public StringBuffer getHandlers() {
		return handlers;
	}

	/**
	 * @param handlers the handlers to set
	 */
	public void setHandlers(StringBuffer handlers) {
		this.handlers = handlers;
	}

}
