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
import com.scsvision.cms.gather.dao.GatherRsdDAO;
import com.scsvision.cms.gather.manager.GatherRsdManager;
import com.scsvision.cms.util.xml.XmlUtil;
import com.scsvision.database.dao.UserDAO;
import com.scsvision.database.dao.VOrganDAO;
import com.scsvision.database.entity.GatherRsd;
import com.scsvision.database.entity.TmDevice;

/**
 * @author wangbinyu
 *
 */
@Stateless
public class GatherRsdManagerImpl implements GatherRsdManager {
	@EJB(beanName = "GatherRsdDAOImpl")
	private GatherRsdDAO gatherRsdDAO;

	@EJB(beanName = "UserDAOImpl")
	private UserDAO userDAO;

	@EJB(beanName = "VOrganDAOImpl")
	private VOrganDAO vorganDAO;

	@EJB(beanName = "DeviceRealDAOImpl")
	private DeviceRealDAO deviceRealDAO;

	@Override
	public GatherRsd getGatherEntity(Long deviceId, Long userId, Integer type) {
		GatherRsd rsd = gatherRsdDAO.getGatherEntity(deviceId);
		return rsd;
	}

	@Override
	public void storeData(List<Element> datas, Map<String, TmDevice> map) {
		if (datas.size() == 0) {
			return;
		}
		List<GatherRsd> list = new LinkedList<GatherRsd>();
		Date recTime = new Date();
		for (Element data : datas) {
			String sn = data.attributeValue("Sn");
			TmDevice device = map.get(sn);
			if (null == device) {
				continue;
			}
			GatherRsd rsd = new GatherRsd();
			rsd.setCommStatus(XmlUtil.getInteger(data, "CommStatus"));
			rsd.setDeviceId(device.getId());
			rsd.setStatus(XmlUtil.getInteger(data, "Status"));
			Long longRecTime = XmlUtil.getLong(data, "RecTime");
			if (null != longRecTime) {
				rsd.setRecTime(new Date(longRecTime.longValue()));
			} else {
				rsd.setRecTime(recTime);
			}
			rsd.setRainVol(XmlUtil.getDouble(data, "RainVol"));
			rsd.setRoadSurface(XmlUtil.getInteger(data, "RoadState"));
			rsd.setRoadTemp(XmlUtil.getDouble(data, "RoadTemp"));
			rsd.setSnowVol(XmlUtil.getDouble(data, "SnowVol"));
			list.add(rsd);
		}
		if (list.size() > 0) {
			gatherRsdDAO.batchInsert(list);
			deviceRealDAO.saveMany(list);
		}
	}
}
