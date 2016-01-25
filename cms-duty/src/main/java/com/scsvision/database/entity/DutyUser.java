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
 * DutyTeam
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午5:33:50
 */
@Entity
@Table(name = "duty_user")
@TableGenerator(name = "dutyUserGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", pkColumnValue = "duty_user_id", initialValue = 100, allocationSize = 10)
public class DutyUser implements Serializable {

	private static final long serialVersionUID = 6049046641655772773L;

	@Id
	@GeneratedValue(generator = "dutyUserGen", strategy = GenerationType.TABLE)
	private Long id;

	private String phone;

	private Short status;

	@Column(name = "login_name")
	private String loginName;

	private String password;

	private String name;

	@Column(name = "organ_id")
	private Long organId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Long getOrganId() {
		return organId;
	}

	public void setOrganId(Long organId) {
		this.organId = organId;
	}

}
