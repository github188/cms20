package com.scsvision.database.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * GatherCd
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 上午10:14:47
 */
public class GatherCd implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5458903884873001771L;

	private String id;

	private Long deviceId;

	private Date recTime;

	private Integer type;

	private String workState;

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getWorkState() {
		return workState;
	}

	public void setWorkState(String workState) {
		this.workState = workState;
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
