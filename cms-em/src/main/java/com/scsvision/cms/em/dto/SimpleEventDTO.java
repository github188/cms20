package com.scsvision.cms.em.dto;

import java.util.List;

import com.scsvision.cms.response.BaseDTO;

public class SimpleEventDTO extends BaseDTO {
	private List<SimpleEvent> events;

	public List<SimpleEvent> getSimpleEvents() {
		return events;
	}

	public void setSimpleEvents(List<SimpleEvent> simpleEvents) {
		this.events = simpleEvents;
	}

	public class SimpleEvent {
		private Long id;
		private String organName;
		private Long organId;
		private String beginStake;
		private String endStake;
		private String SubType;
		private String sourceName;
		private String phone;
		private String description;
		private String navigation;
		private String crowdMeter;
		private String createTime;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getOrganId() {
			return organId;
		}

		public void setOrganId(Long organId) {
			this.organId = organId;
		}

		public String getOrganName() {
			return organName;
		}

		public void setOrganName(String organName) {
			this.organName = organName;
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

		public String getSubType() {
			return SubType;
		}

		public void setSubType(String subType) {
			this.SubType = subType;
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

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getNavigation() {
			return navigation;
		}

		public void setNavigation(String navigation) {
			this.navigation = navigation;
		}

		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		public String getCrowdMeter() {
			return crowdMeter;
		}

		public void setCrowdMeter(String crowdMeter) {
			this.crowdMeter = crowdMeter;
		}

	}
}
