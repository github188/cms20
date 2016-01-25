package com.scsvision.cms.statistic.manager;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import com.scsvision.database.entity.DayVd;
import com.scsvision.database.entity.HourVd;
import com.scsvision.database.entity.MonthVd;

/**
 * VdStatisticManager
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午2:33:34
 */
@Local
public interface VdStatisticManager {
	/**
	 * 批量插入小时统计数据
	 * 
	 * @param list
	 *            要插入的数据集合
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午2:37:17
	 */
	public void batchInsertHourVd(List<HourVd> list);

	/**
	 * 列表查询指定路段下的小时统计数据
	 * 
	 * @param organIds
	 *            路段真实机构ID列表
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午2:38:54
	 */
	public List<HourVd> listByHour(List<Long> organIds, Date beginTime,
			Date endTime, int start, int limit);

	/**
	 * 获取最近的一条归并记录
	 * 
	 * @return 最近的一条归并记录
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午4:43:02
	 */
	public HourVd getLastestHourVd();

	/**
	 * 获取最早的归并记录
	 * 
	 * @return 最早的小时归并记录
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午3:56:03
	 */
	public HourVd getFirstHourVd();

	/**
	 * 批量插入天统计数据
	 * 
	 * @param list
	 *            要插入的数据集合
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午3:33:35
	 */
	public void batchInsertDayVd(List<DayVd> list);

	/**
	 * 列表查询指定路段下的日统计数据
	 * 
	 * @param organIds
	 *            路段真实机构ID列表
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午3:34:42
	 */
	public List<DayVd> listByDay(List<Long> organIds, Date beginTime,
			Date endTime, int start, int limit);

	/**
	 * 获取最近的一条归并记录
	 * 
	 * @return 最近的一条归并记录
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午3:35:33
	 */
	public DayVd getLastestDayVd();

	/**
	 * 获取最近一条按月归并记录
	 * 
	 * @return 最近一条按月归并记录
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午10:09:26
	 */
	public MonthVd getLastestMonthVd();

	/**
	 * 获取最早的天归并记录
	 * 
	 * @return 最早的天归并记录
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午10:23:53
	 */
	public DayVd getFirstDayVd();

	/**
	 * 批量插入月统计数
	 * 
	 * @param list
	 *            月统计数
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午2:09:29
	 */
	public void batchInsertMonthVd(List<MonthVd> list);

	/**
	 * 列表查询指定路段下的月统计数据
	 * 
	 * @param organIds
	 *            路段真实机构ID列表
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return 统计列表
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午2:25:21
	 */
	public List<MonthVd> listByMonth(List<Long> organIds, Date beginTime,
			Date endTime, int start, int limit);
}
