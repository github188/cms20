/**
 * 
 */
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
 * @author wangbinyu
 *
 */
@Entity
@Table(name = "sv_preset")
@TableGenerator(name = "svPresetGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", pkColumnValue = "sv_preset_id", initialValue = 1, allocationSize = 1)
public class SvPreset implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -914072621039761005L;

	@Id
	@GeneratedValue(generator = "svPresetGen", strategy = GenerationType.TABLE)
	private Long id;

	private String name;

	@Column(name = "device_id")
	private Long deviceId;

	@Column(name = "preset_value")
	private Integer presetValue;

	@Column(name = "resource_id")
	private Long resourceId;

	private Short flag;

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

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getPresetValue() {
		return presetValue;
	}

	public void setPresetValue(Integer presetValue) {
		this.presetValue = presetValue;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Short getFlag() {
		return flag;
	}

	public void setFlag(Short flag) {
		this.flag = flag;
	}

}
