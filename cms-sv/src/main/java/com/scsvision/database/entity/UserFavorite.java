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
 * UserFavorite
 * 
 * @author sjt
 *         <p />
 *         Create at 2016年 上午11:21:37
 */
@Entity
@Table(name = "sv_user_favorite")
@TableGenerator(name = "userFavoriteGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", pkColumnValue = "user_favorite_id", initialValue = 1, allocationSize = 1)
public class UserFavorite implements Serializable {

	private static final long serialVersionUID = 5935591176774235317L;

	@Id
	@GeneratedValue(generator = "userFavoriteGen", strategy = GenerationType.TABLE)
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "favorite_name")
	private String favoriteName;

	@Column(name = "create_time")
	private Long createTime;

	private String note;

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

	public String getFavoriteName() {
		return favoriteName;
	}

	public void setFavoriteName(String favoriteName) {
		this.favoriteName = favoriteName;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
