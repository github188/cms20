package com.scsvision.cms.tm.dto;

import java.util.List;

import com.scsvision.cms.response.BaseDTO;
import com.scsvision.database.entity.SvDevice;
import com.scsvision.database.entity.TmDevice;

/**
 * DeviceDTO
 * 
 * @author sjt
 *         <p />
 *         Create at 2016年 上午11:00:40
 */

public class DeviceDTO extends BaseDTO {
	private List<TmDevice> tmList;
	private List<SvDevice> svList;

	public List<TmDevice> getTmList() {
		return tmList;
	}

	public void setTmList(List<TmDevice> tmList) {
		this.tmList = tmList;
	}

	public List<SvDevice> getSvList() {
		return svList;
	}

	public void setSvList(List<SvDevice> svList) {
		this.svList = svList;
	}

}
