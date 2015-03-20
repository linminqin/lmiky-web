package com.lmiky.capture.task.pojo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lmiky.capture.resource.pojo.CaptureResource;
import com.lmiky.jdp.database.pojo.BasePojo;

/**
 * 抓取人物-资源中间表
 * @author lmiky
 * @date 2013-11-12
 */
@Entity
@Table(name="t_capture_task_resource")
public class CaptureTaskToResource extends BasePojo {
	private static final long serialVersionUID = -264636793559917662L;
	private CaptureTask captureTask;
	private CaptureResource captureResource;
	
	/**
	 * @return the captureTask
	 */
	@ManyToOne(fetch=FetchType.LAZY)  
    @JoinColumn(name="taskId", updatable = false) 
	public CaptureTask getCaptureTask() {
		return captureTask;
	}
	/**
	 * @param captureTask the captureTask to set
	 */
	public void setCaptureTask(CaptureTask captureTask) {
		this.captureTask = captureTask;
	}
	/**
	 * @return the captureResource
	 */
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="resourceId")
	public CaptureResource getCaptureResource() {
		return captureResource;
	}
	/**
	 * @param captureResource the captureResource to set
	 */
	public void setCaptureResource(CaptureResource captureResource) {
		this.captureResource = captureResource;
	}
}
