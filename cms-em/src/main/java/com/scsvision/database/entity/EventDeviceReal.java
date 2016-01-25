package com.scsvision.database.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * EventDeviceReal
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午4:46:14
 */
@Entity
@Table(name = "em_event_device_real")
public class EventDeviceReal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1130360383410173164L;

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

	private String navigation;

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

	public String getNavigation() {
		return navigation;
	}

	public void setNavigation(String navigation) {
		this.navigation = navigation;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

}
