package com.lmiky.tiger.goods.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lmiky.jdp.database.model.Sort;
import com.lmiky.jdp.form.model.ValidateError;
import com.lmiky.jdp.form.util.ValidateUtils;
import com.lmiky.tiger.controller.TigerController;
import com.lmiky.tiger.goods.pojo.Goods;

/**
 * 商品
 * @author lmiky
 * @date 2013-4-15
 */
@Controller
@RequestMapping("/tiger/goods")
public class GoodsController extends TigerController<Goods> {

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.form.controller.FormController#getAddAuthorityCode(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected String getAddAuthorityCode(ModelMap modelMap, HttpServletRequest request) {
		return "tiger_goods_add";
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.form.controller.FormController#getModifyAuthorityCode(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected String getModifyAuthorityCode(ModelMap modelMap, HttpServletRequest request) {
		return "tiger_goods_modify";
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.form.controller.FormController#getDeleteAuthorityCode(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected String getDeleteAuthorityCode(ModelMap modelMap, HttpServletRequest request) {
		return "tiger_goods_delete";
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.base.controller.BaseController#getLoadAuthorityCode(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected String getLoadAuthorityCode(ModelMap modelMap, HttpServletRequest request) {
		return "tiger_goods_load";
	}
	
	/**
	 * @author lmiky
	 * @date 2013-4-17
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/list.shtml")
	public String list(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse) throws Exception {
		return executeList(modelMap, request, resopnse);
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.web.controller.BaseController#getDefaultSort(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected List<Sort> getDefaultSort(ModelMap modelMap, HttpServletRequest request) {
		List<Sort> sorts = super.getDefaultSort(modelMap, request);
		sorts.add(0, new Sort("createTime", Sort.SORT_TYPE_DESC, Goods.class));
		return sorts;
	}
	
	/**
	 * 加载
	 * @author lmiky
	 * @date 2013-4-18
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/load.shtml")
	public String load(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse, @RequestParam(value = "id", required = false) Long id) throws Exception {
		return executeLoad(modelMap, request, resopnse, id);
	}
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.form.controller.FormController#generateNewPojo(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Goods generateNewPojo(ModelMap modelMap, HttpServletRequest request) throws Exception {
		Goods goods = super.generateNewPojo(modelMap, request);
		Date date = new Date();
		goods.setCreateTime(date);
		goods.setUpdateTime(date);
		goods.setAudit(0);
		return goods;
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
	@RequestMapping("/save.shtml")
	public String save(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse, @RequestParam(value = "id", required = false) Long id) throws Exception {
		return executeSave(modelMap, request, resopnse, id);
	}

	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.form.controller.FormController#setPojoProperties(com.lmiky.jdp.database.pojo.BasePojo, org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void setPojoProperties(Goods pojo, ModelMap modelMap, HttpServletRequest request) throws Exception {
		super.setPojoProperties(pojo, modelMap, request);
		if(pojo.getId() != null) {	//修改更新时间
			pojo.setUpdateTime(new Date());
		}
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.form.controller.FormController#validateInput(com.lmiky.jdp.database.pojo.BasePojo, java.lang.String, org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	public List<ValidateError> validateInput(Goods pojo, String openMode, ModelMap modelMap, HttpServletRequest request) throws Exception {
		List<ValidateError> validateErrors = new ArrayList<ValidateError>();
		ValidateUtils.validateRequired(request, "title", "名称", validateErrors);
		if(ValidateUtils.validateRequired(request, "salePrice", "销售价", validateErrors)) {
			if(ValidateUtils.validateNumber(request, "salePrice", "销售价", validateErrors)) {
				ValidateUtils.validateNumberMin(request, "salePrice", "销售价", 0.0, validateErrors);
			}
		}
		if(ValidateUtils.validateRequired(request, "marketPrice", "市场价", validateErrors)) {
			if(ValidateUtils.validateNumber(request, "marketPrice", "市场价", validateErrors)) {
				ValidateUtils.validateNumberMin(request, "marketPrice", "市场价", 0.0, validateErrors);
			}
		}
		if(ValidateUtils.validateRequired(request, "couponDiscount", "优惠券折扣", validateErrors)) {
			if(ValidateUtils.validateNumber(request, "couponDiscount", "优惠券折扣", validateErrors)) {
				ValidateUtils.validateNumberMin(request, "couponDiscount", "优惠券折扣", 0.0, validateErrors);
			}
		}
		return validateErrors;
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
	@RequestMapping("/delete.shtml")
	public String delete(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse, @RequestParam(value = "id", required = false) Long id) throws Exception {
		return executeDelete(modelMap, request, resopnse, id);
	}
	
	/**
	 * 批量删除
	 * @author lmiky
	 * @date 2013-6-24
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/batchDelete.shtml")
	public String batchDelete(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse, @RequestParam(value = "batchDeleteId", required = false) Long[] ids) throws Exception {
		return executeBatchDelete(modelMap, request, resopnse, ids);
	}
}
