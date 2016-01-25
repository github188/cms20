package com.scsvision.cms.gather.manager.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.dom4j.Element;

import com.scsvision.cms.gather.dao.DeviceRealDAO;
import com.scsvision.cms.gather.dao.GatherWstDAO;
import com.scsvision.cms.gather.manager.GatherWstManager;
import com.scsvision.cms.util.xml.XmlUtil;
import com.scsvision.database.dao.UserDAO;
import com.scsvision.database.dao.VOrganDAO;
import com.scsvision.database.entity.GatherWst;
import com.scsvision.database.entity.TmDevice;

/**
 * GatherWstManagerImpl
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午3:25:21
 */
@Stateless
public class GatherWstManagerImpl implements GatherWstManager {
	@EJB(beanName = "GatherWstDAOImpl")
	private GatherWstDAO gatherWstDAO;

	@EJB(beanName = "UserDAOImpl")
	private UserDAO userDAO;

	@EJB(beanName = "VOrganDAOImpl")
	private VOrganDAO vorganDAO;

	@EJB(beanName = "DeviceRealDAOImpl")
	private DeviceRealDAO deviceRealDAO;

	@Override
	public List<GatherWst> list(Long deviceId, int start, int limit) {
		return gatherWstDAO.list(deviceId, start, limit);
	}

	@Override
	public GatherWst getGatherEntity(Long deviceId, Long userId, Integer type) {
		GatherWst wst = gatherWstDAO.getGatherEntity(deviceId);
		return wst;
	}

	@Override
	public void storeData(List<Element> datas, Map<String, TmDevice> map) {
		if (datas.size() == 0) {
			return;
		}
		List<GatherWst> list = new LinkedList<GatherWst>();
		Date recTime = new Date();
		for (Element data : datas) {
			String sn = data.attributeValue("Sn");
			TmDevice device = map.get(sn);
			if (null == device) {
				continue;
			}
			GatherWst wst = new GatherWst();
			wst.setCommStatus(XmlUtil.getInteger(data, "CommStatus"));
			wst.setDeviceId(device.getId());
			wst.setStatus(XmlUtil.getInteger(data, "Status"));
			Long longRecTime = XmlUtil.getLong(data, "RecTime");
			if (null != longRecTime) {
				wst.setRecTime(new Date(longRecTime.longValue()));
			} else {
				wst.setRecTime(recTime);
			}
			wst.setAirTemp(XmlUtil.getDouble(data, "AirTemp"));
			wst.setDirection(data.attributeValue("WindDir"));
			wst.setHumi(XmlUtil.getDouble(data, "Humi"));
			wst.setRainVol(XmlUtil.getDouble(data, "RainVol"));
			wst.setRoadSurface(XmlUtil.getInteger(data, "RoadState"));
			wst.setRoadTemp(XmlUtil.getDouble(data, "RoadTemp"));
			wst.setSnowVol(XmlUtil.getDouble(data, "SnowVol"));
			wst.setVi(XmlUtil.getDouble(data, "Vis"));
			wst.setWs(XmlUtil.getDouble(data, "WindSpeed"));
			list.add(wst);
		}
		if (list.size() > 0) {
			gatherWstDAO.batchInsert(list);
			deviceRealDAO.saveMany(list);
		}
	}

	@Override
	public List<GatherWst> listWst(List<Long> ids, Date beginTime, Date endTime) {
		return gatherWstDAO.list(ids, beginTime, endTime);
	}
}
