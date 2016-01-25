package com.scsvision.cms.sv.dto;

import java.util.List;

import com.scsvision.cms.response.BaseDTO;
import com.scsvision.database.entity.SvPreset;

public class ListPresetDTO extends BaseDTO {

	private List<SvPreset> list;

	public List<SvPreset> getList() {
		return list;
	}

	public void setList(List<SvPreset> list) {
		this.list = list;
	}

}
