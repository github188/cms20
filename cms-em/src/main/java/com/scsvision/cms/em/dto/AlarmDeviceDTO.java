package com.scsvision.cms.em.dto;

import java.util.LinkedList;
import java.util.List;

import com.scsvision.cms.response.BaseDTO;

public class AlarmDeviceDTO extends BaseDTO {
	private List<Alarm> alarm = new LinkedList<Alarm>();

	public List<Alarm> getAlarm() {
		return alarm;
	}

	public void setAlarm(List<Alarm> alarm) {
		this.alarm = alarm;
	}

	public class Alarm {
		private Long id;
		private Integer subType;
		private Integer detailType;
		private String deviceType;
		private String deviceName;
		private String threshold;
		private String currentValue;
		private String alarmReason;
		private String alarmContent;
		private String name;
		private String confirmUserName;
		private Short confirmFlag;
		private String ConfirmNote;
		private String createTime;
		private Integer alarmLevel;
		private String location;
		private String confirmTime;
		private String organName;
		private String organId;
		private String stakeNumber;

		public Long getId() {
			return id;
		}

		public String getOrganId() {
			return organId;
		}

		public void setOrganId(String organId) {
			this.organId = organId;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Integer getDetailType() {
			return detailType;
		}

		public void setDetailType(Integer detailType) {
			this.detailType = detailType;
		}

		public Integer getSubType() {
			return subType;
		}

		public void setSubType(Integer subType) {
			this.subType = subType;
		}

		public String getDeviceType() {
			return deviceType;
		}

		public void setDeviceType(String deviceType) {
			this.deviceType = deviceType;
		}

		public String getDeviceName() {
			return deviceName;
		}

		public void setDeviceName(String deviceName) {
			this.deviceName = deviceName;
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

		public String getAlarmReason() {
			return alarmReason;
		}

		public void setAlarmReason(String alarmReason) {
			this.alarmReason = alarmReason;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getConfirmUserName() {
			return confirmUserName;
		}

		public void setConfirmUserName(String confirmUserName) {
			this.confirmUserName = confirmUserName;
		}

		public Short getConfirmFlag() {
			return confirmFlag;
		}

		public void setConfirmFlag(Short confirmFlag) {
			this.confirmFlag = confirmFlag;
		}

		public String getConfirmNote() {
			return ConfirmNote;
		}

		public void setConfirmNote(String confirmNote) {
			ConfirmNote = confirmNote;
		}

		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		public Integer getAlarmLevel() {
			return alarmLevel;
		}

		public void setAlarmLevel(Integer alarmLevel) {
			this.alarmLevel = alarmLevel;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public String getConfirmTime() {
			return confirmTime;
		}

		public void setConfirmTime(String confirmTime) {
			this.confirmTime = confirmTime;
		}

		public String getOrganName() {
			return organName;
		}

		public void setOrganName(String organName) {
			this.organName = organName;
		}

		public String getStakeNumber() {
			return stakeNumber;
		}

		public void setStakeNumber(String stakeNumber) {
			this.stakeNumber = stakeNumber;
		}

		public String getAlarmContent() {
			return alarmContent;
		}

		public void setAlarmContent(String alarmContent) {
			this.alarmContent = alarmContent;
		}

	}
}
