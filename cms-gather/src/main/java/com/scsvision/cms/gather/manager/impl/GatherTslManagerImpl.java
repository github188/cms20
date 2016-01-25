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

import com.scsvision.cms.gather.dao.DeviceRealDAO;
import com.scsvision.cms.gather.dao.GatherTslDAO;
import com.scsvision.cms.gather.manager.GatherTslManager;
import com.scsvision.cms.util.xml.XmlUtil;
import com.scsvision.database.dao.UserDAO;
import com.scsvision.database.dao.VOrganDAO;
import com.scsvision.database.entity.GatherTsl;
import com.scsvision.database.entity.TmDevice;

/**
 * @author wangbinyu
 *
 */
@Stateless
public class GatherTslManagerImpl implements GatherTslManager {

	@EJB(beanName = "GatherTslDAOImpl")
	private GatherTslDAO gatherTslDAO;

	@EJB(beanName = "VOrganDAOImpl")
	private VOrganDAO vorganDAO;

	@EJB(beanName = "UserDAOImpl")
	private UserDAO userDAO;

	@EJB(beanName = "DeviceRealDAOImpl")
	private DeviceRealDAO deviceRealDAO;

	@Override
	public GatherTsl getGatherEntity(Long deviceId, Long userId, Integer type) {
		GatherTsl tsl = gatherTslDAO.getGatherEntity(deviceId);
		return tsl;
	}

	@Override
	public void storeData(List<Element> datas, Map<String, TmDevice> map) {
		if (datas.size() == 0) {
			return;
		}
		List<GatherTsl> list = new LinkedList<GatherTsl>();
		Date recTime = new Date();
		for (Element data : datas) {
			String sn = data.attributeValue("Sn");
			TmDevice device = map.get(sn);
			if (null == device) {
				continue;
			}
			GatherTsl tsl = new GatherTsl();
			tsl.setCommStatus(XmlUtil.getInteger(data, "CommStatus"));
			tsl.setDeviceId(device.getId());
			tsl.setGreenStatus(XmlUtil.getInteger(data, "GreenStatus"));
			Long longRecTime = XmlUtil.getLong(data, "RecTime");
			if (null != longRecTime) {
				tsl.setRecTime(new Date(longRecTime.longValue()));
			} else {
				tsl.setRecTime(recTime);
			}
			tsl.setRedStatus(XmlUtil.getInteger(data, "RedStatus"));
			tsl.setStatus(XmlUtil.getInteger(data, "Status"));
			tsl.setTurnStatus(XmlUtil.getInteger(data, "TurnStatus"));
			tsl.setYellowStatus(XmlUtil.getInteger(data, "YellowStatus"));
			list.add(tsl);
		}
		if (list.size() > 0) {
			gatherTslDAO.batchInsert(list);
			deviceRealDAO.saveMany(list);
		}
	}
}
