package com.scsvision.database.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.scsvision.database.dao.TmDeviceDAO;
import com.scsvision.database.entity.Organ;
import com.scsvision.database.entity.TmDevice;

/**
 * TmDeviceDAOImpl
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午2:44:57
 */
@Stateless
public class TmDeviceDAOImpl extends BaseDAOImpl<TmDevice, Long> implements
		TmDeviceDAO {

	@Override
	public Map<Long, TmDevice> mapDeviceByOrgan(List<Organ> organs,
			Integer type[]) {
		StringBuffer sql = new StringBuffer();
		sql.append("select d from TmDevice d where d.organId in (");
		for (int i = 0; i < organs.size(); i++) {
			if (i != organs.size() - 1) {
				sql.append(organs.get(i).getId() + ",");
			} else {
				sql.append(organs.get(i).getId());
			}
		}
		sql.append(") ");
		if (type != null && type.length > 0) {
			sql.append("and d.type in (");
			for (int i = 0; i < type.length; i++) {
				if (i != type.length - 1) {
					sql.append(type[i] + ",");
				} else {
					sql.append(type[i]);
				}
			}
			sql.append(")");
		}
		Query q = entityManager.createQuery(sql.toString());
		List<TmDevice> list = q.getResultList();
		Map<Long, TmDevice> map = new HashMap<Long, TmDevice>();
		for (TmDevice td : list) {
			map.put(td.getId(), td);
		}
		return map;
	}

	@Override
	public void deleteTmDevice(Long organId) {
		Query q = entityManager
				.createQuery("delete from TmDevice o where o.organId =:organId");
		q.setParameter("organId", organId);
		q.executeUpdate();
	}

	@Override
	public List<TmDevice> listTmDevice(Long organId, String name,
			String standardNumber, String stakeNumber, String type,
			String subType, int start, int limit) {
		StringBuffer sql = new StringBuffer();
		sql.append("select d from TmDevice d where d.organId = :organId");
		if (StringUtils.isNotBlank(name)) {
			sql.append(" and d.name like :name");
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			sql.append(" and d.standardNumber = :standardNumber");
		}
		if (StringUtils.isNotBlank(stakeNumber)) {
			sql.append(" and d.stakeNumber = :stakeNumber");
		}
		if (StringUtils.isNotBlank(type)) {
			sql.append(" and d.type = :type");
		}
		if (StringUtils.isNotBlank(subType)) {
			sql.append(" and d.subType = :subType");
		}
		Query q = entityManager.createQuery(sql.toString());
		q.setParameter("organId", organId);
		q.setFirstResult(start);
		q.setMaxResults(limit);
		if (StringUtils.isNotBlank(name)) {
			q.setParameter("name", "%" + name + "%");
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			q.setParameter("standardNumber", standardNumber);
		}
		if (StringUtils.isNotBlank(stakeNumber)) {
			q.setParameter("stakeNumber", stakeNumber);
		}
		if (StringUtils.isNotBlank(type)) {
			q.setParameter("type", type);
		}
		if (StringUtils.isNotBlank(subType)) {
			q.setParameter("subType", subType);
		}
		List<TmDevice> list = q.getResultList();
		return list;
	}

	@Override
	public Integer countTmDevice(Long organId, String name,
			String standardNumber, String stakeNumber, String type,
			String subType) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(d) from TmDevice d where d.organId = :organId");
		if (StringUtils.isNotBlank(name)) {
			sql.append(" and d.name like :name");
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			sql.append(" and d.standardNumber = :standardNumber");
		}
		if (StringUtils.isNotBlank(stakeNumber)) {
			sql.append(" and d.stakeNumber = :stakeNumber");
		}
		if (StringUtils.isNotBlank(type)) {
			sql.append(" and d.type = :type");
		}
		if (StringUtils.isNotBlank(subType)) {
			sql.append(" and d.subType = :subType");
		}
		Query q = entityManager.createQuery(sql.toString());
		q.setParameter("organId", organId);
		if (StringUtils.isNotBlank(name)) {
			q.setParameter("name", "%" + name + "%");
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			q.setParameter("standardNumber", standardNumber);
		}
		if (StringUtils.isNotBlank(stakeNumber)) {
			q.setParameter("stakeNumber", stakeNumber);
		}
		if (StringUtils.isNotBlank(type)) {
			q.setParameter("type", type);
		}
		if (StringUtils.isNotBlank(subType)) {
			q.setParameter("subType", subType);
		}
		Number count = (Number) q.getSingleResult();
		return count.intValue();
	}

}
