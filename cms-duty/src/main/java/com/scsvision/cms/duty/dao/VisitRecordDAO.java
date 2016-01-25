package com.scsvision.cms.duty.dao;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.database.entity.VisitRecord;

/**
 * VisitRecordDAO
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午3:03:21
 */
@Local
public interface VisitRecordDAO extends BaseDutyDAO<VisitRecord, Long> {
	/**
	 * 列表查询来电来访记录
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param monitor
	 *            值班监控人员名称，模糊查询
	 * @param start
	 *            分页起始行
	 * @param limit
	 *            分页要查询的行数
	 * @return 满足条件的来电来访记录列表
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 上午10:01:40
	 */
	public List<VisitRecord> listVisitRecord(Long beginTime, Long endTime,
			String monitor, int start, int limit);

	/**
	 * 统计满足条件的来电来访记录数量
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param monitor
	 *            值班监控人员名称，模糊查询
	 * @return 满足条件的来电来访记录数量
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 上午11:19:28
	 */
	public int countVisitRecord(Long beginTime, Long endTime, String monitor);
}
