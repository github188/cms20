package com.scsvision.database.manager.impl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;

import com.scsvision.cms.interceptor.CacheInterceptor;
import com.scsvision.database.dao.PackageVersionDAO;
import com.scsvision.database.entity.PackageVersion;
import com.scsvision.database.manager.PackageVersionManager;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Interceptors(CacheInterceptor.class)
public class PackageVersionManagerImpl implements PackageVersionManager {

	@EJB(name = "PackageVersionDTOImple")
	private PackageVersionDAO packageVersionDAO;

	@Override
	public Long getResourceIdByPackageType(String type) {
		Long resourceId = null;
		PackageVersion packageVersion = packageVersionDAO.loadByType(type);
		if (null != packageVersion) {
			resourceId = packageVersion.getResoureId();
		}
		return resourceId;
	}

	@Override
	public void updateOrSavePackageVersion(Long resourceId, String name,String type,
			String packageVersion) {
		PackageVersion packaversion = new PackageVersion();
		PackageVersion packageV = packageVersionDAO.loadByType(type);
		if (null != packageV) {
			packaversion.setName(name);
			packaversion.setResoureId(resourceId);
			packaversion.setVersion(packageVersion);
			packaversion.setType(type);
			packaversion.setId(packageV.getId());
			packageVersionDAO.update(packaversion);
		} else {
			packaversion.setName(name);
			packaversion.setId(resourceId);
			packaversion.setResoureId(resourceId);
			packaversion.setVersion(packageVersion);
			packaversion.setType(type);
			packageVersionDAO.save(packaversion);
		}

	}

}
