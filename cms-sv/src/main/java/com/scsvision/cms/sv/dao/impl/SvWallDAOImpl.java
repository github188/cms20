/**
 * 
 */
package com.scsvision.cms.sv.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.scsvision.cms.sv.dao.SvWallDAO;
import com.scsvision.database.entity.SvWall;

/**
 * @author wangbinyu
 *
 */
@Stateless
public class SvWallDAOImpl extends SvBaseDAOImpl<SvWall, Long> implements
		SvWallDAO {

	@Override
	public List<SvWall> listWall(Integer start, Integer limit) {
		String sql = "select o from SvWall o";
		Query q = entityManager.createQuery(sql);
		q.setFirstResult(start);
		q.setMaxResults(limit);
		return q.getResultList();
	}

	@Override
	public Integer countWall() {
		String sql = "select count(o) from SvWall o";
		Query q = entityManager.createQuery(sql);
		Number count = (Number) q.getSingleResult();
		return count.intValue();
	}

}
