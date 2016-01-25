package com.scsvision.database.dao;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.database.entity.EventResource;

/**
 * EventResourceDAO
 * 
 * @author sjt
 *         <p />
 *         Create at 2015年 下午1:38:53
 */
@Local
public interface EventResourceDAO extends BaseDAO<EventResource, Long> {
	/**
	 * 根据eventId获取列表
	 * 
	 * @param eventId
	 * 
	 * @return 列表
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午2:09:22
	 */
	public List<EventResource> getResourceIdsByEventId(Long eventId);

	/**
	 * 根据事件id删除资源关系
	 * 
	 * @param id
	 *            事件id
	 */
	public void deleteByEventId(Long id);

	/**
	 * 根据事件id和资源id删除关联表
	 * 
	 * @param eventId
	 *            事件id
	 * @param resourceId
	 *            资源id
	 */
	public void deleteResource(Long eventId, Long resourceId);

	public void deleteByEventRealId(Long id);

	/**
	 * 获取所有具有关联的资源ID集合
	 * 
	 * @return 具有关联的资源ID集合
	 * @author MIKE
	 *         <p />
	 *         Create at 2016年1月19日 下午3:40:01
	 */
	public List<Long> listReferencedResourceIds();
}
