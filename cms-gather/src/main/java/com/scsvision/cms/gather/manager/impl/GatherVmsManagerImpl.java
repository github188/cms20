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
import com.scsvision.cms.gather.dao.GatherVmsDAO;
import com.scsvision.cms.gather.manager.GatherVmsManager;
import com.scsvision.cms.util.xml.XmlUtil;
import com.scsvision.database.dao.UserDAO;
import com.scsvision.database.dao.VOrganDAO;
import com.scsvision.database.entity.GatherVms;
import com.scsvision.database.entity.TmDevice;

/**
 * @author wangbinyu
 *
 */
@Stateless
public class GatherVmsManagerImpl implements GatherVmsManager {

	@EJB(beanName = "GatherVmsDAOImpl")
	private GatherVmsDAO gatherVmsDAO;

	@EJB(beanName = "VOrganDAOImpl")
	private VOrganDAO vorganDAO;

	@EJB(beanName = "UserDAOImpl")
	private UserDAO userDAO;

	@EJB(beanName = "DeviceRealDAOImpl")
	private DeviceRealDAO deviceRealDAO;

	@Override
	public GatherVms getGatherEntity(Long deviceId, Long userId, Integer type) {
		GatherVms vms = gatherVmsDAO.getGatherEntity(deviceId);
		return vms;
	}

	@Override
	public void storeData(List<Element> datas, Map<String, TmDevice> map) {
		if (datas.size() == 0) {
			return;
		}
		List<GatherVms> list = new LinkedList<GatherVms>();
		Date recTime = new Date();
		for (Element data : datas) {
			String sn = data.attributeValue("Sn");
			TmDevice device = map.get(sn);
			if (null == device) {
				continue;
			}
			GatherVms vms = new GatherVms();
			vms.setColor(data.attributeValue("Color"));
			vms.setCommStatus(XmlUtil.getInteger(data, "CommStatus"));
			vms.setStatus(XmlUtil.getInteger(data, "Status"));
			Long longRecTime = XmlUtil.getLong(data, "RecTime");
			if (null != longRecTime) {
				vms.setRecTime(new Date(longRecTime.longValue()));
			} else {
				vms.setRecTime(recTime);
			}
			vms.setDeviceId(device.getId());
			vms.setDispCont(data.attributeValue("DispText"));
			vms.setDispTime(XmlUtil.getLong(data, "DispTime"));
			vms.setFont(data.attributeValue("Font"));
			vms.setFontSize(data.attributeValue("FontSize"));
			vms.setDispSrc(XmlUtil.getInteger(data, "DispSrc"));
			list.add(vms);
		}
		if (list.size() > 0) {
			gatherVmsDAO.batchInsert(list);
			deviceRealDAO.saveMany(list);
		}
	}
}
