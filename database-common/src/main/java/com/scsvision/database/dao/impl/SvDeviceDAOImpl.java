package com.scsvision.database.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.scsvision.database.dao.SvDeviceDAO;
import com.scsvision.database.entity.SvDevice;

/**
 * @author sjt
 *
 */
@Stateless
public class SvDeviceDAOImpl extends BaseDAOImpl<SvDevice, Long> implements
		SvDeviceDAO {

	@Override
	public void deleteSvDevice(Long organId) {
		Query q = entityManager
				.createQuery("delete from SvDevice o where o.organId =:organId");
		q.setParameter("organId", organId);
		q.executeUpdate();
	}

	@Override
	public Integer countDvr(String ip, String name) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(o) from SvDevice o where type = 30 ");
		if (StringUtils.isNotBlank(ip)) {
			sql.append("and o.ip like :ip ");
		}
		if (StringUtils.isNotBlank(name)) {
			sql.append("and o.name like :name");
		}
		Query q = entityManager.createQuery(sql.toString());
		if (StringUtils.isNotBlank(ip)) {
			q.setParameter("ip", "%/" + ip + "/%");
		}
		if (StringUtils.isNotBlank(name)) {
			q.setParameter("name", "%" + name + "%");
		}
		Number count = (Number) q.getSingleResult();
		return count.intValue();
	}

	@Override
	public List<SvDevice> listDvr(String ip, String name, Integer start,
			Integer limit) {
		StringBuffer sql = new StringBuffer();
		sql.append("select o from SvDevice o where type = 30 ");
		if (StringUtils.isNotBlank(ip)) {
			sql.append("and o.ip like :ip ");
		}
		if (StringUtils.isNotBlank(name)) {
			sql.append("and o.name like :name");
		}
		Query q = entityManager.createQuery(sql.toString());
		if (StringUtils.isNotBlank(ip)) {
			q.setParameter("ip", "%/" + ip + "/%");
		}
		if (StringUtils.isNotBlank(name)) {
			q.setParameter("name", "%" + name + "%");
		}
		q.setFirstResult(start);
		q.setMaxResults(limit);
		return q.getResultList();
	}

	@Override
	public List<SvDevice> listCamera(String name, String stakeNumber,
			String standardNumber, Long parentId, Integer start, Integer limit) {
		StringBuffer sb = new StringBuffer();
		sb.append("select o from SvDevice o where type = 31 ");
		if (StringUtils.isNotBlank(name)) {
			sb.append("and o.name like :name ");
		}
		if (StringUtils.isNotBlank(stakeNumber)) {
			sb.append("and o.stakeNumber like :stakeNumber ");
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			sb.append("and o.standardNumber like :standardNumber ");
		}
		if (null != parentId) {
			sb.append("and o.parentId = :parentId ");
		}
		Query q = entityManager.createQuery(sb.toString());
		if (StringUtils.isNotBlank(name)) {
			q.setParameter("name", "%" + name + "%");
		}
		if (StringUtils.isNotBlank(stakeNumber)) {
			q.setParameter("stakeNumber", "%" + stakeNumber + "%");
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			q.setParameter("standardNumber", "%" + standardNumber + "%");
		}
		if (null != parentId) {
			q.setParameter("parentId", parentId);
		}
		q.setFirstResult(start);
		q.setMaxResults(limit);
		return q.getResultList();
	}

	@Override
	public int countCamera(String name, String stakeNumber,
			String standardNumber, Long parentId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(o) from SvDevice o where type = 31 ");
		if (StringUtils.isNotBlank(name)) {
			sb.append("and o.name like :name ");
		}
		if (StringUtils.isNotBlank(stakeNumber)) {
			sb.append("and o.stakeNumber like :stakeNumber ");
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			sb.append("and o.standardNumber like :standardNumber ");
		}
		if (null != parentId) {
			sb.append("and o.parentId = :parentId ");
		}
		Query q = entityManager.createQuery(sb.toString());
		if (StringUtils.isNotBlank(name)) {
			q.setParameter("name", "%" + name + "%");
		}
		if (StringUtils.isNotBlank(stakeNumber)) {
			q.setParameter("stakeNumber", "%" + stakeNumber + "%");
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			q.setParameter("standardNumber", "%" + standardNumber + "%");
		}
		if (null != parentId) {
			q.setParameter("parentId", parentId);
		}
		Number count = (Number) q.getSingleResult();
		return count.intValue();
	}

	@Override
	public List<SvDevice> listCameraByStake(Long organId, Float beginStake,
			Float endStake) {
		String sql = "select o from SvDevice o where o.type = 31 and o.organId = :organId and o.floatStake >= :beginStake and o.floatStake <= :endStake";
		Query q = entityManager.createQuery(sql);
		q.setParameter("organId", organId);
		q.setParameter("beginStake", beginStake);
		q.setParameter("endStake", endStake);
		return q.getResultList();
	}
}
