/**
 * 
 */
package com.scsvision.cms.gather.manager.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scsvision.cms.gather.dao.DeviceRealDAO;
import com.scsvision.cms.gather.dao.GatherVdDAO;
import com.scsvision.cms.gather.manager.GatherVdManager;
import com.scsvision.cms.util.xml.XmlUtil;
import com.scsvision.database.dao.OrganDAO;
import com.scsvision.database.dao.TmDeviceDAO;
import com.scsvision.database.dao.UserDAO;
import com.scsvision.database.dao.VOrganDAO;
import com.scsvision.database.entity.GatherVd;
import com.scsvision.database.entity.TmDevice;

/**
 * @author wangbinyu
 *
 */
@Stateless
public class GatherVdManagerImpl implements GatherVdManager {

	private final Logger logger = LoggerFactory
			.getLogger(GatherVdManagerImpl.class);

	@EJB(beanName = "OrganDAOImpl")
	private OrganDAO organDAO;

	@EJB(beanName = "GatherVdDAOImpl")
	private GatherVdDAO gatherVdDAO;

	@EJB(beanName = "TmDeviceDAOImpl")
	private TmDeviceDAO tmDeviceDAO;

	@EJB(beanName = "UserDAOImpl")
	private UserDAO userDAO;

	@EJB(beanName = "VOrganDAOImpl")
	private VOrganDAO vorganDAO;

	@EJB(beanName = "DeviceRealDAOImpl")
	private DeviceRealDAO deviceRealDAO;

	@Override
	public List<GatherVd> list(List<Long> vdIds, Date beginTime, Date endTime) {
		return gatherVdDAO.list(vdIds, beginTime, endTime);
	}
	
	@Override
	public List<GatherVd> listById(Long vdId, Date beginTime, Date endTime) {
		return gatherVdDAO.list(vdId, beginTime, endTime);
	}

	@Override
	public GatherVd getGatherEntity(Long deviceId, Long userId, Integer type) {
		GatherVd vd = gatherVdDAO.getGatherEntity(deviceId);
		return vd;
	}

	@Override
	public GatherVd getFirst() {
		return gatherVdDAO.getFirst();
	}

