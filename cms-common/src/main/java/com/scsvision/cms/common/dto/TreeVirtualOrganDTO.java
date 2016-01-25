package com.scsvision.cms.common.dto;

import java.util.List;

import com.scsvision.cms.response.BaseDTO;

/**
 * 
 * TreeVirtualOrganDTO
 * 
 * @author sjt
 *         <p />
 *         Create at 2016年 上午9:47:41
 */

public class TreeVirtualOrganDTO extends BaseDTO {
	List<Children> children;

	public List<Children> getChildren() {
		return children;
	}

	public void setChildren(List<Children> children) {
		this.children = children;
	}

	public class Children {
		private String id;
		private String pid;
		private String type;
		private String name;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
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

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}
}
