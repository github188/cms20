package com.scsvision.cms.common.dto;

import com.scsvision.cms.response.BaseDTO;

/**
 * FileUploadDTO
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015年12月31日 下午4:00:52
 */
public class FileUploadDTO extends BaseDTO {
	private String id;
	private String address;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
