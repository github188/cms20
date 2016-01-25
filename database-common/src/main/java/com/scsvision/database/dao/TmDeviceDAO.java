package com.scsvision.database.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.scsvision.database.entity.Organ;
import com.scsvision.database.entity.TmDevice;

/**
 * TmDeviceDAO
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午2:44:17
 */
@Local
public interface TmDeviceDAO extends BaseDAO<TmDevice, Long> {

	/**
	 * 
	 * 根据机构集合查询和设备类型查询数据设备列表
	 * 
	 * @param organs
	 *            机构集合
	 * @param type
	 *            检测器类型
	 * @return 设备列表
	 */
	public Map<Long, TmDevice> mapDeviceByOrgan(List<Organ> organs,
			Integer types[]);

	/**
	 * 删除机构下的数据设备
	 * 
	 * @param organId
	 *            机构id
	 */
	public void deleteTmDevice(Long organId);

	/**
	 * 根据条件查询数据设备列表
	 * 
	 * @param organId
	 *            设备所在机构
	 * @param name
	 *            设备名称
	 * @param standardNumber
	 *            设备标准号
	 * @param stakeNumber
	 *            设备桩号
	 * @param type
	 *            设备主类型
	 * @param subType
	 *            设备子类型
	 * @param start
	 *            开始查询条数
	 * @param limit
	 *            每次查询条数
	 * @return 数据设备列表
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午10:00:10
	 */
	public List<TmDevice> listTmDevice(Long organId, String name,
			String standardNumber, String stakeNumber, String type,
			String subType, int start, int limit);

	/**
	 * 获取某机构下的设备数量
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            设备名称
	 * @param standardNumber
	 *            设备标准号
	 * @param stakeNumber
	 *            设备桩号
	 * @param type
	 *            设备类型
	 * @return 设备数量
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午10:27:47
	 */
	public Integer countTmDevice(Long organId, String name,
			String standardNumber, String stakeNumber, String type,
			String subType);

}
