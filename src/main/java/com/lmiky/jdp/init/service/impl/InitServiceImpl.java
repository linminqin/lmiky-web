package com.lmiky.jdp.init.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmiky.jdp.authority.pojo.Authority;
import com.lmiky.jdp.authority.service.AuthorityService;
import com.lmiky.jdp.init.parser.ModuleParser;
import com.lmiky.jdp.init.service.InitService;
import com.lmiky.jdp.module.pojo.Function;
import com.lmiky.jdp.module.pojo.Module;
import com.lmiky.jdp.module.pojo.ModuleGroup;
import com.lmiky.jdp.service.impl.BaseServiceImpl;
import com.lmiky.jdp.system.menu.pojo.LatelyOperateMenu;
import com.lmiky.jdp.system.menu.pojo.MyFavoriteMenu;
import com.lmiky.jdp.system.menu.service.MenuParseService;
import com.lmiky.jdp.user.pojo.Operator;
import com.lmiky.jdp.user.pojo.Role;
import com.lmiky.jdp.user.pojo.User;
import com.lmiky.jdp.user.service.OperatorService;
import com.lmiky.jdp.util.EncoderUtils;

/**
 * 初始化业务实现类
 * @author lmiky
 * @date 2013-10-13
 */
@Service("initService")
public class InitServiceImpl extends BaseServiceImpl implements InitService {
	private ModuleParser moduleParser;
	private MenuParseService menuParseService;
	private AuthorityService authorityService;
	private OperatorService operatorService;

	/*
	 * (non-Javadoc)
	 * @see com.lmiky.jdp.init.service.InitService#init(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional(rollbackFor = { Exception.class })
	public void init(String adminName, String adminLoginName, String adminPassword) throws Exception {
		// 最近操作菜单
		delete(LatelyOperateMenu.class);
		// 收藏夹
		delete(MyFavoriteMenu.class);

		// 角色
		delete(Role.class);
		Role role = new Role();
		role.setName(adminName);
		save(role);

		// 用户
		Operator user = new Operator();
		user.setName(adminName);
		user.setLoginName(adminLoginName);
		user.setPassword(EncoderUtils.md5(adminPassword));
		user.setValid(User.VALID_YES);
		Date currentDate = new Date();
		user.setCreateTime(currentDate);
		user.setLastSetPasswordTime(currentDate);
		Set<Role> roles = new HashSet<Role>();
		roles.add(role);
		user.setRoles(roles);
		//TODO 应该改为删除Operator对应的用户，如果是hibernate框架，则直接执行delete(Operator.class);就可以了，但是mybatis需要额外的操作
		operatorService.deleteOperatorUser();
		delete(Operator.class);
		operatorService.save(user);

		//放在初始化模块之前，是因为修改模块中也要授权，先删除，修改模块就不需要浪费时间授权之后还要再删除
		delete(Authority.class);
		// 模块
		updateModule();

		// 权限：拥有系统管理员的权限
		authorityService.authorize(Module.MODULE_PATH_SYSTEM, Module.MODULE_TYPE_SYSTEM, new String[]{role.getId() + ""});
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.init.service.InitService#updateModule()
	 */
	@Transactional(rollbackFor = { Exception.class })
	public void updateModule() throws Exception {
		//保存模块
		List<ModuleGroup> moduleGroups = moduleParser.parse();
		delete(Function.class);
		delete(Module.class);
		delete(ModuleGroup.class);
		//hibernate写法：save(moduleGroups);
		for(ModuleGroup moduleGroup : moduleGroups) {
			save(moduleGroup);
			for(Module module : moduleGroup.getModules()) {
				module.setGroup(moduleGroup);
				save(module);
				for(Function function : module.getFunctions()) {
					function.setModule(module);
					save(function);
				}
			}
		}
		//重新授权，防止出现类似“权限管理里面看到是整个系统的管理员，而实际有某个模块是没有权限”的情况
		for(ModuleGroup moduleGroup : moduleGroups) {
			for(Module module : moduleGroup.getModules()) {
				for(Function function : module.getFunctions()) {
					List<Role> roles = authorityService.listAuthorizedOperator(function.getAuthorityCode());
					String[] roleStrs = new String[roles.size()];
					for(int i=0; i<roles.size(); i++) {
						roleStrs[i] = roles.get(i).getId().toString();
					}
					authorityService.authorize(function.getAuthorityCode(), Module.MODULE_TYPE_FUNCTION, roleStrs);
				}
				List<Role> roles = authorityService.listAuthorizedOperator(module.getPath());
				String[] roleStrs = new String[roles.size()];
				for(int i=0; i<roles.size(); i++) {
					roleStrs[i] = roles.get(i).getId().toString();
				}
				authorityService.authorize(module.getPath(), Module.MODULE_TYPE_MODULE, roleStrs);
			}
			List<Role> roles = authorityService.listAuthorizedOperator(moduleGroup.getPath());
			String[] roleStrs = new String[roles.size()];
			for(int i=0; i<roles.size(); i++) {
				roleStrs[i] = roles.get(i).getId().toString();
			}
			authorityService.authorize(moduleGroup.getPath(), Module.MODULE_TYPE_GROUP, roleStrs);
		}
		List<Role> roles = authorityService.listAuthorizedOperator(Module.MODULE_PATH_SYSTEM);
		String[] roleStrs = new String[roles.size()];
		for(int i=0; i<roles.size(); i++) {
			roleStrs[i] = roles.get(i).getId().toString();
		}
		authorityService.authorize(Module.MODULE_PATH_SYSTEM, Module.MODULE_TYPE_SYSTEM, roleStrs);
		//更新菜单
		updateMenu();
	}
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.init.service.InitService#updateMenu()
	 */
	public void updateMenu() throws Exception {
		menuParseService.parse();
	}
	
	/**
	 * @return the moduleParser
	 */
	public ModuleParser getModuleParser() {
		return moduleParser;
	}

	/**
	 * @param moduleParser the moduleParser to set
	 */
	@Resource(name = "moduleParser")
	public void setModuleParser(ModuleParser moduleParser) {
		this.moduleParser = moduleParser;
	}


	/**
	 * @return the authorityService
	 */
	public AuthorityService getAuthorityService() {
		return authorityService;
	}

	/**
	 * @param authorityService the authorityService to set
	 */
	@Resource(name = "authorityService")
	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	/**
	 * @return the menuParseService
	 */
	public MenuParseService getMenuParseService() {
		return menuParseService;
	}

	/**
	 * @param menuParseService the menuParseService to set
	 */
	@Resource(name="menuParseService")
	public void setMenuParseService(MenuParseService menuParseService) {
		this.menuParseService = menuParseService;
	}
	
	/**
	 * @return the operatorService
	 */
	public OperatorService getOperatorService() {
		return operatorService;
	}

	/**
	 * @param operatorService the operatorService to set
	 */
	@Resource(name="operatorService")
	public void setOperatorService(OperatorService operatorService) {
		this.operatorService = operatorService;
	}
}
