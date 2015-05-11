package com.lmiky.jdp.filemanager.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lmiky.jdp.controller.BaseWebController;
import com.lmiky.jdp.filemanager.util.FileUtils;
import com.lmiky.jdp.json.util.JsonUtils;
import com.lmiky.jdp.util.ResponseUtils;

/**
 * 文件管理
 * @author lmiky
 * @date 2014-7-27
 */
@Controller
@RequestMapping("/file")
public class FileController extends BaseWebController {
	//文件路径
	public static final String KEY_NAME_FILE_PATH = "filePath";
	//是否成功键名
	public static final String RETURN_KEY_NAME_CODE = "code";
	//提示
	public static final String RETURN_KEY_NAME_MESSAGE = "message";
	//返回数据
	public static final String RETURN_KEY_NAME_DATA = "data";
	//文件路径
	public static final String RETURN_KEY_NAME_DATA_FILE_PATH = "filePath";
	
	//结果码
	public static final int VALUE_NAME_ERROR_SUCCESS = 200;
	public static final int VALUE_NAME_ERROR_ERROR = 500;

	/**
	 * 上传文件， 返回格式为{code: code, message: message, data: {filePath: filePath}}
	 * @author lmiky
	 * @date 2014-7-27
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/upload.shtml")
	public void upload(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String filePath = FileUtils.upload(modelMap, request, response, request.getParameter(KEY_NAME_FILE_PATH));
			result.put(RETURN_KEY_NAME_CODE, VALUE_NAME_ERROR_SUCCESS);
			result.put(RETURN_KEY_NAME_MESSAGE, "上传成功！");
			Map<String, Object> data = new HashMap<String, Object>();
			data.put(RETURN_KEY_NAME_DATA_FILE_PATH, filePath);
			result.put(RETURN_KEY_NAME_DATA, data);
		} catch(Exception e) {
			result.put(RETURN_KEY_NAME_CODE, VALUE_NAME_ERROR_ERROR);
			result.put(RETURN_KEY_NAME_MESSAGE, e.getMessage());
		}
		ResponseUtils.write(response, JsonUtils.toJson(result));
	}
	
	
	
}
