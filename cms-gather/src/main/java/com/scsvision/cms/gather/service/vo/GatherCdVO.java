/**
 * 
 */
package com.scsvision.cms.gather.service.vo;

/**
 * @author wangbinyu
 *
 */
public class GatherCdVO {
	private String id;

	private String deviceId;

	private String recTime;

	private String type;

	private String workState;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWorkState() {
		return workState;
	}

	public void setWorkState(String workState) {
		this.workState = workState;
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
