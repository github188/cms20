package com.scsvision.database.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * GatherTsl
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午1:55:32
 */
public class GatherTsl implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5131594638737694603L;

	private String id;

	private Long deviceId;

	private Date recTime;

	private Integer greenStatus;

	private Integer yellowStatus;

	private Integer redStatus;

	private Integer turnStatus;

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

	public Integer getGreenStatus() {
		return greenStatus;
	}

	public void setGreenStatus(Integer greenStatus) {
		this.greenStatus = greenStatus;
	}

	public Integer getYellowStatus() {
		return yellowStatus;
	}

	public void setYellowStatus(Integer yellowStatus) {
		this.yellowStatus = yellowStatus;
	}

	public Integer getRedStatus() {
		return redStatus;
	}

	public void setRedStatus(Integer redStatus) {
		this.redStatus = redStatus;
	}

	public Integer getTurnStatus() {
		return turnStatus;
	}

	public void setTurnStatus(Integer turnStatus) {
		this.turnStatus = turnStatus;
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
