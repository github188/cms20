/**
 * 
 */
package com.scsvision.cms.tm.dto;

import java.util.List;

import com.scsvision.cms.response.BaseDTO;
import com.scsvision.database.entity.TmPlayList;

/**
 * @author wangbinyu
 *
 */
public class ListCMSPubItemDTO extends BaseDTO {
	private List<TmPlayList> list;

	public List<TmPlayList> getList() {
		return list;
	}

	public void setList(List<TmPlayList> list) {
		this.list = list;
	}

}
