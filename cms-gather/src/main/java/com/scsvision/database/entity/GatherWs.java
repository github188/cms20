package com.scsvision.database.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * GatherWs
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午2:04:01
 */
public class GatherWs implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3926824640933186214L;

	private String id;

	private Long deviceId;

	private Date recTime;

	private Integer direction;

	private Double speed;

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

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
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
