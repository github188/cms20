package com.scsvision.cms.duty.dao;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.database.entity.DutyRecord;

/**
 * DutyRecordDAO
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 上午9:25:25
 */
@Local
public interface DutyRecordDAO extends BaseDutyDAO<DutyRecord, Long> {
	/**
	 * 列表查询值班记录
	 * 
	 * @param dutyUserName
	 *            监控员-模糊查询
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
	 *         Create at 2015 上午10:24:16
	 */
	public List<DutyRecord> listDutyRecord(String dutyUserName, Long beginTime,
			Long endTime, int start, int limit);

	/**
	 * 统计查询值班记录行数
	 * 
	 * @param dutyUserName
	 *            监控员-模糊查询
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 满足条件的值班记录行数
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 上午11:38:13
	 */
	public int countDutyRecord(String dutyUserName, Long beginTime, Long endTime);

	/**
	 * 获取最近的值班上班记录
	 * 
	 * @return 最近的值班上班记录
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午2:09:17
	 */
	public DutyRecord getLatest();
}
