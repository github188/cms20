package com.scsvision.database.manager.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import com.scsvision.cms.interceptor.CacheInterceptor;
import com.scsvision.database.dao.StandardNumberDAO;
import com.scsvision.database.entity.StandardNumber;
import com.scsvision.database.manager.StandardNumberManager;

/**
 * StandardNumberManagerImpl
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午3:52:20
 */
@Stateless
@Interceptors(CacheInterceptor.class)
public class StandardNumberManagerImpl implements StandardNumberManager {

	@EJB(beanName = "StandardNumberDAOImpl")
	private StandardNumberDAO snDAO;

	@Override
	public Object getByStandardNumber(String sn) {
		StandardNumber standardNumber = snDAO.findBySn(sn);
		if (null == standardNumber) {
			return null;
		}
		String className = standardNumber.getClassName();
		return snDAO.getBySn(className, sn);
	}
}
