/**
 * 
 */
package com.scsvision.cms.sv.dto;

import java.util.List;

import com.scsvision.cms.response.BaseDTO;
import com.scsvision.cms.sv.vo.ListWallMonitorVO;

/**
 * @author wangbinyu
 *
 */
public class ListWallMonitorDTO extends BaseDTO {
	private List<ListWallMonitorVO> walls;

	public List<ListWallMonitorVO> getWalls() {
		return walls;
	}

	public void setWalls(List<ListWallMonitorVO> walls) {
		this.walls = walls;
	}
}
