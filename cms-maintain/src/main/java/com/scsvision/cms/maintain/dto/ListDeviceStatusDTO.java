/**
 * 
 */
package com.scsvision.cms.maintain.dto;

import java.util.List;

import com.scsvision.cms.response.BaseDTO;

/**
 * @author wangbinyu
 *
 */
public class ListDeviceStatusDTO extends BaseDTO {
	private List<DeviceStatusVO> listStatus;

	public List<DeviceStatusVO> getListStatus() {
		return listStatus;
	}

	public void setListStatus(List<DeviceStatusVO> listStatus) {
		this.listStatus = listStatus;
	}
	
	public class DeviceStatusVO {
		private String id;
		private boolean online;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public boolean isOnline() {
			return online;
		}

		public void setOnline(boolean online) {
			this.online = online;
		}
	}
}
