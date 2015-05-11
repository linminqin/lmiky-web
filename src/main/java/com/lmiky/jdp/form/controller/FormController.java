package com.lmiky.jdp.form.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;

import com.lmiky.jdp.controller.BaseWebController;
import com.lmiky.jdp.database.pojo.BasePojo;
import com.lmiky.jdp.form.model.ValidateError;
import com.lmiky.jdp.lock.exception.LockException;
import com.lmiky.jdp.lock.service.LockService;
import com.lmiky.jdp.logger.model.OperateType;
import com.lmiky.jdp.logger.util.LoggerUtils;
import com.lmiky.jdp.service.exception.ServiceException;
import com.lmiky.jdp.session.model.SessionInfo;
import com.lmiky.jdp.view.controller.ViewController;
import com.lmiky.jdp.web.constants.Constants;

/**
 * 控制器
 * @author lmiky
 * @date 2013-4-15
 */
public abstract class FormController<T extends BasePojo> extends ViewController<T> {
	
	public static final String HTTP_PARAM_FORM_OEPNMODE = "openMode";	//表单打开方式
	public static final String HTTP_PARAM_FORM_SUBFORM = "subForm";	//表单子页面
	
	//打开方式
	public static final String OPEN_MODE_READ = "read";		//只读
	public static final String OPEN_MODE_CTEATE = "create";	//创建
	public static final String OPEN_MODE_EDIT = "edit";			//编辑
	
	//子页面模式
	public static final String SUB_FORM_READ = "Read";	//创建
	public static final String SUB_FORM_EDIT = "Edit";			//编辑
	
	//校验错误信息
	public static final String VALIDATE_ERROR_INFO_KEY = "validateErrorInfos";
	//加锁目标ID
	public static final String LOCK_TARGET_ID_KEY = "lockTargetId";
	
	protected LockService lockService;
	
	/**
	 * 获取增加权限值，如果返回值为空，表示不需要检查权限
	 * @author lmiky
	 * @date 2013-12-30
	 * @param modelMap
	 * @param request
	 * @return
	 */
	protected String getAddAuthorityCode(ModelMap modelMap, HttpServletRequest request) {
		return "";
	}
	
	/**
	 * 获取修改权限值，如果返回值为空，表示不需要检查权限
	 * @author lmiky
	 * @date 2013-12-30
	 * @param modelMap
	 * @param request
	 * @return
	 */
	protected String getModifyAuthorityCode(ModelMap modelMap, HttpServletRequest request) {
		return "";
	}
	
	/**
	 * 获取删除权限值，如果返回值为空，表示不需要检查权限
	 * @author lmiky
	 * @date 2013-12-30
	 * @param modelMap
	 * @param request
	 * @return
	 */
	protected String getDeleteAuthorityCode(ModelMap modelMap, HttpServletRequest request) {
		return "";
	}
	
