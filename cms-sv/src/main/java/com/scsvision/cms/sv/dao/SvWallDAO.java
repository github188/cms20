/**
 * 
 */
package com.scsvision.cms.sv.dao;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.database.entity.SvWall;

/**
 * @author wangbinyu
 *
 */
@Local
public interface SvWallDAO extends SvBaseDAO<SvWall, Long> {

	/**
	 * 查询电视墙列表
	 * 
	 * @param start
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return List<SvWall>
	 */
	public List<SvWall> listWall(Integer start, Integer limit);

	/**
	 * 统计电视墙数量
	 * 
	 * @return Integer
	 */
	public Integer countWall();

}
