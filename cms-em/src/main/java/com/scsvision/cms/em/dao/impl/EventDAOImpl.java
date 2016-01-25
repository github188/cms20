package com.scsvision.cms.em.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.scsvision.cms.em.dao.EventDAO;
import com.scsvision.database.entity.Event;

/**
 * EventDAOImpl
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午2:43:32
 */
@Stateless
public class EventDAOImpl extends EmBaseDAOImpl<Event, Long> implements
		EventDAO {
	@Override
	public List<Event> listEvent(Long beginTime, Long endTime, Integer type,
			List<Long> organIds, Integer level, Integer subType, int start,
			int limit) {
		StringBuffer sql = new StringBuffer();
		sql.append("select o from Event o where o.createTime>=:beginTime and o.createTime<:endTime ");
		if (null != type) {
			sql.append("and o.type=:type ");
		}
		if (null != organIds && organIds.size() > 0) {
			sql.append("and o.organId in :organIds ");
		}
		if (null != level) {
			sql.append("and o.eventLevel =:level ");
		}
		if (null != subType) {
			sql.append("and o.subType =:subType ");
		}
		sql.append("order by o.createTime asc");
		Query q = entityManager.createQuery(sql.toString());
		q.setParameter("beginTime", beginTime);
		q.setParameter("endTime", endTime);
		if (null != type) {
			q.setParameter("type", type);
		}
		if (null != organIds && organIds.size() > 0) {
			q.setParameter("organIds", organIds);
		}
		if (null != level) {
			q.setParameter("level", level);
		}
		if (null != subType) {
			q.setParameter("subType", subType);
		}
		q.setFirstResult(start);
		q.setMaxResults(limit);
		return q.getResultList();
	}

	@Override
	public int countEvent(Long beginTime, Long endTime, Integer type,
			List<Long> organIds, Integer level, Integer subType) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(o) from Event o where o.createTime>=:beginTime and o.createTime<:endTime ");
		if (null != type) {
			sql.append("and o.type=:type ");
		}
		if (null != organIds && organIds.size() > 0) {
			sql.append("and o.organId in :organIds ");
		}
		if (null != level) {
			sql.append("and o.eventLevel=:level ");
		}
		if (null != subType) {
			sql.append("and o.subType =:subType ");
		}
		sql.append("order by o.createTime asc");
		Query q = entityManager.createQuery(sql.toString());
		q.setParameter("beginTime", beginTime);
		q.setParameter("endTime", endTime);
		if (null != type) {
			q.setParameter("type", type);
		}
		if (null != organIds && organIds.size() > 0) {
			q.setParameter("organIds", organIds);
		}
		if (null != level) {
			q.setParameter("level", level);
		}
		if (null != subType) {
			q.setParameter("subType", subType);
		}
		Number count = (Number) q.getSingleResult();
		return count.intValue();
	}

	@Override
	public List<Event> listEventByType(Integer subType, int start, int limit,
			Long beginTime, Long endTime, String sourceName) {
		StringBuffer sql = new StringBuffer();
		sql.append("select o from Event o where o.subType = :subType ");
		if (null != beginTime) {
			sql.append("and o.createTime>=:beginTime ");
		}
		if (null != endTime) {
			sql.append("and o.createTime<:endTime ");
		}
		if (null != sourceName) {
			sql.append("and o.sourceName like :sourceName ");
		}
		sql.append("order by o.createTime asc");
		Query q = entityManager.createQuery(sql.toString());
		if (null != beginTime) {
			q.setParameter("beginTime", beginTime);
		}
		if (null != endTime) {
			q.setParameter("endTime", endTime);
		}
		if (null != sourceName) {
			q.setParameter("sourceName", "%" + sourceName + "%");
		}
		q.setParameter("subType", subType);
		q.setFirstResult(start);
		q.setMaxResults(limit);
		return q.getResultList();
	}

	@Override
	public Integer countEventByType(Integer subType, Long beginTime,
			Long endTime, String sourceName) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(o) from Event o where o.subType = :subType ");
		if (null != beginTime) {
			sql.append("and o.createTime>=:beginTime ");
		}
		if (null != endTime) {
			sql.append("and o.createTime<:endTime ");
		}
		if (null != sourceName) {
			sql.append("and o.sourceName like :sourceName ");
		}
		sql.append("order by o.createTime asc");
		Query q = entityManager.createQuery(sql.toString());
		if (null != beginTime) {
			q.setParameter("beginTime", beginTime);
		}
		if (null != endTime) {
			q.setParameter("endTime", endTime);
		}
		if (null != sourceName) {
			q.setParameter("sourceName", "%" + sourceName + "%");
		}
		q.setParameter("subType", subType);
		Number count = (Number) q.getSingleResult();
		return count.intValue();
	}

}
