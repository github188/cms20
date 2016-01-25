/**
 * 
 */
package com.scsvision.cms.gather.dto;

import java.util.List;

import com.scsvision.cms.gather.service.vo.ListGatherCoviVO;
import com.scsvision.cms.response.BaseDTO;

/**
 * @author wangbinyu
 *
 */
public class FiveHourCoviDTO extends BaseDTO {
	private List<ListGatherCoviVO> coList;

	private List<ListGatherCoviVO> viList;

	public List<ListGatherCoviVO> getCoList() {
		return coList;
	}

	public void setCoList(List<ListGatherCoviVO> coList) {
		this.coList = coList;
	}

	public List<ListGatherCoviVO> getViList() {
		return viList;
	}

	public void setViList(List<ListGatherCoviVO> viList) {
		this.viList = viList;
	}

}
