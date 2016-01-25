/**
 * 
 */
package com.scsvision.cms.common.dto;

import java.util.List;

import com.scsvision.cms.response.BaseDTO;

/**
 * @author wangbinyu
 *
 */
public class TreeRealOrganDTO extends BaseDTO {
	private List<TreeRealOrganVO> children;

	public List<TreeRealOrganVO> getChildren() {
		return children;
	}

	public void setChildren(List<TreeRealOrganVO> children) {
		this.children = children;
	}

	public class TreeRealOrganVO {
		private String id;
		private String standardNumber;
		private String pid;
		private String type;
		private String name;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getStandardNumber() {
			return standardNumber;
		}

		public void setStandardNumber(String standardNumber) {
			this.standardNumber = standardNumber;
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
