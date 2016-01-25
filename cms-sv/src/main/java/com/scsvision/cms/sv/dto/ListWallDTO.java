/**
 * 
 */
package com.scsvision.cms.sv.dto;

import java.util.List;

import com.scsvision.cms.response.BaseDTO;
import com.scsvision.database.entity.SvWall;

/**
 * @author wangbinyu
 *
 */
public class ListWallDTO extends BaseDTO {
	private List<SvWall> walls;

	public List<SvWall> getWalls() {
		return walls;
	}

	public void setWalls(List<SvWall> walls) {
		this.walls = walls;
	}
}
