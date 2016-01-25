package com.scsvision.cms.em.dao;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.database.entity.EventDevice;
import com.scsvision.database.entity.EventDeviceReal;

@Local
public interface EventDeviceRealDAO extends EmBaseDAO<EventDeviceReal, Long> {
	/**
	 * 获取未处理报警列表
	 * 
	 * @param start
	 *            开始数
	 * @param limit
	 *            每页显示数量
	 * @return 未处理报警列表
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午10:58:46
	 */
	public List<EventDeviceReal> edeviceRealList(Integer start, Integer limit);
	
	/**
	 * 根据设备类型，设备ids查询设备报警的数量
	 * 
	 * @param deviceType
	 * @param eventIds
	 * @return
	 */
	public Integer countByDeviceType(Integer deviceType,List<Long> eventIds);
	/**
	 * 根据设备类型，设备ids查询设备报警的集合
	 * 
	 * @param deviceType
	 * @param eventIds
	 * @return
	 */
	public List<EventDeviceReal> listByDeviceType(Integer deviceType, List<Long> eventIds,int start,int limit);
}
