package com.scsvision.cms.em.dto;

import com.scsvision.cms.response.BaseDTO;

/**
 * EventAndAlarmCountDTO
 * 
 * @author sjt
 *         <p />
 *         Create at 2015 下午3:18:08
 */
public class EventAndAlarmCountDTO extends BaseDTO {
	private QueryCount queryCount;

	public QueryCount getQueryCount() {
		return queryCount;
	}

	public void setQueryCount(QueryCount queryCount) {
		this.queryCount = queryCount;
	}

	public class QueryCount {
		private Integer traffic;
		private Integer terrible;
		private Integer thresHoldAlarm;
		private Integer offline;
		private Integer mechanicalFault;

		public Integer getTraffic() {
			return traffic;
		}

		public void setTraffic(Integer traffic) {
			this.traffic = traffic;
		}

		public Integer getTerrible() {
			return terrible;
		}

		public void setTerrible(Integer terrible) {
			this.terrible = terrible;
		}

		public Integer getThresHoldAlarm() {
			return thresHoldAlarm;
		}

		public void setThresHoldAlarm(Integer thresHoldAlarm) {
			this.thresHoldAlarm = thresHoldAlarm;
		}

		public Integer getOffline() {
			return offline;
		}

		public void setOffline(Integer offline) {
			this.offline = offline;
		}

		public Integer getMechanicalFault() {
			return mechanicalFault;
		}

		public void setMechanicalFault(Integer mechanicalFault) {
			this.mechanicalFault = mechanicalFault;
		}

	}
}
