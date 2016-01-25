/**
 * 
 */
package com.scsvision.database.dao;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.database.entity.SvPreset;

/**
 * @author wangbinyu
 *
 */
@Local
public interface SvPresetDAO extends BaseDAO<SvPreset, Long> {

	/**
	 * 根据设备id查询预置点索引最大数
	 * 
	 * @param deviceId
	 *            设备id
	 * @return Integer
	 */
	public Integer getLastPreset(Long deviceId);

	/**
	 * 获取预置点中所有具有关联的图片ID集合
	 * 
	 * @return 预置点中所有具有关联的图片ID集合
	 * @author MIKE
	 *         <p />
	 *         Create at 2016年1月22日 下午2:40:01
	 */
	public List<Long> listReferencedResourceIds();
}
