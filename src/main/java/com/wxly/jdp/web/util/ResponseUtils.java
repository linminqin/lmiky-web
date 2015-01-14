package com.wxly.jdp.web.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.lmiky.jdp.util.PropertiesUtils;

/**
 * response工具
 * @author lmiky
 * @date 2013-6-18
 */
public class ResponseUtils {
	public static final String DEFAULT_CODE = PropertiesUtils.getStringContextValue("system.encode");

	/**
	 * 初始化response头部
	 * @author lmiky
	 * @date 2013-6-18
	 * @param response
	 */
	private static void initResponseHeader(HttpServletResponse response) {
		// 编码
		response.setCharacterEncoding(DEFAULT_CODE);
		//内容格式
		response.setContentType("text/html;charset=" + DEFAULT_CODE);
		// 无缓存
		// Http 1.0 header
		response.setDateHeader("Expires", 1L);
		response.addHeader("Pragma", "no-cache");
		// Http 1.1 header
		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
	}
	
	/**
	 * 获取写入工具
	 * @author lmiky
	 * @date 2013-6-18
	 * @param response
	 * @return
	 * @throws IOException
	 */
	private static PrintWriter getWriter(HttpServletResponse response) throws IOException {
		return response.getWriter();
	}
	
	/**
	 * 写入内容
	 * @author lmiky
	 * @date 2013-6-18
	 * @param response
	 * @param content
	 * @throws IOException
	 */
	public static void write(HttpServletResponse response, String content) throws IOException {
		initResponseHeader(response);
		PrintWriter writer = getWriter(response);
		writer.write(content);
		writer.flush();
		writer.close();
	}
	
	/**
	 * 写入内容
	 * @author lmiky
	 * @date 2013-6-18
	 * @param response
	 * @param content
	 * @throws IOException
	 */
	public static void write(HttpServletResponse response, int content) throws IOException {
		initResponseHeader(response);
		PrintWriter writer = getWriter(response);
		writer.write(content);
		writer.flush();
		writer.close();
	}
	
	/**
	 * 写入内容
	 * @author lmiky
	 * @date 2013-6-18
	 * @param response
	 * @param content
	 * @throws IOException
	 */
	public static void write(HttpServletResponse response, char[] content) throws IOException {
		initResponseHeader(response);
		PrintWriter writer = getWriter(response);
		writer.write(content);
		writer.flush();
		writer.close();
	}
	
	/**
	 * 写入内容
	 * @author lmiky
	 * @date 2013-6-18
	 * @param response
	 * @param content
	 * @param off
	 * @param length
	 * @throws IOException
	 */
	public static void write(HttpServletResponse response, String content, int off, int length) throws IOException {
		initResponseHeader(response);
		PrintWriter writer = getWriter(response);
		writer.write(content, off, length);
		writer.flush();
		writer.close();
	}
	
	/**
	 * 写入内容
	 * @author lmiky
	 * @date 2013-6-18
	 * @param response
	 * @param content
	 * @param off
	 * @param length
	 * @throws IOException
	 */
	public static void write(HttpServletResponse response, char[] content, int off, int length) throws IOException {
		initResponseHeader(response);
		PrintWriter writer = getWriter(response);
		writer.write(content, off, length);
		writer.flush();
		writer.close();
	}
}
