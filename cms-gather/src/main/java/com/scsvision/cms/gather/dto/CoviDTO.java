package com.scsvision.cms.gather.dto;

import java.util.Date;
import java.util.List;

import com.scsvision.cms.response.BaseDTO;
import com.scsvision.database.entity.GatherCovi;

/**
 * CoviDTO
 * 
 * @author sjt
 *         <p />
 *         Create at 2016年 下午2:40:55
 */

public class CoviDTO extends BaseDTO {
	List<Covi> list;

	public List<Covi> getList() {
		return list;
	}

	public void setList(List<Covi> list) {
		this.list = list;
	}

	public class Covi {
		private String id;
		private Long deviceId;
		private Long recTime;
		private Double co;
		private Double vi;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public Long getDeviceId() {
			return deviceId;
		}

		public void setDeviceId(Long deviceId) {
			this.deviceId = deviceId;
		}

		public Long getRecTime() {
			return recTime;
		}

		public void setRecTime(Long recTime) {
			this.recTime = recTime;
		}

		public Double getCo() {
			return co;
		}

		public void setCo(Double co) {
			this.co = co;
		}

		public Double getVi() {
			return vi;
		}

		public void setVi(Double vi) {
			this.vi = vi;
		}

	}
}
