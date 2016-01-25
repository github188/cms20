package com.scsvision.database.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * DicManufacture
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 上午10:09:37
 */
@Entity
@Table(name = "common_dic_manufacture")
@TableGenerator(name = "manufactureGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", pkColumnValue = "dic_manufacture_id", initialValue = 100, allocationSize = 1)
public class DicManufacture implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1695767142952236583L;

	@Id
	@GeneratedValue(generator = "manufactureGen", strategy = GenerationType.TABLE)
	private Long id;

	private String name;

	private String protocol;

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

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

}
