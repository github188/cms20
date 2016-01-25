package com.scsvision.database.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * EventManual
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 上午9:50:12
 */
@Entity
@Table(name = "em_event_manual")
public class EventManual implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1786271108248104697L;

	@Id
	private Long id;

	@Column(name = "road_type")
	private Integer roadType;

	@Column(name = "roadName")
	private String roadName;

	@Column(name = "road_code")
	private String roadCode;

	@Column(name = "begin_stake")
	private String beginStake;

	@Column(name = "end_stake")
	private String endStake;

	private Integer navigation;

	private String phone;

	@Column(name = "impact_province")
	private String impactProvince;

	@Column(name = "estimates_recover_time")
	private Long estimatesRecoverTime;

	private String description;

	@Column(name = "hurt_number")
	private Integer hurtNumber;

	@Column(name = "death_number")
	private Integer deathNumber;

	@Column(name = "delay_human_number")
	private Integer delayHumanNumber;

	@Column(name = "delay_car_number")
	private Integer delayCarNumber;

	@Column(name = "destroy_car_number")
	private Integer destroyCarNumber;

	public Integer getDestroyCarNumber() {
		return destroyCarNumber;
	}

	public void setDestroyCarNumber(Integer destroyCarNumber) {
		this.destroyCarNumber = destroyCarNumber;
	}

	@Column(name = "crowd_meter")
	private Integer crowdMeter;

	@Column(name = "loss_amount")
	private Double lossAmount;

	private String administration;

	@Column(name = "manager_unit")
	private String managerUnit;

	@Column(name = "is_fire")
	private Short isFire;

	@Column(name = "lane_number")
	private Short laneNumber;

	private String longitude;

	private String latitude;

	private String note;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getRoadType() {
		return roadType;
	}

	public void setRoadType(Integer roadType) {
		this.roadType = roadType;
	}

	public String getRoadName() {
		return roadName;
	}

	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}

	public String getRoadCode() {
		return roadCode;
	}

	public void setRoadCode(String roadCode) {
		this.roadCode = roadCode;
	}

	public String getBeginStake() {
		return beginStake;
	}

	public void setBeginStake(String beginStake) {
		this.beginStake = beginStake;
	}

	public String getEndStake() {
		return endStake;
	}

	public void setEndStake(String endStake) {
		this.endStake = endStake;
	}

	public Integer getNavigation() {
		return navigation;
	}

	public void setNavigation(Integer navigation) {
		this.navigation = navigation;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getImpactProvince() {
		return impactProvince;
	}

	public void setImpactProvince(String impactProvince) {
		this.impactProvince = impactProvince;
	}

	public Long getEstimatesRecoverTime() {
		return estimatesRecoverTime;
	}

	public void setEstimatesRecoverTime(Long estimatesRecoverTime) {
		this.estimatesRecoverTime = estimatesRecoverTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getHurtNumber() {
		return hurtNumber;
	}

	public void setHurtNumber(Integer hurtNumber) {
		this.hurtNumber = hurtNumber;
	}

	public Integer getDeathNumber() {
		return deathNumber;
	}

	public void setDeathNumber(Integer deathNumber) {
		this.deathNumber = deathNumber;
	}

	public Integer getDelayHumanNumber() {
		return delayHumanNumber;
	}

	public void setDelayHumanNumber(Integer delayHumanNumber) {
		this.delayHumanNumber = delayHumanNumber;
	}

	public Integer getDelayCarNumber() {
		return delayCarNumber;
	}

	public void setDelayCarNumber(Integer delayCarNumber) {
		this.delayCarNumber = delayCarNumber;
	}

	public Integer getCrowdMeter() {
		return crowdMeter;
	}

	public void setCrowdMeter(Integer crowdMeter) {
		this.crowdMeter = crowdMeter;
	}

	public Double getLossAmount() {
		return lossAmount;
	}

	public void setLossAmount(Double lossAmount) {
		this.lossAmount = lossAmount;
	}

	public String getAdministration() {
		return administration;
	}

	public void setAdministration(String administration) {
		this.administration = administration;
	}

	public String getManagerUnit() {
		return managerUnit;
	}

	public void setManagerUnit(String managerUnit) {
		this.managerUnit = managerUnit;
	}

	public Short getIsFire() {
		return isFire;
	}

	public void setIsFire(Short isFire) {
		this.isFire = isFire;
	}

	public Short getLaneNumber() {
		return laneNumber;
	}

	public void setLaneNumber(Short laneNumber) {
		this.laneNumber = laneNumber;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