	/**
	 * 加载
	 * @author lmiky
	 * @date 2013-4-15
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public String executeLoad(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse, Long id) throws Exception {
		return executeLoad(modelMap, request, resopnse, id, BaseWebController.REQUESTTYPE_NORMAL);
	}

	/**
	 * 加载
	 * @author lmiky
	 * @date 2013-4-15
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @param id
	 * @param requestTyp 请求方式
	 * @return
	 * @throws Exception 
	 */
	public String executeLoad(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse, Long id, String requestTyp) throws Exception {
		try {
			//判断是否有登陆
			SessionInfo sessionInfo = getSessionInfo(modelMap, request);
			//检查单点登陆
			checkSso(sessionInfo, modelMap, request);
			//获取打开方式
			String openMode = getOpenMode(modelMap, request, id);
			//获取记录锁
			if(OPEN_MODE_EDIT.equals(openMode)) {
				//检查权限
				checkAuthority(modelMap, request, sessionInfo, getModifyAuthorityCode(modelMap, request));
				try {
					String lockTargetId = getLockTargetId(request, id);
					lockService.lock(lockTargetId, sessionInfo.getUserId(), sessionInfo.getUserName());
					modelMap.put(LOCK_TARGET_ID_KEY, lockTargetId);
				} catch(LockException le) {
					openMode = OPEN_MODE_READ;
					if(!StringUtils.isBlank(le.getLockUserName())) {
						putMessage(modelMap, "该记录正被" + le.getLockUserName() + "编辑！");
					} else {
						throw le;
					}
				}
			} else if(OPEN_MODE_CTEATE.equals(openMode)) {
				//检查权限
				checkAuthority(modelMap, request, sessionInfo, getAddAuthorityCode(modelMap, request));
			} else {
				//检查权限
				checkAuthority(modelMap, request, sessionInfo, getLoadAuthorityCode(modelMap, request));
			}
			//将打开方式设入页面
			modelMap.put(HTTP_PARAM_FORM_OEPNMODE, openMode);
			//获取表单子页面并设入页面
			modelMap.put(HTTP_PARAM_FORM_SUBFORM, getSubForm(modelMap, request, openMode));
			//生成实体对象
			T pojo = generatePojo(modelMap, request, id);
			//将实体对象设入页面
			modelMap.put("pojo", pojo);
			appendLoadAttribute(modelMap, request, resopnse, openMode, pojo);
			String modulePath = getModulePath(modelMap, request);
			modelMap.put(Constants.HTTP_PARAM_MODULE_PATH, modulePath);
			//设置菜单
			setMenuInfo(modelMap, request);
			return getExecuteLoadRet(modelMap, request, modulePath);
		} catch(Exception e) {
			return transactException(e, modelMap, request, resopnse, requestTyp);
		}
	}
	
	/**
	 * 检查记录是否被锁住
	 * @author lmiky
	 * @date 2013-4-23
	 * @param modelMap
	 * @param request
	 * @param id
	 * @return
	 * @throws LockException
	 */
	public boolean isLock(ModelMap modelMap, HttpServletRequest request, Long id) throws LockException {
		if(id == null) {
			return false;
		}
		return lockService.isLockByUser(String.valueOf(id), 123l);
	}
	
	/**
	 * 获取打开方式
	 * @author lmiky
	 * @date 2013-4-19
	 * @param modelMap
	 * @param request
	 * @param id
	 * @return
	 */
	public String getOpenMode(ModelMap modelMap, HttpServletRequest request, Long id) {
		String openMode = (String)request.getParameter(HTTP_PARAM_FORM_OEPNMODE);
		if(StringUtils.isBlank(openMode)) {
			openMode =  (String)(request.getSession().getAttribute(HTTP_PARAM_FORM_OEPNMODE));
		}
		//如果是编辑但是ID为空，则修改打开方式为只读
		if(OPEN_MODE_EDIT.equals(openMode) && id == null) {
			openMode = OPEN_MODE_CTEATE;
		}
		//如果打开方式为空，则默认为只读
		if(StringUtils.isBlank(openMode)) {
			return FormController.OPEN_MODE_READ;
		}
		return openMode;
	}
	
	/**
	 * 获取锁ID
	 * @author lmiky
	 * @date 2013-5-10
	 * @param request
	 * @param pojoId
	 * @return
	 */
	public String getLockTargetId(HttpServletRequest request, Long pojoId) {
		return pojoClass.getName() + "_" + pojoId;
	}
	
	/**
	 * 获取表单子页面
	 * @author lmiky
	 * @date 2013-5-8
	 * @param modelMap
	 * @param request
	 * @param openMode
	 * @return
	 */
	public String getSubForm(ModelMap modelMap, HttpServletRequest request, String openMode) {
		if(StringUtils.isBlank(openMode)) {
			openMode = OPEN_MODE_READ;
		}
		if(OPEN_MODE_READ.equals(openMode)) {
			return getControllerName(modelMap, request) + SUB_FORM_READ + getViewType();
		} else {
			return getControllerName(modelMap, request) + SUB_FORM_EDIT + getViewType();
		}
	}
	
