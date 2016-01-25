package com.scsvision.database.service.vo;

/**
 * @author sjt
 *
 */
public class OrganDeviceVO {
	private Long id;
	private String standardNumber;
	private String name;
	private String longitude;
	private String latitude;
	private String stakeNumber;
	private Integer type;
	private String channelNumber;
	private Long solarId;
	private String ip;
	private String port;
	private String manufacture;
	private String deviceModel;
	private Long parentId;
	private String path;
	private Integer visible;
	private String decode;
	
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

	public Integer getVisible() {
		return visible;
	}

	public void setVisible(Integer visible) {
		this.visible = visible;
	}

	public void setStakeNumber(String stakeNumber) {
		this.stakeNumber = stakeNumber;
	}

	public String getChannelNumber() {
		return channelNumber;
	}

	public void setChannelNumber(String channelNumber) {
		this.channelNumber = channelNumber;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getSolarId() {
		return solarId;
	}

	public void setSolarId(Long solarId) {
		this.solarId = solarId;
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

	public String getManufacture() {
		return manufacture;
	}

	public void setManufacture(String manufacture) {
		this.manufacture = manufacture;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDecode() {
		return decode;
	}

	public void setDecode(String decode) {
		this.decode = decode;
	}

}
