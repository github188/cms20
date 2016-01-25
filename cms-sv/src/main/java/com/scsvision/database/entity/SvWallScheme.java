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
@Table(name = "sv_wall_scheme")
@TableGenerator(name = "svWallSchemeGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", pkColumnValue = "sv_wall_scheme_id", initialValue = 1000, allocationSize = 1)
public class SvWallScheme implements Serializable{
	
	private static final long serialVersionUID = 1929862779397754890L;

	@Id
	@GeneratedValue(generator = "svWallSchemeGen", strategy = GenerationType.TABLE)
	private Long id;

	@Column(name = "user_group_id")
	private Long userGroupId;

	@Column(name = "wall_id")
	private Long wallId;

	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(Long userGroupId) {
		this.userGroupId = userGroupId;
	}

	public Long getWallId() {
		return wallId;
	}

	public void setWallId(Long wallId) {
		this.wallId = wallId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
