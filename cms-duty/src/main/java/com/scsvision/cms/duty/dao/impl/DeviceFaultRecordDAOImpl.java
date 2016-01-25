package com.scsvision.cms.duty.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.scsvision.cms.duty.dao.DeviceFaultRecordDAO;
import com.scsvision.cms.duty.dto.CountDeviceFaultDTO.CountDevice;
import com.scsvision.database.entity.DeviceFaultRecord;

@Stateless
public class DeviceFaultRecordDAOImpl extends
		BaseDutyDAOImpl<DeviceFaultRecord, Long> implements
		DeviceFaultRecordDAO {

	@Override
	public List<DeviceFaultRecord> listDeviceFaultRecord(int start, int limit,
			Long beginTime, Long endTime) {
		StringBuffer sql = new StringBuffer();
		sql.append("select o from DeviceFaultRecord o where 1 = 1 ");
		if (null != beginTime) {
			sql.append("and o.recordTime>=:beginTime ");
		}
		if (null != endTime) {
			sql.append("and o.recordTime<:endTime ");
		}
		sql.append("order by o.recordTime desc");
		Query q = entityManager.createQuery(sql.toString());
		if (null != beginTime) {
			q.setParameter("beginTime", beginTime);
		}
		if (null != endTime) {
			q.setParameter("endTime", endTime);
		}
		q.setFirstResult(start);
		q.setMaxResults(limit);
		return q.getResultList();
	}

	@Override
	public Integer countDeviceFaultRecord(Long beginTime, Long endTime) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(o) from DeviceFaultRecord o where 1 = 1 ");
		if (null != beginTime) {
			sql.append("and o.recordTime>=:beginTime ");
		}
		if (null != endTime) {
			sql.append("and o.recordTime<:endTime ");
		}
		sql.append("order by o.recordTime asc");
		Query q = entityManager.createQuery(sql.toString());
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
	public List<DeviceFaultRecord> listDeviceFaultRecord(Integer deviceType,
			String deviceName, int start, int limit, Long beginTime,
			Long endTime, List<Long> organIds, String confirmUserName) {
		StringBuffer sql = new StringBuffer();
		sql.append("select o from DeviceFaultRecord o where 1 = 1 ");
		if (null != deviceType) {
			sql.append("and o.deviceType = :deviceType ");
		}
		if (StringUtils.isNotBlank(deviceName)) {
			sql.append("and o.deviceName like :deviceName ");
		}
		if (StringUtils.isNotBlank(confirmUserName)) {
			sql.append("and o.confirmUserName like :confirmUserName ");
		}
		if (organIds.size() > 0) {
			sql.append("and o.organId in :organIds ");
		}
		if (null != beginTime) {
			sql.append("and o.recordTime>=:beginTime ");
		}
		if (null != endTime) {
			sql.append("and o.recordTime<:endTime ");
		}
		sql.append("order by o.recordTime asc");
		Query q = entityManager.createQuery(sql.toString());
		if (null != deviceType) {
			q.setParameter("deviceType", deviceType);
		}
		if (StringUtils.isNotBlank(deviceName)) {
			q.setParameter("deviceName", "%" + deviceName + "%");
		}
		if (StringUtils.isNotBlank(confirmUserName)) {
			q.setParameter("confirmUserName", "%" + confirmUserName + "%");
		}
		if (organIds.size() > 0) {
			q.setParameter("organIds", organIds);
		}
		if (null != beginTime) {
			q.setParameter("beginTime", beginTime);
		}
		if (null != endTime) {
			q.setParameter("endTime", endTime);
		}

		q.setFirstResult(start);
		q.setMaxResults(limit);
		return q.getResultList();
	}

	@Override
	public Integer countDeviceFaultRecord(Integer deviceType,
			String deviceName, int start, int limit, Long beginTime,
			Long endTime, List<Long> organIds, String confirmUserName) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(o) from DeviceFaultRecord o where 1 = 1 ");
		if (null != deviceType) {
			sql.append("and o.deviceType = :deviceType ");
		}
		if (StringUtils.isNotBlank(deviceName)) {
			sql.append("and o.deviceName like :deviceName ");
		}
		if (StringUtils.isNotBlank(confirmUserName)) {
			sql.append("and o.confirmUserName like :confirmUserName ");
		}
		if (organIds.size() > 0) {
			sql.append("and o.organId in :organIds ");
		}
		if (null != beginTime) {
			sql.append("and o.recordTime>=:beginTime ");
		}
		if (null != endTime) {
			sql.append("and o.recordTime<:endTime ");
		}
		sql.append("order by o.recordTime asc");
		Query q = entityManager.createQuery(sql.toString());
		if (null != deviceType) {
			q.setParameter("deviceType", deviceType);
		}
		if (StringUtils.isNotBlank(deviceName)) {
			q.setParameter("deviceName", "%" + deviceName + "%");
		}
		if (StringUtils.isNotBlank(confirmUserName)) {
			q.setParameter("confirmUserName", "%" + confirmUserName + "%");
		}
		if (organIds.size() > 0) {
			q.setParameter("organIds", organIds);
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
	public List<DeviceFaultRecord> countByTypeDeviceFaultRecord(Long beginTime,
			Long endTime, List<Long> organIds) {
		StringBuffer sql = new StringBuffer();
		sql.append("select o from DeviceFaultRecord o where 1 = 1 ");
		if (null != beginTime) {
			sql.append("and o.recordTime>=:beginTime ");
		}
		if (null != endTime) {
			sql.append("and o.recordTime<:endTime ");
		}
		if (organIds.size() > 0) {
			sql.append("and o.organId in :organIds ");
		}
		sql.append("order by o.recordTime asc");
		Query q = entityManager.createQuery(sql.toString());
		if (organIds.size() > 0) {
			q.setParameter("organIds", organIds);
		}
		if (null != beginTime) {
			q.setParameter("beginTime", beginTime);
		}
		if (null != endTime) {
			q.setParameter("endTime", endTime);
		}
		return q.getResultList();
	}
}
