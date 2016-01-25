package com.scsvision.database.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * GatherWst
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午1:58:52
 */
public class GatherWst implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5766213344509045160L;

	private String id;

	private Long deviceId;

	private Date recTime;

	private Double vi;

	private Double ws;

	private String direction;

	private Double airTemp;

	private Double roadTemp;

	private Double humi;

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

	public Double getVi() {
		return vi;
	}

	public void setVi(Double vi) {
		this.vi = vi;
	}

	public Double getWs() {
		return ws;
	}

	public void setWs(Double ws) {
		this.ws = ws;
	}

	public Double getAirTemp() {
		return airTemp;
	}

	public void setAirTemp(Double airTemp) {
		this.airTemp = airTemp;
	}

	public Double getRoadTemp() {
		return roadTemp;
	}

	public void setRoadTemp(Double roadTemp) {
		this.roadTemp = roadTemp;
	}

	public Double getHumi() {
		return humi;
	}

	public void setHumi(Double humi) {
		this.humi = humi;
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

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

}
