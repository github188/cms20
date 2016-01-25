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
 * VirtualOrgan
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 上午9:41:44
 */
@Entity
@Table(name = "common_virtual_organ")
@TableGenerator(name = "virtualOrganGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", pkColumnValue = "virtual_organ_id", initialValue = 20000, allocationSize = 10)
public class VirtualOrgan implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5898541149180261745L;

	@Id
	@GeneratedValue(generator = "virtualOrganGen", strategy=GenerationType.TABLE)
	private Long id;

	private String name;

	// 资源主类型(1000-1999)机构, (2000-2999)虚拟机构, (3000-3999)视频设备, (4000-4999)数据设备
	private Integer type;

	private Integer visible;

	@Column(name = "device_id")
	private Long deviceId;

	@Column(name = "parent_id")
	private Long parentId;

	private String path;

	@Column(name = "user_group_id")
	private Long userGroupId;

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

	public Integer getVisible() {
		return visible;
	}

	public void setVisible(Integer visible) {
		this.visible = visible;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(Long userGroupId) {
		this.userGroupId = userGroupId;
	}

}
