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
import com.scsvision.cms.gather.dao.GatherCoviDAO;
import com.scsvision.cms.gather.manager.GatherCoviManager;
import com.scsvision.cms.util.xml.XmlUtil;
import com.scsvision.database.dao.UserDAO;
import com.scsvision.database.dao.VOrganDAO;
import com.scsvision.database.entity.GatherCovi;
import com.scsvision.database.entity.TmDevice;

/**
 * @author wangbinyu
 *
 */
@Stateless
public class GatherCoviManagerImpl implements GatherCoviManager {
	@EJB(beanName = "GatherCoviDAOImpl")
	private GatherCoviDAO gatherCoviDAO;

	@EJB(beanName = "UserDAOImpl")
	private UserDAO userDAO;

	@EJB(beanName = "VOrganDAOImpl")
	private VOrganDAO vorganDAO;

	@EJB(beanName = "DeviceRealDAOImpl")
	private DeviceRealDAO deviceRealDAO;

	@Override
	public GatherCovi getGatherEntity(Long deviceId, Long userId, Integer type) {
		GatherCovi covi = gatherCoviDAO.getGatherEntity(deviceId);
		return covi;
	}

	@Override
	public void storeData(List<Element> datas, Map<String, TmDevice> map) {
		if (datas.size() == 0) {
			return;
		}
		List<GatherCovi> list = new LinkedList<GatherCovi>();
		Date recTime = new Date();
		for (Element data : datas) {
			String sn = data.attributeValue("Sn");
			TmDevice device = map.get(sn);
			if (null == device) {
				continue;
			}
			GatherCovi covi = new GatherCovi();
			covi.setCommStatus(XmlUtil.getInteger(data, "CommStatus"));
			covi.setDeviceId(device.getId());
			covi.setStatus(XmlUtil.getInteger(data, "Status"));
			Long longRecTime = XmlUtil.getLong(data, "RecTime");
			if (null != longRecTime) {
				covi.setRecTime(new Date(longRecTime.longValue()));
			} else {
				covi.setRecTime(recTime);
			}
			covi.setCo(XmlUtil.getDouble(data, "COConct"));
			covi.setVi(XmlUtil.getDouble(data, "Visibility"));
			list.add(covi);
		}
		if (list.size() > 0) {
			gatherCoviDAO.batchInsert(list);
			deviceRealDAO.saveMany(list);
		}
	}

	@Override
	public List<GatherCovi> listCovi(List<Long> ids, Date beginTime,
			Date endTime) {
		return gatherCoviDAO.list(ids, beginTime, endTime);
	}

}
