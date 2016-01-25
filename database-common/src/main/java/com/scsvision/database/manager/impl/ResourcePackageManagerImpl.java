package com.scsvision.database.manager.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;

import com.scsvision.cms.interceptor.CacheInterceptor;
import com.scsvision.database.dao.ResourceDAO;
import com.scsvision.database.dao.ResourcePackageDAO;
import com.scsvision.database.entity.ResourcePackage;
import com.scsvision.database.manager.ResourcePackageManager;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Interceptors(CacheInterceptor.class)
public class ResourcePackageManagerImpl implements ResourcePackageManager {

	@EJB(beanName = "ResourcePackageDAOImpl")
	private ResourcePackageDAO resourcePackageDAO;

	@EJB(beanName = "ResourceDAOImpl")
	private ResourceDAO resourceDAO;

	@Override
	public Map<Long, ResourcePackage> mapResourcePackageById(Long resourceId) {
		List<ResourcePackage> list = resourcePackageDAO.list();
		Map<Long, ResourcePackage> maps = new HashMap<Long, ResourcePackage>();
		for (ResourcePackage resourcePackage : list) {
			maps.put(resourcePackage.getId(), resourcePackage);
		}
		return maps;
	}

	@Override
	public Long saveResourcePackage(Long resourceId, String address,
			String name, String packageVersion) {
		ResourcePackage resourcePackage = null;
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("id", resourceId);
		List<ResourcePackage> list = resourcePackageDAO.findByPropertys(params);
		if (list.size() > 0) {
			resourcePackage = list.get(0);
			resourcePackage.setId(resourceId);
			resourcePackage.setName(name);
			resourcePackage.setAddress(address);
			resourcePackage.setPackageVersion(packageVersion);
			resourcePackage.setCreateTime(System.currentTimeMillis());
			resourcePackageDAO.update(resourcePackage);
		} else {
			resourcePackage = new ResourcePackage();
			resourcePackage.setId(resourceId);
			resourcePackage.setName(name);
			resourcePackage.setAddress(address);
			resourcePackage.setPackageVersion(packageVersion);
			resourcePackage.setCreateTime(System.currentTimeMillis());
			resourcePackageDAO.save(resourcePackage);
		}
		return resourcePackage.getId();

	}

	@Override
	public Long saveResourcePackage(ResourcePackage resourcePackage) {
		ResourcePackage resourcePacka = resourcePackageDAO.get(resourcePackage
				.getId());
		if (null != resourcePacka) {
			resourcePackageDAO.update(resourcePackage);
		} else {
			resourcePackageDAO.save(resourcePackage);
		}
		return resourcePackage.getId();
	}

	@Override
	public ResourcePackage gindResourceById(Long resourceId) {
		return resourcePackageDAO.get(resourceId);
	}

}
