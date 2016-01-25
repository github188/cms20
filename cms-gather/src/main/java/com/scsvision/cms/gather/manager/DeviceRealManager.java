package com.scsvision.cms.gather.manager;

import java.util.List;

import javax.ejb.Local;

import org.bson.Document;

/**
 * DeviceRealManager
 * 
 * @author MIKE
 *         <p />
 *         Create at 2016年1月7日 下午5:18:03
 */
@Local
public interface DeviceRealManager {
	/**
	 * 查询给定ID集合的设备实时采集数据
	 * 
	 * @param deviceIds
	 *            设备ID集合
	 * @return 设备实时采集数据
	 * @author MIKE
	 *         <p />
	 *         Create at 2016年1月8日 上午10:34:57
	 */
	public List<Document> listDeviceReal(List<Long> deviceIds);
}
