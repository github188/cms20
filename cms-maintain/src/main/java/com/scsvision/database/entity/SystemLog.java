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
 * SystemLog
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午3:15:53
 */
@Entity
@Table(name = "maintain_system_log")
@TableGenerator(name = "systemLogGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", pkColumnValue = "system_log_id", initialValue = 100, allocationSize = 1000)
public class SystemLog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7931066048627736167L;

	@Id
	@GeneratedValue(generator = "systemLogGen", strategy=GenerationType.TABLE)
	private Long id;

	@Column(name = "module_name")
	private String moduleName;

	private String method;

	@Column(name = "create_time")
	private Long createTime;

	@Column(name = "standard_number")
	private String standardNumber;

	@Column(name = "business_id")
	private String businessId;

	private String level;

	private String content;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
