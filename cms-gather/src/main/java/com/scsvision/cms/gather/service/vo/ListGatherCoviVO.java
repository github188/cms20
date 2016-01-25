/**
 * 
 */
package com.scsvision.cms.gather.service.vo;

import java.util.List;

/**
 * @author wangbinyu
 *
 */
public class ListGatherCoviVO {
	private Long id;
	private String name;
	private List<CoviData> data;

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

	public List<CoviData> getData() {
		return data;
	}

	public void setData(List<CoviData> data) {
		this.data = data;
	}

	public class CoviData {
		// 时间点
		private Long x;
		// co或者vi数据
		private Double y;

		public Long getX() {
			return x;
		}

		public void setX(Long x) {
			this.x = x;
		}

		public Double getY() {
			return y;
		}

		public void setY(Double y) {
			this.y = y;
		}

	}
}
