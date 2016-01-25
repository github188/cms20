package com.scsvision.database.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Vms
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 上午10:02:01
 */
public class GatherVms implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5844015988367403457L;

	private String id;

	private Long deviceId;

	private Date recTime;

	private String dispCont;

	private Long dispTime;

	private Integer dispSrc;

	private String color;

	private String fontSize;

	private String font;

	private Integer status;

	private Integer commStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public Date getRecTime() {
		return recTime;
	}

	public void setRecTime(Date recTime) {
		this.recTime = recTime;
	}

	public String getDispCont() {
		return dispCont;
	}

	public void setDispCont(String dispCont) {
		this.dispCont = dispCont;
	}

	public Long getDispTime() {
		return dispTime;
	}

	public void setDispTime(Long dispTime) {
		this.dispTime = dispTime;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getFontSize() {
		return fontSize;
	}

	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCommStatus() {
		return commStatus;
	}

	public void setCommStatus(Integer commStatus) {
		this.commStatus = commStatus;
	}

	public Integer getDispSrc() {
		return dispSrc;
	}

	public void setDispSrc(Integer dispSrc) {
		this.dispSrc = dispSrc;
	}

}
