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
 * EventReal
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午4:32:39
 */
@Entity
@Table(name = "em_event_real")
@TableGenerator(name = "eventRealGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", pkColumnValue = "event_real_id", initialValue = 1, allocationSize = 1)
public class EventReal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1737397788349283890L;

	@Id
	@GeneratedValue(generator = "eventRealGen", strategy = GenerationType.TABLE)
	private Long id;

	private String name;

	@Column(name = "business_id")
	private String businessId;

	@Column(name = "link_man")
	private String linkMan;

	private Integer type;

	@Column(name = "sub_type")
	private Integer subType;

	@Column(name = "detail_type")
	private String detailType;

	@Column(name = "create_time")
	private Long createTime;

	@Column(name = "event_level")
	private Integer eventLevel;

	private String location;

	@Column(name = "source_name")
	private String sourceName;

	@Column(name = "confirm_time")
	private Long confirmTime;

	// 真实机构ID
	@Column(name = "organ_id")
	private Long organId;

	@Column(name = "confirm_user_name")
	private String confirmUserName;

	@Column(name = "alarm_content")
	private String alarmContent;

	@Column(name = "confirm_flag")
	private Short confirmFlag;

	@Column(name = "confirm_note")
	private String ConfirmNote;

	@Column(name = "resource_flag")
	private Short resourceFlag;

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

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public Integer getSubType() {
		return subType;
	}

	public void setSubType(Integer subType) {
		this.subType = subType;
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

	public String getSourceName() {
		return sourceName;
	}

	public String getDetailType() {
		return detailType;
	}

	public void setDetailType(String detailType) {
		this.detailType = detailType;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public Integer getEventLevel() {
		return eventLevel;
	}

	public void setEventLevel(Integer eventLevel) {
		this.eventLevel = eventLevel;
	}

	public Long getOrganId() {
		return organId;
	}

	public void setOrganId(Long organId) {
		this.organId = organId;
	}

	public String getConfirmUserName() {
		return confirmUserName;
	}

	public void setConfirmUserName(String confirmUserName) {
		this.confirmUserName = confirmUserName;
	}

	public String getAlarmContent() {
		return alarmContent;
	}

	public void setAlarmContent(String alarmContent) {
		this.alarmContent = alarmContent;
	}

	public Short getConfirmFlag() {
		return confirmFlag;
	}

	public void setConfirmFlag(Short confirmFlag) {
		this.confirmFlag = confirmFlag;
	}

	public String getConfirmNote() {
		return ConfirmNote;
	}

	public void setConfirmNote(String confirmNote) {
		ConfirmNote = confirmNote;
	}

	public Long getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Long confirmTime) {
		this.confirmTime = confirmTime;
	}

	public Short getResourceFlag() {
		return resourceFlag;
	}

	public void setResourceFlag(Short resourceFlag) {
		this.resourceFlag = resourceFlag;
	}

}
