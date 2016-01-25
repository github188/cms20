/**
 * 
 */
package com.scsvision.database.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.scsvision.database.dao.SvPresetDAO;
import com.scsvision.database.entity.SvPreset;

/**
 * @author wangbinyu
 *
 */
@Stateless
public class SvPresetDAOImpl extends BaseDAOImpl<SvPreset, Long> implements
		SvPresetDAO {

	@Override
	public Integer getLastPreset(Long deviceId) {
		Integer result = 0;
		String sql = "select Max(o.presetValue) from SvPreset o where o.deviceId =:deviceId";
		Query q = entityManager.createQuery(sql);
		q.setParameter("deviceId", deviceId);
		Number count = (Number) q.getSingleResult();
		if (null != count) {
			result = count.intValue();
		}
		return result;
	}
	
	@Override
	public List<Long> listReferencedResourceIds() {
		String sql = "select o.resourceId from SvPreset o";
		Query q = entityManager.createQuery(sql);
		return q.getResultList();
	}
}
