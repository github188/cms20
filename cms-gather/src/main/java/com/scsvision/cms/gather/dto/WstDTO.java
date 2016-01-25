package com.scsvision.cms.gather.dto;

import java.util.List;

import com.scsvision.cms.response.BaseDTO;

/**
 * @author sjt
 *         <p />
 *         Create at 2016年 下午3:28:35
 */

public class WstDTO extends BaseDTO {
	private String[] times;
	private List<Datas> data;

	public List<Datas> getData() {
		return data;
	}

	public void setData(List<Datas> data) {
		this.data = data;
	}

	public String[] getTimes() {
		return times;
	}

	public void setTimes(String[] times) {
		this.times = times;
	}

	public class Datas {
		private String name;
		private Integer[] data;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer[] getData() {
			return data;
		}

		public void setData(Integer[] data) {
			this.data = data;
		}

	}
}
