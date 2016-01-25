package com.scsvision.cms.em.manager;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.scsvision.database.entity.EventDevice;
import com.scsvision.database.entity.EventDeviceReal;
import com.scsvision.database.entity.EventReal;

@Local
public interface EventDeviceManager {
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
	 * 获取未处理报警Map表
	 * 
	 * @param ids
	 *            报警事件id列表
	 * @return 未处理报警Map表
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午11:11:07
	 */
	public Map<Long, EventDeviceReal> edviceRealMap(List<Long> ids);

	/**
	 * 根据id获取信息
	 * 
	 * @param id
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午11:31:22
	 */
	public EventDevice getEventDevice(Long id);

	/**
	 * 获取历史报警映射表
	 * 
	 * @param ids
	 *            历史报警ID列表
	 * @return key为ID，value为历史报警对象的映射表
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 上午10:17:42
	 */
	public Map<Long, EventDevice> mapEventDevice(List<Long> ids);

	/**
	 * 创建报警
	 * 
	 * @param alarm
	 *            报警主对象
	 * @param subAlarm
	 *            报警子对象
	 * @return 报警ID
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午4:49:37
	 */
	public Long createAlarm(EventReal alarm, EventDeviceReal subAlarm);
}
