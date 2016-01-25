package com.scsvision.cms.gather.dto;

import java.util.LinkedList;
import java.util.List;

import com.scsvision.cms.response.BaseDTO;

/**
 * ListDeviceRealDTO
 * 
 * @author MIKE
 *         <p />
 *         Create at 2016年1月7日 下午5:49:36
 */
public class ListDeviceRealDTO extends BaseDTO {
	private List<Device> devices = new LinkedList<Device>();

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public class Device {
		private long id;
		private String recTime;
		private String workState;
		private String status;
		private String commStatus;
		private String co;
		private String vi;
		private String longitude;
		private String latitude;
		private String bearing;
		private String altitude;
		private String speed;
		private String backArrowStatus;
		private String backXStatus;
		private String frontArrowStatus;
		private String frontXStatus;
		private String lo;
		private String li;
		private String no;
		private String no2;
		private String roadTemp;
		private String roadSurface;
		private String greenStatus;
		private String redStatus;
		private String yellowStatus;
		private String turnStatus;
		private String lane1Flux;
		private String lane1Speed;
		private String lane1Occ;
		private String lane1Headway;
		private String lane2Flux;
		private String lane2Speed;
		private String lane2Occ;
		private String lane2Headway;
		private String lane3Flux;
		private String lane3Speed;
		private String lane3Occ;
		private String lane3Headway;
		private String lane4Flux;
		private String lane4Speed;
		private String lane4Occ;
		private String lane4Headway;
		private String lane5Flux;
		private String lane5Speed;
		private String lane5Occ;
		private String lane5Headway;
		private String lane6Flux;
		private String lane6Speed;
		private String lane6Occ;
		private String lane6Headway;
		private String ws;
		private String airTemp;
		private String humi;
		private String rainVol;
		private String snowVol;
		private String standardNumber;

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getRecTime() {
			return recTime;
		}

		public void setRecTime(String recTime) {
			this.recTime = recTime;
		}

		public String getWorkState() {
			return workState;
		}

		public void setWorkState(String workState) {
			this.workState = workState;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getCommStatus() {
			return commStatus;
		}

		public void setCommStatus(String commStatus) {
			this.commStatus = commStatus;
		}

		public String getCo() {
			return co;
		}

		public void setCo(String co) {
			this.co = co;
		}

		public String getVi() {
			return vi;
		}

		public void setVi(String vi) {
			this.vi = vi;
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

		public String getBearing() {
			return bearing;
		}

		public void setBearing(String bearing) {
			this.bearing = bearing;
		}

		public String getAltitude() {
			return altitude;
		}

		public void setAltitude(String altitude) {
			this.altitude = altitude;
		}

		public String getSpeed() {
			return speed;
		}

		public void setSpeed(String speed) {
			this.speed = speed;
		}

		public String getBackArrowStatus() {
			return backArrowStatus;
		}

		public void setBackArrowStatus(String backArrowStatus) {
			this.backArrowStatus = backArrowStatus;
		}

		public String getBackXStatus() {
			return backXStatus;
		}

		public void setBackXStatus(String backXStatus) {
			this.backXStatus = backXStatus;
		}

		public String getFrontArrowStatus() {
			return frontArrowStatus;
		}

		public void setFrontArrowStatus(String frontArrowStatus) {
			this.frontArrowStatus = frontArrowStatus;
		}

		public String getFrontXStatus() {
			return frontXStatus;
		}

		public void setFrontXStatus(String frontXStatus) {
			this.frontXStatus = frontXStatus;
		}

		public String getLo() {
			return lo;
		}

		public void setLo(String lo) {
			this.lo = lo;
		}

		public String getLi() {
			return li;
		}

		public void setLi(String li) {
			this.li = li;
		}

		public String getNo() {
			return no;
		}

		public void setNo(String no) {
			this.no = no;
		}

		public String getNo2() {
			return no2;
		}

		public void setNo2(String no2) {
			this.no2 = no2;
		}

		public String getRoadTemp() {
			return roadTemp;
		}

		public void setRoadTemp(String roadTemp) {
			this.roadTemp = roadTemp;
		}

		public String getRoadSurface() {
			return roadSurface;
		}

		public void setRoadSurface(String roadSurface) {
			this.roadSurface = roadSurface;
		}

		public String getGreenStatus() {
			return greenStatus;
		}

		public void setGreenStatus(String greenStatus) {
			this.greenStatus = greenStatus;
		}

		public String getRedStatus() {
			return redStatus;
		}

		public void setRedStatus(String redStatus) {
			this.redStatus = redStatus;
		}

		public String getYellowStatus() {
			return yellowStatus;
		}

		public void setYellowStatus(String yellowStatus) {
			this.yellowStatus = yellowStatus;
		}

		public String getTurnStatus() {
			return turnStatus;
		}

		public void setTurnStatus(String turnStatus) {
			this.turnStatus = turnStatus;
		}

		public String getLane1Flux() {
			return lane1Flux;
		}

		public void setLane1Flux(String lane1Flux) {
			this.lane1Flux = lane1Flux;
		}

		public String getLane1Speed() {
			return lane1Speed;
		}

		public void setLane1Speed(String lane1Speed) {
			this.lane1Speed = lane1Speed;
		}

		public String getLane1Occ() {
			return lane1Occ;
		}

		public void setLane1Occ(String lane1Occ) {
			this.lane1Occ = lane1Occ;
		}

		public String getLane1Headway() {
			return lane1Headway;
		}

		public void setLane1Headway(String lane1Headway) {
			this.lane1Headway = lane1Headway;
		}

		public String getLane2Flux() {
			return lane2Flux;
		}

		public void setLane2Flux(String lane2Flux) {
			this.lane2Flux = lane2Flux;
		}

		public String getLane2Speed() {
			return lane2Speed;
		}

		public void setLane2Speed(String lane2Speed) {
			this.lane2Speed = lane2Speed;
		}

		public String getLane2Occ() {
			return lane2Occ;
		}

		public void setLane2Occ(String lane2Occ) {
			this.lane2Occ = lane2Occ;
		}

		public String getLane2Headway() {
			return lane2Headway;
		}

		public void setLane2Headway(String lane2Headway) {
			this.lane2Headway = lane2Headway;
		}

		public String getLane3Flux() {
			return lane3Flux;
		}

		public void setLane3Flux(String lane3Flux) {
			this.lane3Flux = lane3Flux;
		}

		public String getLane3Speed() {
			return lane3Speed;
		}

		public void setLane3Speed(String lane3Speed) {
			this.lane3Speed = lane3Speed;
		}

		public String getLane3Occ() {
			return lane3Occ;
		}

		public void setLane3Occ(String lane3Occ) {
			this.lane3Occ = lane3Occ;
		}

		public String getLane3Headway() {
			return lane3Headway;
		}

		public void setLane3Headway(String lane3Headway) {
			this.lane3Headway = lane3Headway;
		}

		public String getLane4Flux() {
			return lane4Flux;
		}

		public void setLane4Flux(String lane4Flux) {
			this.lane4Flux = lane4Flux;
		}

		public String getLane4Speed() {
			return lane4Speed;
		}

		public void setLane4Speed(String lane4Speed) {
			this.lane4Speed = lane4Speed;
		}

		public String getLane4Occ() {
			return lane4Occ;
		}

		public void setLane4Occ(String lane4Occ) {
			this.lane4Occ = lane4Occ;
		}

		public String getLane4Headway() {
			return lane4Headway;
		}

		public void setLane4Headway(String lane4Headway) {
			this.lane4Headway = lane4Headway;
		}

		public String getLane5Flux() {
			return lane5Flux;
		}

		public void setLane5Flux(String lane5Flux) {
			this.lane5Flux = lane5Flux;
		}

		public String getLane5Speed() {
			return lane5Speed;
		}

		public void setLane5Speed(String lane5Speed) {
			this.lane5Speed = lane5Speed;
		}

		public String getLane5Occ() {
			return lane5Occ;
		}

		public void setLane5Occ(String lane5Occ) {
			this.lane5Occ = lane5Occ;
		}

		public String getLane5Headway() {
			return lane5Headway;
		}

		public void setLane5Headway(String lane5Headway) {
			this.lane5Headway = lane5Headway;
		}

		public String getLane6Flux() {
			return lane6Flux;
		}

		public void setLane6Flux(String lane6Flux) {
			this.lane6Flux = lane6Flux;
		}

		public String getLane6Speed() {
			return lane6Speed;
		}

		public void setLane6Speed(String lane6Speed) {
			this.lane6Speed = lane6Speed;
		}

		public String getLane6Occ() {
			return lane6Occ;
		}

		public void setLane6Occ(String lane6Occ) {
			this.lane6Occ = lane6Occ;
		}

		public String getLane6Headway() {
			return lane6Headway;
		}

		public void setLane6Headway(String lane6Headway) {
			this.lane6Headway = lane6Headway;
		}

		public String getWs() {
			return ws;
		}

		public void setWs(String ws) {
			this.ws = ws;
		}

		public String getAirTemp() {
			return airTemp;
		}

		public void setAirTemp(String airTemp) {
			this.airTemp = airTemp;
		}

		public String getHumi() {
			return humi;
		}

		public void setHumi(String humi) {
			this.humi = humi;
		}

		public String getRainVol() {
			return rainVol;
		}

		public void setRainVol(String rainVol) {
			this.rainVol = rainVol;
		}

		public String getSnowVol() {
			return snowVol;
		}

		public void setSnowVol(String snowVol) {
			this.snowVol = snowVol;
		}

		public String getStandardNumber() {
			return standardNumber;
		}

		public void setStandardNumber(String standardNumber) {
			this.standardNumber = standardNumber;
		}

	}
}
