/**
 * 
 */
package com.scsvision.cms.sv.dao.impl;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.scsvision.cms.sv.dao.SvWallSchemeItemDAO;
import com.scsvision.database.entity.SvWallSchemeItem;

/**
 * @author wangbinyu
 *
 */
@Stateless
public class SvWallSchemeItemDAOImpl extends SvBaseDAOImpl<SvWallSchemeItem, Long> implements
		SvWallSchemeItemDAO {

	@Override
	public void deleteItem(Long wallSchemeId) {
		Query q = entityManager
				.createQuery("delete from SvWallSchemeItem o where o.wallSchemeId =:wallSchemeId");
		q.setParameter("wallSchemeId", wallSchemeId);
		q.executeUpdate();
	}


}
