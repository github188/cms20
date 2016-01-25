package com.scsvision.cms.em.manager;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.scsvision.database.entity.EventManual;
import com.scsvision.database.entity.EventManualReal;

@Local
public interface EventManualManager {
	/**
	 * 获取未处理事件列表
	 * 
	 * @param start
	 *            开始数
	 * @param limit
	 *            每页显示数量
	 * @return 未处理事件列表
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午1:39:05
	 */
	public List<EventManualReal> emanualRealList(Integer start, Integer limit);

	/**
	 * 获取未处理事件列表
	 * 
	 * @param start
	 *            开始数
	 * @param limit
	 *            每页显示数量
	 * @return 未处理事件列表
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午1:39:10
	 */
	public Map<Long, EventManualReal> emanualRealMap(Integer start,
			Integer limit);

	/**
	 * 获取历史事件键值对
	 * 
	 * @param ids
	 *            主键id列表
	 * @return 事件键值对
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午2:28:14
	 */
	public Map<Long, EventManual> mapEvent(List<Long> ids);

	/**
	 * 获取实时事件键值对
	 * 
	 * @param ids
	 *            主键id列表
	 * @return 事件键值对
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午11:48:14
	 */
	public Map<Long, EventManualReal> mapEventReal(List<Long> ids);

	/**
	 * 录入事件
	 * 
	 * @param entity
	 *            事件实体
	 * @return 事件id
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午2:05:20
	 */
	public Long createEventManualReal(EventManualReal entity);

	/**
	 * 获取某条事件详情
	 * 
	 * @param id
	 *            事件id
	 * @return 事件
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午3:36:15
	 */
	public EventManualReal getEventManualReal(Long id);

	/**
	 * 删除事件
	 * 
	 * @param id
	 *            事件id
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 日 上午10:46:55
	 */
	public void deleteEventManualReal(Long id);

	/**
	 * 修改事件详情
	 * 
	 * @param entity
	 *            事件详情
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午11:05:47
	 */
	public void updateEventManualReal(EventManualReal entity);
}
