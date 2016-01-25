/**
 * 
 */
package com.scsvision.cms.gather.manager.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.dom4j.Element;

import com.scsvision.cms.gather.dao.DeviceRealDAO;
import com.scsvision.cms.gather.dao.GatherWsDAO;
import com.scsvision.cms.gather.manager.GatherWsManager;
import com.scsvision.cms.util.xml.XmlUtil;
import com.scsvision.database.dao.UserDAO;
import com.scsvision.database.dao.VOrganDAO;
import com.scsvision.database.entity.GatherWs;
import com.scsvision.database.entity.TmDevice;
import com.scsvision.database.entity.User;
import com.scsvision.database.entity.VirtualOrgan;

/**
 * @author wangbinyu
 *
 */
@Stateless
public class GatherWsManagerImpl implements GatherWsManager {

	@EJB(beanName = "GatherWsDAOImpl")
	private GatherWsDAO gatherWsDAO;

	@EJB(beanName = "UserDAOImpl")
	private UserDAO userDAO;

	@EJB(beanName = "VOrganDAOImpl")
	private VOrganDAO vorganDAO;

	@EJB(beanName = "DeviceRealDAOImpl")
	private DeviceRealDAO deviceRealDAO;

	@Override
	public GatherWs getGatherEntity(Long deviceId, Long userId, Integer type) {
		GatherWs ws = gatherWsDAO.getGatherEntity(deviceId);
		return ws;
	}

	@Override
	public void storeData(List<Element> datas, Map<String, TmDevice> map) {
		if (datas.size() == 0) {
			return;
		}
		List<GatherWs> list = new LinkedList<GatherWs>();
		Date recTime = new Date();
		for (Element data : datas) {
			String sn = data.attributeValue("Sn");
			TmDevice device = map.get(sn);
			if (null == device) {
				continue;
			}
			GatherWs ws = new GatherWs();
			ws.setCommStatus(XmlUtil.getInteger(data, "CommStatus"));
			ws.setDeviceId(device.getId());
			ws.setStatus(XmlUtil.getInteger(data, "Status"));
			Long longRecTime = XmlUtil.getLong(data, "RecTime");
			if (null != longRecTime) {
				ws.setRecTime(new Date(longRecTime.longValue()));
			} else {
				ws.setRecTime(recTime);
			}
			ws.setDirection(XmlUtil.getInteger(data, "Direction"));
			ws.setSpeed(XmlUtil.getDouble(data, "Speed"));
			list.add(ws);
		}
		if (list.size() > 0) {
			gatherWsDAO.batchInsert(list);
			deviceRealDAO.saveMany(list);
		}
	}
}
