package com.scsvision.database.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * TmDevice
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午2:14:01
 */
@Entity
@Table(name = "common_tm_device")
@TableGenerator(name = "tmDeviceGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", pkColumnValue = "tm_device_id", initialValue = 10000, allocationSize = 1)
public class TmDevice implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1434826308308780799L;

	@Id
	@GeneratedValue(generator = "tmDeviceGen", strategy = GenerationType.TABLE)
	private Long id;

	@Column(name = "standard_number")
	private String standardNumber;

	private String name;

	private String type;

	@Column(name = "sub_type")
	private String subType;

	@Column(name = "parent_id")
	private Long parentId;

	@Column(name = "organ_id")
	private Long organId;

	private String location;

	private String longitude;

	private String latitude;

	@Column(name = "stake_number")
	private String stakeNumber;

	@Column(name = "manufacturer_id")
	private Long manufacturerId;

	@Column(name = "das_id")
	private Long dasId;

	private String ip;

	private String port;

	private String navigation;

	@Column(name = "create_time")
	private Long createTime;

	private String threshold1;

	private String threshold2;

	private String threshold3;

	private String threshold4;

	private String extend;

	@Column(name = "slaveid")
	private String slaveId;

	@Column(name = "addrs_for_set")
	private String addrsForSet;

	@Column(name = "addrs_for_get")
	private String addrsForGet;

	private String user;

	private String password;

	@Column(name = "channel_number")
	private String channelNumber;

	@Column(name = "gather_interval")
	private Integer gatherInterval;

	@Column(name = "config_value")
	@Lob
	private String configValue;

	@Column(name = "alarm_flag")
	private Short alarmFlag;

	@Column(name = "float_stake")
	private Float floatStake;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getOrganId() {
		return organId;
	}

	public void setOrganId(Long organId) {
		this.organId = organId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public String getStakeNumber() {
		return stakeNumber;
	}

	public void setStakeNumber(String stakeNumber) {
		this.stakeNumber = stakeNumber;
	}

	public Long getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerId(Long manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	public Long getDasId() {
		return dasId;
	}

	public void setDasId(Long dasId) {
		this.dasId = dasId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getNavigation() {
		return navigation;
	}

	public void setNavigation(String navigation) {
		this.navigation = navigation;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getThreshold1() {
		return threshold1;
	}

	public void setThreshold1(String threshold1) {
		this.threshold1 = threshold1;
	}

	public String getThreshold2() {
		return threshold2;
	}

	public void setThreshold2(String threshold2) {
		this.threshold2 = threshold2;
	}

	public String getThreshold3() {
		return threshold3;
	}

	public void setThreshold3(String threshold3) {
		this.threshold3 = threshold3;
	}

	public String getThreshold4() {
		return threshold4;
	}

	public void setThreshold4(String threshold4) {
		this.threshold4 = threshold4;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public String getSlaveId() {
		return slaveId;
	}

	public void setSlaveId(String slaveId) {
		this.slaveId = slaveId;
	}

	public String getAddrsForSet() {
		return addrsForSet;
	}

	public void setAddrsForSet(String addrsForSet) {
		this.addrsForSet = addrsForSet;
	}

	public String getAddrsForGet() {
		return addrsForGet;
	}

	public void setAddrsForGet(String addrsForGet) {
		this.addrsForGet = addrsForGet;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getChannelNumber() {
		return channelNumber;
	}

	public void setChannelNumber(String channelNumber) {
		this.channelNumber = channelNumber;
	}

	public Integer getGatherInterval() {
		return gatherInterval;
	}

	public void setGatherInterval(Integer gatherInterval) {
		this.gatherInterval = gatherInterval;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	public Short getAlarmFlag() {
		return alarmFlag;
	}

	public void setAlarmFlag(Short alarmFlag) {
		this.alarmFlag = alarmFlag;
	}

	public Float getFloatStake() {
		return floatStake;
	}

	public void setFloatStake(Float floatStake) {
		this.floatStake = floatStake;
	}

}