	/**
	 * 生成实体对象
	 * @author lmiky
	 * @date 2013-4-18
	 * @param modelMap
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	protected T generatePojo(ModelMap modelMap, HttpServletRequest request, Long id) throws Exception {
		T pojo = null;
		if(id != null) {
			pojo = loadPojo(modelMap, request, id);
		}
		if(pojo == null) {
			pojo = generateNewPojo(modelMap, request);
		}
		return pojo;
	}
	
	/**
	 * 生成新实体对象
	 * @author lmiky
	 * @date 2013-4-18
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected T generateNewPojo(ModelMap modelMap, HttpServletRequest request) throws Exception {
		return pojoClass.newInstance();
	}
	
	/**
	 * 根据id加载实体对象
	 * @author lmiky
	 * @date 2013-4-18
	 * @param modelMap
	 * @param request
	 * @param id
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ServiceException 
	 * @throws InvocationTargetException 
	 */
	protected T loadPojo(ModelMap modelMap, HttpServletRequest request, Long id) throws InstantiationException, IllegalAccessException, ServiceException, InvocationTargetException  {
		return service.find(pojoClass, id);
	}
	
	/**
	 * 追加属性
	 * @author lmiky
	 * @date 2013-5-13
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @param openMode
	 * @param pojo
	 * @throws Exception
	 */
	protected void appendLoadAttribute(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse, String openMode, T pojo) throws Exception {
		
	}

	/**
	 * 获取加载查询结果
	 * @author
	 * @date 2013-4-19
	 * @param modelMap
	 * @param request
	 * @param modulePath
	 * @return
	 */
	public String getExecuteLoadRet(ModelMap modelMap, HttpServletRequest request, String modulePath) {
		return getViewNamePrefix(modelMap, request, modulePath) + "Form";
	}
	
	/**
	 * 保存
	 * @author lmiky
	 * @date 2013-5-6
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String executeSave(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse,  Long id) throws Exception {
		return executeSave(modelMap, request, resopnse, id, BaseWebController.REQUESTTYPE_NORMAL);
	}
	
	/**
	 * 保存
	 * @author lmiky
	 * @date 2013-5-6
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @param id
	 * @param requestTyp 请求方式
	 * @return
	 * @throws Exception
	 */
	public String executeSave(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse,  Long id, String requestTyp) throws Exception {
		try {
			//判断是否有登陆
			SessionInfo sessionInfo = getSessionInfo(modelMap, request);
			//检查单点登陆
			checkSso(sessionInfo, modelMap, request);
			//获取打开方式
			String openMode = getOpenMode(modelMap, request, id);
			//操作类别
			String opeType = OperateType.OPE_TYPE_UPDATE;
			if(OPEN_MODE_CTEATE.equals(openMode)) {
				opeType = OperateType.OPE_TYPE_ADD;
			}
			//获取实体对象
			T pojo = generatePojo(modelMap, request, id);
			boolean hasLock = true;
			//检查记录锁
			if(OPEN_MODE_EDIT.equals(openMode)) {
				//检查权限
				checkAuthority(modelMap, request, sessionInfo, getModifyAuthorityCode(modelMap, request));
				try {
					String lockTargetId = getLockTargetId(request, id);
					lockService.lock(lockTargetId, sessionInfo.getUserId(), sessionInfo.getUserName());
					modelMap.put(LOCK_TARGET_ID_KEY, lockTargetId);
				} catch(LockException le) {
					openMode = OPEN_MODE_READ;
					hasLock = false;
					if(!StringUtils.isBlank(le.getLockUserName())) {
						putMessage(modelMap, "该记录正被" + le.getLockUserName() + "编辑！");
					} else {
						throw le;
					}
				}
			} else {
				//检查权限
				checkAuthority(modelMap, request, sessionInfo, getAddAuthorityCode(modelMap, request));
			}
			if(hasLock) {
				//检查输入
				List<ValidateError> errors = validateInput(pojo, openMode, modelMap, request);
				if(errors != null && !errors.isEmpty()) {
					for(ValidateError error : errors) {
						putValidateError(modelMap, error);
					}
				} else {
					//设置对象值
					setPojoProperties(pojo, modelMap, request);
					//保存对象
					savePojo(pojo, modelMap, request, resopnse);
					putMessage(modelMap, "保存成功!");
					//改为编辑模式
					openMode = OPEN_MODE_EDIT;
					//记录日志
					logOpe(pojo, modelMap, request, sessionInfo, opeType);
				}
			}
			//将对象设入页面
			modelMap.put("pojo", pojo);
			//将打开方式设入页面
			modelMap.put(HTTP_PARAM_FORM_OEPNMODE, openMode);
			//获取表单子页面并设入页面
			modelMap.put(HTTP_PARAM_FORM_SUBFORM, getSubForm(modelMap, request, (OPEN_MODE_EDIT.equals(openMode)) ? OperateType.OPE_TYPE_UPDATE : OperateType.OPE_TYPE_ADD));
			appendSaveAttribute(modelMap, request, resopnse);
			String modulePath = getModulePath(modelMap, request);
			modelMap.put(Constants.HTTP_PARAM_MODULE_PATH, modulePath);
			//设置菜单
			setMenuInfo(modelMap, request);
			appendLoadAttribute(modelMap, request, resopnse, openMode, pojo);
			return getExecuteSaveRet(modelMap, request, modulePath);
		} catch(Exception e) {
			return transactException(e, modelMap, request, resopnse, requestTyp);
		}
	}
	
