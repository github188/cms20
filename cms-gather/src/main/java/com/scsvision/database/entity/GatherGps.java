package com.scsvision.database.entity;

import java.io.Serializable;
import java.sql.Date;

/**
 * GatherGps
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 上午10:25:10
 */
public class GatherGps implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5819727216234273142L;

	private String id;

	private Long deviceId;

	private Date recTime;

	private String longitude;

	private String latitude;

	private String bearing;

	private Double speed;

	private String altitude;

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

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getBearing() {
		return bearing;
	}

	public void setBearing(String bearing) {
		this.bearing = bearing;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public String getAltitude() {
		return altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
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
