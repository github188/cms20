package com.scsvision.database.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "common_dic_error")
@TableGenerator(name = "errorGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_vlue", pkColumnValue = "error_value", initialValue = 100, allocationSize = 1)
public class ErrorMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -357077246358519748L;
	
	private Long id;
	
	private String errormsgCode;
	
	private String msg;
	
	@Id
	@GeneratedValue(generator="errorGen",strategy=GenerationType.TABLE)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="error_code")
	public String getErrormsgCode() {
		return errormsgCode;
	}

	public void setErrormsgCode(String errormsgCode) {
		this.errormsgCode = errormsgCode;
	}
	
	@Column(name="error_msg")
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
