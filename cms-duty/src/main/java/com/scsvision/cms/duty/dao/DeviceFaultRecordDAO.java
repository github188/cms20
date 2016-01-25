package com.scsvision.cms.duty.dao;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.cms.duty.dto.CountDeviceFaultDTO.CountDevice;
import com.scsvision.database.entity.DeviceFaultRecord;

@Local
public interface DeviceFaultRecordDAO extends
		BaseDutyDAO<DeviceFaultRecord, Long> {
	/**
	 * 获取满足条件的设备故障列表
	 * 
	 * @param start
	 *            开始行号
	 * @param limit
	 *            要查询的行数
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 设备故障列表
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午9:56:53
	 */
	public List<DeviceFaultRecord> listDeviceFaultRecord(int start, int limit,
			Long beginTime, Long endTime);

	/**
	 * 获取满足条件的设备故障数量
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 设备故障数量
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午9:57:53
	 */
	public Integer countDeviceFaultRecord(Long beginTime, Long endTime);

	/**
	 * 获取满足条件的设备故障手报列表
	 * 
	 * @param deviceType
	 *            设备类型
	 * @param deviceName
	 *            设备名称
	 * @param start
	 *            开始行号
	 * @param limit
	 *            查询行数
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param organIds
	 *            所属机构ids
	 * @param confirmUserName
	 *            确认人
	 * @param confirmFlag
	 * @return
	 */
	public List<DeviceFaultRecord> listDeviceFaultRecord(Integer deviceType,
			String deviceName, int start, int limit, Long beginTime,
			Long endTime, List<Long> organIds, String confirmUserName);

	/**
	 * 获取满足条件的设备故障手报统计数
	 * 
	 * @param deviceType
	 *            设备类型
	 * @param deviceName
	 *            设备名称
	 * @param start
	 *            开始行号
	 * @param limit
	 *            查询行数
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param organIds
	 *            所属机构ids
	 * @param confirmUserName
	 *            确认人
	 * @param confirmFlag
	 * @return
	 */
	public Integer countDeviceFaultRecord(Integer deviceType,
			String deviceName, int start, int limit, Long beginTime,
			Long endTime, List<Long> organIds, String confirmUserName);

	/**
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param organIds
	 *            所属机构id集合
	 * @return 按照deviceType分组的list集合
	 */
	public List<DeviceFaultRecord> countByTypeDeviceFaultRecord(Long beginTime,
			Long endTime, List<Long> organIds);
}
