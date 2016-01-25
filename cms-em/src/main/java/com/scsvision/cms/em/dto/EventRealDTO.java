package com.scsvision.cms.em.dto;

import java.util.List;

import com.scsvision.cms.em.service.vo.ResourceVO;
import com.scsvision.cms.response.BaseDTO;

/**
 * EventRealDTO
 * 
 * @author sjt
 *         <p />
 *         Create at 2015 上午9:38:08
 */
public class EventRealDTO extends BaseDTO {
	private Event Event;

	public Event getEvent() {
		return Event;
	}

	public void setEvent(Event event) {
		Event = event;
	}

	public class Event {
		private Long id;
		private Integer subType;
		private String roadType;
		private String administration;
		private String roadCode;
		private String location;
		private String beginStake;
		private String endStake;
		private String navigation;
		private Long organId;
		private String organName;
		private String managerUnit;
		private String sourceName;
		private String phone;
		private String laneNumber;
		private String createTime;
		private String estimatesRecoverTime;
		private String impactProvince;
		private String description;

		private String hurtNumber;
		private String deathNumber;
		private String delayHumanNumber;
		private String crowdMeter;
		private String destoryCarNumber;
		private String delayCarNumber;
		private List<String> detailTypes;
		private List<ResourceVO> image;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Integer getSubType() {
			return subType;
		}

		public void setSubType(Integer subType) {
			this.subType = subType;
		}

		public String getRoadType() {
			return roadType;
		}

		public void setRoadType(String roadType) {
			this.roadType = roadType;
		}

		public String getOrganName() {
			return organName;
		}

		public void setOrganName(String organName) {
			this.organName = organName;
		}

		public String getAdministration() {
			return administration;
		}

		public void setAdministration(String administration) {
			this.administration = administration;
		}

		public String getRoadCode() {
			return roadCode;
		}

		public void setRoadCode(String roadCode) {
			this.roadCode = roadCode;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
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

		public String getNavigation() {
			return navigation;
		}

		public void setNavigation(String navigation) {
			this.navigation = navigation;
		}

		public Long getOrganId() {
			return organId;
		}

		public void setOrganId(Long organId) {
			this.organId = organId;
		}

		public String getManagerUnit() {
			return managerUnit;
		}

		public void setManagerUnit(String managerUnit) {
			this.managerUnit = managerUnit;
		}

		public String getSourceName() {
			return sourceName;
		}

		public void setSourceName(String sourceName) {
			this.sourceName = sourceName;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getLaneNumber() {
			return laneNumber;
		}

		public void setLaneNumber(String laneNumber) {
			this.laneNumber = laneNumber;
		}

		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		public String getEstimatesRecoverTime() {
			return estimatesRecoverTime;
		}

		public void setEstimatesRecoverTime(String estimatesRecoverTime) {
			this.estimatesRecoverTime = estimatesRecoverTime;
		}

		public String getImpactProvince() {
			return impactProvince;
		}

		public void setImpactProvince(String impactProvince) {
			this.impactProvince = impactProvince;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getHurtNumber() {
			return hurtNumber;
		}

		public void setHurtNumber(String hurtNumber) {
			this.hurtNumber = hurtNumber;
		}

		public String getDeathNumber() {
			return deathNumber;
		}

		public void setDeathNumber(String deathNumber) {
			this.deathNumber = deathNumber;
		}

		public String getDelayHumanNumber() {
			return delayHumanNumber;
		}

		public void setDelayHumanNumber(String delayHumanNumber) {
			this.delayHumanNumber = delayHumanNumber;
		}

		public String getCrowdMeter() {
			return crowdMeter;
		}

		public void setCrowdMeter(String crowdMeter) {
			this.crowdMeter = crowdMeter;
		}

		public String getDestoryCarNumber() {
			return destoryCarNumber;
		}

		public void setDestoryCarNumber(String destoryCarNumber) {
			this.destoryCarNumber = destoryCarNumber;
		}

		public String getDelayCarNumber() {
			return delayCarNumber;
		}

		public void setDelayCarNumber(String delayCarNumber) {
			this.delayCarNumber = delayCarNumber;
		}

		public List<String> getDetailTypes() {
			return detailTypes;
		}

		public void setDetailTypes(List<String> detailTypes) {
			this.detailTypes = detailTypes;
		}

		public List<ResourceVO> getImage() {
			return image;
		}

		public void setImage(List<ResourceVO> image) {
			this.image = image;
		}

	}
}
