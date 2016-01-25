package com.scsvision.database.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * MonthVd
 * 
 * @author sjt
 *         <p />
 *         Create at 2015 上午9:34:15
 */
public class MonthVd implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6100278387242245842L;

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
