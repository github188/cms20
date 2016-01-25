package com.scsvision.cms.duty.dto;

import java.util.List;

import com.scsvision.cms.response.BaseDTO;

public class CountDeviceFaultDTO extends BaseDTO {
	private String organName;
	private List<String> names;
	private List<CountDevice> data;

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public List<CountDevice> getData() {
		return data;
	}

	public void setData(List<CountDevice> data) {
		this.data = data;
	}

	public class CountDevice {
		private String deviceName;
		private List<Integer> deviceCount;

		public String getDeviceName() {
			return deviceName;
		}

		public void setDeviceName(String deviceName) {
			this.deviceName = deviceName;
		}

		public List<Integer> getDeviceCount() {
			return deviceCount;
		}

		public void setDeviceCount(List<Integer> deviceCount) {
			this.deviceCount = deviceCount;
		}

	}
}
