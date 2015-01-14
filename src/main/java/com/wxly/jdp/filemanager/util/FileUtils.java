package com.wxly.jdp.filemanager.util;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.util.WebUtils;

import com.lmiky.jdp.util.PropertiesUtils;
import com.lmiky.jdp.util.UUIDGenerator;
import com.wxly.jdp.constants.Constants;
import com.wxly.jdp.filemanager.exception.FileUploadException;

/**
 * 文件工具
 * @author lmiky
 * @date 2014-1-27
 */
public class FileUtils {
	//参数名
	public static final String PARAMNAME_FILEEXTENSION = "fileExtension";	//允许的文件格式，多个格式之间以“,”分隔
	public static final String PARAMNAME_FILEPATH = "filePath";	//文件保存路径
	public static final String PARAMNAME_FILE = "file";	//文件字段

	/**
	 * 上传文件
	 * @author lmiky
	 * @date 2014-1-27
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param formFieldName 表单中文件字段名称
	 * @param savePath 保存路径，相对路径
	 * @throws FileUploadException
	 */
	public static String upload(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, String formFieldName, String savePath) throws FileUploadException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile multipartFile = (CommonsMultipartFile) multipartRequest.getFile(formFieldName);
		if (multipartFile == null) {
			throw new FileUploadException("请选择上传文件！", FileUploadException.CODE_FILE_EMPTY);
		}
		String oldFileName = multipartFile.getOriginalFilename();
		if (StringUtils.isBlank(oldFileName)) {
			throw new FileUploadException("请选择上传文件！", FileUploadException.CODE_FILE_EMPTY);
		}
		String filePrefix = UUIDGenerator.generateString();
		String fileSuffix = StringUtils.substringAfterLast(oldFileName, ".");
		String newFileName = filePrefix + "." + fileSuffix;
		// 检查文件格式
		String fileExtension = request.getParameter(PARAMNAME_FILEEXTENSION); 
		if (!StringUtils.isBlank(fileExtension)) {
			if (("," + fileExtension.toLowerCase() + ",").indexOf("," + fileSuffix.toLowerCase() + ",") == -1) {
				throw new FileUploadException("文件格式错误！", FileUploadException.CODE_FORMAT_ERROR);
			}
		}
		if(StringUtils.isBlank(savePath)) {
			savePath = PropertiesUtils.getStringContextValue(Constants.SYSTEM_FILE_PATH);
		}
		InputStream in = null;
		OutputStream out = null;
		try {
			String realPath = WebUtils.getRealPath(request.getSession().getServletContext(), savePath);
			File newFile = new File(realPath + "/" + newFileName);
			in = multipartFile.getInputStream();
			out = org.apache.commons.io.FileUtils.openOutputStream(newFile);
			IOUtils.copy(in, out);
			in.close();
			out.close();
			return savePath + "/" + newFileName;
		} catch (Exception e) {
			throw new FileUploadException(e.getMessage());
		} finally {
			if(in != null) {
				IOUtils.closeQuietly(in);
			}
			if(out != null) {
				IOUtils.closeQuietly(out);
			}
		}
	}

	/**
	 * 上传文件
	 * @author lmiky
	 * @date 2014-1-27
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws FileUploadException
	 */
	public static String upload(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws FileUploadException {
		//保存路径
		String savePath = request.getParameter(PARAMNAME_FILEPATH);
		//默认路径
        if(StringUtils.isBlank(savePath)) {
        	savePath = PropertiesUtils.getStringContextValue(Constants.SYSTEM_FILE_PATH);
        }
		return upload(modelMap, request, response, savePath);
	}
	
	/**
	 * 上传文件
	 * @author lmiky
	 * @date 2014-6-4
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param savePath
	 * @return
	 * @throws FileUploadException
	 */
	public static String upload(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, String savePath) throws FileUploadException {
		return upload(modelMap, request, response, PARAMNAME_FILE, savePath);
	}
}
