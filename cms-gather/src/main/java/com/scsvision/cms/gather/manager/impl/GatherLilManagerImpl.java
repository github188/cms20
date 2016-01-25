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
import com.scsvision.cms.gather.dao.GatherLilDAO;
import com.scsvision.cms.gather.manager.GatherLilManager;
import com.scsvision.cms.util.xml.XmlUtil;
import com.scsvision.database.dao.UserDAO;
import com.scsvision.database.dao.VOrganDAO;
import com.scsvision.database.entity.GatherLil;
import com.scsvision.database.entity.TmDevice;

/**
 * @author wangbinyu
 *
 */
@Stateless
public class GatherLilManagerImpl implements GatherLilManager {

	@EJB(beanName = "GatherLilDAOImpl")
	private GatherLilDAO gatherLilDAO;

	@EJB(beanName = "VOrganDAOImpl")
	private VOrganDAO vorganDAO;

	@EJB(beanName = "UserDAOImpl")
	private UserDAO userDAO;

	@EJB(beanName = "DeviceRealDAOImpl")
	private DeviceRealDAO deviceRealDAO;

	@Override
	public GatherLil getGatherEntity(Long deviceId, Long userId, Integer type) {
		GatherLil lil = gatherLilDAO.getGatherEntity(deviceId);
		return lil;
	}

	@Override
	public void storeData(List<Element> datas, Map<String, TmDevice> map) {
		if (datas.size() == 0) {
			return;
		}
		List<GatherLil> list = new LinkedList<GatherLil>();
		Date recTime = new Date();
		for (Element data : datas) {
			String sn = data.attributeValue("Sn");
			TmDevice device = map.get(sn);
			if (null == device) {
				continue;
			}
			GatherLil lil = new GatherLil();
			lil.setBackArrowStatus(XmlUtil.getInteger(data, "BackArrowStatus"));
			lil.setBackXStatus(XmlUtil.getInteger(data, "BackXStatus"));
			Long longRecTime = XmlUtil.getLong(data, "RecTime");
			if (null != longRecTime) {
				lil.setRecTime(new Date(longRecTime.longValue()));
			} else {
				lil.setRecTime(recTime);
			}
			lil.setCommStatus(XmlUtil.getInteger(data, "CommStatus"));
			lil.setDeviceId(device.getId());
			lil.setFrontArrowStatus(XmlUtil
					.getInteger(data, "FrontArrowStatus"));
			lil.setFrontXStatus(XmlUtil.getInteger(data, "FrontXStatus"));
			lil.setStatus(XmlUtil.getInteger(data, "Status"));
			list.add(lil);
		}
		if (list.size() > 0) {
			gatherLilDAO.batchInsert(list);
			deviceRealDAO.saveMany(list);
		}
	}
}
