package com.scsvision.database.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ResourceVideo
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午3:38:18
 */
@Entity
@Table(name = "resource_video")
public class ResourceVideo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4943337546469363036L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Column(name = "stream_type")
	private String streamType;

	@Column(name = "begin_time")
	private Long beginTime;

	@Column(name = "end_time")
	private Long endTime;

	private Long duration;

	@Column(name = "device_id")
	private Long deviceId;

	private String address;

	@Column(name = "server_id")
	private Long serverId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreamType() {
		return streamType;
	}

	public void setStreamType(String streamType) {
		this.streamType = streamType;
	}

	public Long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getServerId() {
		return serverId;
	}

	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}

}
