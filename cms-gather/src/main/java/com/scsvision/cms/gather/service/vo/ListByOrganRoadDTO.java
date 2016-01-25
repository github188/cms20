/**
 * 
 */
package com.scsvision.cms.gather.service.vo;

import java.util.List;

import com.scsvision.cms.response.BaseDTO;

/**
 * @author wangbinyu
 *
 */
public class ListByOrganRoadDTO extends BaseDTO {
	private List<GatherVdOrganVO> list;
	private String[] date;

	public List<GatherVdOrganVO> getList() {
		return list;
	}

	public void setList(List<GatherVdOrganVO> list) {
		this.list = list;
	}

	public String[] getDate() {
		return date;
	}

	public void setDate(String[] date) {
		this.date = date;
	}
}
