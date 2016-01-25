package com.scsvision.cms.common.dto;

import java.util.List;

import com.scsvision.cms.response.BaseDTO;

public class ListUserDTO extends BaseDTO {
	public List<User> list;

	public List<User> getList() {
		return list;
	}

	public void setList(List<User> list) {
		this.list = list;
	}

	public class User {
		private Long id;
		private String name;
		private String standardNumber;
		private String loginName;
		private String password;
		private Long userGroupId;
		private Short tunnelPriv;
		private Short eventPriv;
		private Short statisticPriv;
		private Short roadPriv;
		private Short viedoPriv;
		private Short workPriv;
		private Short alarmPriv;
		private Short infoPriv;
		private Short systemPriv;
		private Short adminPriv;
		private Long organId;
		private String organName;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
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

		public String getLoginName() {
			return loginName;
		}

		public void setLoginName(String loginName) {
			this.loginName = loginName;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public Long getUserGroupId() {
			return userGroupId;
		}

		public void setUserGroupId(Long userGroupId) {
			this.userGroupId = userGroupId;
		}

		public Short getTunnelPriv() {
			return tunnelPriv;
		}

		public void setTunnelPriv(Short tunnelPriv) {
			this.tunnelPriv = tunnelPriv;
		}

		public Short getEventPriv() {
			return eventPriv;
		}

		public void setEventPriv(Short eventPriv) {
			this.eventPriv = eventPriv;
		}

		public Short getStatisticPriv() {
			return statisticPriv;
		}

		public void setStatisticPriv(Short statisticPriv) {
			this.statisticPriv = statisticPriv;
		}

		public Short getRoadPriv() {
			return roadPriv;
		}

		public void setRoadPriv(Short roadPriv) {
			this.roadPriv = roadPriv;
		}

		public Short getViedoPriv() {
			return viedoPriv;
		}

		public void setViedoPriv(Short viedoPriv) {
			this.viedoPriv = viedoPriv;
		}

		public Short getWorkPriv() {
			return workPriv;
		}

		public void setWorkPriv(Short workPriv) {
			this.workPriv = workPriv;
		}

		public Short getAlarmPriv() {
			return alarmPriv;
		}

		public void setAlarmPriv(Short alarmPriv) {
			this.alarmPriv = alarmPriv;
		}

		public Short getInfoPriv() {
			return infoPriv;
		}

		public void setInfoPriv(Short infoPriv) {
			this.infoPriv = infoPriv;
		}

		public Short getSystemPriv() {
			return systemPriv;
		}

		public void setSystemPriv(Short systemPriv) {
			this.systemPriv = systemPriv;
		}

		public Short getAdminPriv() {
			return adminPriv;
		}

		public void setAdminPriv(Short adminPriv) {
			this.adminPriv = adminPriv;
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

	}

}
