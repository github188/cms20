package com.scsvision.cms.maintain.dto;

import java.util.List;

import com.scsvision.cms.response.BaseDTO;

/**
 * 
 * ListDeviceDTO
 * 
 * @author sjt
 *         <p />
 *         Create at 2016年 上午10:02:58
 */

public class ListDeviceDTO extends BaseDTO {
	private List<Device> svList;
	private List<Device> vdList;
	private List<Device> cmsList;
	private List<Device> tslList;
	private List<Device> lilList;
	private List<Device> wstList;
	private List<Device> wsList;
	private List<Device> rdList;
	private List<Device> bdList;
	private List<Device> viList;
	private List<Device> coviList;
	private List<Device> noList;
	private List<Device> loLiList;
	private List<Device> switchList;

	public List<Device> getSvList() {
		return svList;
	}

	public void setSvList(List<Device> svList) {
		this.svList = svList;
	}

	public List<Device> getVdList() {
		return vdList;
	}

	public void setVdList(List<Device> vdList) {
		this.vdList = vdList;
	}

	public List<Device> getCmsList() {
		return cmsList;
	}

	public void setCmsList(List<Device> cmsList) {
		this.cmsList = cmsList;
	}

	public List<Device> getTslList() {
		return tslList;
	}

	public void setTslList(List<Device> tslList) {
		this.tslList = tslList;
	}

	public List<Device> getLilList() {
		return lilList;
	}

	public void setLilList(List<Device> lilList) {
		this.lilList = lilList;
	}

	public List<Device> getWstList() {
		return wstList;
	}

	public void setWstList(List<Device> wstList) {
		this.wstList = wstList;
	}

	public List<Device> getWsList() {
		return wsList;
	}

	public void setWsList(List<Device> wsList) {
		this.wsList = wsList;
	}

	public List<Device> getRdList() {
		return rdList;
	}

	public void setRdList(List<Device> rdList) {
		this.rdList = rdList;
	}

	public List<Device> getBdList() {
		return bdList;
	}

	public void setBdList(List<Device> bdList) {
		this.bdList = bdList;
	}

	public List<Device> getViList() {
		return viList;
	}

	public void setViList(List<Device> viList) {
		this.viList = viList;
	}

	public List<Device> getCoviList() {
		return coviList;
	}

	public void setCoviList(List<Device> coviList) {
		this.coviList = coviList;
	}

	public List<Device> getNoList() {
		return noList;
	}

	public void setNoList(List<Device> noList) {
		this.noList = noList;
	}

	public List<Device> getLoLiList() {
		return loLiList;
	}

	public void setLoLiList(List<Device> loLiList) {
		this.loLiList = loLiList;
	}

	public List<Device> getSwitchList() {
		return switchList;
	}

	public void setSwitchList(List<Device> switchList) {
		this.switchList = switchList;
	}

	public class Device {
		private String name;
		private String deviceModel;
		private String pid;
		private String type;
		private boolean isOnline;
		private String id;
		private String manufacturer;
		private String standarNumber;
		private String stakeNumber;
		private String longitude;
		private String latitude;
		private String organName;
		private String laneNumber;
		private String width;
		private String height;
		private String navigation;
		private String channelNumber;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDeviceModel() {
			return deviceModel;
		}

		public void setDeviceModel(String deviceModel) {
			this.deviceModel = deviceModel;
		}

		public String getPid() {
			return pid;
		}

		public void setPid(String pid) {
			this.pid = pid;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public boolean isOnline() {
			return isOnline;
		}

		public void setOnline(boolean isOnline) {
			this.isOnline = isOnline;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getManufacturer() {
			return manufacturer;
		}

		public void setManufacturer(String manufacturer) {
			this.manufacturer = manufacturer;
		}

		public String getStandarNumber() {
			return standarNumber;
		}

		public void setStandarNumber(String standarNumber) {
			this.standarNumber = standarNumber;
		}

		public String getStakeNumber() {
			return stakeNumber;
		}

		public void setStakeNumber(String stakeNumber) {
			this.stakeNumber = stakeNumber;
		}

		public String getLongitude() {
			return longitude;
		}

		public void setLongitude(String longitude) {
			this.longitude = longitude;
		}

		public String getLatitude() {
			return latitude;
		}

		public void setLatitude(String latitude) {
			this.latitude = latitude;
		}

		public String getOrganName() {
			return organName;
		}

		public void setOrganName(String organName) {
			this.organName = organName;
		}

		public String getLaneNumber() {
			return laneNumber;
		}

		public void setLaneNumber(String laneNumber) {
			this.laneNumber = laneNumber;
		}

		public String getWidth() {
			return width;
		}

		public void setWidth(String width) {
			this.width = width;
		}

		public String getHeight() {
			return height;
		}

		public void setHeight(String height) {
			this.height = height;
		}

		public String getNavigation() {
			return navigation;
		}

		public void setNavigation(String navigation) {
			this.navigation = navigation;
		}

		public String getChannelNumber() {
			return channelNumber;
		}

		public void setChannelNumber(String channelNumber) {
			this.channelNumber = channelNumber;
		}

	}

}
