package com.scsvision.database.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.scsvision.cms.constant.TypeDefinition;
import com.scsvision.database.dao.VOrganDAO;
import com.scsvision.database.entity.VirtualOrgan;

/**
 * VOrganDAOImpl
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午4:43:06
 */
@Stateless
public class VOrganDAOImpl extends BaseDAOImpl<VirtualOrgan, Long> implements
		VOrganDAO {
	@Override
	public VirtualOrgan getUgRoot(Long ugId) {
		String sql = "select o from VirtualOrgan o where o.parentId is null and o.userGroupId = :ugId";
		Query q = entityManager.createQuery(sql);
		q.setParameter("ugId", ugId);
		return (VirtualOrgan) q.getSingleResult();
	}

	@Override
	public List<VirtualOrgan> listUgOrgan(Long id) {
		String sql = "select o from VirtualOrgan o where o.path like :id";
		Query q = entityManager.createQuery(sql);
		q.setParameter("id", "%" + id.toString() + "%");
		return q.getResultList();
	}

	@Override
	public List<VirtualOrgan> listChildren(Long id) {
		String sql = "select o from VirtualOrgan o where o.parentId = :id";
		Query q = entityManager.createQuery(sql);
		q.setParameter("id", id);
		return q.getResultList();
	}

	@Override
	public List<VirtualOrgan> getvOrganList(Long userGroupId) {
		StringBuffer sql = new StringBuffer(
				"select vo from VirtualOrgan vo where  vo.userGroupId = :userGroupId");
		Query q = entityManager.createQuery(sql.toString());
		if (null != userGroupId) {
			q.setParameter("userGroupId", userGroupId);
		}
		return q.getResultList();
	}

	@Override
	public List<VirtualOrgan> listUgOrganInTypes(Long ugId,
			List<Integer> types, Integer visible) {
		StringBuffer sql = new StringBuffer();
		sql.append("select o from VirtualOrgan o where o.userGroupId=:ugId");
		if (visible != null) {
			sql.append(" and o.visible =:visible");
		}
		if (types != null && types.size() > 0) {
			sql.append(" and o.type in :types");
		}
		Query q = entityManager.createQuery(sql.toString());
		q.setParameter("ugId", ugId);
		if (visible != null) {
			q.setParameter("visible", visible);
		}
		if (types != null && types.size() > 0) {
			q.setParameter("types", types);
		}
		return q.getResultList();
	}

	@Override
	public List<VirtualOrgan> listChildInTypes(Long id, List<Integer> types) {
		StringBuffer sql = new StringBuffer();
		sql.append("select o from VirtualOrgan o where o.path like :id");
		if (types != null && types.size() > 0) {
			sql.append(" and o.type in :types");
		}
		Query q = entityManager.createQuery(sql.toString());
		q.setParameter("id", "%" + id.toString() + "%");
		if (types != null && types.size() > 0) {
			q.setParameter("types", types);
		}
		return q.getResultList();
	}

	@Override
	public List<VirtualOrgan> listChildDeviceInTypes(Long id,
			List<Integer> types) {
		StringBuffer sql = new StringBuffer();
		sql.append("select o from VirtualOrgan o where o.parentId = :id");
		if (types != null && types.size() > 0) {
			sql.append(" and o.type in :types");
		}
		Query q = entityManager.createQuery(sql.toString());
		q.setParameter("id", id);
		if (types != null && types.size() > 0) {
			q.setParameter("types", types);
		}
		return q.getResultList();
	}

	@Override
	public List<VirtualOrgan> listUgDevice(Long ugId) {
		String sql = "select o from VirtualOrgan o where o.userGroupId=:ugId and o.type >= :type";
		Query q = entityManager.createQuery(sql);
		q.setParameter("ugId", ugId);
		q.setParameter("type", Integer.valueOf(TypeDefinition.VIDEO_DVR));
		return q.getResultList();
	}

	@Override
	public List<VirtualOrgan> listVOrgan(Long deviceId, Integer type) {
		String sql = "select o from VirtualOrgan o where o.deviceId=:deviceId and o.type = :type";
		Query q = entityManager.createQuery(sql);
		if (deviceId != null) {
			q.setParameter("deviceId", deviceId);
		}
		if (type != null) {
			q.setParameter("type", type);
		}
		return q.getResultList();
	}

	@Override
	public void deleteByDeviceId(Long deviceId) {
		Query q = entityManager
				.createQuery("delete from VirtualOrgan o where o.deviceId =:deviceId");
		q.setParameter("deviceId", deviceId);
		q.executeUpdate();

	}

	@Override
	public List<VirtualOrgan> listSvVirtualOrgan(Long parentId) {
		String sql = "select o from VirtualOrgan o where o.path like :id and o.type < :type";
		Query q = entityManager.createQuery(sql);
		q.setParameter("id", "%" + parentId.toString() + "%");
		q.setParameter("type", Integer.valueOf(TypeDefinition.VIDEO_DVR + 1000));
		return q.getResultList();
	}
}
