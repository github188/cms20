package com.scsvision.cms.sv.manager.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.scsvision.cms.sv.dao.SvPlaySchemeDAO;
import com.scsvision.cms.sv.manager.PlaySchemeManager;
import com.scsvision.database.entity.SvPlayScheme;

/**
 * PlaySchemeManagerImpl
 * 
 * @author MIKE
 *         <p />
 *         Create at 2016年1月12日 下午4:52:39
 */
@Stateless
public class PlaySchemeManagerImpl implements PlaySchemeManager {
	@EJB(beanName = "SvPlaySchemeDAOImpl")
	private SvPlaySchemeDAO svPlaySchemeDAO;

	@Override
	public Long createPlayScheme(String name, Integer screenNo,
			String schemeConfig) {
		SvPlayScheme entity = new SvPlayScheme();
		entity.setName(name);
		entity.setScreenNo(screenNo);
		entity.setSchemeConfig(schemeConfig);
		svPlaySchemeDAO.save(entity);
		return entity.getId();
	}

	@Override
	public List<SvPlayScheme> listPlayScheme() {
		return svPlaySchemeDAO.list();
	}

	@Override
	public void updatePlayScheme(Long id, String name, Integer screenNo,
			String schemeConfig) {
		SvPlayScheme entity = svPlaySchemeDAO.get(id);
		entity.setName(name);
		entity.setScreenNo(screenNo);
		entity.setSchemeConfig(schemeConfig);
		svPlaySchemeDAO.update(entity);
	}

	@Override
	public void deletePlayScheme(Long id) {
		svPlaySchemeDAO.deleteById(id);
	}
}
