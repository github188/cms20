package com.scsvision.cms.maintain.dto;

import java.util.List;

import com.scsvision.cms.response.BaseDTO;

/**
 * 
 * OnlineRealDTO
 * 
 * @author sjt
 *         <p />
 *         Create at 2015年 下午3:27:25
 */

public class OnlineRealDTO extends BaseDTO {
	private List<OnlineRealDto> list;

	public List<OnlineRealDto> getList() {
		return list;
	}

	public void setList(List<OnlineRealDto> list) {
		this.list = list;
	}

	public class OnlineRealDto {
		private String id;
		private Integer type;
		private Long resourceId;
		private String name;
		private String standardNumber;
		private String ip;
		private Long onlineTime;
		private Long updateTime;
		private String ticket;
		private String clientType;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public Integer getType() {
			return type;
		}

		public void setType(Integer type) {
			this.type = type;
		}

		public Long getResourceId() {
			return resourceId;
		}

		public void setResourceId(Long resourceId) {
			this.resourceId = resourceId;
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

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		public Long getOnlineTime() {
			return onlineTime;
		}

		public void setOnlineTime(Long onlineTime) {
			this.onlineTime = onlineTime;
		}

		public Long getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(Long updateTime) {
			this.updateTime = updateTime;
		}

		public String getTicket() {
			return ticket;
		}

		public void setTicket(String ticket) {
			this.ticket = ticket;
		}

		public String getClientType() {
			return clientType;
		}

		public void setClientType(String clientType) {
			this.clientType = clientType;
		}

	}
}
