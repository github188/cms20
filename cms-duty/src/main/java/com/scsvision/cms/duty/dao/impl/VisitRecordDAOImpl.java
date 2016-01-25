package com.scsvision.cms.duty.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.scsvision.cms.duty.dao.VisitRecordDAO;
import com.scsvision.database.entity.VisitRecord;

/**
 * VisitRecordDAOImpl
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午3:04:18
 */
@Stateless
public class VisitRecordDAOImpl extends BaseDutyDAOImpl<VisitRecord, Long>
		implements VisitRecordDAO {
	@Override
	public List<VisitRecord> listVisitRecord(Long beginTime, Long endTime,
			String monitor, int start, int limit) {
		StringBuffer sb = new StringBuffer();
		sb.append("select o from VisitRecord o where 1=1");
		if (null != beginTime) {
			sb.append(" and o.arriveTime >= :beginTime");
		}
		if (null != endTime) {
			sb.append(" and o.arriveTime < :endTime");
		}
		if (StringUtils.isNotBlank(monitor)) {
			sb.append(" and o.monitors like :monitor");
		}
		sb.append(" order by o.arriveTime asc");

		Query q = entityManager.createQuery(sb.toString());
		q.setFirstResult(start);
		q.setMaxResults(limit);

		if (null != beginTime) {
			q.setParameter("beginTime", beginTime);
		}
		if (null != endTime) {
			q.setParameter("endTime", endTime);
		}
		if (StringUtils.isNotBlank(monitor)) {
			q.setParameter("monitor", "%" + monitor + "%");
		}
		return q.getResultList();
	}

	@Override
	public int countVisitRecord(Long beginTime, Long endTime, String monitor) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(o) from VisitRecord o where 1=1");
		if (null != beginTime) {
			sb.append(" and o.arriveTime >= :beginTime");
		}
		if (null != endTime) {
			sb.append(" and o.arriveTime < :endTime");
		}
		if (StringUtils.isNotBlank(monitor)) {
			sb.append(" and o.monitors like :monitor");
		}

		Query q = entityManager.createQuery(sb.toString());

		if (null != beginTime) {
			q.setParameter("beginTime", beginTime);
		}
		if (null != endTime) {
			q.setParameter("endTime", endTime);
		}
		if (StringUtils.isNotBlank(monitor)) {
			q.setParameter("monitor", "%" + monitor + "%");
		}

		Number count = (Number) q.getSingleResult();
		return count.intValue();
	}
}
