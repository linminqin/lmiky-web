package com.lmiky.jdp.filemanager.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lmiky.jdp.controller.BaseController;
import com.lmiky.jdp.filemanager.util.FileUtils;
import com.lmiky.jdp.json.util.JsonUtils;
import com.lmiky.jdp.util.PropertiesUtils;
import com.lmiky.jdp.util.ResponseUtils;

/**
 * kindEditor文件管理
 * @author lmiky
 * @date 2014-1-21
 */
@Controller
@RequestMapping("/kindEditorFile")
public class KindEditorFileController extends BaseController {
	//文件字段名称
	public static final String PARAM_FIELDNAME_FILE = "imgFile";
	//是否成功键名
	public static final String KEY_NAME_ERROR = "error";
	//提示
	public static final String KEY_NAME_MESSAGE = "message";
	//文件路径
	public static final String KEY_NAME_FILE_URL = "url";
	
	//结果码
	public static final int VALUE_NAME_ERROR_SUCCESS = 0;
	public static final int VALUE_NAME_ERROR_ERROR = 1;

	/**
	 * 上传文件
	 * imgFile: 文件form名称
	 * dir: 上传类型，分别为image、flash、media、file
	 * @author lmiky
	 * @date 2014-1-21
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/upload.shtml")
	public void upload(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String filePath = FileUtils.upload(modelMap, request, response, PARAM_FIELDNAME_FILE, PropertiesUtils.getStringContextValue("system.file.path"));
			result.put(KEY_NAME_ERROR, VALUE_NAME_ERROR_SUCCESS);
			result.put(KEY_NAME_MESSAGE, "上传成功！");
			result.put(KEY_NAME_FILE_URL, request.getContextPath() + filePath);
		} catch(Exception e) {
			result.put(KEY_NAME_ERROR, VALUE_NAME_ERROR_ERROR);
			result.put(KEY_NAME_MESSAGE, e.getMessage());
			result.put(KEY_NAME_FILE_URL, "");
		}
		ResponseUtils.write(response, JsonUtils.toJson(result));
	}
	
	
	
}
