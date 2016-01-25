package com.scsvision.cms.duty.dto;

import java.util.List;

import com.scsvision.cms.response.BaseDTO;

/**
 * DutyUserDTO
 * 
 * @author sjt
 *         <p />
 *         Create at 2015 下午3:18:08
 */
public class DutyUserDTO extends BaseDTO {
	private List<DutyUser> list;

	public List<DutyUser> getList() {
		return list;
	}

	public void setList(List<DutyUser> list) {
		this.list = list;
	}

	public class DutyUser {
		private Long id;
		private String phone;
		private Short status;
		private String name;
		private Long organId;
		private String organName;
		private String loginName;
		private String password;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public Short getStatus() {
			return status;
		}

		public void setStatus(Short status) {
			this.status = status;
		}

		public String getName() {
			return name;
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

		public void setName(String name) {
			this.name = name;
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
