/**
 * 
 */
package com.scsvision.cms.em.dao;

import java.util.List;

import com.scsvision.cms.response.BaseDTO;

/**
 * @author wangbinyu
 *
 */
public class ListEventHistoryAlarmDTO extends BaseDTO {

	private List<EventHistoryAlarmVO> list;

	public class EventHistoryAlarmVO {
		private String id;
		private String name;
		private String location;
		private String sourceName;
		private String confirmFlag;
		private String confirmUserName;
		private String recoverTime;
		private String alarmContent;
		private String confirmNote;
		private String subType;
		private String createTime;
		private String eventLevel;
		private String resourceFlag;
		private String organName;
		private String threshold;
		private String currentValue;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public String getSourceName() {
			return sourceName;
		}

		public void setSourceName(String sourceName) {
			this.sourceName = sourceName;
		}

		public String getConfirmFlag() {
			return confirmFlag;
		}

		public void setConfirmFlag(String confirmFlag) {
			this.confirmFlag = confirmFlag;
		}

		public String getConfirmUserName() {
			return confirmUserName;
		}

		public void setConfirmUserName(String confirmUserName) {
			this.confirmUserName = confirmUserName;
		}

		public String getRecoverTime() {
			return recoverTime;
		}

		public void setRecoverTime(String recoverTime) {
			this.recoverTime = recoverTime;
		}

		public String getAlarmContent() {
			return alarmContent;
		}

		public void setAlarmContent(String alarmContent) {
			this.alarmContent = alarmContent;
		}

		public String getConfirmNote() {
			return confirmNote;
		}

		public void setConfirmNote(String confirmNote) {
			this.confirmNote = confirmNote;
		}

		public String getSubType() {
			return subType;
		}

		public void setSubType(String subType) {
			this.subType = subType;
		}

		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		public String getEventLevel() {
			return eventLevel;
		}

		public void setEventLevel(String eventLevel) {
			this.eventLevel = eventLevel;
		}

		public String getResourceFlag() {
			return resourceFlag;
		}

		public void setResourceFlag(String resourceFlag) {
			this.resourceFlag = resourceFlag;
		}

		public String getOrganName() {
			return organName;
		}

		public void setOrganName(String organName) {
			this.organName = organName;
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
	}
}
