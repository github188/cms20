/**
 * 
 */
package com.scsvision.cms.em.dto;

import java.util.List;

import com.scsvision.cms.response.BaseDTO;
import com.scsvision.database.entity.DicManagement;

/**
 * @author wangbinyu
 *
 */
public class ListEventOrganInfoDTO extends BaseDTO {
	private List<organVO> organs;
	private List<DicManagement> managements;
	private String userName;
	private String id;

	public List<organVO> getOrgans() {
		return organs;
	}

	public void setOrgans(List<organVO> organs) {
		this.organs = organs;
	}

	public List<DicManagement> getManagements() {
		return managements;
	}

	public void setManagements(List<DicManagement> managements) {
		this.managements = managements;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public class organVO {
		private String id;
		private String name;
		private String code = "";

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

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}
	}

}
