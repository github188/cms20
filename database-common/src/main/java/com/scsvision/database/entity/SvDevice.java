package com.scsvision.database.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * SvDevice
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015年9月10日 下午2:44:49
 */
@Entity
@Table(name = "common_sv_device")
@TableGenerator(name = "svDeviceGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", pkColumnValue = "sv_device_id", initialValue = 10000, allocationSize = 1)
public class SvDevice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2210634714411758770L;
	@Id
	@GeneratedValue(generator = "svDeviceGen", strategy = GenerationType.TABLE)
	private Long id;

	@Column(name = "standard_number")
	private String standardNumber;

	private String name;

	private String type;

	@Column(name = "sub_type")
	private String subType;

	@Column(name = "parent_id")
	private Long parentId;

	private String location;

	@Column(name = "organ_id")
	private Long organId;

	@Column(name = "pts_id")
	private Long ptsId;

	@Column(name = "crs_id")
	private Long crsId;

	@Column(name = "manufacturer_id")
	private Long manufacturerId;

	@Column(name = "solar_battery_id")
	private Long solarBatteryId;

	@Column(name = "gps_id")
	private Long gpsId;

	@Column(name = "channel_amount")
	private Integer channelAmount;

	@Column(name = "channel_number")
	private String channelNumber;

	private String ip;

	private String port;

	@Column(name = "create_time")
	private Long createTime;

	@Column(name = "path_name")
	private String pathName;

	@Column(name = "is_local")
	private Short isLocal;

	private String longitude;

	private String latitude;

	@Column(name = "stake_number")
	private String stakeNumber;

	private String navigation;

	@Column(name = "store_type")
	private Integer storeType;

	@Column(name = "local_store_plan")
	private String localStorePlan;

	@Column(name = "center_store_plan")
	private String centerStorePlan;

	private String extend;

	private String user;

	private String password;

	private String owner;

	@Column(name = "civil_code")
	private String civilCode;

	private String block;

	@Column(name = "cert_num")
	private String certNum;

	private String certifiable;

	@Column(name = "err_code")
	private String errCode;

	@Column(name = "end_time")
	private Long endTime;

	private String secrecy;

	@Column(name = "device_model")
	private String deviceModel;

	@Column(name = "float_stake")
	private Float floatStake;

	public SvDevice() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStandardNumber() {
		return this.standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
	}

	public String getName() {
		return this.name;
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

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getParentId() {
		return parentId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long getOrganId() {
		return organId;
	}

	public void setOrganId(Long organId) {
		this.organId = organId;
	}

	public Long getPtsId() {
		return ptsId;
	}

	public void setPtsId(Long ptsId) {
		this.ptsId = ptsId;
	}

	public Long getCrsId() {
		return crsId;
	}

	public void setCrsId(Long crsId) {
		this.crsId = crsId;
	}

	public Long getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerId(Long manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	public Long getSolarBatteryId() {
		return solarBatteryId;
	}

	public void setSolarBatteryId(Long solarBatteryId) {
		this.solarBatteryId = solarBatteryId;
	}

	public Long getGpsId() {
		return gpsId;
	}

	public void setGpsId(Long gpsId) {
		this.gpsId = gpsId;
	}

	public Integer getChannelAmount() {
		return channelAmount;
	}

	public void setChannelAmount(Integer channelAmount) {
		this.channelAmount = channelAmount;
	}

	public String getChannelNumber() {
		return channelNumber;
	}

	public void setChannelNumber(String channelNumber) {
		this.channelNumber = channelNumber;
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

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	public Short getIsLocal() {
		return isLocal;
	}

	public void setIsLocal(Short isLocal) {
		this.isLocal = isLocal;
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

	public String getNavigation() {
		return navigation;
	}

	public void setNavigation(String navigation) {
		this.navigation = navigation;
	}

	public Integer getStoreType() {
		return storeType;
	}

	public void setStoreType(Integer storeType) {
		this.storeType = storeType;
	}

	public String getLocalStorePlan() {
		return localStorePlan;
	}

	public void setLocalStorePlan(String localStorePlan) {
		this.localStorePlan = localStorePlan;
	}

	public String getCenterStorePlan() {
		return centerStorePlan;
	}

	public void setCenterStorePlan(String centerStorePlan) {
		this.centerStorePlan = centerStorePlan;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getCivilCode() {
		return civilCode;
	}

	public void setCivilCode(String civilCode) {
		this.civilCode = civilCode;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getCertNum() {
		return certNum;
	}

	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}

	public String getCertifiable() {
		return certifiable;
	}

	public void setCertifiable(String certifiable) {
		this.certifiable = certifiable;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public String getSecrecy() {
		return secrecy;
	}

	public void setSecrecy(String secrecy) {
		this.secrecy = secrecy;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public Float getFloatStake() {
		return floatStake;
	}

	public void setFloatStake(Float floatStake) {
		this.floatStake = floatStake;
	}

}
