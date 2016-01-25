package com.scsvision.cms.em.manager;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.scsvision.cms.em.service.vo.StatEventTypeVO;
import com.scsvision.database.entity.Event;
import com.scsvision.database.entity.EventDeviceReal;
import com.scsvision.database.entity.EventReal;

/**
 * EventManager
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午2:44:47
 */
@Local
public interface EventManager {

	/**
	 * 按月统计每种类型的事件产生次数
	 * 
	 * @param beginTime
	 *            查询开始时间
	 * @param endTime
	 *            查询结束时间
	 * @param organIds
	 *            具有权限的真实机构ID列表
	 * @return 事件类型为key, 范围内每月统计的产生的次数为value的集合
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午2:58:52
	 */
	public Map<Integer, List<StatEventTypeVO>> statManualEventType(
			Long beginTime, Long endTime, List<Long> organIds);

	/**
	 * 统计各路段每天产生的报警次数
	 * 
	 * @param beginTime
	 *            查询开始时间
	 * @param endTime
	 *            查询结束时间
	 * @param organIds
	 *            具有权限的真实机构ID列表
	 * @return 路段真实机构ID为key, 每天产生的报警次数为value的集合
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 上午11:21:33
	 */
	public Map<Long, List<StatEventTypeVO>> statAlarmTimes(Long beginTime,
			Long endTime, List<Long> organIds);

	/**
	 * 查询历史报警列表
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param organIds
	 *            机构ID列表
	 * @param level
	 *            报警级别
	 * @param subType
	 *            报警子类型
	 * @param start
	 *            开始行号
	 * @param limit
	 *            要查询的行数
	 * @return 满足条件的历史报警列表
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 上午11:19:30
	 */
	public List<Event> listAlarm(Long beginTime, Long endTime,
			List<Long> organIds, Integer level, Integer subType, int start,
			int limit);

	/**
	 * 统计满足条件的报警数量
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param organIds
	 *            机构ID列表
	 * @param level
	 *            报警级别
	 * @param subType
	 *            报警子类型
	 * @return 满足条件的报警数量
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午2:13:54
	 */
	public int countAlarm(Long beginTime, Long endTime, List<Long> organIds,
			Integer level, Integer subType);

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
	 *         Create at 2015年 上午10:09:53
	 */
	public Integer countEventByType(Integer type, Long beginTime, Long endTime,
			String sourceName);

	/**
	 * 条件统计实时报警数
	 * 
	 * @param type
	 *            报警类型 1-报警 2-事件
	 * @param subType
	 *            子类型 1-交通事故 2-恶劣天气 3-地质灾害 4-交通控制 5-视频检测 6-其他 7-设备离线 8-阀值报警
	 * @param eventLevel
	 *            报警级别
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param confirmUserName
	 *            确认人
	 * @param organIds
	 *            机构id
	 * @param resourceName
	 *            设备源名称
	 * @return 实时报警条数
	 */
	public Integer countAlarmReal(Integer type, Integer subType,
			Integer eventLevel, Long beginTime, Long endTime,
			String confirmUserName, List<Long> organIds, String resourceName);

	/**
	 * 条件统计实时报警列表
	 * 
	 * @param type
	 *            报警类型 1-报警 2-事件
	 * @param subType
	 *            子类型 1-交通事故 2-恶劣天气 3-地质灾害 4-交通控制 5-视频检测 6-其他 7-设备离线 8-阀值报警
	 * @param eventLevel
	 *            报警级别
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param confirmUserName
	 *            确认人
	 * @param start
	 *            开始条数
	 * @param limit
	 *            需要条数
	 * @param organIds
	 *            机构id
	 * @param resourceName
	 *            设备源名称
	 * @return 实时报警列表
	 */
	public List<EventReal> listEventAlarmReal(Integer type, Integer subType,
			Integer eventLevel, Long beginTime, Long endTime,
			String confirmUserName, Integer start, Integer limit,
			List<Long> organIds, String resourceName);

	/**
	 * 根据ids查询阀值报警实时表
	 * 
	 * @param ids
	 *            id
	 * @return 阀值报警实时表列表
	 */
	public Map<Long, EventDeviceReal> listEeventDeviceRealByIds(List<Long> ids);

	/**
	 * 根据id获取历史事件
	 * 
	 * @param id
	 *            事件id
	 * @return 事件
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午11:21:07
	 */
	public Event getEvent(Long id);

	/**
	 * 清除实时事件，保存历史事件
	 * 
	 * @param eventId
	 *            事件id
	 * 
	 */
	public void saveEvent(Long eventId);

}
