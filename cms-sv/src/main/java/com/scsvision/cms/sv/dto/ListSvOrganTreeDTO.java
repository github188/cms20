/**
 * 
 */
package com.scsvision.cms.sv.dto;

import java.util.List;

import com.scsvision.cms.response.BaseDTO;

/**
 * @author wangbinyu
 *
 */
public class ListSvOrganTreeDTO extends BaseDTO {
	private List<ListSvOrganTree> children;

	public List<ListSvOrganTree> getChildren() {
		return children;
	}

	public void setChildren(List<ListSvOrganTree> children) {
		this.children = children;
	}

	public class ListSvOrganTree {
		private String id;
		private String pid;
		private String name;
		private String type;

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

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}
}
