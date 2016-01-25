package com.scsvision.database.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * DayVd
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午3:21:15
 */
public class DayVd implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6064663915120392197L;

	private String id;

	// 真实路段机构ID
	private Long organId;

	private Date recTime;

	private Double flux;

	private Double speedAvg;

	private Double occAvg;

	private Double headWayAvg;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getOrganId() {
		return organId;
	}

	public void setOrganId(Long organId) {
		this.organId = organId;
	}

	public Date getRecTime() {
		return recTime;
	}

	public void setRecTime(Date recTime) {
		this.recTime = recTime;
	}

	public Double getFlux() {
		return flux;
	}

	public void setFlux(Double flux) {
		this.flux = flux;
	}

	public Double getSpeedAvg() {
		return speedAvg;
	}

	public void setSpeedAvg(Double speedAvg) {
		this.speedAvg = speedAvg;
	}

	public Double getOccAvg() {
		return occAvg;
	}

	public void setOccAvg(Double occAvg) {
		this.occAvg = occAvg;
	}

	public Double getHeadWayAvg() {
		return headWayAvg;
	}

	public void setHeadWayAvg(Double headWayAvg) {
		this.headWayAvg = headWayAvg;
	}

}
