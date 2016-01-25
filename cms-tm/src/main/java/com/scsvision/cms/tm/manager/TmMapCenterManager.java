/**
 * 
 */
package com.scsvision.cms.tm.manager;

import javax.ejb.Local;

import com.scsvision.cms.tm.service.vo.TmMapCenterVO;

/**
 * @author wangbinyu
 *
 */
@Local
public interface TmMapCenterManager {

	/**
	 * 
	 * 设置地图中心点
	 * 
	 * @param longitude
	 *            纬度
	 * @param userId
	 *            用户id
	 * @param latitude
	 *            经度
	 * @param mapLevel
	 *            地图级别
	 */
	public void saveMapCenter(String longitude, Long userId, String latitude,
			Integer mapLevel);

	/**
	 * 根据用户查询默认中心点位
	 * 
	 * @param userId
	 *            用户id
	 * @return 中心点位信息
	 */
	public TmMapCenterVO getMapCenter(Long userId);

}
