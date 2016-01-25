/**
 * 
 */
package com.scsvision.cms.sv.dao;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.database.entity.SvMonitor;

/**
 * @author wangbinyu
 *
 */
@Local
public interface SvMonitorDAO extends SvBaseDAO<SvMonitor, Long> {

	/**
	 * 统计电视墙通道数
	 * 
	 * @param wallId
	 *            电视墙id
	 * @return Integer
	 */
	public Integer countMonitor(Long wallId);

	/**
	 * 查询电视墙通道列表
	 * 
	 * @param wallId
	 *            电视墙id
	 * @param start
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 电视墙通道列表
	 */
	public List<SvMonitor> listMonitor(Long wallId, Integer start, Integer limit);

}
