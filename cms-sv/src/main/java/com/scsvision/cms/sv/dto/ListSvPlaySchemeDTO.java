package com.scsvision.cms.sv.dto;

import org.json.JSONArray;

import com.scsvision.cms.response.BaseDTO;

/**
 * ListSvPlaySchemeDTO
 * 
 * @author MIKE
 *         <p />
 *         Create at 2016年1月14日 上午10:42:19
 */
public class ListSvPlaySchemeDTO extends BaseDTO {
	private JSONArray playSchemes = new JSONArray();

	public JSONArray getPlaySchemes() {
		return playSchemes;
	}

	public void setPlaySchemes(JSONArray playSchemes) {
		this.playSchemes = playSchemes;
	}

}
