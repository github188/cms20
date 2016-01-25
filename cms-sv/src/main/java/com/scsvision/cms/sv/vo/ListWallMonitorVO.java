/**
 * 
 */
package com.scsvision.cms.sv.vo;

import java.util.List;

import javax.persistence.Column;

import com.scsvision.database.entity.SvMonitor;

/**
 * @author wangbinyu
 *
 */
public class ListWallMonitorVO {
	private Long id;

	private String name;

	private String note;

	private List<SvMonitorVO> monitors;

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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<SvMonitorVO> getMonitors() {
		return monitors;
	}

	public void setMonitors(List<SvMonitorVO> monitors) {
		this.monitors = monitors;
	}

	public class SvMonitorVO {
		private Long id;

		private String name;

		private String standardNumber;

		private String channelNumber;

		private String x;

		private String y;

		private String width;

		private String height;

		private Long dwsId;

		private Long wallId;

		private String dwsStandardNumber;

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

		public String getStandardNumber() {
			return standardNumber;
		}

		public void setStandardNumber(String standardNumber) {
			this.standardNumber = standardNumber;
		}

		public String getChannelNumber() {
			return channelNumber;
		}

		public void setChannelNumber(String channelNumber) {
			this.channelNumber = channelNumber;
		}

		public String getX() {
			return x;
		}

		public void setX(String x) {
			this.x = x;
		}

		public String getY() {
			return y;
		}

		public void setY(String y) {
			this.y = y;
		}

		public String getWidth() {
			return width;
		}

		public void setWidth(String width) {
			this.width = width;
		}

		public String getHeight() {
			return height;
		}

		public void setHeight(String height) {
			this.height = height;
		}

		public Long getDwsId() {
			return dwsId;
		}

		public void setDwsId(Long dwsId) {
			this.dwsId = dwsId;
		}

		public Long getWallId() {
			return wallId;
		}

		public void setWallId(Long wallId) {
			this.wallId = wallId;
		}

		public String getDwsStandardNumber() {
			return dwsStandardNumber;
		}

		public void setDwsStandardNumber(String dwsStandardNumber) {
			this.dwsStandardNumber = dwsStandardNumber;
		}

	}
}
