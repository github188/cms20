package com.scsvision.cms.maintain.dto;

import java.util.LinkedList;
import java.util.List;

import com.scsvision.cms.response.BaseDTO;

/**
 * ListSvOrganDeviceDTO
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 上午10:52:47
 */
public class ListSvOrganDeviceDTO extends BaseDTO {

	private List<Node> children = new LinkedList<Node>();

	public List<Node> getChildren() {
		return children;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}

	public class Node {
		private String name;
		private String pid;
		private String type;
		private boolean isOnline;
		private String id;
		private String manufacturer;
		private String standarNumber;
		private String stakeNumber;
		private String realName;
		private String ip;
		private String port;
		private String location;
		private int onlineCount;
		private int totalCount;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPid() {
			return pid;
		}

		public void setPid(String pid) {
			this.pid = pid;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public boolean getIsOnline() {
			return isOnline;
		}

		public void setIsOnline(boolean isOnline) {
			this.isOnline = isOnline;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getManufacturer() {
			return manufacturer;
		}

		public void setManufacturer(String manufacturer) {
			this.manufacturer = manufacturer;
		}

		public String getStandarNumber() {
			return standarNumber;
		}

		public void setStandarNumber(String standarNumber) {
			this.standarNumber = standarNumber;
		}

		public String getStakeNumber() {
			return stakeNumber;
		}

		public void setStakeNumber(String stakeNumber) {
			this.stakeNumber = stakeNumber;
		}

		public String getRealName() {
			return realName;
		}

		public void setRealName(String realName) {
			this.realName = realName;
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

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public int getOnlineCount() {
			return onlineCount;
		}

		public void setOnlineCount(int onlineCount) {
			this.onlineCount = onlineCount;
		}

		public int getTotalCount() {
			return totalCount;
		}

		public void setTotalCount(int totalCount) {
			this.totalCount = totalCount;
		}

	}
}
