package com.scsvision.cms.duty.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.scsvision.cms.duty.dao.DutyUserDAO;
import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.database.entity.DutyUser;

/**
 * DutyTeamDAOImpl
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 上午9:27:22
 */
@Stateless
public class DutyUserDAOImpl extends BaseDutyDAOImpl<DutyUser, Long> implements
		DutyUserDAO {
	@Override
	public DutyUser getByLoginName(String loginName) throws BusinessException {
		String sql = "select o from DutyUser o where o.loginName = :loginName";
		Query q = entityManager.createQuery(sql);
		q.setParameter("loginName", loginName);
		List<DutyUser> list = q.getResultList();
		if (list.size() == 0) {
			return null;
		}
		if (list.size() > 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"More than 1 DutyUser loginName[" + loginName + "] found !");
		}
		return list.get(0);
	}

	@Override
	public Integer countDutyUser(String name, String master) {
		StringBuffer sql = new StringBuffer(
				"select count(o) from DutyUser o where 1=1");
		if (StringUtils.isNotBlank(name)) {
			sql.append(" and o.name like :name");
		}
		if (StringUtils.isNotBlank(master)) {
			sql.append(" and o.master like :master");
		}
		Query q = entityManager.createQuery(sql.toString());
		if (StringUtils.isNotBlank(name)) {
			q.setParameter("name", "%" + name + "%");
		}
		if (StringUtils.isNotBlank(master)) {
			q.setParameter("master", "%" + master + "%");
		}
		Number count = (Number) q.getSingleResult();
		return count.intValue();
	}

	@Override
	public List<DutyUser> listDutyUser(Long organId) {
		String sql = "select o from DutyUser o where o.organId = :organId ";
		Query q = entityManager.createQuery(sql);
		q.setParameter("organId", organId);
		List<DutyUser> list = q.getResultList();
		return list;
	}

	@Override
	public List<DutyUser> dutyUserList(Long organId, int start, int limit) {
		StringBuffer sql = new StringBuffer(
				"select o from DutyUser o where 1=1");
		if (null != organId) {
			sql.append(" and o.organId =:organId");
		}
		Query q = entityManager.createQuery(sql.toString());
		if (null != organId) {
			q.setParameter("organId", organId);
		}
		q.setFirstResult(start);
		q.setMaxResults(limit);
		List<DutyUser> list = q.getResultList();
		return list;
	}

	@Override
	public Integer dutyUserCount(Long organId) {
		StringBuffer sql = new StringBuffer(
				"select count(o) from DutyUser o where 1=1");
		if (null != organId) {
			sql.append(" and o.organId =:organId");
		}
		Query q = entityManager.createQuery(sql.toString());
		if (null != organId) {
			q.setParameter("organId", organId);
		}
		Number count = (Number) q.getSingleResult();
		return count.intValue();
	}
}
