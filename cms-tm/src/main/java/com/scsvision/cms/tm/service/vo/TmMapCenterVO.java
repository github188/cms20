/**
 * 
 */
package com.scsvision.cms.tm.service.vo;

/**
 * @author wangbinyu
 *
 */
public class TmMapCenterVO {
	private String id;
	
	private String userId;

	private String longitude;

	private String latitude;
	
	private String mapLevel;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getMapLevel() {
		return mapLevel;
	}

	public void setMapLevel(String mapLevel) {
		this.mapLevel = mapLevel;
	}
}
