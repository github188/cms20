package com.scsvision.database.manager;

import javax.ejb.Local;


@Local
public interface PackageVersionManager {
	/**
	 * 
	 * @param type 包类型
	 * @return 资源id
	 */	
	public Long getResourceIdByPackageType(String type);
	/**
	 * 
	 * @param resourceId 资源id
	 * @param address	资源路径
	 * @param name		资源名称
	 * @param packageVersion	资源版本
	 */
	public void updateOrSavePackageVersion(Long resourceId, String name,String type,
			String packageVersion);
}
