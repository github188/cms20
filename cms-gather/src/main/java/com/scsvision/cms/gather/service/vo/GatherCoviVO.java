/**
 * 
 */
package com.scsvision.cms.gather.service.vo;

/**
 * @author wangbinyu
 *
 */
public class GatherCoviVO {
	private String id;

	private String deviceId;

	private String recTime;

	private String co;

	private String vi;

	private String status;

	private String commStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getRecTime() {
		return recTime;
	}

	public void setRecTime(String recTime) {
		this.recTime = recTime;
	}

	public String getCo() {
		return co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public String getVi() {
		return vi;
	}

	public void setVi(String vi) {
		this.vi = vi;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCommStatus() {
		return commStatus;
	}

	public void setCommStatus(String commStatus) {
		this.commStatus = commStatus;
	}
}
