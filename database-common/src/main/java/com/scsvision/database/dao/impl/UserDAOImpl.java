package com.scsvision.database.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.scsvision.database.dao.UserDAO;
import com.scsvision.database.entity.User;

/**
 * UserDAOImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015年8月6日 下午5:29:22
 */
@Stateless
public class UserDAOImpl extends BaseDAOImpl<User, Long> implements UserDAO {

	public List<User> list(String logonName, int start, int limit) {
		StringBuffer sql = new StringBuffer("select u from User u where 1=1");
		if (StringUtils.isNotBlank(logonName)) {
			sql.append(" and u.logonName like :logonName");
		}
		Query q = entityManager.createQuery(sql.toString());
		if (StringUtils.isNotBlank(logonName)) {
			q.setParameter("logonName", "%" + logonName + "%");
		}
		q.setFirstResult(start);
		q.setMaxResults(limit);
		return q.getResultList();
	}
	
	@Override
	public Integer count(String logonName, int start, int limit) {
		StringBuffer sql = new StringBuffer("select count(u) from User u where 1=1");
		if (StringUtils.isNotBlank(logonName)) {
			sql.append(" and u.logonName like :logonName");
		}
		Query q = entityManager.createQuery(sql.toString());
		if (StringUtils.isNotBlank(logonName)) {
			q.setParameter("logonName", "%" + logonName + "%");
		}
		Number count = (Number) q.getSingleResult();
		return count.intValue();
	}

	@Override
	public List<User> getUserByName(String name) {
		StringBuffer sql = new StringBuffer("select u from User u where 1=1");
		if (StringUtils.isNotBlank(name)) {
			sql.append(" and u.logonName = :name");
		}

		Query q = entityManager.createQuery(sql.toString());
		if (StringUtils.isNotBlank(name)) {
			q.setParameter("name", name);
		}
		return q.getResultList();
	}

	

}
