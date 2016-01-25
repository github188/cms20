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
@Table(name = "common_package_version")
public class PackageVersion implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9006723053179702756L;

	@Id
	private Long id;
	private String name;

	@Column(name = "resource_id")
	private Long resoureId;

	@Column(name = "package_version")
	private String version;
	
	@Column(name = "package_type")
	private String type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getResoureId() {
		return resoureId;
	}

	public void setResoureId(Long resoureId) {
		this.resoureId = resoureId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

}
