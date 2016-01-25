/**
 * 
 */
package com.scsvision.cms.gather.manager;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.dom4j.Element;

import com.scsvision.database.entity.GatherVd;
import com.scsvision.database.entity.TmDevice;

/**
 * @author wangbinyu
 *
 */
@Local
public interface GatherVdManager {

	/**
	 * 
	 * @param beginTime
	 *            开始查询时间
	 * @param endTime
	 *            结束时间
	 * @param organId
	 *            机构id
	 * @return 道路车检器数据
	 */

	// public List<GatherVdOrganVO> listByOrganRoad(Date beginTime, Date
	// endTime,
	// Long organId);

	/**
	 * 查询时间段采集车检器数据
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param vdIds
	 *            车检器id集合
	 * @return 车检器采集数据
	 */
	public List<GatherVd> list(List<Long> vdIds, Date beginTime, Date endTime);
	
	/**
	 * 查询时间段采集车检器数据
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param vdIds
	 *            车检器id
	 * @return 车检器采集数据
	 */
	public List<GatherVd> listById(Long vdId, Date beginTime, Date endTime);

	/**
	 * 
	 * @param deviceId
	 *            设备id
	 * @param userId
	 *            用户id
	 * @param type
	 *            类型
	 * @return 实体对象GatherVd
	 */
	public GatherVd getGatherEntity(Long deviceId, Long userId, Integer type);

	/**
	 * 查询最早的采集数据
	 * 
	 * @return
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午5:26:00
	 */
	public GatherVd getFirst();

	/**
	 * 采集上报存储
	 * 
	 * @param datas
	 *            要保存的数据
	 * @param map
	 *            所有数据设备以SN为key,设备对象为value的键值集合
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午4:29:42
	 */
	public void storeData(List<Element> datas, Map<String, TmDevice> map);

}
