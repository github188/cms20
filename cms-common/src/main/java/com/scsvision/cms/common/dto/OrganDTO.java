package com.scsvision.cms.common.dto;

import java.util.List;

import com.scsvision.cms.response.BaseDTO;

/**
 * OrganDTO
 * 
 * @author sjt
 *         <p />
 *         Create at 2015年 上午11:24:54
 */

public class OrganDTO extends BaseDTO {
	private List<OrganDto> list;

	public List<OrganDto> getList() {
		return list;
	}

	public void setList(List<OrganDto> list) {
		this.list = list;
	}

	public class OrganDto {
		private Long id;
		private String name;
		private String standardNumber;
		private Long parentId;
		private String path;
		private String beginStake;
		private String endStake;
		private String longitude;
		private String latitude;
		private Integer type;
		private Float length;
		private Integer laneNumber;
		private Integer entranceNumber;
		private Integer exitNumber;
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
}
