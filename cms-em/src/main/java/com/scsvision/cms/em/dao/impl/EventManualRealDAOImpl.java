package com.scsvision.cms.em.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.scsvision.cms.em.dao.EventManualRealDAO;
import com.scsvision.database.entity.EventManualReal;

@Stateless
public class EventManualRealDAOImpl extends
		EmBaseDAOImpl<EventManualReal, Long> implements EventManualRealDAO {

	@Override
	public List<EventManualReal> emanualRealList(Integer start, Integer limit) {
		StringBuffer sql = new StringBuffer(
				"select e from EventManualReal e order by e.id");
		Query q = entityManager.createQuery(sql.toString());
		q.setFirstResult(start);
		q.setMaxResults(limit);

		return q.getResultList();
	}

}
