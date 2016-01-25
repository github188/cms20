package com.scsvision.database.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;

import com.scsvision.cms.interceptor.CacheInterceptor;
import com.scsvision.database.dao.ResourceImageDAO;
import com.scsvision.database.entity.ResourceImage;
import com.scsvision.database.manager.ResourceImageManager;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Interceptors(CacheInterceptor.class)
public class ResourceImageManagerImpl implements ResourceImageManager {

	@EJB(beanName = "ResourceImageDAOImpl")
	private ResourceImageDAO resourceImageDAO;

	@Override
	public Map<Long, ResourceImage> mapImageByIds(List<Long> ids) {
		Map<Long, ResourceImage> map = new HashMap<Long, ResourceImage>();
		map = resourceImageDAO.map(ids);
		return map;
	}

	@Override
	public Long createImage(ResourceImage entity) {
		resourceImageDAO.save(entity);
		return entity.getId();
	}

	@Override
	public String findAddressById(Long resourceId) {
		return resourceImageDAO.get(resourceId) != null ? resourceImageDAO.get(
				resourceId).getAddress() : null;
	}
}
