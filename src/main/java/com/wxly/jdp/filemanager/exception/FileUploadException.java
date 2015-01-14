package com.wxly.jdp.filemanager.exception;

import com.lmiky.jdp.exception.BaseCodeException;

/**
 * 文件上传异常
 * @author lmiky
 * @date 2014-1-27
 */
public class FileUploadException extends BaseCodeException {
	private static final long serialVersionUID = -3064029072394779658L;
	public static final int CODE_FILE_EMPTY = 10;
	public static final int CODE_FORMAT_ERROR = 11;
	
	public FileUploadException() {
		super();
	}
	
	public FileUploadException(String message) {
		super(message);
	}
	
	public FileUploadException(int code) {
		super(code);
	}
	
	public FileUploadException(String message, int code) {
		super(message, code);
	}
}
