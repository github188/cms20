/**
 * 
 */
package com.scsvision.cms.sv.dto;

import java.util.List;

import com.scsvision.cms.response.BaseDTO;
import com.scsvision.database.entity.SvMonitor;

/**
 * @author wangbinyu
 *
 */
public class ListMonitorDTO extends BaseDTO {
	private List<SvMonitor> monitors;

	public List<SvMonitor> getMonitors() {
		return monitors;
	}

	public void setMonitors(List<SvMonitor> monitors) {
		this.monitors = monitors;
	}
}