	/**
	 * 设入校验错误信息
	 * @author lmiky
	 * @date 2013-5-9
	 * @param modelMap
	 * @param validateError
	 */
	@SuppressWarnings("unchecked")
	public void putValidateError(ModelMap modelMap, ValidateError validateError) {
		List<ValidateError> validateErrors = (List<ValidateError>)modelMap.get(VALIDATE_ERROR_INFO_KEY);
		if(validateErrors == null) {
			validateErrors = new ArrayList<ValidateError>();
			modelMap.put(VALIDATE_ERROR_INFO_KEY, validateErrors);
		}
		validateErrors.add(validateError);
	}
	
	/**
	 * 检查输入
	 * @author lmiky
	 * @date 2013-5-9
	 * @param pojo
	 * @param openMode
	 * @param modelMap
	 * @param request
	 * @return 如果输入没有异常,则回复空链表或者null
	 * @throws Exception
	 */
	public List<ValidateError> validateInput(T pojo, String openMode, ModelMap modelMap, HttpServletRequest request) throws Exception {
		return new ArrayList<ValidateError>();
	}
	
	/**
	 * 设置实体对象属性
	 * @author lmiky
	 * @date 2013-5-6
	 * @param pojo
	 * @param modelMap
	 * @param request
	 * @throws Exception
	 */
	protected void setPojoProperties(T pojo, ModelMap modelMap, HttpServletRequest request) throws Exception {
		BeanUtils.populate(pojo, request.getParameterMap());
	}
	
	/**
	 * 保存实体对象
	 * @author lmiky
	 * @date 2013-5-6
	 * @param pojo
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @throws ServiceException
	 */
	protected void savePojo(T pojo, ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse)  throws ServiceException {
		if(pojo.getId() == null) {	//新对象
			service.add(pojo);
		} else {
			service.update(pojo);
		}
	}
	
	/**
	 * 记录操作
	 * @author lmiky
	 * @date 2013-5-10
	 * @param pojo
	 * @param modelMap
	 * @param request
	 * @param sessionInfo
	 * @param opeType
	 * @throws Exception
	 */
	public void logOpe(T pojo, ModelMap modelMap, HttpServletRequest request, SessionInfo sessionInfo, String opeType) throws Exception {
		LoggerUtils.save(request, pojoClass.getName(), pojo.getId(), sessionInfo.getUserId(), sessionInfo.getUserName(),
				opeType, this.getClass().getName(),
				getPojoDescribe(pojo, modelMap, request), service);
	}
	
