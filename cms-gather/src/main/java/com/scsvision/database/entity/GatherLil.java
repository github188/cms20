package com.scsvision.database.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * GatherLil
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 上午10:30:28
 */
public class GatherLil implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8387975079352865091L;

	private String id;

	private Long deviceId;

	private Date recTime;

	private Integer backArrowStatus;

	private Integer backXStatus;

	private Integer frontArrowStatus;

	private Integer frontXStatus;

	private Integer status;

	private Integer commStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public Date getRecTime() {
		return recTime;
	}

	public void setRecTime(Date recTime) {
		this.recTime = recTime;
	}

	public Integer getBackArrowStatus() {
		return backArrowStatus;
	}

	public void setBackArrowStatus(Integer backArrowStatus) {
		this.backArrowStatus = backArrowStatus;
	}

	public Integer getBackXStatus() {
		return backXStatus;
	}

	public void setBackXStatus(Integer backXStatus) {
		this.backXStatus = backXStatus;
	}

	public Integer getFrontArrowStatus() {
		return frontArrowStatus;
	}

	public void setFrontArrowStatus(Integer frontArrowStatus) {
		this.frontArrowStatus = frontArrowStatus;
	}

	public Integer getFrontXStatus() {
		return frontXStatus;
	}

	public void setFrontXStatus(Integer frontXStatus) {
		this.frontXStatus = frontXStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCommStatus() {
		return commStatus;
	}

	public void setCommStatus(Integer commStatus) {
		this.commStatus = commStatus;
	}

}
