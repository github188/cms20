/**
 * 
 */
package com.scsvision.cms.gather.service.vo;

/**
 * @author wangbinyu
 *
 */
public class GatherTslVO {
	private String id;

	private String deviceId;

	private String recTime;

	private String greenStatus;

	private String yellowStatus;

	private String redStatus;

	private String turnStatus;

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

	public String getGreenStatus() {
		return greenStatus;
	}

	public void setGreenStatus(String greenStatus) {
		this.greenStatus = greenStatus;
	}

	public String getYellowStatus() {
		return yellowStatus;
	}

	public void setYellowStatus(String yellowStatus) {
		this.yellowStatus = yellowStatus;
	}

	public String getRedStatus() {
		return redStatus;
	}

	public void setRedStatus(String redStatus) {
		this.redStatus = redStatus;
	}

	public String getTurnStatus() {
		return turnStatus;
	}

	public void setTurnStatus(String turnStatus) {
		this.turnStatus = turnStatus;
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
