package com.scsvision.database.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * EventDevice
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 上午9:47:28
 */
@Entity
@Table(name = "em_event_device")
public class EventDevice implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6268221550346775105L;

	@Id
	private Long id;

	private String threshold;

	@Column(name = "current_value")
	private String currentValue;

	@Column(name = "device_id")
	private Long deviceId;

	@Column(name = "device_type")
	private Integer deviceType;

	@Column(name = "stake_number")
	private String stakeNumber;

	private Integer navigation;

	@Column(name = "organ_name")
	private String organName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getThreshold() {
		return threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}

	public String getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public String getStakeNumber() {
		return stakeNumber;
	}

	public void setStakeNumber(String stakeNumber) {
		this.stakeNumber = stakeNumber;
	}

	public Integer getNavigation() {
		return navigation;
	}

	public void setNavigation(Integer navigation) {
		this.navigation = navigation;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

}
