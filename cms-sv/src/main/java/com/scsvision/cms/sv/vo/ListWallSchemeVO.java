/**
 * 
 */
package com.scsvision.cms.sv.vo;

import java.util.List;

import org.json.JSONArray;

/**
 * @author wangbinyu
 *
 */
public class ListWallSchemeVO {
	private Long id;
	private String name;
	private Long wallId;
	private List<WallSchemeItemVO> monitors;

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

	public Long getWallId() {
		return wallId;
	}

	public void setWallId(Long wallId) {
		this.wallId = wallId;
	}

	public List<WallSchemeItemVO> getMonitors() {
		return monitors;
	}

	public void setMonitors(List<WallSchemeItemVO> monitors) {
		this.monitors = monitors;
	}

	public class WallSchemeItemVO {
		private Long monitorId;
		private JSONArray schemes;

		public Long getMonitorId() {
			return monitorId;
		}

		public void setMonitorId(Long monitorId) {
			this.monitorId = monitorId;
		}

		public JSONArray getSchemes() {
			return schemes;
		}

		public void setSchemes(JSONArray schemes) {
			this.schemes = schemes;
		}

	}
}
