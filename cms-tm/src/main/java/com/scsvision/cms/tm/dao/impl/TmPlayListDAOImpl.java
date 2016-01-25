/**
 * 
 */
package com.scsvision.cms.tm.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.scsvision.cms.tm.dao.TmPlayListDAO;
import com.scsvision.database.entity.TmPlayList;

/**
 * @author wangbinyu
 *
 */
@Stateless
public class TmPlayListDAOImpl extends BaseTmDAOImpl<TmPlayList, Long>
		implements TmPlayListDAO {

	@Override
	public List<TmPlayList> listPlaylist(String sizeType) {
		StringBuffer sql = new StringBuffer();
		sql.append("select o from TmPlayList o ");
		if (StringUtils.isNotBlank(sizeType)) {
			sql.append("where o.sizeType =:sizeType");
		}
		Query q = entityManager.createQuery(sql.toString());
		if (StringUtils.isNotBlank(sizeType)) {
			q.setParameter("sizeType", sizeType);
		}
		return q.getResultList();
	}

}
