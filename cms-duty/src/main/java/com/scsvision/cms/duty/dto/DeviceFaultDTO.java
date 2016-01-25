package com.scsvision.cms.duty.dto;

import java.util.List;

import com.scsvision.cms.response.BaseDTO;

public class DeviceFaultDTO extends BaseDTO {
	List<DeviceFault> list;

	public List<DeviceFault> getList() {
		return list;
	}

	public void setList(List<DeviceFault> list) {
		this.list = list;
	}

	public class DeviceFault {
		private String deviceType;
		private String deviceName;
		private Long organId;
		private String organName;
		private String stakeNumber;
		private String recordTime;
		private String note;
		private String maintainer;

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

		public String getStakeNumber() {
			return stakeNumber;
		}

		public void setStakeNumber(String stakeNumber) {
			this.stakeNumber = stakeNumber;
		}

		public String getRecordTime() {
			return recordTime;
		}

		public void setRecordTime(String recordTime) {
			this.recordTime = recordTime;
		}

		public String getNote() {
			return note;
		}

		public void setNote(String note) {
			this.note = note;
		}

		public String getMaintainer() {
			return maintainer;
		}

		public void setMaintainer(String maintainer) {
			this.maintainer = maintainer;
		}

	}
}
