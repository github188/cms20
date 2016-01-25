package com.scsvision.database.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.scsvision.database.dao.EventResourceDAO;
import com.scsvision.database.entity.EventResource;

@Stateless
public class EventResourceDAOImpl extends BaseDAOImpl<EventResource, Long>
		implements EventResourceDAO {

	@Override
	public List<EventResource> getResourceIdsByEventId(Long eventRealId) {
		String sql = "select o from EventResource o where o.eventRealId = :eventRealId";
		Query q = entityManager.createQuery(sql.toString());
		q.setParameter("eventRealId", eventRealId);
		return q.getResultList();
	}

	@Override
	public void deleteByEventId(Long eventId) {
		Query q = entityManager
				.createQuery("delete from EventResource o where o.eventId =:eventId");
		q.setParameter("eventId", eventId);
		q.executeUpdate();
	}

	@Override
	public void deleteResource(Long eventId, Long resourceId) {
		Query q = entityManager
				.createQuery("delete from EventResource o where o.eventId =:eventId and o.resourceId =:resourceId");
		q.setParameter("eventId", eventId);
		q.setParameter("resourceId", resourceId);
		q.executeUpdate();

	}

	@Override
	public void deleteByEventRealId(Long id) {
		Query q = entityManager
				.createQuery("delete from EventResource o where o.eventRealId =:eventRealId");
		q.setParameter("eventRealId", id);
		q.executeUpdate();
	}

	@Override
	public List<Long> listReferencedResourceIds() {
		String sql = "select distinct o.resourceId from EventResource o";
		Query q = entityManager.createQuery(sql);
		return q.getResultList();
	}
}
