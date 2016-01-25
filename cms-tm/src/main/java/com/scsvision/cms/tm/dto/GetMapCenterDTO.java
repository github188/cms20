/**
 * 
 */
package com.scsvision.cms.tm.dto;

import com.scsvision.cms.response.BaseDTO;
import com.scsvision.cms.tm.service.vo.TmMapCenterVO;

/**
 * @author wangbinyu
 *
 */
public class GetMapCenterDTO extends BaseDTO {
	private TmMapCenterVO centerDTO;

	public TmMapCenterVO getCenterDTO() {
		return centerDTO;
	}

	public void setCenterDTO(TmMapCenterVO centerDTO) {
		this.centerDTO = centerDTO;
	}

}
