package com.wxly.jdp.controllerview;

/**
 * 接过码
 * 1000以内的跟http结果码同步
 * 系统业务结果码，从1001开始
 * @author lmiky
 * @date 2014年11月3日 下午2:30:31
 */
public class BaseCode {
	
	//通用
	public static final int CODE_SUCCESS = 200;
	public static final int CODE_ERROR = 500;
	
	//账号
	public static final int CODE_ACCOUNT_REGISTERED = 1001;	//账号已被注册
	public static final int CODE_ACCOUNT_LOGINNAME_NOTEXISTS = 1005;	//账号不存在
	public static final int CODE_ACCOUNT_PWD_ERROR = 1006;	//密码错误
}
