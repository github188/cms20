/**
 * 
 */
package com.scsvision.cms.gather.service.vo;

/**
 * @author wangbinyu
 *
 */
public class GatherRsdVO {
	private String id;

	private String deviceId;

	private String recTime;

	private String roadTemp;

	private String rainVol;

	private String snowVol;

	private String roadSurface;

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

	public String getRoadTemp() {
		return roadTemp;
	}

	public void setRoadTemp(String roadTemp) {
		this.roadTemp = roadTemp;
	}

	public String getRainVol() {
		return rainVol;
	}

	public void setRainVol(String rainVol) {
		this.rainVol = rainVol;
	}

	public String getSnowVol() {
		return snowVol;
	}

	public void setSnowVol(String snowVol) {
		this.snowVol = snowVol;
	}

	public String getRoadSurface() {
		return roadSurface;
	}

	public void setRoadSurface(String roadSurface) {
		this.roadSurface = roadSurface;
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
