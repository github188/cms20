package com.scsvision.database.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * GatherNod
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午1:49:28
 */
public class GatherNod implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4110163296524929990L;

	private String id;

	private Long deviceId;

	private Date recTime;

	private Double no;

	private Double no2;

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

	public Double getNo() {
		return no;
	}

	public void setNo(Double no) {
		this.no = no;
	}

	public Double getNo2() {
		return no2;
	}

	public void setNo2(Double no2) {
		this.no2 = no2;
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
