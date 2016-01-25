package com.scsvision.cms.em.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.scsvision.cms.em.dao.EventDeviceRealDAO;
import com.scsvision.database.entity.EventDevice;
import com.scsvision.database.entity.EventDeviceReal;

@Stateless
public class EventDeviceRealDAOImpl extends
		EmBaseDAOImpl<EventDeviceReal, Long> implements EventDeviceRealDAO {

	@Override
	public List<EventDeviceReal> edeviceRealList(Integer start, Integer limit) {
		StringBuffer sql = new StringBuffer(
				"select e from EventDeviceReal e order by e.id");
		Query q = entityManager.createQuery(sql.toString());
		if (null != start) {
			q.setFirstResult(start);
		}
		if (null != limit) {
			q.setMaxResults(limit);
		}
		return q.getResultList();
	}

	@Override
	public Integer countByDeviceType(Integer deviceType, List<Long> ids) {
		StringBuffer sql = new StringBuffer();
		if (deviceType >= 3100 && deviceType < 3999) {
			sql.append("select count(o) from EventDeviceReal o where o.deviceType >= 3100 and o.deviceType <3999 ");
		}
		if (deviceType >= 4000 && deviceType < 4010) {
			sql.append("select count(o) from EventDeviceReal o where o.deviceType >= 4000 and o.deviceType <4010 ");
		}
		if (deviceType >= 4100 && deviceType < 4199) {
			sql.append("select count(o) from EventDeviceReal o where o.deviceType >= 4100 and o.deviceType <4199 ");
		}if (deviceType >= 4400 && deviceType < 4405) {
			sql.append("select count(o) from EventDeviceReal o where o.deviceType >= 4400 and o.deviceType <4405 ");
		} else {
			sql.append("select count(o) from EventDeviceReal o where o.deviceType = :deviceType ");
		}
		if (ids.size() > 0) {
			sql.append("and o.id in :ids ");
		}
		Query q = entityManager.createQuery(sql.toString());
		if (ids.size() > 0) {
			q.setParameter("ids", ids);
		}
		if (deviceType >= 3100 && deviceType < 3999) {

		}
		if (deviceType >= 4000 && deviceType < 4010) {

		}
		if (deviceType >= 4100 && deviceType < 4199) {

		} 
		if (deviceType >= 4400 && deviceType < 4405) {
			
		} else {
			q.setParameter("deviceType", deviceType);
		}

		Number count = (Number) q.getSingleResult();
		return count.intValue();
	}

	@Override
	public List<EventDeviceReal> listByDeviceType(Integer deviceType,
			List<Long> ids, int start, int limit) {
		StringBuffer sql = new StringBuffer();
		if (deviceType >= 3100 && deviceType < 3999) {
			sql.append("select o from EventDeviceReal o where o.deviceType >= 3100 and o.deviceType <3999 ");
		}
		if (deviceType >= 4000 && deviceType < 4010) {
			sql.append("select o from EventDeviceReal o where o.deviceType >= 4000 and o.deviceType <4010 ");
		}
		if (deviceType >= 4100 && deviceType < 4199) {
			sql.append("select o from EventDeviceReal o where o.deviceType >= 4100 and o.deviceType <4199 ");
		} else {
			sql.append("select o from EventDeviceReal o where o.deviceType = :deviceType ");
		}
		if (ids.size() > 0) {
			sql.append("and o.id in :ids ");
		}
		Query q = entityManager.createQuery(sql.toString());
		if (ids.size() > 0) {
			q.setParameter("ids", ids);
		}
		if (deviceType >= 3100 && deviceType < 3999) {

		}
		if (deviceType >= 4000 && deviceType < 4010) {

		}
		if (deviceType >= 4100 && deviceType < 4199) {

		} else {
			q.setParameter("deviceType", deviceType);
		}
		q.setFirstResult(start);
		q.setMaxResults(limit);

		return q.getResultList();
	}

}
