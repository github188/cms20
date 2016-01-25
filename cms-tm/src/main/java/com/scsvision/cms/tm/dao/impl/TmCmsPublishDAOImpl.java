package com.scsvision.cms.tm.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.scsvision.cms.tm.dao.TmCmsPublishDAO;
import com.scsvision.database.entity.TmCmsPublish;

/**
 * TmCmsPublishDAOImpl
 * 
 * @author sjt
 *         <p />
 *         Create at 2015 下午2:56:57
 */
@Stateless
public class TmCmsPublishDAOImpl extends BaseTmDAOImpl<TmCmsPublish, Long>
		implements TmCmsPublishDAO {

	@Override
	public List<TmCmsPublish> listCmsPublish(Integer start, Integer limit,
			Long playlistId) {
		StringBuffer sql = new StringBuffer(
				"select o from TmCmsPublish o where 1=1");
		if (null != playlistId) {
			sql.append(" and o.playlistId =:playlistId");
		}
		Query q = entityManager.createQuery(sql.toString());
		if (null != playlistId) {
			q.setParameter("playlistId", playlistId);
		}
		q.setFirstResult(start);
		q.setMaxResults(limit);
		return q.getResultList();
	}

	@Override
	public Integer countCmsPublish(Long cmsId) {
		String sql = "select count(o) from TmCmsPublish o where o.cmsId = :cmsId";
		Query q = entityManager.createQuery(sql.toString());
		q.setParameter("cmsId", cmsId);
		Number count = (Number) q.getSingleResult();
		return count.intValue();
	}

	@Override
	public void deleteByPlaylistId(Long playlistId) {
		String sql = "delete from TmCmsPublish o where o.playlistId =:playlistId";
		Query q = entityManager.createQuery(sql.toString());
		q.setParameter("playlistId", playlistId);
		q.executeUpdate();
	}

	@Override
	public void deleteCmsPublish(List<Long> ids) {
		String sql = "delete from TmCmsPublish o where o.id in :ids";
		Query q = entityManager.createQuery(sql.toString());
		q.setParameter("ids", ids);
		q.executeUpdate();
	}

}
