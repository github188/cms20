package com.scsvision.cms.em.dto;

import java.util.List;

import com.scsvision.cms.response.BaseDTO;

/**
 * TunnelBrightDTO
 * 
 * @author sjt
 *         <p />
 *         Create at 2015 下午3:18:08
 */
public class TunnelBrightListDTO extends BaseDTO {
	private TunnelBright tunnelBright;
	private List<TunnelBright> list;

	public List<TunnelBright> getList() {
		return list;
	}

	public void setList(List<TunnelBright> list) {
		this.list = list;
	}

	public TunnelBright getTunnelBright() {
		return tunnelBright;
	}

	public void setTunnelBright(TunnelBright tunnelBright) {
		this.tunnelBright = tunnelBright;
	}

	public class TunnelBright {
		private String id;
		private String beginStake;
		private String endStake;
		private String type;
		private String name;
		private String organName;
		private String length;
		private boolean isAlarm;
		private String laneNumber;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getBeginStake() {
			return beginStake;
		}

		public void setBeginStake(String beginStake) {
			this.beginStake = beginStake;
		}

		public String getEndStake() {
			return endStake;
		}

		public void setEndStake(String endStake) {
			this.endStake = endStake;
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

		public String getOrganName() {
			return organName;
		}

		public void setOrganName(String organName) {
			this.organName = organName;
		}

		public String getLength() {
			return length;
		}

		public void setLength(String length) {
			this.length = length;
		}

		public boolean isAlarm() {
			return isAlarm;
		}

		public void setAlarm(boolean isAlarm) {
			this.isAlarm = isAlarm;
		}

		public String getLaneNumber() {
			return laneNumber;
		}

		public void setLaneNumber(String laneNumber) {
			this.laneNumber = laneNumber;
		}

	}
}
