package com.scsvision.database.manager;

import java.util.List;

import javax.ejb.Local;

/**
 * EventResourceManager
 * 
 * @author sjt
 *         <p />
 *         Create at 2015 下午2:14:47
 */
@Local
public interface EventResourceManager {
	/**
	 * 根据eventId获取resourceId列表
	 * 
	 * @param eventId
	 * 
	 * @return resourceId列表
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午2:06:26
	 */
	public List<Long> getResourceIdsByEventId(Long eventId);

}
