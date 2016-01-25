package com.scsvision.cms.em.manager;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.database.entity.EventReal;

@Local
public interface EventRealManager {
	/**
	 * 根据类型获取事件数量
	 * 
	 * @param type
	 *            事件类型 1-报警 2-事件
	 * @return 事件数量
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午10:58:46
	 */
	public Integer getEventRealCount(Integer type);

	/**
	 * 根据类型获取实时事件列表
	 * 
	 * @param type
	 *            事件类型
	 * @param start
	 *            开始行号
	 * @param limit
	 *            要查询的行数
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param sourceName
	 *            记录员
	 * @return 满足条件的事件列表
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午11:26:03
	 */
	public List<EventReal> listEventRealByType(Integer type, int start,
			int limit, Long beginTime, Long endTime, String sourceName);

	/**
	 * 根据类型获取实时事件列表
	 * 
	 * @param type
	 *            事件类型
	 * 
	 * @param start
	 *            开始行号
	 * 
	 * @param limit
	 *            要查询的行数
	 * 
	 * @param beginTime
	 *            开始时间
	 * 
	 * @param endTime
	 *            结束时间
	 * 
	 * @param organId
	 *            所属机构id
	 * 
	 * @param confirmUserName
	 *            确认人
	 * 
	 * @param confirmFlag
	 *            确认票号
	 * 
	 * @param alarmLevel
	 *            报警等级
	 * @return
	 */
	public List<EventReal> listEventRealByType(Integer deviceType,
			Integer type, Integer subType, String sourceName,
			String detailType, int start, int limit, Long beginTime,
			Long endTime, List<Long> organIds, String confirmUserName,
			Short confirmFlag, Integer alarmLevel);

	/**
	 * 根据 获取实时事件数量
	 * 
	 * @param type
	 *            事件类型
	 * @param start
	 *            开始行号
	 * @param endTime
	 *            结束时间
	 * @param sourceName
	 *            记录员
	 * @return 满足条件的事件列表
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午10:00:03
	 */
	public Integer countEventReal(Integer type, Long beginTime, Long endTime,
			String sourceName);

	/**
	 * 根据 获取实时事件数量
	 * 
	 * @param type
	 *            事件类型
	 * 
	 * @param start
	 *            开始行号
	 * 
	 * @param limit
	 *            要查询的行数
	 * 
	 * @param beginTime
	 *            开始时间
	 * 
	 * @param endTime
	 *            结束时间
	 * 
	 * @param organIds
	 *            所属机构id
	 * 
	 * @param confirmUserName
	 *            确认人
	 * 
	 * @param confirmFlag
	 *            确认票号
	 * 
	 * @param alarmLevel
	 *            报警等级
	 * @return
	 */
	public Integer countEventReal(Integer deviceType, Integer type,
			Integer subType, String sourceName, String detailType, int start,
			int limit, Long beginTime, Long endTime, List<Long> organIds,
			String confirmUserName, Short confirmFlag, Integer alarmLevel);

	/**
	 * 获取根据类型统计事件数量的列表
	 * 
	 * @return 数量列表
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午3:13:51
	 */
	public List<Integer> countEventAndAlarm(List<Long> organIds);

	/**
	 * 录入事件
	 * 
	 * @param entity
	 *            事件
	 * @return 事件Id
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午2:04:22
	 */
	public Long saveEventReal(EventReal entity);

	/**
	 * 获取事件详情
	 * 
	 * @param id
	 *            事件id
	 * @return 事件
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午3:24:23
	 */
	public EventReal getEventRealDetail(Long id);

	/**
	 * 删除事件
	 * 
	 * @param id
	 *            事件id
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午10:41:19
	 */
	public void deleteEventReal(Long id);

	/**
	 * 修改事件
	 * 
	 * @param entity
	 *            要修改的事件信息
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午11:03:06
	 */
	public void updateEventReal(EventReal entity);

}
