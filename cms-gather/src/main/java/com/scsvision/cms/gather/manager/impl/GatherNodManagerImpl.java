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
import com.scsvision.cms.gather.dao.GatherNodDAO;
import com.scsvision.cms.gather.manager.GatherNodManager;
import com.scsvision.cms.util.xml.XmlUtil;
import com.scsvision.database.dao.UserDAO;
import com.scsvision.database.dao.VOrganDAO;
import com.scsvision.database.entity.GatherNod;
import com.scsvision.database.entity.TmDevice;

/**
 * @author wangbinyu
 *
 */
@Stateless
public class GatherNodManagerImpl implements GatherNodManager {
	@EJB(beanName = "GatherNodDAOImpl")
	private GatherNodDAO gatherNodDAO;

	@EJB(beanName = "UserDAOImpl")
	private UserDAO userDAO;

	@EJB(beanName = "VOrganDAOImpl")
	private VOrganDAO vorganDAO;

	@EJB(beanName = "DeviceRealDAOImpl")
	private DeviceRealDAO deviceRealDAO;

	@Override
	public GatherNod getGatherEntity(Long deviceId, Long userId, Integer type) {
		GatherNod nod = gatherNodDAO.getGatherEntity(deviceId);
		return nod;
	}

	@Override
	public void storeData(List<Element> datas, Map<String, TmDevice> map) {
		if (datas.size() == 0) {
			return;
		}
		List<GatherNod> list = new LinkedList<GatherNod>();
		Date recTime = new Date();
		for (Element data : datas) {
			String sn = data.attributeValue("Sn");
			TmDevice device = map.get(sn);
			if (null == device) {
				continue;
			}
			GatherNod nod = new GatherNod();
			nod.setCommStatus(XmlUtil.getInteger(data, "CommStatus"));
			nod.setDeviceId(device.getId());
			nod.setStatus(XmlUtil.getInteger(data, "Status"));
			Long longRecTime = XmlUtil.getLong(data, "RecTime");
			if (null != longRecTime) {
				nod.setRecTime(new Date(longRecTime.longValue()));
			} else {
				nod.setRecTime(recTime);
			}
			nod.setNo(XmlUtil.getDouble(data, "NOConct"));
			nod.setNo2(XmlUtil.getDouble(data, "NO2Conct"));
			list.add(nod);
		}
		if (list.size() > 0) {
			gatherNodDAO.batchInsert(list);
			deviceRealDAO.saveMany(list);
		}
	}
}
