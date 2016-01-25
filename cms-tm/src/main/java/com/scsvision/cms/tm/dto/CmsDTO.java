package com.scsvision.cms.tm.dto;

import java.util.List;

import com.scsvision.cms.response.BaseDTO;

/**
 * CmsDTO
 * 
 * @author sjt
 *         <p />
 *         Create at 2015年 上午11:03:24
 */

public class CmsDTO extends BaseDTO {
	public List<Cms> list;

	public List<Cms> getList() {
		return list;
	}

	public void setList(List<Cms> list) {
		this.list = list;
	}

	public class Cms {
		private String stype;
		private List<Children> list;

		public String getStype() {
			return stype;
		}

		public void setStype(String stype) {
			this.stype = stype;
		}

		public List<Children> getList() {
			return list;
		}

		public void setList(List<Children> list) {
			this.list = list;
		}

		public class Children {
			private Long id;
			private String name;
			private String sn;
			private Integer type;

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

			public String getSn() {
				return sn;
			}

			public void setSn(String sn) {
				this.sn = sn;
			}

			public Integer getType() {
				return type;
			}

			public void setType(Integer type) {
				this.type = type;
			}

		}
	}
}
