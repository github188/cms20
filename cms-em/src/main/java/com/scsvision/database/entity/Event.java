package com.scsvision.database.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Event
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 上午9:30:40
 */
@Entity
@Table(name = "em_event")
// @TableGenerator(name = "eventGen", table = "id_gen", pkColumnName =
// "gen_key", valueColumnName = "gen_value", pkColumnValue = "event_id",
// initialValue = 100, allocationSize = 20)
public class Event implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4922691810211985344L;

	@Id
	// @GeneratedValue(generator = "eventGen", strategy = GenerationType.TABLE)
	private Long id;

	private String name;

	private Integer type;

	@Column(name = "sub_type")
	private Integer subType;

	@Column(name = "detail_type")
	private String detailType;

	@Column(name = "event_level")
	private Integer eventLevel;

	@Column(name = "create_time")
	private Long createTime;

	private String location;

	@Column(name = "source_name")
	private String sourceName;

	@Column(name = "confirm_time")
	private Long confirmTime;

	@Column(name = "recover_time")
	private Long recoverTime;

	@Column(name = "confirm_flag")
	private Short confirmFlag;

	@Column(name = "confirm_user_name")
	private String confirmUserName;

	@Column(name = "resource_flag")
	private Short resourceFlag;

	@Column(name = "confirm_note")
	private String confirmNote;

	// 真实机构ID
	@Column(name = "organ_id")
	private Long organId;

	@Column(name = "alarm_content")
	private String alarmContent;

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSubType() {
		return subType;
	}

	public void setSubType(Integer subType) {
		this.subType = subType;
	}

	public Integer getEventLevel() {
		return eventLevel;
	}

	public void setEventLevel(Integer eventLevel) {
		this.eventLevel = eventLevel;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDetailType() {
		return detailType;
	}

	public void setDetailType(String detailType) {
		this.detailType = detailType;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public Long getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Long confirmTime) {
		this.confirmTime = confirmTime;
	}

	public Long getRecoverTime() {
		return recoverTime;
	}

	public void setRecoverTime(Long recoverTime) {
		this.recoverTime = recoverTime;
	}

	public Short getConfirmFlag() {
		return confirmFlag;
	}

	public void setConfirmFlag(Short confirmFlag) {
		this.confirmFlag = confirmFlag;
	}

	public String getConfirmUserName() {
		return confirmUserName;
	}

	public void setConfirmUserName(String confirmUserName) {
		this.confirmUserName = confirmUserName;
	}

	public Short getResourceFlag() {
		return resourceFlag;
	}

	public void setResourceFlag(Short resourceFlag) {
		this.resourceFlag = resourceFlag;
	}

	public String getConfirmNote() {
		return confirmNote;
	}

	public void setConfirmNote(String confirmNote) {
		this.confirmNote = confirmNote;
	}

	public Long getOrganId() {
		return organId;
	}

	public void setOrganId(Long organId) {
		this.organId = organId;
	}

	public String getAlarmContent() {
		return alarmContent;
	}

	public void setAlarmContent(String alarmContent) {
		this.alarmContent = alarmContent;
	}

}
