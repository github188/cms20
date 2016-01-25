package com.scsvision.database.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.scsvision.database.dao.UserGroupDAO;
import com.scsvision.database.entity.UserGroup;

/**
 * UserGroupDAOImpl
 * 
 * @author sjt
 *         <p />
 *         Create at 2015年 上午10:59:22
 */
@Stateless
public class UserGroupDAOImpl extends BaseDAOImpl<UserGroup, Long> implements
		UserGroupDAO {

	@Override
	public List<UserGroup> listUserGroup(String name, int start, int limit) {
		StringBuffer sql = new StringBuffer();
		sql.append("select o from UserGroup o where 1=1 ");
		if(StringUtils.isNotBlank(name)){
			sql.append("and o.name like :name");
		}
		Query q = entityManager.createQuery(sql.toString());
		if(StringUtils.isNotBlank(name)){
			q.setParameter("name", "%"+name+"%");
		}
		q.setFirstResult(start);
		q.setMaxResults(limit);
		return q.getResultList();
	}

	@Override
	public Integer countUserGroup(String name) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(o) from UserGroup o where 1=1 ");
		if(StringUtils.isNotBlank(name)){
			sql.append("and o.name like :name");
		}
		Query q = entityManager.createQuery(sql.toString());
		if(StringUtils.isNotBlank(name)){
			q.setParameter("name", "%"+name+"%");
		}
		Number count = (Number) q.getSingleResult();
		return count.intValue();
	}

}
