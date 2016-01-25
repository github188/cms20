/**
 * 
 */
package com.scsvision.cms.sv.dao;

import javax.ejb.Local;

import com.scsvision.database.entity.SvWallSchemeItem;

/**
 * @author wangbinyu
 *
 */
@Local
public interface SvWallSchemeItemDAO extends SvBaseDAO<SvWallSchemeItem, Long> {

	/**
	 * 删除电视墙播放方案节目
	 * 
	 * @param id
	 *            播放方案id
	 */
	public void deleteItem(Long id);

}
