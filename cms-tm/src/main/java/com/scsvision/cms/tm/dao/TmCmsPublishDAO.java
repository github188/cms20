package com.scsvision.cms.tm.dao;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.database.entity.TmCmsPublish;

/**
 * TmCmsPublishDAO
 * 
 * @author sjt
 *         <p />
 *         Create at 2015 下午2:54:17
 */
@Local
public interface TmCmsPublishDAO extends BaseTmDAO<TmCmsPublish, Long> {
	/**
	 * 获取情报板发布信息列表
	 * 
	 * @param start
	 *            开始行号
	 * @param limit
	 *            要查询的行数
	 * @param playlistId
	 *            节目单id
	 * @return 情报板列表
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午9:46:18
	 */
	public List<TmCmsPublish> listCmsPublish(Integer start, Integer limit,
			Long playlistId);

	/**
	 * 获取信息数量
	 * 
	 * @param cmsId
	 *            要查询的情报板id
	 * @return 数量
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午9:56:52
	 */
	public Integer countCmsPublish(Long cmsId);

	/**
	 * 根据节目单id删除常用条目
	 * 
	 * @param id
	 *            节目单id
	 */
	public void deleteByPlaylistId(Long playlistId);

	/**
	 * 删除节目单
	 * 
	 * @param ids
	 *            节目单ids
	 */
	public void deleteCmsPublish(List<Long> ids);
}
