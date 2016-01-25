/**
 * 
 */
package com.scsvision.cms.tm.dao;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.database.entity.TmPlayList;

/**
 * @author wangbinyu
 *
 */
@Local
public interface TmPlayListDAO extends BaseTmDAO<TmPlayList, Long> {

	/**
	 * 查询常用条目
	 * 
	 * @param sizeType
	 *            情报板大小类型
	 * @return List<TmPlayList>
	 */
	public List<TmPlayList> listPlaylist(String sizeType);

}
