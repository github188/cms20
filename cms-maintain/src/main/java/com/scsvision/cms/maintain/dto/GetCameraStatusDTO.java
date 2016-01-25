/**
 * 
 */
package com.scsvision.cms.maintain.dto;

import com.scsvision.cms.response.BaseDTO;

/**
 * @author wangbinyu
 *
 */
public class GetCameraStatusDTO extends BaseDTO {
	private CameraStatus cameraStatus;

	public CameraStatus getCameraStatus() {
		return cameraStatus;
	}

	public void setCameraStatus(CameraStatus cameraStatus) {
		this.cameraStatus = cameraStatus;
	}

	public class CameraStatus {
		private String id;
		private String online;
		private String updateTime;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getOnline() {
			return online;
		}

		public void setOnline(String online) {
			this.online = online;
		}

		public String getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(String updateTime) {
			this.updateTime = updateTime;
		}

	}
}
