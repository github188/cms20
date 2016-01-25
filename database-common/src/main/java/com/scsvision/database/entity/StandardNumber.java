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
 * StandardNumber
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午2:51:18
 */
@Entity
@Table(name = "common_standard_number")
@TableGenerator(name = "standardNumberGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", pkColumnValue = "standard_number_id", initialValue = 20000, allocationSize = 10)
public class StandardNumber implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8821826689975235465L;

	@Id
	@GeneratedValue(generator = "standardNumberGen", strategy = GenerationType.TABLE)
	private Long id;

	private String name;

	@Column(name = "standard_number")
	private String standardNumber;

	@Column(name = "class_name")
	private String className;

	private String type;

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

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
