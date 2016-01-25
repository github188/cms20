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
 * @author wangbinyu
 *
 */
@Entity
@Table(name = "sv_monitor")
@TableGenerator(name = "svMonitorGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", pkColumnValue = "sv_play_scheme_id", initialValue = 1, allocationSize = 1)
public class SvMonitor implements Serializable {

	private static final long serialVersionUID = -8881892381866318952L;

	@Id
	@GeneratedValue(generator = "svMonitorGen", strategy = GenerationType.TABLE)
	private Long id;

	private String name;

	@Column(name = "standard_number")
	private String standardNumber;

	@Column(name = "channel_number")
	private String channelNumber;

	private String x;

	private String y;

	private String width;

	private String height;

	@Column(name = "dws_id")
	private Long dwsId;

	@Column(name = "wall_id")
	private Long wallId;

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

	public String getChannelNumber() {
		return channelNumber;
	}

	public void setChannelNumber(String channelNumber) {
		this.channelNumber = channelNumber;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public Long getDwsId() {
		return dwsId;
	}

	public void setDwsId(Long dwsId) {
		this.dwsId = dwsId;
	}

	public Long getWallId() {
		return wallId;
	}

	public void setWallId(Long wallId) {
		this.wallId = wallId;
	}

}
