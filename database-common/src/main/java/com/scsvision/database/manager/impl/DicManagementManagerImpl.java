/**
 * 
 */
package com.scsvision.database.manager.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;

import com.scsvision.cms.annotation.Cache;
import com.scsvision.cms.interceptor.CacheInterceptor;
import com.scsvision.database.dao.DicManagementDAO;
import com.scsvision.database.entity.DicManagement;
import com.scsvision.database.manager.DicManagementManager;

/**
 * @author wangbinyu
 *
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Interceptors(CacheInterceptor.class)
public class DicManagementManagerImpl implements DicManagementManager {

	@EJB(beanName = "DicManagementDAOImpl")
	private DicManagementDAO dicManagementDAO;

	@Override
	@Cache
	public List<DicManagement> list() {
		return dicManagementDAO.list();
	}

}
