package com.scsvision.database.entity;

import java.io.Serializable;

/**
 * Online
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午2:57:42
 */
public class Online implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8861339066503416196L;

	private String id;

	private Integer type;

	private Long resourceId;

	private String name;

	private String standardNumber;

	private String ip;

	private Long onlineTime;

	private Long offlineTime;

	private Long duration;

	private Integer kickFlag;

	private String clientType;

	private String ticket;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Long getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(Long onlineTime) {
		this.onlineTime = onlineTime;
	}

	public Long getOfflineTime() {
		return offlineTime;
	}

	public void setOfflineTime(Long offlineTime) {
		this.offlineTime = offlineTime;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Integer getKickFlag() {
		return kickFlag;
	}

	public void setKickFlag(Integer kickFlag) {
		this.kickFlag = kickFlag;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

}
