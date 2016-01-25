/**
 * 
 */
package com.scsvision.database.service.vo;

/**
 * @author wangbinyu
 *
 */
public class OrganVO {
	private Long id;

	private String name;

	private Integer type;

	private String path;

	private String beginStake;

	private String endStake;

	private Float length;

	private Integer laneNumber;

	private Integer entranceNumber;

	private Integer exitNumber;

	private String parentName;

	private Long parentId;

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getBeginStake() {
		return beginStake;
	}

	public void setBeginStake(String beginStake) {
		this.beginStake = beginStake;
	}

	public String getEndStake() {
		return endStake;
	}

	public void setEndStake(String endStake) {
		this.endStake = endStake;
	}

	public Float getLength() {
		return length;
	}

	public void setLength(Float length) {
		this.length = length;
	}

	public Integer getLaneNumber() {
		return laneNumber;
	}

	public void setLaneNumber(Integer laneNumber) {
		this.laneNumber = laneNumber;
	}

	public Integer getEntranceNumber() {
		return entranceNumber;
	}

	public void setEntranceNumber(Integer entranceNumber) {
		this.entranceNumber = entranceNumber;
	}

	public Integer getExitNumber() {
		return exitNumber;
	}

	public void setExitNumber(Integer exitNumber) {
		this.exitNumber = exitNumber;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
