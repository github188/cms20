package com.scsvision.database.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * GatherCovi
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 上午10:20:45
 */
public class GatherCovi implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1704650381347338353L;

	private String id;

	private Long deviceId;

	private Date recTime;

	private Double co;

	private Double vi;

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

	public Double getCo() {
		return co;
	}

	public void setCo(Double co) {
		this.co = co;
	}

	public Double getVi() {
		return vi;
	}

	public void setVi(Double vi) {
		this.vi = vi;
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