	@Override
	public void storeData(List<Element> datas, Map<String, TmDevice> map) {
		if (datas.size() == 0) {
			return;
		}
		List<GatherVd> list = new LinkedList<GatherVd>();
		Date recTime = new Date();
		for (Element data : datas) {
			// 车检器属性
			String sn = data.attributeValue("Sn");
			TmDevice device = map.get(sn);
			if (null == device) {
				continue;
			}
			GatherVd vd = new GatherVd();
			vd.setDeviceId(device.getId());
			vd.setCommStatus(XmlUtil.getInteger(data, "CommStatus"));
			vd.setStatus(XmlUtil.getInteger(data, "Status"));
			Long longRecTime = XmlUtil.getLong(data, "RecTime");
			if (null != longRecTime) {
				vd.setRecTime(new Date(longRecTime.longValue()));
			} else {
				vd.setRecTime(recTime);
			}
			// 车道属性
			List<Element> lanes = data.elements("Lane");
			for (Element lane : lanes) {
				Integer index = XmlUtil.getInteger(lane, "Index");
				if (null == index) {
					logger.error("Report data for Sn[" + sn
							+ "] is invalid ! The Attribute[" + index
							+ "] for Element[" + lane + "] is null !");
					continue;
				}
				switch (index.intValue()) {
				case 1:
					vd.setLane1Flux(XmlUtil.getDouble(lane, "Flux"));
					vd.setLane1Fluxb(XmlUtil.getDouble(lane, "FluxB"));
					vd.setLane1Fluxm(XmlUtil.getDouble(lane, "FluxM"));
					vd.setLane1Fluxms(XmlUtil.getDouble(lane, "FluxMS"));
					vd.setLane1Fluxs(XmlUtil.getDouble(lane, "FluxS"));
					vd.setLane1Headway(XmlUtil.getDouble(lane, "Headway"));
					vd.setLane1Occ(XmlUtil.getDouble(lane, "Occ"));
					vd.setLane1Speed(XmlUtil.getDouble(lane, "Speed"));
					vd.setLane1Speedb(XmlUtil.getDouble(lane, "SpeedB"));
					vd.setLane1Speedm(XmlUtil.getDouble(lane, "SpeedM"));
					vd.setLane1Speedms(XmlUtil.getDouble(lane, "SpeedMS"));
					vd.setLane1Speeds(XmlUtil.getDouble(lane, "SpeedS"));
					break;
				case 2:
					vd.setLane2Flux(XmlUtil.getDouble(lane, "Flux"));
					vd.setLane2Fluxb(XmlUtil.getDouble(lane, "FluxB"));
					vd.setLane2Fluxm(XmlUtil.getDouble(lane, "FluxM"));
					vd.setLane2Fluxms(XmlUtil.getDouble(lane, "FluxMS"));
					vd.setLane2Fluxs(XmlUtil.getDouble(lane, "FluxS"));
					vd.setLane2Headway(XmlUtil.getDouble(lane, "Headway"));
					vd.setLane2Occ(XmlUtil.getDouble(lane, "Occ"));
					vd.setLane2Speed(XmlUtil.getDouble(lane, "Speed"));
					vd.setLane2Speedb(XmlUtil.getDouble(lane, "SpeedB"));
					vd.setLane2Speedm(XmlUtil.getDouble(lane, "SpeedM"));
					vd.setLane2Speedms(XmlUtil.getDouble(lane, "SpeedMS"));
					vd.setLane2Speeds(XmlUtil.getDouble(lane, "SpeedS"));
					break;
				case 3:
					vd.setLane3Flux(XmlUtil.getDouble(lane, "Flux"));
					vd.setLane3Fluxb(XmlUtil.getDouble(lane, "FluxB"));
					vd.setLane3Fluxm(XmlUtil.getDouble(lane, "FluxM"));
					vd.setLane3Fluxms(XmlUtil.getDouble(lane, "FluxMS"));
					vd.setLane3Fluxs(XmlUtil.getDouble(lane, "FluxS"));
					vd.setLane3Headway(XmlUtil.getDouble(lane, "Headway"));
					vd.setLane3Occ(XmlUtil.getDouble(lane, "Occ"));
					vd.setLane3Speed(XmlUtil.getDouble(lane, "Speed"));
					vd.setLane3Speedb(XmlUtil.getDouble(lane, "SpeedB"));
					vd.setLane3Speedm(XmlUtil.getDouble(lane, "SpeedM"));
					vd.setLane3Speedms(XmlUtil.getDouble(lane, "SpeedMS"));
					vd.setLane3Speeds(XmlUtil.getDouble(lane, "SpeedS"));
					break;
				case 4:
					vd.setLane4Flux(XmlUtil.getDouble(lane, "Flux"));
					vd.setLane4Fluxb(XmlUtil.getDouble(lane, "FluxB"));
					vd.setLane4Fluxm(XmlUtil.getDouble(lane, "FluxM"));
					vd.setLane4Fluxms(XmlUtil.getDouble(lane, "FluxMS"));
					vd.setLane4Fluxs(XmlUtil.getDouble(lane, "FluxS"));
					vd.setLane4Headway(XmlUtil.getDouble(lane, "Headway"));
					vd.setLane4Occ(XmlUtil.getDouble(lane, "Occ"));
					vd.setLane4Speed(XmlUtil.getDouble(lane, "Speed"));
					vd.setLane4Speedb(XmlUtil.getDouble(lane, "SpeedB"));
					vd.setLane4Speedm(XmlUtil.getDouble(lane, "SpeedM"));
					vd.setLane4Speedms(XmlUtil.getDouble(lane, "SpeedMS"));
					vd.setLane4Speeds(XmlUtil.getDouble(lane, "SpeedS"));
					break;
				case 5:
					vd.setLane5Flux(XmlUtil.getDouble(lane, "Flux"));
					vd.setLane5Fluxb(XmlUtil.getDouble(lane, "FluxB"));
					vd.setLane5Fluxm(XmlUtil.getDouble(lane, "FluxM"));
					vd.setLane5Fluxms(XmlUtil.getDouble(lane, "FluxMS"));
					vd.setLane5Fluxs(XmlUtil.getDouble(lane, "FluxS"));
					vd.setLane5Headway(XmlUtil.getDouble(lane, "Headway"));
					vd.setLane5Occ(XmlUtil.getDouble(lane, "Occ"));
					vd.setLane5Speed(XmlUtil.getDouble(lane, "Speed"));
					vd.setLane5Speedb(XmlUtil.getDouble(lane, "SpeedB"));
					vd.setLane5Speedm(XmlUtil.getDouble(lane, "SpeedM"));
					vd.setLane5Speedms(XmlUtil.getDouble(lane, "SpeedMS"));
					vd.setLane5Speeds(XmlUtil.getDouble(lane, "SpeedS"));
					break;
				case 6:
					vd.setLane6Flux(XmlUtil.getDouble(lane, "Flux"));
					vd.setLane6Fluxb(XmlUtil.getDouble(lane, "FluxB"));
					vd.setLane6Fluxm(XmlUtil.getDouble(lane, "FluxM"));
					vd.setLane6Fluxms(XmlUtil.getDouble(lane, "FluxMS"));
					vd.setLane6Fluxs(XmlUtil.getDouble(lane, "FluxS"));
					vd.setLane6Headway(XmlUtil.getDouble(lane, "Headway"));
					vd.setLane6Occ(XmlUtil.getDouble(lane, "Occ"));
					vd.setLane6Speed(XmlUtil.getDouble(lane, "Speed"));
					vd.setLane6Speedb(XmlUtil.getDouble(lane, "SpeedB"));
					vd.setLane6Speedm(XmlUtil.getDouble(lane, "SpeedM"));
					vd.setLane6Speedms(XmlUtil.getDouble(lane, "SpeedMS"));
					vd.setLane6Speeds(XmlUtil.getDouble(lane, "SpeedS"));
					break;
				default:
					break;
				}
			}

			list.add(vd);
		}

		if (list.size() > 0) {
			gatherVdDAO.batchInsert(list);
			deviceRealDAO.saveMany(list);
		}
	}
}
