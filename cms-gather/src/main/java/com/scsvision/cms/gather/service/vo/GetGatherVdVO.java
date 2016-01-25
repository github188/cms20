/**
 * 
 */
package com.scsvision.cms.gather.service.vo;

import java.util.Date;

/**
 * @author wangbinyu
 *
 */
public class GetGatherVdVO {
	private Double upFluxb;
	private Double upFluxs;
	private Double upOcc;
	private Double upSpeed;
	private Double upFlux;
	private Double dwFluxb;
	private Double dwFluxs;
	private Double dwOcc;
	private Double dwSpeed;
	private Double dwFlux;
	private Long deviceId;
	private Integer gatherInterval;
	private String recTime;

	public Double getUpFluxb() {
		return upFluxb;
	}

	public void setUpFluxb(Double upFluxb) {
		this.upFluxb = upFluxb;
	}

	public Double getUpFluxs() {
		return upFluxs;
	}

	public void setUpFluxs(Double upFluxs) {
		this.upFluxs = upFluxs;
	}

	public Double getUpOcc() {
		return upOcc;
	}

	public void setUpOcc(Double upOcc) {
		this.upOcc = upOcc;
	}

	public Double getUpSpeed() {
		return upSpeed;
	}

	public void setUpSpeed(Double upSpeed) {
		this.upSpeed = upSpeed;
	}

	public Double getUpFlux() {
		return upFlux;
	}

	public void setUpFlux(Double upFlux) {
		this.upFlux = upFlux;
	}

	public Double getDwFluxb() {
		return dwFluxb;
	}

	public void setDwFluxb(Double dwFluxb) {
		this.dwFluxb = dwFluxb;
	}

	public Double getDwFluxs() {
		return dwFluxs;
	}

	public void setDwFluxs(Double dwFluxs) {
		this.dwFluxs = dwFluxs;
	}

	public Double getDwOcc() {
		return dwOcc;
	}

	public void setDwOcc(Double dwOcc) {
		this.dwOcc = dwOcc;
	}

	public Double getDwSpeed() {
		return dwSpeed;
	}

	public void setDwSpeed(Double dwSpeed) {
		this.dwSpeed = dwSpeed;
	}

	public Double getDwFlux() {
		return dwFlux;
	}

	public void setDwFlux(Double dwFlux) {
		this.dwFlux = dwFlux;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getGatherInterval() {
		return gatherInterval;
	}

	public void setGatherInterval(Integer gatherInterval) {
		this.gatherInterval = gatherInterval;
	}

	public String getRecTime() {
		return recTime;
	}

	public void setRecTime(String recTime) {
		this.recTime = recTime;
	}

}
