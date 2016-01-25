package com.scsvision.database.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ResourcePackage
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午3:49:33
 */
@Entity
@Table(name = "resource_package")
public class ResourcePackage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1641427281662002847L;

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Column(name = "module_name")
	private String moduleName;

	@Column(name = "package_size")
	private Long packageSize;

	@Column(name = "create_time")
	private Long createTime;

	@Column(name = "package_version")
	private String packageVersion;

	@Column(name = "svn_version")
	private String svnVersion;

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

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Long getPackageSize() {
		return packageSize;
	}

	public void setPackageSize(Long packageSize) {
		this.packageSize = packageSize;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getPackageVersion() {
		return packageVersion;
	}

	public void setPackageVersion(String packageVersion) {
		this.packageVersion = packageVersion;
	}

	public String getSvnVersion() {
		return svnVersion;
	}

	public void setSvnVersion(String svnVersion) {
		this.svnVersion = svnVersion;
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
