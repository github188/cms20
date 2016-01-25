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
 * DeviceFaultRecord
 * 
 * @author sjt
 *         <p />
 *         Create at 2015 下午3:04:33
 */
@Entity
@Table(name = "duty_device_fault_record")
@TableGenerator(name = "deviceFaultRecordGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", pkColumnValue = "device_fault_record_id", initialValue = 100, allocationSize = 10)
public class DeviceFaultRecord implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8249093421062619059L;

	@Id
	@GeneratedValue(generator = "deviceFaultRecordGen", strategy = GenerationType.TABLE)
	private Long id;

	// 设备id
	@Column(name = "device_id")
	private Long deviceId;

	// 设备类型
	@Column(name = "device_type")
	private String deviceType;

	@Column(name = "record_time")
	private Long recordTime;

	@Column(name = "recover_time")
	private Long recoverTime;

	// 设备故障发生位置
	@Column(name = "stake_number")
	private String stakeNumber;

	// 设备名称
	@Column(name = "device_name")
	private String deviceName;

	// 填报人
	@Column(name = "maintainers")
	private String maintainer;

	// 确认时间，暂定与创建时间一样与创建
	@Column(name = "confirm_time")
	private Long confirmTime;

	// 确认票号
	@Column(name = "confirm_flag")
	private Short confirmFlag;

	// 确认人，暂定与填报人一样
	@Column(name = "confirm_user_name")
	private String confirmUserName;

	// 真实机构ID
	@Column(name = "organ_id")
	private Long organId;

	// 填报报警信息
	@Column(name = "note")
	private String Note;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public Long getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Long recordTime) {
		this.recordTime = recordTime;
	}

	public Long getRecoverTime() {
		return recoverTime;
	}

	public void setRecoverTime(Long recoverTime) {
		this.recoverTime = recoverTime;
	}

	public String getStakeNumber() {
		return stakeNumber;
	}

	public void setStakeNumber(String stakeNumber) {
		this.stakeNumber = stakeNumber;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getMaintainer() {
		return maintainer;
	}

	public void setMaintainer(String maintainer) {
		this.maintainer = maintainer;
	}

	public Long getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Long confirmTime) {
		this.confirmTime = confirmTime;
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

	public Long getOrganId() {
		return organId;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public void setOrganId(Long organId) {
		this.organId = organId;
	}

	public String getNote() {
		return Note;
	}

	public void setNote(String note) {
		Note = note;
	}

}
