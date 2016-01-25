package com.scsvision.cms.gather.dao;

import java.util.List;

import javax.ejb.Local;

import org.bson.Document;

import com.scsvision.database.entity.DeviceReal;

/**
 * DeviceRealDAO
 * 
 * @author MIKE
 *         <p />
 *         Create at 2016年1月7日 下午4:49:17
 */
@Local
public interface DeviceRealDAO extends GatherBaseDAO<DeviceReal> {
	/**
	 * 保存多条实时采集数据
	 * 
	 * @param list
	 *            实时采集数据
	 * @author MIKE
	 *         <p />
	 *         Create at 2016年1月7日 下午5:15:54
	 */
	public void saveMany(List list);

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
