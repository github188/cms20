package com.scsvision.cms.em.manager.impl;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;

import com.scsvision.cms.em.dao.EventDeviceDAO;
import com.scsvision.cms.em.dao.EventDeviceRealDAO;
import com.scsvision.cms.em.dao.EventRealDAO;
import com.scsvision.cms.em.manager.EventDeviceManager;
import com.scsvision.cms.interceptor.CacheInterceptor;
import com.scsvision.database.entity.EventDevice;
import com.scsvision.database.entity.EventDeviceReal;
import com.scsvision.database.entity.EventReal;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Interceptors(CacheInterceptor.class)
public class EventDeviceManagerImpl implements EventDeviceManager {
	@EJB(beanName = "EventDeviceRealDAOImpl")
	private EventDeviceRealDAO eventDeviceRealDAO;
	@EJB(beanName = "EventDeviceDAOImpl")
	private EventDeviceDAO eventDeviceDAO;
	@EJB(beanName = "EventRealDAOImpl")
	private EventRealDAO eventRealDAO;

	@Override
	public List<EventDeviceReal> edeviceRealList(Integer start, Integer limit) {
		return eventDeviceRealDAO.edeviceRealList(start, limit);
	}

	@Override
	public Map<Long, EventDeviceReal> edviceRealMap(List<Long> ids) {
		return eventDeviceRealDAO.map(ids);
	}

	@Override
	public EventDevice getEventDevice(Long id) {
		EventDevice entity = eventDeviceDAO.get(id);
		return entity;
	}

	@Override
	public Map<Long, EventDevice> mapEventDevice(List<Long> ids) {
		return eventDeviceDAO.map(ids);
	}

	@Override
	public Long createAlarm(EventReal alarm, EventDeviceReal subAlarm) {
		eventRealDAO.save(alarm);
		subAlarm.setId(alarm.getId());
		eventDeviceRealDAO.save(subAlarm);
		return alarm.getId();
	}
}
