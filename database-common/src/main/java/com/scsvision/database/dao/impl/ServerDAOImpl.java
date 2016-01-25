package com.scsvision.database.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.scsvision.database.dao.ServerDAO;
import com.scsvision.database.entity.Server;

/**
 * @author sjt
 *
 */
@Stateless
public class ServerDAOImpl extends BaseDAOImpl<Server, Long> implements
		ServerDAO {

	@Override
	public Server getServerBySN(String sn) {
		String sql = "select o from Server o where o.standardNumber = :sn";
		Query q = entityManager.createQuery(sql.toString());
		q.setParameter("sn", sn);
		return (Server) q.getSingleResult();
	}

	@Override
	public List<Server> getServerByType(Integer type) {
		String sql = "select o from Server o where o.type = :type";
		Query q = entityManager.createQuery(sql.toString());
		q.setParameter("type", type);
		return q.getResultList();
	}

	@Override
	public List<Server> listServer(String name, Integer type, int limit,
			int start) {
		StringBuffer sql = new StringBuffer("select o from Server o where 1=1");
		if (StringUtils.isNotBlank(name)) {
			sql.append(" and o.name like :name");
		}
		if (null != type) {
			sql.append(" and o.type = :type");
		}
		Query q = entityManager.createQuery(sql.toString());
		if (StringUtils.isNotBlank(name)) {
			q.setParameter("name", "%" + name + "%");
		}
		if (null != type) {
			q.setParameter("type", type);
		}
		q.setFirstResult(start);
		q.setMaxResults(limit);
		return q.getResultList();
	}

	@Override
	public Integer countServers(String name, Integer type) {
		StringBuffer sql = new StringBuffer(
				"select count(o) from Server o where 1=1");
		if (StringUtils.isNotBlank(name)) {
			sql.append(" and o.name like :name");
		}
		if (null != type) {
			sql.append(" and o.type = :type");
		}
		Query q = entityManager.createQuery(sql.toString());
		if (StringUtils.isNotBlank(name)) {
			q.setParameter("name", "%" + name + "%");
		}
		if (null != type) {
			q.setParameter("type", type);
		}
		Number count = (Number) q.getSingleResult();
		return count.intValue();
	}

}
