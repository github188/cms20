/**
 * 
 */
package com.scsvision.cms.tm.dto;

import com.scsvision.cms.response.BaseDTO;
import com.scsvision.database.entity.TmPlayList;

/**
 * @author wangbinyu
 *
 */
public class GetPlayListDTO extends BaseDTO {
	private TmPlayList playlist;

	public TmPlayList getPlaylist() {
		return playlist;
	}

	public void setPlaylist(TmPlayList playlist) {
		this.playlist = playlist;
	}
}
