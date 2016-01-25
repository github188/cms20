/**
 * 
 */
package com.scsvision.database.entity;

import java.io.Serializable;

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
@Table(name = "common_dic_management")
@TableGenerator(name = "managementGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", pkColumnValue = "dic_management_id", initialValue = 1, allocationSize = 1)
public class DicManagement implements Serializable{
	
	private static final long serialVersionUID = -8998069675649505391L;
	
	@Id
	@GeneratedValue(generator = "managementGen", strategy = GenerationType.TABLE)
	private Long id;
	
	private String name;

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

}
