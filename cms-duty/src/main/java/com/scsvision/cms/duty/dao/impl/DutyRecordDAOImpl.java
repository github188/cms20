package com.scsvision.cms.duty.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.scsvision.cms.duty.dao.DutyRecordDAO;
import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.database.entity.DutyRecord;

/**
 * DutyRecordDAOImpl
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 上午9:26:46
 */
@Stateless
public class DutyRecordDAOImpl extends BaseDutyDAOImpl<DutyRecord, Long>
		implements DutyRecordDAO {
	@Override
	public List<DutyRecord> listDutyRecord(String dutyUserName, Long beginTime,
			Long endTime, int start, int limit) {
		StringBuffer sb = new StringBuffer();
		sb.append("select o from DutyRecord o where 1=1 ");
		if (StringUtils.isNotBlank(dutyUserName)) {
			sb.append("and o.name like :dutyUserName ");
		}
		if (null != beginTime) {
			sb.append("and o.beginTime >= :beginTime ");
		}
		if (null != endTime) {
			sb.append("and o.endTime < :endTime ");
		}
		Query q = entityManager.createQuery(sb.toString());
		q.setFirstResult(start);
		q.setMaxResults(limit);
		if (StringUtils.isNotBlank(dutyUserName)) {
			q.setParameter("dutyUserName", "%" + dutyUserName + "%");
		}
		if (null != beginTime) {
			q.setParameter("beginTime", beginTime);
		}
		if (null != endTime) {
			q.setParameter("endTime", endTime);
		}
		return q.getResultList();
	}

	@Override
	public int countDutyRecord(String dutyUserName, Long beginTime, Long endTime) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(o) from DutyRecord o where 1=1 ");
		if (StringUtils.isNotBlank(dutyUserName)) {
			sb.append("and o.name like :dutyUserName ");
		}
		if (null != beginTime) {
			sb.append("and o.beginTime >= :beginTime ");
		}
		if (null != endTime) {
			sb.append("and o.endTime < :endTime ");
		}
		Query q = entityManager.createQuery(sb.toString());
		if (StringUtils.isNotBlank(dutyUserName)) {
			q.setParameter("dutyUserName", "%" + dutyUserName + "%");
		}
		if (null != beginTime) {
			q.setParameter("beginTime", beginTime);
		}
		if (null != endTime) {
			q.setParameter("endTime", endTime);
		}
		Number count = (Number) q.getSingleResult();
		return count.intValue();
	}

	@Override
	public DutyRecord getLatest() {
		String sql = "select o from DutyRecord o where o.endTime is null";
		Query q = entityManager.createQuery(sql);
		List<DutyRecord> list = q.getResultList();
		if (list.size() > 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"More than 1 latest DutyRecord found !");
		} else if (list.size() == 1) {
			return list.get(0);
		} else {
			return null;
		}
	}
}
