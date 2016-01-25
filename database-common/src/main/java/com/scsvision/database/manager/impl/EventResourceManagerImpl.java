package com.scsvision.database.manager.impl;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;

import com.scsvision.cms.interceptor.CacheInterceptor;
import com.scsvision.database.dao.EventResourceDAO;
import com.scsvision.database.entity.EventResource;
import com.scsvision.database.manager.EventResourceManager;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Interceptors(CacheInterceptor.class)
public class EventResourceManagerImpl implements EventResourceManager {
	@EJB(beanName = "EventResourceDAOImpl")
	private EventResourceDAO eventResourceDAO;

	@Override
	public List<Long> getResourceIdsByEventId(Long eventId) {
		List<EventResource> list = eventResourceDAO
				.getResourceIdsByEventId(eventId);
		List<Long> resourceIds = new LinkedList<Long>();
		for (EventResource e : list) {
			resourceIds.add(e.getResourceId());
		}
		return resourceIds;
	}

}
