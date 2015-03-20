package com.lmiky.jdp.web.ui.taglib;

import com.lmiky.jdp.constants.Constants;


/**
 * 上下文资源文件
 * @author lmiky
 * @date 2014年8月2日 下午6:14:11
 */
public class ResourceContextTag extends ResourceTag {
	private static final long serialVersionUID = -4691494747486099012L;

	/**
	 * 
	 */
	public ResourceContextTag () {
		this.setPropertyKey(Constants.PROPERTIES_KEY_CONTEXT_FILE);
	}


}
