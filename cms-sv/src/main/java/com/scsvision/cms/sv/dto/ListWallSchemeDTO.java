/**
 * 
 */
package com.scsvision.cms.sv.dto;

import java.util.List;

import com.scsvision.cms.response.BaseDTO;
import com.scsvision.cms.sv.vo.ListWallSchemeVO;

/**
 * @author wangbinyu
 *
 */
public class ListWallSchemeDTO extends BaseDTO {
	private List<ListWallSchemeVO> list;

	public List<ListWallSchemeVO> getList() {
		return list;
	}

	public void setList(List<ListWallSchemeVO> list) {
		this.list = list;
	}
}
