package com.lmiky.jdp.authority.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmiky.jdp.authority.dao.AuthorityDAO;
import com.lmiky.jdp.authority.pojo.Authority;
import com.lmiky.jdp.authority.service.AuthorityService;
import com.lmiky.jdp.database.dao.BaseDAO;
import com.lmiky.jdp.database.exception.DatabaseException;
import com.lmiky.jdp.module.pojo.Function;
import com.lmiky.jdp.module.service.ModuleService;
import com.lmiky.jdp.service.exception.ServiceException;
import com.lmiky.jdp.user.pojo.Role;

/**
 * @author lmiky
 * @date 2013-5-20
 */
@Service("authorityService")
public class AuthorityServiceImpl implements AuthorityService {
	private BaseDAO baseDAO; 
	private AuthorityDAO authorityDAO;
	private ModuleService moduleService;

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.authority.service.AuthorityService#listAuthorizedOperator(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Role> listAuthorizedOperator(String modulePath) throws ServiceException {
		try {
			return authorityDAO.listAuthorizedOperator(modulePath);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.authority.service.AuthorityService#listUnauthorizedOperator(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Role> listUnauthorizedOperator(String modulePath) throws ServiceException {
		try {
			return authorityDAO.listUnauthorizedOperator(modulePath);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.authority.service.AuthorityService#authorize(java.lang.String, java.util.List)
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void authorize(String modulePath, String moduleType, String[] operatorIds) throws ServiceException {
		try {
			// 删除旧的数据
			authorityDAO.delete(Authority.class, "modulePath", modulePath);
			if (operatorIds == null || operatorIds.length == 0) {
				return;
			}
			//获取所包含的功能列表
			List<Function> functions = moduleService.listFunctionByModulePath(modulePath, moduleType);
			if(functions.size() > 0) {
				List<Authority> authorities = new ArrayList<Authority>();
				Authority authority = null;
				for (String idStr: operatorIds) {
					for(Function function : functions) {
						System.out.println(function.getId());
						authority = new Authority();
						authority.setAuthorityCode(function.getAuthorityCode());
						authority.setModulePath(modulePath);
						authority.setOperator(Long.parseLong(idStr));
						authorities.add(authority);
					}
				}
				authorityDAO.save(authorities);
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lmiky.jdp.authority.service.AuthorityService#checkAuthority(java.lang.String, java.lang.Long)
	 */
	@Transactional(readOnly = true)
	public boolean checkAuthority(String authorityCode, Long userId) throws ServiceException {
		try {
			return authorityDAO.checkAuthority(authorityCode, userId);
		} catch (DatabaseException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	/**
	 * 注入DAO
	 * @author lmiky
	 * @date 2013-5-24
	 * @param dao
	 */
	@Resource(name = "authorityDAO")
	public void setDAO(BaseDAO dao) {
		this.baseDAO = dao;
		this.authorityDAO = (AuthorityDAO)this.baseDAO;
	}

	/**
	 * @return the moduleService
	 */
	public ModuleService getModuleService() {
		return moduleService;
	}

	/**
	 * @param moduleService the moduleService to set
	 */
	@Resource(name = "moduleService")
	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}
}
