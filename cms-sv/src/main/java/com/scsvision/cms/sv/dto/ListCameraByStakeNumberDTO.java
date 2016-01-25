package com.scsvision.cms.sv.dto;

import java.util.LinkedList;
import java.util.List;

import com.scsvision.cms.response.BaseDTO;

/**
 * ListCameraByStakeNumberDTO
 * 
 * @author MIKE
 *         <p />
 *         Create at 2016年1月11日 下午2:38:21
 */
public class ListCameraByStakeNumberDTO extends BaseDTO {

	private List<Camera> cameras = new LinkedList<Camera>();

	public List<Camera> getCameras() {
		return cameras;
	}

	public void setCameras(List<Camera> cameras) {
		this.cameras = cameras;
	}

	public class Camera {
		private String id;
		private String name;
		private String standardNumber;
		private String stakeNumber;
		private String ip;
		private String port;
		private String manufacturer;
		private String location;
		private String type;

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

		public String getStandardNumber() {
			return standardNumber;
		}

		public void setStandardNumber(String standardNumber) {
			this.standardNumber = standardNumber;
		}

		public String getStakeNumber() {
			return stakeNumber;
		}

		public void setStakeNumber(String stakeNumber) {
			this.stakeNumber = stakeNumber;
		}

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		public String getPort() {
			return port;
		}

		public void setPort(String port) {
			this.port = port;
		}

		public String getManufacturer() {
			return manufacturer;
		}

		public void setManufacturer(String manufacturer) {
			this.manufacturer = manufacturer;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

	}
}
