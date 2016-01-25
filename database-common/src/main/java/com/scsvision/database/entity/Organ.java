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
 * @author MIKE
 *
 */
@Entity
@Table(name = "common_organ")
@TableGenerator(name = "organGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", pkColumnValue = "organ_id", initialValue = 20000, allocationSize = 1)
public class Organ implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9206751590910953214L;

	@Id
	@GeneratedValue(generator = "organGen", strategy = GenerationType.TABLE)
	private Long id;

	private String name;

	@Column(name = "standard_number")
	private String standardNumber;

	@Column(name = "parent_id")
	private Long parentId;

	private String path;

	@Column(name = "begin_stake")
	private String beginStake;

	@Column(name = "end_stake")
	private String endStake;

	private String longitude;

	private String latitude;

	private Integer type;

	private Float length;

	@Column(name = "lane_number")
	private Integer laneNumber;

	@Column(name = "entrance_number")
	private Integer entranceNumber;

	@Column(name = "exit_number")
	private Integer exitNumber;

	@Column(name = "road_number")
	private String roadNumber;

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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
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

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public String getRoadNumber() {
		return roadNumber;
	}

	public void setRoadNumber(String roadNumber) {
		this.roadNumber = roadNumber;
	}

}
