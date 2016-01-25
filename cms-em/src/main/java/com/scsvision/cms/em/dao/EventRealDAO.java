package com.scsvision.cms.em.dao;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.database.entity.EventReal;

@Local
public interface EventRealDAO extends EmBaseDAO<EventReal, Long> {
	/**
	 * 根据类型获取事件数量
	 * 
	 * @param type
	 *            类型 1-报警 2-事件
	 * @return 事件数量
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午9:20:03
	 */
	public Integer getEventRealCount(Integer type);

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
	 *         Create at 2015年 上午11:37:53
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
	public List<EventReal> listEventRealByType(Integer type,Integer subType, 
			String detailType,String sourceName,int start, 
			int limit, Long beginTime,Long endTime, List<Long> organIds, 
			String confirmUserName, Short confirmFlag,Integer alarmLevel);

	/**
	 * 根据类型获取实时事件数量
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
	 *         Create at 2015年 上午10:03:03
	 */
	public Integer countEventReal(Integer type, Long beginTime, Long endTime,
			String sourceName);

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
	public Integer countEventReal(Integer type, Integer subType,
			String detailType, String sourceName, int start, int limit,
			Long beginTime, Long endTime, List<Long> organIds,
			String confirmUserName, Short confirmFlag, Integer alarmLevel);

	/**
	 * 根据类型统计各种未处理事件的数量
	 * 
	 * @param type
	 *            事件主类型
	 * @param subType
	 *            事件子类型
	 * @return 未处理事件数量
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午3:09:00
	 */
	public int countEventRealByType(Integer type, Integer subType);

	public int countEventRealByType(Integer type, Integer subType,
			Integer eventLevel, String detailType, List<Long> organIds);
}
