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
 * DicType
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 上午10:13:30
 */
@Entity
@Table(name = "common_dic_type")
@TableGenerator(name = "typeGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", pkColumnValue = "dic_type_id", initialValue = 100, allocationSize = 1)
public class DicType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6965070988039471145L;

	@Id
	@GeneratedValue(generator = "typeGen", strategy=GenerationType.TABLE)
	private Long id;

	private Integer type;

	@Column(name = "type_name")
	private String typeName;

	@Column(name = "sub_type")
	private Integer subType;

	@Column(name = "sub_type_name")
	private String subTypeName;

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

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getSubType() {
		return subType;
	}

	public void setSubType(Integer subType) {
		this.subType = subType;
	}

	public String getSubTypeName() {
		return subTypeName;
	}

	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}

}
