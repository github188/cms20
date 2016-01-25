package com.scsvision.database.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;

import com.scsvision.cms.annotation.Cache;
import com.scsvision.cms.interceptor.CacheInterceptor;
import com.scsvision.database.dao.DicManufactureDAO;
import com.scsvision.database.entity.DicManufacture;
import com.scsvision.database.manager.DicManufactureManager;

/**
 * @author sjt
 *
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Interceptors(CacheInterceptor.class)
public class DicManufactureManagerImpl implements DicManufactureManager {

	@EJB(beanName = "DicManufactureDAOImpl")
	private DicManufactureDAO dicManufactureDAO;

	@Override
	@Cache
	public Map<Long, DicManufacture> getvDicManufactureList() {
		List<DicManufacture> dmlist = dicManufactureDAO.list();
		Map<Long, DicManufacture> map = new HashMap<Long, DicManufacture>();
		for (DicManufacture dm : dmlist) {
			map.put(dm.getId(), dm);
		}
		return map;
	}
}
