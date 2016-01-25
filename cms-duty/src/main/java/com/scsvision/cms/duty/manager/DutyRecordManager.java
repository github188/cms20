package com.scsvision.cms.duty.manager;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.database.entity.DutyRecord;

/**
 * DutyRecordManager
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 上午10:20:38
 */
@Local
public interface DutyRecordManager {
	/**
	 * 列表查询值班记录
	 * 
	 * @param dutyUserName
	 *            值班监控员-模糊查询
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param start
	 *            查询起始行号
	 * @param limit
	 *            要查询的行数
	 * @return 满足条件的值班记录列表
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 上午11:41:22
	 */
	public List<DutyRecord> listDutyRecord(String dutyUserName, Long beginTime,
			Long endTime, int start, int limit);

	/**
	 * 统计查询值班记录行数
	 * 
	 * @param dutyUserName
	 *            值班监控员-模糊查询
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 满足条件的值班记录行数
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 上午11:41:58
	 */
	public int countDutyRecord(String dutyUserName, Long beginTime, Long endTime);
}
