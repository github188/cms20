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
 * User
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015年8月6日 下午4:48:47
 */
@Entity
@Table(name = "common_user")
@TableGenerator(name = "userGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", pkColumnValue = "user_id", initialValue = 20000, allocationSize = 1)
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5039887174845142113L;

	@Id
	@GeneratedValue(generator = "userGen", strategy = GenerationType.TABLE)
	private Long id;

	private String name;

	@Column(name = "standard_number")
	private String standardNumber;

	@Column(name = "logon_name")
	private String logonName;

	private String password;

	@Column(name = "user_group_id")
	private Long userGroupId;

	@Column(name = "tunnel_priv")
	private Short tunnelPriv;

	@Column(name = "event_priv")
	private Short eventPriv;

	@Column(name = "statistic_priv")
	private Short statisticPriv;

	@Column(name = "road_priv")
	private Short roadPriv;

	@Column(name = "video_priv")
	private Short viedoPriv;

	@Column(name = "work_priv")
	private Short workPriv;

	@Column(name = "alarm_priv")
	private Short alarmPriv;

	@Column(name = "info_priv")
	private Short infoPriv;

	@Column(name = "system_priv")
	private Short systemPriv;

	@Column(name = "admin_priv")
	private Short adminPriv;

	@Column(name = "organ_id")
	private Long organId;

	public User() {

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

	public String getLogonName() {
		return logonName;
	}

	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(Long userGroupId) {
		this.userGroupId = userGroupId;
	}

	public Short getTunnelPriv() {
		return tunnelPriv;
	}

	public void setTunnelPriv(Short tunnelPriv) {
		this.tunnelPriv = tunnelPriv;
	}

	public Short getEventPriv() {
		return eventPriv;
	}

	public void setEventPriv(Short eventPriv) {
		this.eventPriv = eventPriv;
	}

	public Short getStatisticPriv() {
		return statisticPriv;
	}

	public void setStatisticPriv(Short statisticPriv) {
		this.statisticPriv = statisticPriv;
	}

	public Short getRoadPriv() {
		return roadPriv;
	}

	public void setRoadPriv(Short roadPriv) {
		this.roadPriv = roadPriv;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Short getViedoPriv() {
		return viedoPriv;
	}

	public void setViedoPriv(Short viedoPriv) {
		this.viedoPriv = viedoPriv;
	}

	public Short getWorkPriv() {
		return workPriv;
	}

	public void setWorkPriv(Short workPriv) {
		this.workPriv = workPriv;
	}

	public Short getAlarmPriv() {
		return alarmPriv;
	}

	public void setAlarmPriv(Short alarmPriv) {
		this.alarmPriv = alarmPriv;
	}

	public Short getInfoPriv() {
		return infoPriv;
	}

	public void setInfoPriv(Short infoPriv) {
		this.infoPriv = infoPriv;
	}

	public Short getSystemPriv() {
		return systemPriv;
	}

	public void setSystemPriv(Short systemPriv) {
		this.systemPriv = systemPriv;
	}

	public Short getAdminPriv() {
		return adminPriv;
	}

	public void setAdminPriv(Short adminPriv) {
		this.adminPriv = adminPriv;
	}

	public Long getOrganId() {
		return organId;
	}

	public void setOrganId(Long organId) {
		this.organId = organId;
	}

}
