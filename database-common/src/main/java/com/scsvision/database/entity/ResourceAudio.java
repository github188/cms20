package com.scsvision.database.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ResourceAudio
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午3:45:22
 */
@Entity
@Table(name = "resource_audio")
public class ResourceAudio implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5322385828369939146L;

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

	@Column(name = "call_number")
	private String callNumber;

	@Column(name = "answer_number")
	private String answerNumber;

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

	public String getCallNumber() {
		return callNumber;
	}

	public void setCallNumber(String callNumber) {
		this.callNumber = callNumber;
	}

	public String getAnswerNumber() {
		return answerNumber;
	}

	public void setAnswerNumber(String answerNumber) {
		this.answerNumber = answerNumber;
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
