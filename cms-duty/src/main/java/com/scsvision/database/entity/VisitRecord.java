package com.scsvision.database.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * VisitRecord
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午2:55:45
 */
@Entity
@Table(name = "duty_visit_record")
@TableGenerator(name = "dutyVisitRecordGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", pkColumnValue = "duty_visit_record_id", initialValue = 100, allocationSize = 100)
public class VisitRecord implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -211896175004536402L;

	@Id
	@GeneratedValue(generator = "dutyVisitRecordGen", strategy = GenerationType.TABLE)
	private Long id;

	private String visitors;

	private String phone;

	private String reason;

	@Column(name = "arrive_time")
	private Long arriveTime;

	@Column(name = "leave_time")
	private Long leaveTime;

	@Column(name = "record_time")
	private Long recordTime;

	private String result;

	private String note;

	private String monitors;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVisitors() {
		return visitors;
	}

	public void setVisitors(String visitors) {
		this.visitors = visitors;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(Long arriveTime) {
		this.arriveTime = arriveTime;
	}

	public Long getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(Long leaveTime) {
		this.leaveTime = leaveTime;
	}

	public Long getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Long recordTime) {
		this.recordTime = recordTime;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getMonitors() {
		return monitors;
	}

	public void setMonitors(String monitors) {
		this.monitors = monitors;
	}

}
