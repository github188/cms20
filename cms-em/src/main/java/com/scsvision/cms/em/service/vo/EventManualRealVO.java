package com.scsvision.cms.em.service.vo;

/**
 * @author sjt
 *         <p />
 *         Create at 2015年  上午9:37:43
 */

public class EventManualRealVO {
	private Long id;
	private String roadType;
	private String roadName;
	private String roadCode;
	private String beginStake;
	private String endStake;
	private String impactProvince;
	private Long estimatesRecoverTime;
	private String description;
	private Integer hurtNumber;
	private Integer deathNumber;
	private Integer delayHumanNumber;
	private Integer delayCarNumber;
	private Integer crowdMeter;
	private Double lossAmount;
	private String administration;
	private String managerUnit;
	private Short isFire;
	private Short laneNumber;
	private Long createTime;
	private Integer eventLevel;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setRoadType(String roadType) {
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
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Integer getEventLevel() {
		return eventLevel;
	}
	public void setEventLevel(Integer eventLevel) {
		this.eventLevel = eventLevel;
	}
}


