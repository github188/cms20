/**
 * 
 */
package com.scsvision.cms.gather.manager;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.dom4j.Element;

import com.scsvision.database.entity.GatherCovi;
import com.scsvision.database.entity.TmDevice;

/**
 * @author wangbinyu
 *
 */
@Local
public interface GatherCoviManager {

	/**
	 * 
	 * @param deviceId
	 *            设备id
	 * @param userId
	 *            用户id
	 * @return 实体对象GatherCovi
	 */
	public GatherCovi getGatherEntity(Long deviceId, Long userId, Integer type);

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

	/**
	 * 根据真实coviId查询covi时间内数据列表
	 * 
	 * @param ids
	 *            covi的id集合
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return List<GatherCovi>
	 */
	public List<GatherCovi> listCovi(List<Long> ids, Date beginTime,
			Date endTime);

}
