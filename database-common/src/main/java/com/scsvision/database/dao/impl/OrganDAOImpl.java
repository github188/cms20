package com.scsvision.database.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.scsvision.database.dao.OrganDAO;
import com.scsvision.database.entity.Organ;

/**
 * @author sjt
 *
 */
@Stateless
public class OrganDAOImpl extends BaseDAOImpl<Organ, Long> implements OrganDAO {

	@Override
	public List<Organ> listOrganlikePath(Long organId) {
		String sql = "select o from Organ o where o.path like :organId";
		Query q = entityManager.createQuery(sql);
		q.setParameter("organId", "%" + organId + "%");
		return q.getResultList();
	}

	@Override
	public List<Organ> listOrgan(Long organId, String name, int start, int limit) {
		StringBuffer sql = new StringBuffer();
		sql.append("select o from Organ o where 1=1 ");
		if (null != organId) {
			sql.append("and o.path like :organId ");
		}
		if (StringUtils.isNotBlank(name)) {
			sql.append("and o.name like :name");
		}
		Query q = entityManager.createQuery(sql.toString());
		if (null != organId) {
			q.setParameter("organId", "%" + organId + "%");
		}
		if (StringUtils.isNotBlank(name)) {
			q.setParameter("name", "%" + name + "%");
		}
		q.setFirstResult(start);
		q.setMaxResults(limit);
		return q.getResultList();
	}

	@Override
	public Integer countOrgan(Long organId, String name) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(o) from Organ o where 1=1 ");
		if (null != organId) {
			sql.append("and o.path like :organId ");
		}
		if (StringUtils.isNotBlank(name)) {
			sql.append("and o.name like :name");
		}
		Query q = entityManager.createQuery(sql.toString());
		if (null != organId) {
			q.setParameter("organId", "%/" + organId + "/%");
		}
		if (StringUtils.isNotBlank(name)) {
			q.setParameter("name", "%" + name + "%");
		}
		Number count = (Number) q.getSingleResult();
		return count.intValue();
	}

	@Override
	public List<Organ> listOrganByIds(List<Long> ids) {
		String sql = "select o from Organ o where id in :ids";
		Query q = entityManager.createQuery(sql.toString());
		q.setParameter("ids", ids);
		return q.getResultList();
	}

}
