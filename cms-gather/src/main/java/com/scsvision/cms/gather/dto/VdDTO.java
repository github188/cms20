package com.scsvision.cms.gather.dto;

import java.util.List;

import com.scsvision.cms.response.BaseDTO;

public class VdDTO extends BaseDTO {
	private FluxCount fluxCount;
	private CarCount carCount;

	public FluxCount getFluxCount() {
		return fluxCount;
	}

	public void setFluxCount(FluxCount fluxCount) {
		this.fluxCount = fluxCount;
	}

	public CarCount getCarCount() {
		return carCount;
	}

	public void setCarCount(CarCount carCount) {
		this.carCount = carCount;
	}

	public class FluxCount {
		private String[] times;
		private List<Datas> data;

		public String[] getTimes() {
			return times;
		}

		public void setTimes(String[] times) {
			this.times = times;
		}

		public List<Datas> getData() {
			return data;
		}

		public void setData(List<Datas> data) {
			this.data = data;
		}

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

	public class CarCount {
		Object[] carocxx;

		public Object[] getCarocxx() {
			return carocxx;
		}

		public void setCarocxx(Object[] carocxx) {
			this.carocxx = carocxx;
		}

	}
}
