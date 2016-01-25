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
@Table(name = "tm_map_center")
@TableGenerator(name = "tmMapCenterGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", pkColumnValue = "tm_map_center_id", initialValue = 100, allocationSize = 1)
public class TmMapCenter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3912387317327022420L;

	@Id
	@GeneratedValue(generator = "tmMapCenterGen", strategy = GenerationType.TABLE)
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	private String longitude;

	private String latitude;

	@Column(name = "map_level")
	private Integer mapLevel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Integer getMapLevel() {
		return mapLevel;
	}

	public void setMapLevel(Integer mapLevel) {
		this.mapLevel = mapLevel;
	}
}
