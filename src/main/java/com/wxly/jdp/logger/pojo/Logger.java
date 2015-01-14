package com.wxly.jdp.logger.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lmiky.jdp.database.pojo.BasePojo;

/**
 * 日志
 * @author lmiky
 * @date 2013-5-10
 */
@Entity
@Table(name="t_logger")
public class Logger extends BasePojo {
	private static final long serialVersionUID = 3769782013004612938L;
	private String pojoName;
	private Long pojoId;
	private Long userId;
	private String userName;
	private Date logTime;
	private String opeType;
	private String opeClassName;
	private String logDesc;
	private String ip;
	
	/**
	 * @return the pojoName
	 */
	@Column(name="pojoName")
	public String getPojoName() {
		return pojoName;
	}
	/**
	 * @param pojoName the pojoName to set
	 */
	public void setPojoName(String pojoName) {
		this.pojoName = pojoName;
	}
	/**
	 * @return the pojoId
	 */
	@Column(name="pojoId")
	public Long getPojoId() {
		return pojoId;
	}
	/**
	 * @param pojoId the pojoId to set
	 */
	public void setPojoId(Long pojoId) {
		this.pojoId = pojoId;
	}
	/**
	 * @return the userId
	 */
	@Column(name="userId")
	public Long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * @return the userName
	 */
	@Column(name="userName")
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the logTime
	 */
	@Column(name="logTime")
	public Date getLogTime() {
		return logTime;
	}
	/**
	 * @param logTime the logTime to set
	 */
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	/**
	 * @return the opeType
	 */
	@Column(name="opeType")
	public String getOpeType() {
		return opeType;
	}
	/**
	 * @param opeType the opeType to set
	 */
	public void setOpeType(String opeType) {
		this.opeType = opeType;
	}
	/**
	 * @return the opeClassName
	 */
	@Column(name="opeClassName")
	public String getOpeClassName() {
		return opeClassName;
	}
	/**
	 * @param opeClassName the opeClassName to set
	 */
	public void setOpeClassName(String opeClassName) {
		this.opeClassName = opeClassName;
	}
	/**
	 * @return the logDesc
	 */
	@Column(name="logDesc")
	public String getLogDesc() {
		return logDesc;
	}
	/**
	 * @param logDesc the logDesc to set
	 */
	public void setLogDesc(String logDesc) {
		this.logDesc = logDesc;
	}
	/**
	 * @return the ip
	 */
	@Column(name="ip")
	public String getIp() {
		return ip;
	}
	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
}
