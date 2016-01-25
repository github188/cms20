package com.scsvision.database.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ResourceImage
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午3:43:39
 */
@Entity
@Table(name = "resource_image")
public class ResourceImage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1584241317694358980L;

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Column(name = "create_time")
	private Long createTime;

	private String address;

	@Column(name = "server_id")
	private Long serverId;

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

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getServerId() {
		return serverId;
	}

	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}

}