	/**
	 * 记录操作
	 * @author lmiky
	 * @date 2013-6-5
	 * @param className
	 * @param pojoId
	 * @param modelMap
	 * @param request
	 * @param sessionInfo
	 * @param opeType
	 * @param describe
	 * @throws Exception
	 */
	public void logOpe(String className, Long pojoId, ModelMap modelMap, HttpServletRequest request, SessionInfo sessionInfo, String opeType, String describe) throws Exception {
		LoggerUtils.save(request, className, pojoId, sessionInfo.getUserId(), sessionInfo.getUserName(),
				opeType, this.getClass().getName(),
				describe, service);
	}
	
	/**
	 * 获取记录说明字段，当记录内容不是subject、title或name时继承并修改本函数
	 * @author lmiky
	 * @date 2013-5-10
	 * @param pojo
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String[] getPojoDescField(T pojo, ModelMap modelMap, HttpServletRequest request) throws Exception {
		return new String[] {"name", "subject", "title"};
	}
	
	/**
	 * 获取记录说明
	 * @author lmiky
	 * @date 2013-5-10
	 * @param pojo
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected String getPojoDescribe(T pojo, ModelMap modelMap, HttpServletRequest request) throws Exception {
		String[] properties = getPojoDescField(pojo, modelMap, request);
		for(int i=0; i<properties.length; i++) {
			if(PropertyUtils.isReadable(pojo, properties[i])) {
				return "" + PropertyUtils.getProperty(pojo, properties[i]);
			}
		}
		return "";
	}
	
	/**
	 * 追加属性
	 * @author lmiky
	 * @date 2013-5-13
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @throws Exception
	 */
	protected void appendSaveAttribute(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse) throws Exception {
		
	}
	
	/**
	 * 获取保存查询结果
	 * @author
	 * @date 2013-4-19
	 * @param modelMap
	 * @param request
	 * @param modulePath
	 * @return
	 */
	protected String getExecuteSaveRet(ModelMap modelMap, HttpServletRequest request, String modulePath) {
		return getViewNamePrefix(modelMap, request, modulePath) + "Form";
	}
	
	/**
	 * 删除
	 * @author lmiky
	 * @date 2013-5-10
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String executeDelete(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse, Long id) throws Exception {
		return executeDelete(modelMap, request, resopnse, id, BaseWebController.REQUESTTYPE_NORMAL);
	}
	
	/**
	 * 删除
	 * @author lmiky
	 * @date 2013-5-10
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @param id
	 * @param requestTyp 请求方式
	 * @return
	 * @throws Exception
	 */
	public String executeDelete(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse, Long id, String requestTyp) throws Exception {
		try {
			//检查ID
			if(id == null) {
				putError(modelMap, "请选择要删除的记录！");
			} else {
				//判断是否有登陆
				SessionInfo sessionInfo = getSessionInfo(modelMap, request);
				//检查单点登陆
				checkSso(sessionInfo, modelMap, request);
				//判断权限
				checkAuthority(modelMap, request, sessionInfo, getDeleteAuthorityCode(modelMap, request));
				boolean hasLock = true;
				//检查记录锁
				String lockTargetId = getLockTargetId(request, id);
				try {
					lockService.lock(lockTargetId, sessionInfo.getUserId(), sessionInfo.getUserName());
				} catch(LockException le) {
					hasLock = false;
					if(!StringUtils.isBlank(le.getLockUserName())) {
						putMessage(modelMap, "删除失败, 该记录正被" + le.getLockUserName() + "编辑！");
					} else {
						throw le;
					}
				}
				if(hasLock) {
					T pojo = loadPojo(modelMap, request, id);
					//删除对象
					deletePojo(modelMap, request, pojo);
					//TODO 考虑要不要执行“去掉加锁缓存数据”
					putMessage(modelMap, "删除成功!");
					//记录日志
					logOpe(pojo, modelMap, request, sessionInfo, OperateType.OPE_TYPE_DELETE);
					//解锁
					lockService.unlock(lockTargetId, sessionInfo.getUserId());
				}
			}
			return getExecuteDeleteRet(modelMap, request, resopnse);
		} catch(Exception e) {
			return transactException(e, modelMap, request, resopnse, requestTyp);
		}
	}
	
