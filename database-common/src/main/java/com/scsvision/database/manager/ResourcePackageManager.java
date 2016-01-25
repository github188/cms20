package com.scsvision.database.manager;

import java.util.Map;

import javax.ejb.Local;

import com.scsvision.database.entity.ResourcePackage;



@Local
public interface ResourcePackageManager {
	/**
	 * 
	 * @param resourceId 资源id
	 * @return 以id为键的resource对象
	 */
	public Map<Long, ResourcePackage> mapResourcePackageById(Long resourceId);
	
	/**
	 * 
	 * @param resourceId resourceId 资源id
	 * @return	以id为键的resource对象
	 */
	public ResourcePackage gindResourceById(Long resourceId);
	
	/**
	 * 
	 * @param resource 保存包资源
	 * @return 返回包资源id
	 */
	public Long saveResourcePackage(Long resourceId,String address,String name,String packageVersion);
	/***
	 * 
	 * @param resourcePackage
	 * @return
	 */
	public Long saveResourcePackage(ResourcePackage resourcePackage);
	
}
