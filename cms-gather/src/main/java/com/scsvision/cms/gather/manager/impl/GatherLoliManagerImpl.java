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
import com.scsvision.cms.gather.dao.GatherLoliDAO;
import com.scsvision.cms.gather.manager.GatherLoliManager;
import com.scsvision.cms.util.xml.XmlUtil;
import com.scsvision.database.dao.UserDAO;
import com.scsvision.database.dao.VOrganDAO;
import com.scsvision.database.entity.GatherLoli;
import com.scsvision.database.entity.TmDevice;

/**
 * @author wangbinyu
 *
 */
@Stateless
public class GatherLoliManagerImpl implements GatherLoliManager {
	@EJB(beanName = "GatherLoliDAOImpl")
	private GatherLoliDAO gatherLoliDAO;

	@EJB(beanName = "UserDAOImpl")
	private UserDAO userDAO;

	@EJB(beanName = "VOrganDAOImpl")
	private VOrganDAO vorganDAO;

	@EJB(beanName = "DeviceRealDAOImpl")
	private DeviceRealDAO deviceRealDAO;

	@Override
	public GatherLoli getGatherEntity(Long deviceId, Long userId, Integer type) {
		GatherLoli loli = gatherLoliDAO.getGatherEntity(deviceId);
		return loli;
	}

	@Override
	public void storeData(List<Element> datas, Map<String, TmDevice> map) {
		if (datas.size() == 0) {
			return;
		}
		List<GatherLoli> list = new LinkedList<GatherLoli>();
		Date recTime = new Date();
		for (Element data : datas) {
			String sn = data.attributeValue("Sn");
			TmDevice device = map.get(sn);
			if (null == device) {
				continue;
			}
			GatherLoli loli = new GatherLoli();
			loli.setCommStatus(XmlUtil.getInteger(data, "CommStatus"));
			loli.setDeviceId(device.getId());
			loli.setStatus(XmlUtil.getInteger(data, "Status"));
			Long longRecTime = XmlUtil.getLong(data, "RecTime");
			if (null != longRecTime) {
				loli.setRecTime(new Date(longRecTime.longValue()));
			} else {
				loli.setRecTime(recTime);
			}
			loli.setLo(XmlUtil.getDouble(data, "LOLumi"));
			loli.setLi(XmlUtil.getDouble(data, "LILumi"));
			list.add(loli);
		}
		if (list.size() > 0) {
			gatherLoliDAO.batchInsert(list);
			deviceRealDAO.saveMany(list);
		}
	}
}
