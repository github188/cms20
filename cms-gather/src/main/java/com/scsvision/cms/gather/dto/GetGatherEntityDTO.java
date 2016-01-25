/**
 * 
 */
package com.scsvision.cms.gather.dto;

import com.scsvision.cms.response.BaseDTO;

/**
 * @author wangbinyu
 *
 */
public class GetGatherEntityDTO extends BaseDTO {
	private Object gather;
	private String recTime;

	public Object getGather() {
		return gather;
	}

	public void setGather(Object gather) {
		this.gather = gather;
	}

	public String getRecTime() {
		return recTime;
	}

	public void setRecTime(String recTime) {
		this.recTime = recTime;
	}

}
