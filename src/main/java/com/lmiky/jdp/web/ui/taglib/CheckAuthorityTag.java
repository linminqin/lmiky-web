package com.lmiky.jdp.web.ui.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import com.lmiky.jdp.authority.service.AuthorityService;
import com.lmiky.jdp.session.model.SessionInfo;
import com.lmiky.jdp.util.Environment;
import com.lmiky.jdp.web.util.WebUtils;

/**
 * 检查权限
 * @author lmiky
 * @date 2013-6-6
 */
public class CheckAuthorityTag extends BaseTag {
	private static final long serialVersionUID = 4389490262671734934L;
	
	private AuthorityService authorityService;
	private String authorityCode;

	public CheckAuthorityTag() {
		authorityService = (AuthorityService) Environment.getBean("authorityService");
	}

	/*
	 * (non-Javadoc)
	 * @see com.lmiky.jdp.web.ui.taglib.BaseTag#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		try {
			HttpServletRequest request = getRequest();
			SessionInfo sessionInfo = WebUtils.getSessionInfo(request);
			if (sessionInfo == null) {
				return SKIP_BODY;
			}
			if(!WebUtils.checkAuthority(authorityService, sessionInfo, authorityCode)) {	//管理员
				return SKIP_BODY;
			}
			return EVAL_BODY_INCLUDE;
		} catch (Exception e) {
			throw new JspException(e.getMessage());
		}
	}

	/**
	 * @return the authorityCode
	 */
	public String getAuthorityCode() {
		return authorityCode;
	}

	/**
	 * @param authorityCode the authorityCode to set
	 */
	public void setAuthorityCode(String authorityCode) {
		this.authorityCode = authorityCode;
	}


}
