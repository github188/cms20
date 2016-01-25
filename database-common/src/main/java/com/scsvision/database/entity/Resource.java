package com.scsvision.database.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * Resource
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午3:34:02
 */
@Entity
@Table(name = "resource")
@TableGenerator(name = "resourceGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", pkColumnValue = "resource_id", initialValue = 100, allocationSize = 1)
public class Resource implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3051626048453277485L;

	@Id
	@GeneratedValue(generator = "resourceGen", strategy=GenerationType.TABLE)
	private Long id;

	private Integer type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