	/**
	 * 获取删除结果
	 * @author lmiky
	 * @date 2013-10-23
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @return
	 * @throws Exception
	 */
	protected String getExecuteDeleteRet(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse) throws Exception {
		return executeList(modelMap, request, resopnse);
	}
	
	/**
	 * 删除对象
	 * @author lmiky
	 * @date 2013-5-10
	 * @param modelMap
	 * @param request
	 * @param pojo
	 * @throws ServiceException
	 */
	protected void deletePojo(ModelMap modelMap, HttpServletRequest request, T pojo)  throws ServiceException {
		service.delete(pojo);
	}
	
	/**
	 * 执行批量删除
	 * @author lmiky
	 * @date 2013-6-24
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public String executeBatchDelete(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse,  Long[] ids) throws Exception {
		return executeBatchDelete(modelMap, request, resopnse, ids, BaseWebController.REQUESTTYPE_NORMAL);
	}
	
	/**
	 * 执行批量删除
	 * @author lmiky
	 * @date 2013-6-24
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @param ids
	 * @param requestTyp 请求方式
	 * @return
	 * @throws Exception
	 */
	public String executeBatchDelete(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse,  Long[] ids, String requestTyp) throws Exception {
		try {
			//检查ID
			if(ids == null || ids.length == 0) {
				putError(modelMap, "请选择要删除的记录！");
			} else {
				//判断是否有登陆
				SessionInfo sessionInfo = getSessionInfo(modelMap, request);
				//检查单点登陆
				checkSso(sessionInfo, modelMap, request);
				//判断权限
				checkAuthority(modelMap, request, sessionInfo, getDeleteAuthorityCode(modelMap, request));
				boolean hasLock = true;
				//检查记录锁
				try {
					for(Long id : ids) {
						String lockTargetId = getLockTargetId(request, id);
						lockService.lock(lockTargetId, sessionInfo.getUserId(), sessionInfo.getUserName());
					}
				} catch(LockException le) {
					hasLock = false;
					if(!StringUtils.isBlank(le.getLockUserName())) {
						putMessage(modelMap, "删除失败, 有记录正被" + le.getLockUserName() + "编辑！");
					} else {
						throw le;
					}
				}
				if(hasLock) {
					List<T> pojos = new ArrayList<T>();
					for(Long id : ids) {
						pojos.add(service.find(pojoClass, id));
					}
					//删除对象
					batchDeletePojo(modelMap, request, pojos);
					//TODO 考虑要不要执行“去掉加锁缓存数据”
					putMessage(modelMap, "删除成功!");
					//记录日志
					for(T pojo : pojos) {
						logOpe(pojo, modelMap, request, sessionInfo, OperateType.OPE_TYPE_BATCHDELETE);
					}
				}
			}
			return executeList(modelMap, request, resopnse);
		} catch(Exception e) {
			return transactException(e, modelMap, request, resopnse, requestTyp);
		}
	}
	
	/**
	 * 批量删除
	 * @author lmiky
	 * @date 2013-6-24
	 * @param modelMap
	 * @param request
	 * @param pojos
	 * @throws ServiceException
	 */
	protected void batchDeletePojo(ModelMap modelMap, HttpServletRequest request, List<T> pojos)  throws ServiceException {
		service.delete(pojos);
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
