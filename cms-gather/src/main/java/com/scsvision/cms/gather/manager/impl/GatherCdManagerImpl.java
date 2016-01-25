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
import com.scsvision.cms.gather.dao.GatherCdDAO;
import com.scsvision.cms.gather.manager.GatherCdManager;
import com.scsvision.cms.util.xml.XmlUtil;
import com.scsvision.database.dao.UserDAO;
import com.scsvision.database.dao.VOrganDAO;
import com.scsvision.database.entity.GatherCd;
import com.scsvision.database.entity.TmDevice;

/**
 * @author wangbinyu
 *
 */
@Stateless
public class GatherCdManagerImpl implements GatherCdManager {
	@EJB(beanName = "GatherCdDAOImpl")
	private GatherCdDAO gatherCdDAO;

	@EJB(beanName = "UserDAOImpl")
	private UserDAO userDAO;

	@EJB(beanName = "VOrganDAOImpl")
	private VOrganDAO vorganDAO;

	@EJB(beanName = "DeviceRealDAOImpl")
	private DeviceRealDAO deviceRealDAO;

	@Override
	public GatherCd getGatherEntity(Long deviceId, Long userId, Integer type) {
		GatherCd cd = gatherCdDAO.getGatherEntity(deviceId);
		return cd;
	}

	@Override
	public void storeData(List<Element> datas, Map<String, TmDevice> map) {
		if (datas.size() == 0) {
			return;
		}
		List<GatherCd> list = new LinkedList<GatherCd>();
		Date recTime = new Date();
		for (Element data : datas) {
			String sn = data.attributeValue("Sn");
			TmDevice device = map.get(sn);
			if (null == device) {
				continue;
			}
			GatherCd cd = new GatherCd();
			cd.setCommStatus(XmlUtil.getInteger(data, "CommStatus"));
			cd.setDeviceId(device.getId());
			cd.setStatus(XmlUtil.getInteger(data, "Status"));
			Long longRecTime = XmlUtil.getLong(data, "RecTime");
			if (null != longRecTime) {
				cd.setRecTime(new Date(longRecTime.longValue()));
			} else {
				cd.setRecTime(recTime);
			}
			cd.setType(XmlUtil.getInteger(data, "Type"));
			cd.setWorkState(data.attributeValue("WorkState"));
			list.add(cd);
		}
		if (list.size() > 0) {
			gatherCdDAO.batchInsert(list);
			deviceRealDAO.saveMany(list);
		}
	}
}
