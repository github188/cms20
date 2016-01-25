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
 * DutyRecord
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午5:04:33
 */
@Entity
@Table(name = "duty_record")
@TableGenerator(name = "dutyRecordGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", pkColumnValue = "duty_record_id", initialValue = 100, allocationSize = 10)
public class DutyRecord implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8249093421062619059L;

	@Id
	@GeneratedValue(generator = "dutyRecordGen", strategy = GenerationType.TABLE)
	private Long id;

	private String phone;

	private String name;

	@Column(name = "begin_time")
	private Long beginTime;

	@Column(name = "end_time")
	private Long endTime;

	private String note;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
