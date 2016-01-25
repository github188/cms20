package com.scsvision.database.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * GatherLoli
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午2:01:51
 */
public class GatherLoli implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3255085107289258474L;

	private String id;

	private Long deviceId;

	private Date recTime;

	private Double lo;

	private Double li;

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

	public Double getLo() {
		return lo;
	}

	public void setLo(Double lo) {
		this.lo = lo;
	}

	public Double getLi() {
		return li;
	}

	public void setLi(Double li) {
		this.li = li;
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
