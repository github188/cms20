package com.scsvision.database.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * GatherRsd
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午1:51:40
 */
public class GatherRsd implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2596971572964112566L;

	private String id;

	private Long deviceId;

	private Date recTime;

	private Double roadTemp;

	private Double rainVol;

	private Double snowVol;

	private Integer roadSurface;

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

	public Double getRoadTemp() {
		return roadTemp;
	}

	public void setRoadTemp(Double roadTemp) {
		this.roadTemp = roadTemp;
	}

	public Double getRainVol() {
		return rainVol;
	}

	public void setRainVol(Double rainVol) {
		this.rainVol = rainVol;
	}

	public Double getSnowVol() {
		return snowVol;
	}

	public void setSnowVol(Double snowVol) {
		this.snowVol = snowVol;
	}

	public Integer getRoadSurface() {
		return roadSurface;
	}

	public void setRoadSurface(Integer roadSurface) {
		this.roadSurface = roadSurface;
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
