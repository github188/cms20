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
 * UserDeviceFavorite
 * 
 * @author sjt
 *         <p />
 *         Create at 2016年 下午14:41:37
 */
@Entity
@Table(name = "r_user_device_favorite")
@TableGenerator(name = "userDeviceFavoriteGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", pkColumnValue = "user_device_favorite_id", initialValue = 1, allocationSize = 1)
public class UserDeviceFavorite implements Serializable {

	private static final long serialVersionUID = 5935591176774235317L;

	@Id
	@GeneratedValue(generator = "userDeviceFavoriteGen", strategy = GenerationType.TABLE)
	private Long id;

	@Column(name = "favorite_id")
	private Long favoriteId;

	@Column(name = "device_id")
	private Long deviceId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(Long favoriteId) {
		this.favoriteId = favoriteId;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

}
