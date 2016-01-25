package com.scsvision.cms.em.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;

import com.scsvision.cms.annotation.Cache;
import com.scsvision.cms.em.dao.EventManualDAO;
import com.scsvision.cms.em.dao.EventManualRealDAO;
import com.scsvision.cms.em.manager.EventManualManager;
import com.scsvision.cms.interceptor.CacheInterceptor;
import com.scsvision.database.entity.EventManual;
import com.scsvision.database.entity.EventManualReal;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Interceptors(CacheInterceptor.class)
public class EventManualManagerImpl implements EventManualManager {
	@EJB(beanName = "EventManualRealDAOImpl")
	private EventManualRealDAO eventManualRealDAO;

	@EJB(beanName = "EventManualDAOImpl")
	private EventManualDAO eventManualDAO;

	@Override
	@Cache
	public List<EventManualReal> emanualRealList(Integer start, Integer limit) {
		return eventManualRealDAO.emanualRealList(start, limit);
	}

	@Override
	@Cache
	public Map<Long, EventManualReal> emanualRealMap(Integer start,
			Integer limit) {
		List<EventManualReal> elist = eventManualRealDAO.emanualRealList(start,
				limit);
		Map<Long, EventManualReal> map = new HashMap<Long, EventManualReal>();
		for (EventManualReal e : elist) {
			map.put(e.getId(), e);
		}
		return map;
	}

	@Override
	public Map<Long, EventManual> mapEvent(List<Long> ids) {
		return eventManualDAO.map(ids);
	}

	@Override
	public Map<Long, EventManualReal> mapEventReal(List<Long> ids) {
		return eventManualRealDAO.map(ids);
	}
	
	@Override
	public Long createEventManualReal(EventManualReal entity) {
		eventManualRealDAO.save(entity);
		return entity.getId();
	}

	@Override
	public EventManualReal getEventManualReal(Long id) {
		EventManualReal entity = eventManualRealDAO.get(id);
		return entity;
	}

	@Override
	public void deleteEventManualReal(Long id) {
		EventManualReal entity = eventManualRealDAO.get(id);
		eventManualRealDAO.delete(entity);
	}

	@Override
	public void updateEventManualReal(EventManualReal entity) {
		eventManualRealDAO.update(entity);
	}
}
