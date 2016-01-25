package com.scsvision.cms.gather.manager;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.dom4j.Element;

import com.scsvision.database.entity.GatherCovi;
import com.scsvision.database.entity.GatherWst;
import com.scsvision.database.entity.TmDevice;

/**
 * GatherWstManager
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午1:50:12
 */
@Local
public interface GatherWstManager {
	public List<GatherWst> list(Long deviceId, int start, int limit);

	/**
	 * 
	 * @param deviceId
	 *            设备id
	 * @param userId
	 *            用户id
	 * @param type
	 *            类型
	 * @return 实体对象GatherWst
	 */
	public GatherWst getGatherEntity(Long deviceId, Long userId, Integer type);

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
	 * 根据真实设备id查询指定时间内数据列表
	 * 
	 * @param ids
	 *            气象检测器的id集合
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return List
	 * @author sjt
	 *         <p />
	 *         Create at 2016年 下午2:10:05
	 */
	public List<GatherWst> listWst(List<Long> ids, Date beginTime, Date endTime);
}
