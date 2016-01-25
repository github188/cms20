/**
 * 
 */
package com.scsvision.cms.maintain.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.scsvision.cms.maintain.dao.TmCmsPublishLogDAO;
import com.scsvision.database.entity.TmCmsPublishLog;

/**
 * @author wangbinyu
 *
 */
@Stateless
public class TmCmsPublishLogDAOImpl extends
		BaseMaintainDAOImpl<TmCmsPublishLog, Long> implements
		TmCmsPublishLogDAO {

	@Override
	public void updateCmsPublishLog(String businessId, Short flag) {
		String sql = "update TmCmsPublishLog o set o.flag =:flag where o.businessId =:businessId";
		Query q = entityManager.createQuery(sql);
		q.setParameter("flag", flag);
		q.setParameter("businessId", businessId);
		q.executeUpdate();
	}

	@Override
	public TmCmsPublishLog getCmsLog(Long cmsId) {
		String sql = "select o from TmCmsPublishLog o where o.cmsId =:cmsId order by sendTime desc";
		Query q = entityManager.createQuery(sql);
		q.setParameter("cmsId", cmsId);
		q.setFirstResult(0);
		q.setMaxResults(1);
		List<TmCmsPublishLog> cmsPublishLogs = q.getResultList();
		if (cmsPublishLogs.size() > 0) {
			return cmsPublishLogs.get(0);
		} else {
			return null;
		}

	}

	@Override
	public List<TmCmsPublishLog> listCmsLog(Long beginTime, Long endTime,
			String cmsName, Integer start, Integer limit) {
		StringBuffer sb = new StringBuffer(
				"select o from TmCmsPublishLog o where o.sendTime>=:beginTime and o.sendTime<:endTime");
		if (StringUtils.isNotBlank(cmsName)) {
			sb.append(" and o.cmsName like :cmsName");
		}
		Query q = entityManager.createQuery(sb.toString());
		q.setParameter("beginTime", beginTime);
		q.setParameter("endTime", endTime);
		if (StringUtils.isNotBlank(cmsName)) {
			q.setParameter("cmsName", cmsName);
		}
		q.setFirstResult(start);
		q.setMaxResults(limit);
		return q.getResultList();
	}

	@Override
	public List<TmCmsPublishLog> listCmsInfo(List<String> businessIds) {
		String sql = "select o from TmCmsPublishLog o where o.businessId in :businessIds";
		Query q = entityManager.createQuery(sql);
		q.setParameter("businessIds", businessIds);
		return q.getResultList();
	}

	@Override
	public Integer countCmsLog(String cmsName, Long beginTime, Long endTime) {
		StringBuffer sb = new StringBuffer(
				"select count(o) from TmCmsPublishLog o where o.sendTime>=:beginTime and o.sendTime<:endTime");
		if (StringUtils.isNotBlank(cmsName)) {
			sb.append(" and o.cmsName like :cmsName");
		}
		Query q = entityManager.createQuery(sb.toString());
		q.setParameter("beginTime", beginTime);
		q.setParameter("endTime", endTime);
		if (StringUtils.isNotBlank(cmsName)) {
			q.setParameter("cmsName", cmsName);
		}
		Number count = (Number) q.getSingleResult();
		return count.intValue();
	}

}
