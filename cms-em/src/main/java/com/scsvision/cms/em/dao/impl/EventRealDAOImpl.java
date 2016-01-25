package com.scsvision.cms.em.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.scsvision.cms.em.dao.EventRealDAO;
import com.scsvision.database.entity.EventReal;

@Stateless
public class EventRealDAOImpl extends EmBaseDAOImpl<EventReal, Long> implements
		EventRealDAO {

	@Override
	public Integer getEventRealCount(Integer type) {
		StringBuffer sql = new StringBuffer(
				"select count(e) from EventReal e where e.type = :type");
		Query q = entityManager.createQuery(sql.toString());
		q.setParameter("type", type);
		Number count = (Number) q.getSingleResult();
		return count.intValue();
	}

	@Override
	public Integer countAlarmReal(Integer type, Integer subType,
			Integer eventLevel, Long beginTime, Long endTime,
			String confirmUserName, List<Long> organIds, String resourceName) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(o) from EventReal o where o.createTime>=:beginTime and o.createTime<:endTime ");
		if (null != type) {
			sql.append("and o.type=:type ");
		}
		if (null != subType) {
			sql.append("and o.subType=:subType ");
		}
		if (null != eventLevel) {
			sql.append("and o.eventLevel=:eventLevel ");
		}
		if (StringUtils.isNotBlank(confirmUserName)) {
			sql.append("and o.confirmUserName like :confirmUserName ");
		}
		if (StringUtils.isNotBlank(resourceName)) {
			sql.append("and o.resourceName like :resourceName ");
		}
		if (null != organIds && organIds.size() > 0) {
			sql.append("and o.organId in :organIds ");
		}
		sql.append("order by o.createTime asc");
		Query q = entityManager.createQuery(sql.toString());
		q.setParameter("beginTime", beginTime);
		q.setParameter("endTime", endTime);
		if (null != type) {
			q.setParameter("type", type);
		}
		if (null != subType) {
			q.setParameter("subType", subType);
		}
		if (null != eventLevel) {
			q.setParameter("eventLevel", eventLevel);
		}
		if (StringUtils.isNotBlank(confirmUserName)) {
			q.setParameter("confirmUserName", "%" + confirmUserName + "%");
		}
		if (StringUtils.isNotBlank(resourceName)) {
			q.setParameter("resourceName", "%" + resourceName + "%");
		}
		if (null != organIds && organIds.size() > 0) {
			q.setParameter("organIds", organIds);
		}
		Number count = (Number) q.getSingleResult();
		return count.intValue();
	}

	@Override
	public List<EventReal> listEventAlarmReal(Integer type, Integer subType,
			Integer eventLevel, Long beginTime, Long endTime,
			String confirmUserName, Integer start, Integer limit,
			List<Long> organIds, String resourceName) {
		StringBuffer sql = new StringBuffer();
		sql.append("select o from EventReal o where o.createTime>=:beginTime and o.createTime<:endTime ");
		if (null != type) {
			sql.append("and o.type=:type ");
		}
		if (null != subType) {
			sql.append("and o.subType=:subType ");
		}
		if (null != eventLevel) {
			sql.append("and o.eventLevel=:eventLevel ");
		}
		if (StringUtils.isNotBlank(confirmUserName)) {
			sql.append("and o.confirmUserName like :confirmUserName ");
		}
		if (StringUtils.isNotBlank(resourceName)) {
			sql.append("and o.resourceName like :resourceName ");
		}
		if (null != organIds && organIds.size() > 0) {
			sql.append("and o.organId in :organIds ");
		}
		sql.append("order by o.createTime asc");
		Query q = entityManager.createQuery(sql.toString());
		q.setParameter("beginTime", beginTime);
		q.setParameter("endTime", endTime);
		if (null != type) {
			q.setParameter("type", type);
		}
		if (null != subType) {
			q.setParameter("subType", subType);
		}
		if (null != eventLevel) {
			q.setParameter("eventLevel", eventLevel);
		}
		if (StringUtils.isNotBlank(confirmUserName)) {
			q.setParameter("confirmUserName", "%" + confirmUserName + "%");
		}
		if (StringUtils.isNotBlank(resourceName)) {
			q.setParameter("resourceName", "%" + resourceName + "%");
		}
		if (null != organIds && organIds.size() > 0) {
			q.setParameter("organIds", organIds);
		}
		q.setFirstResult(start);
		q.setMaxResults(limit);
		return q.getResultList();
	}

	@Override
	public List<EventReal> listEventRealByType(Integer subType, int start,
			int limit, Long beginTime, Long endTime, String sourceName) {
		StringBuffer sql = new StringBuffer();
		sql.append("select o from EventReal o where o.subType = :subType ");
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
	public List<EventReal> listEventRealByType(Integer type,Integer subType, 
			String detailType,String sourceName,int start, 
			int limit, Long beginTime,Long endTime, List<Long> organIds, 
			String confirmUserName, Short confirmFlag,Integer alarmLevel) {
		StringBuffer sql = new StringBuffer();
		if (null != subType) {
			sql.append("select o from EventReal o where o.subType = :subType ");
		}else{
			sql.append("select o from EventReal o where 1=1 ");
		}
		if (null != type) {
			sql.append("and o.type = :type ");
		}
		if (organIds.size() > 0) {
			sql.append("and o.organId in :organIds ");
		}
		if (StringUtils.isNotBlank(sourceName)) {
			sql.append("and o.sourceName like :sourceName ");
		}
		if (StringUtils.isNotBlank(detailType)) {
			sql.append("and o.detailType = :detailType ");
		}
		if (null != beginTime) {
			sql.append("and o.createTime >= :beginTime ");
		}
		if (null != endTime) {
			sql.append("and o.createTime < :endTime ");
		}
		if (StringUtils.isNotBlank(confirmUserName)) {
			sql.append("and o.confirmUserName like :confirmUserName ");
		}
		if (null != confirmFlag) {
			sql.append("and o.confirmFlag = :confirmFlag ");
		}
		if (null != alarmLevel) {
			sql.append("and o.eventLevel = :eventLevel ");
		}
		sql.append("order by o.createTime asc");
		Query q = entityManager.createQuery(sql.toString());
		if (organIds.size() > 0) {
			q.setParameter("organIds", organIds);
		}
		if (null != beginTime) {
			q.setParameter("beginTime", beginTime);
		}
		if (null != type) {
			q.setParameter("type", type);
		}
		if (null != endTime) {
			q.setParameter("endTime", endTime);
		}
		if (StringUtils.isNotBlank(confirmUserName)) {
			q.setParameter("confirmUserName", "%" + confirmUserName + "%");
		}
		if (StringUtils.isNotBlank(sourceName)) {		
			q.setParameter("sourceName","%" + sourceName + "%");
		}
		if (null != confirmFlag) {
			q.setParameter("confirmFlag", confirmFlag);
		}
		if (null != alarmLevel) {
			q.setParameter("eventLevel", alarmLevel);
		}
		if (StringUtils.isNotBlank(detailType)) {
			q.setParameter("detailType", detailType);
		}
		if (null != subType) {
			q.setParameter("subType", subType);
		}
		if(limit==0){
			return q.getResultList();
		}else{
			q.setFirstResult(start);
			q.setMaxResults(limit);
			return q.getResultList();
		}
		
	}

	@Override
	public int countEventRealByType(Integer type, Integer subType,
			Integer eventLevel, String detailType, List<Long> organIds) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(o) from EventReal o where 1=1");
		if (null != type) {
			sql.append(" and o.type=:type ");
		}
		if (organIds.size() > 0) {
			sql.append("and o.organId in :organIds ");
		}
		if (null != subType) {
			sql.append(" and o.subType=:subType ");
		}
		if (null != eventLevel) {
			sql.append(" and o.eventLevel = :eventLevel  ");
		}
		if (null != detailType) {
			sql.append(" and o.detailType = :detailType  ");
		}
		Query q = entityManager.createQuery(sql.toString());
		if (null != type) {
			q.setParameter("type", type);
		}
		if (organIds.size() > 0) {
			q.setParameter("organIds", organIds);
		}
		if (null != subType) {
			q.setParameter("subType", subType);
		}
		if (null != eventLevel) {
			q.setParameter("eventLevel", eventLevel);
		}
		if (null != detailType) {
			q.setParameter("detailType", detailType);
		}
		Number count = (Number) q.getSingleResult();
		return count.intValue();
	}

	@Override
	public int countEventRealByType(Integer type, Integer subType) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(o) from EventReal o where 1=1");
		if (null != type) {
			sql.append(" and o.type=:type ");
		}
		if (null != subType) {
			sql.append(" and o.subType=:subType ");
		}
		Query q = entityManager.createQuery(sql.toString());
		if (null != type) {
			q.setParameter("type", type);
		}
		if (null != subType) {
			q.setParameter("subType", subType);
		}
		Number count = (Number) q.getSingleResult();
		return count.intValue();
	}

	@Override
	public Integer countEventReal(Integer subType, Long beginTime,
			Long endTime, String sourceName) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(o) from EventReal o where o.subType = :subType ");
		if (null != beginTime) {
			sql.append("and o.createTime >= :beginTime ");
		}
		if (null != endTime) {
			sql.append("and o.createTime < :endTime ");
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
		if (StringUtils.isNotBlank(sourceName)) {
			q.setParameter("sourceName", "%" + sourceName + "%");
		}
		q.setParameter("subType", subType);
		Number count = (Number) q.getSingleResult();
		return count.intValue();
	}

	@Override
	public Integer countEventReal(Integer type,Integer subType, 
			String detailType,String sourceName,int start, 
			int limit, Long beginTime,Long endTime, List<Long> organIds, 
			String confirmUserName, Short confirmFlag,Integer alarmLevel) {
		StringBuffer sql = new StringBuffer();
		if (null != subType) {
			sql.append("select count(o) from EventReal o where o.subType = :subType ");
		}else{
			sql.append("select count(o) from EventReal o where 1=1 ");
		}
		if (organIds.size() > 0) {
			sql.append("and o.organId in :organIds ");
		}
		if (null != type) {
			sql.append("and o.type = :type ");
		}
		if (StringUtils.isNotBlank(sourceName)) {
			sql.append("and o.sourceName like :sourceName ");
		}
		if (StringUtils.isNotBlank(detailType)) {
			sql.append("and o.detailType = :detailType ");
		}
		if (null != beginTime) {
			sql.append("and o.createTime >= :beginTime ");
		}
		if (null != endTime) {
			sql.append("and o.createTime < :endTime ");
		}
		if (StringUtils.isNotBlank(confirmUserName)) {
			sql.append("and o.confirmUserName like :confirmUserName ");
		}
		if (null != confirmFlag) {
			sql.append("and o.confirmFlag = :confirmFlag ");
		}
		if (null != alarmLevel) {
			sql.append("and o.eventLevel = :eventLevel ");
		}
		sql.append("order by o.createTime asc");
		Query q = entityManager.createQuery(sql.toString());
		if (organIds.size() > 0) {
			q.setParameter("organIds", organIds);
		}
		if (null != type) {
			q.setParameter("type", type);
		}
		if (StringUtils.isNotBlank(sourceName)) {		
			q.setParameter("sourceName","%" + sourceName + "%");
		}
		if (null != beginTime) {
			q.setParameter("beginTime", beginTime);
		}
		if (null != endTime) {
			q.setParameter("endTime", endTime);
		}
		if (StringUtils.isNotBlank(confirmUserName)) {
			q.setParameter("confirmUserName",  "%" + confirmUserName + "%");
		}
		if (null != confirmFlag) {
			q.setParameter("confirmFlag", confirmFlag);
		}
		if (null != alarmLevel) {
			q.setParameter("eventLevel", alarmLevel);
		}
		if (StringUtils.isNotBlank(detailType)) {
			q.setParameter("detailType", detailType);
		}
		if (null != subType) {
			q.setParameter("subType", subType);
		}
		
		Number count = (Number) q.getSingleResult();
		return count.intValue();
	}

}
