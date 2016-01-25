package com.scsvision.cms.em.dto;

import java.util.LinkedList;
import java.util.List;

import com.scsvision.cms.response.BaseDTO;

/**
 * DeviceAlarmDTO
 * 
 * @author sjt
 *         <p />
 *         Create at 2015 上午11:38:08
 */
public class DeviceAlarmDTO extends BaseDTO {
	private Alarm alarm;
	private List<Alarm> alarmResponse = new LinkedList<Alarm>();

	public Alarm getAlarm() {
		return alarm;
	}

	public void setAlarm(Alarm alarm) {
		this.alarm = alarm;
	}

	public List<Alarm> getAlarmResponse() {
		return alarmResponse;
	}

	public void setAlarmResponse(List<Alarm> alarmResponse) {
		this.alarmResponse = alarmResponse;
	}

	public class Alarm {
		private Long id;
		private Integer subType;
		private Integer deviceType;
		private String deviceName;
		private String threshold;
		private String currentValue;
		private Long recoverTime;
		private String alarmReason;
		private String alarmContent;
		private String name;
		private String confirmUserName;
		private Short confirmFlag;
		private String ConfirmNote;
		private Long createTime;
		private Integer alarmLevel;
		private String location;
		private Long confirmTime;
		private String organName;
		private String stakeNumber;
		private String sourceName;
		private Integer eventLevel;
		private Short resourceFlag;
		private List<Image> image;
		private Integer navigation;

		public class Image {
			private String url;
			private String id;

			public String getUrl() {
				return url;
			}

			public void setUrl(String url) {
				this.url = url;
			}

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

		}

		public List<Image> getImage() {
			return image;
		}

		public void setImage(List<Image> image) {
			this.image = image;
		}

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

		public Integer getDeviceType() {
			return deviceType;
		}

		public void setDeviceType(Integer deviceType) {
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

		public Long getRecoverTime() {
			return recoverTime;
		}

		public void setRecoverTime(Long recoverTime) {
			this.recoverTime = recoverTime;
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

		public Long getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Long createTime) {
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

		public Long getConfirmTime() {
			return confirmTime;
		}

		public void setConfirmTime(Long confirmTime) {
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

		public String getSourceName() {
			return sourceName;
		}

		public void setSourceName(String sourceName) {
			this.sourceName = sourceName;
		}

		public Integer getEventLevel() {
			return eventLevel;
		}

		public void setEventLevel(Integer eventLevel) {
			this.eventLevel = eventLevel;
		}

		public Short getResourceFlag() {
			return resourceFlag;
		}

		public void setResourceFlag(Short resourceFlag) {
			this.resourceFlag = resourceFlag;
		}

		public Integer getNavigation() {
			return navigation;
		}

		public void setNavigation(Integer navigation) {
			this.navigation = navigation;
		}

	}
}
