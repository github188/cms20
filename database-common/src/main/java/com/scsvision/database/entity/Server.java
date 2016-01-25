package com.scsvision.database.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * Server
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 上午10:19:05
 */
@Entity
@Table(name = "common_server")
@TableGenerator(name = "serverGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", pkColumnValue = "server_id", initialValue = 20000, allocationSize = 1)
public class Server implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2577379231222608L;

	@Id
	@GeneratedValue(generator = "serverGen", strategy=GenerationType.TABLE)
	private Long id;

	private String name;

	@Column(name = "standard_number")
	private String standardNumber;

	private Integer type;

	private String ip1;

	private String ip2;

	private String ip3;

	private String ip4;

	private Integer port;

	@Column(name = "config_value")
	@Lob
	private String configValue;

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getIp1() {
		return ip1;
	}

	public void setIp1(String ip1) {
		this.ip1 = ip1;
	}

	public String getIp2() {
		return ip2;
	}

	public void setIp2(String ip2) {
		this.ip2 = ip2;
	}

	public String getIp3() {
		return ip3;
	}

	public void setIp3(String ip3) {
		this.ip3 = ip3;
	}

	public String getIp4() {
		return ip4;
	}

	public void setIp4(String ip4) {
		this.ip4 = ip4;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

}
