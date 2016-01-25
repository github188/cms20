/**
 * 
 */
package com.scsvision.cms.gather.manager;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.dom4j.Element;

import com.scsvision.database.entity.GatherCd;
import com.scsvision.database.entity.TmDevice;

/**
 * @author wangbinyu
 *
 */
@Local
public interface GatherCdManager {

	/**
	 * 
	 * @param deviceId
	 *            设备id
	 * @param userId
	 *            用户id
	 * @return 实体对象GatherCd
	 */
	public GatherCd getGatherEntity(Long deviceId, Long userId, Integer type);

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
