package com.lmiky.jdp.lock.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lmiky.jdp.controller.BaseController;
import com.lmiky.jdp.lock.service.LockService;
import com.lmiky.jdp.session.model.SessionInfo;

/**
 * 锁
 * @author lmiky
 * @date 2013-5-6
 */
@Controller
@RequestMapping("/lock")
public class LockController extends BaseController {
	private LockService lockService;
	
	/**
	 * 解锁
	 * @author lmiky
	 * @date 2013-5-6
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @param target
	 * @throws Exception 
	 */
	@RequestMapping("/unlock.shtml")
	public void unlock(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "target", required = true) String target) throws Exception {
		try {
			SessionInfo sessionInfo = getSessionInfo(modelMap, request);
			if(sessionInfo.getUserId() != null) {
				lockService.unlock(target, sessionInfo.getUserId());
			}
		} catch(Exception e) {
			logException(e, modelMap, request, response);
		}
	}

	/**
	 * @return the lockService
	 */
	public LockService getLockService() {
		return lockService;
	}

	/**
	 * @param lockService the lockService to set
	 */
	@Resource(name="lockService")
	public void setLockService(LockService lockService) {
		this.lockService = lockService;
	}
	
}
