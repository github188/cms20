package com.scsvision.cms.tm.dto;

import java.util.List;

import com.scsvision.cms.response.BaseDTO;
import com.scsvision.database.entity.TmDevice;

/**
 * TmDeviceDTO
 * 
 * @author sjt
 *         <p />
 *         Create at 2016年 上午9:45:05
 */

public class TmDeviceDTO extends BaseDTO {
	private List<TmDevice> list;

	public List<TmDevice> getList() {
		return list;
	}

	public void setList(List<TmDevice> list) {
		this.list = list;
	}

}
