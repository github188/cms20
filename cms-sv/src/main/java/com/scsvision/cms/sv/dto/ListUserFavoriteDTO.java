package com.scsvision.cms.sv.dto;

import java.util.List;

import com.scsvision.cms.response.BaseDTO;
import com.scsvision.database.entity.SvDevice;

/**
 * ListUserFavoriteDTO
 * 
 * @author sjt
 *         <p />
 *         Create at 2016年 下午2:29:07
 */

public class ListUserFavoriteDTO extends BaseDTO {
	private List<DeviceFavorite> favoriteList;

	public List<DeviceFavorite> getFavoriteList() {
		return favoriteList;
	}

	public void setFavoriteList(List<DeviceFavorite> favoriteList) {
		this.favoriteList = favoriteList;
	}

	public class DeviceFavorite {
		private long id;
		private String name;
		List<SvDevice> channelList;

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<SvDevice> getChannelList() {
			return channelList;
		}

		public void setChannelList(List<SvDevice> channelList) {
			this.channelList = channelList;
		}

	}
}
