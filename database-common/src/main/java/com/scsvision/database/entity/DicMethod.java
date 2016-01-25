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
@Table(name = "common_dic_method")
@TableGenerator(name = "methodGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", pkColumnValue = "dic_method_id", initialValue = 100, allocationSize = 1)
public class DicMethod implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6569649751525618888L;

	@Id
	@GeneratedValue(generator = "methodGen", strategy = GenerationType.TABLE)
	private Long id;

	@Column(name = "operation_name")
	private String operationName;

	private String method;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
}
