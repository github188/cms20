package com.scsvision.cms.common.dto;

import java.util.List;

import com.scsvision.cms.response.BaseDTO;
import com.scsvision.database.entity.UserGroup;

/**
 * UserGroupDTO
 * 
 * @author sjt
 *         <p />
 *         Create at 2015年 下午2:31:34
 */

public class UserGroupDTO extends BaseDTO {
	List<UserGroup> list;

	public List<UserGroup> getList() {
		return list;
	}

	public void setList(List<UserGroup> list) {
		this.list = list;
	}

}
