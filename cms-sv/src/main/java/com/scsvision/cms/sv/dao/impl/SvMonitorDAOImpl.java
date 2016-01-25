/**
 * 
 */
package com.scsvision.cms.sv.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.scsvision.cms.sv.dao.SvMonitorDAO;
import com.scsvision.database.entity.SvMonitor;

/**
 * @author wangbinyu
 *
 */
@Stateless
public class SvMonitorDAOImpl extends SvBaseDAOImpl<SvMonitor, Long> implements
		SvMonitorDAO {

	@Override
	public Integer countMonitor(Long wallId) {
		String sql = "select count(o) from SvMonitor o where o.wallId = :wallId";
		Query q = entityManager.createQuery(sql);
		q.setParameter("wallId", wallId);
		Number count = (Number) q.getSingleResult();
		return count.intValue();
	}

	@Override
	public List<SvMonitor> listMonitor(Long wallId, Integer start, Integer limit) {
		String sql = "select o from SvMonitor o where o.wallId = :wallId";
		Query q = entityManager.createQuery(sql);
		q.setParameter("wallId", wallId);
		q.setFirstResult(start);
		q.setMaxResults(limit);
		return q.getResultList();
	}

}
