package com.scsvision.database.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * UserGroup
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 上午9:37:42
 */
@Entity
@Table(name = "common_user_group")
@TableGenerator(name = "userGroupGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", pkColumnValue = "user_group_id", initialValue = 20000, allocationSize = 1)
public class UserGroup implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7011830902132351259L;

	@Id
	@GeneratedValue(generator = "userGroupGen", strategy=GenerationType.TABLE)
	private Long id;

	private String name;

	private String note;

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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
