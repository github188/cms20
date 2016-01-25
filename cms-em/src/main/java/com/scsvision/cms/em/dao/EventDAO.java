package com.scsvision.cms.em.dao;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.database.entity.Event;

/**
 * EventDAO
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午2:42:25
 */
@Local
public interface EventDAO extends EmBaseDAO<Event, Long> {

	/**
	 * 查询时间范围内的事件列表
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param type
	 *            事件主类型,参见
	 *            {@link com.scsvision.cms.constant.TypeDefinition#EVENT_ALARM},
	 *            {@link com.scsvision.cms.constant.TypeDefinition#EVENT_MANUAL}
	 * @param organIds
	 *            具有权限的真实机构ID列表
	 * @param level
	 *            报警级别
	 * @param subType
	 *            报警子类型
	 * @param start
	 *            开始行号
	 * @param limit
	 *            要查询的行数
	 * @return
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午3:02:46
	 */
	public List<Event> listEvent(Long beginTime, Long endTime, Integer type,
			List<Long> organIds, Integer level, Integer subType, int start,
			int limit);

	/**
	 * 统计时间范围内的事件数量
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param type
	 *            事件主类型,参见
	 *            {@link com.scsvision.cms.constant.TypeDefinition#EVENT_ALARM},
	 *            {@link com.scsvision.cms.constant.TypeDefinition#EVENT_MANUAL}
	 * @param organIds
	 *            具有权限的真实机构ID列表
	 * @param level
	 *            报警级别
	 * @param subType
	 *            报警子类型
	 * @return 时间范围内的事件数量
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午2:15:32
	 */
	public int countEvent(Long beginTime, Long endTime, Integer type,
			List<Long> organIds, Integer level, Integer subType);

	/**
	 * 根据类型获取历史事件列表
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
	 *         Create at 2015年 下午2:17:53
	 */
	public List<Event> listEventByType(Integer type, int start, int limit,
			Long beginTime, Long endTime, String sourceName);

	/**
	 * 根据类型获取历史事件数量
	 * 
	 * @param type
	 *            事件类型
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param sourceName
	 *            记录员
	 * @return 满足条件的事件数量
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午10:07:53
	 */
	public Integer countEventByType(Integer type, Long beginTime, Long endTime,
			String sourceName);
}
