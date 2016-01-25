package com.scsvision.cms.common.dto;

import java.util.List;

import com.scsvision.cms.response.BaseDTO;
import com.scsvision.database.entity.Server;

/**
 * ServerDTO
 * 
 * @author sjt
 *         <p />
 *         Create at 2015年 上午10:08:31
 */

public class ServerDTO extends BaseDTO {
	private List<Server> list;

	public List<Server> getList() {
		return list;
	}

	public void setList(List<Server> list) {
		this.list = list;
	}

}
