package com.scsvision.cms.duty.manager;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.cms.exception.BusinessException;
import com.scsvision.database.entity.VisitRecord;

/**
 * VisitRecordManager
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午3:07:38
 */
@Local
public interface VisitRecordManager {

	/**
	 * 创建来电来访记录
	 * 
	 * @param visitors
	 *            来访人员
	 * @param phone
	 *            来电号码
	 * @param reason
	 *            原因
	 * @param arriveTime
	 *            到达时间
	 * @param leaveTime
	 *            离开时间
	 * @param result
	 *            处理结果
	 * @param note
	 *            备注
	 * @return 创建成功的记录ID
	 * @throws BusinessException
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午3:15:10
	 */
	public Long createVisitRecord(String visitors, String phone, String reason,
			Long arriveTime, Long leaveTime, String result, String note)
			throws BusinessException;

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
	 *         Create at 2015 上午9:59:29
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
