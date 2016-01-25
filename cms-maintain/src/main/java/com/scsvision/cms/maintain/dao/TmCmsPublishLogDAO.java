/**
 * 
 */
package com.scsvision.cms.maintain.dao;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.database.entity.TmCmsPublishLog;

/**
 * @author wangbinyu
 *
 */
@Local
public interface TmCmsPublishLogDAO extends
		BaseMaintainDAO<TmCmsPublishLog, Long> {

	/**
	 * 根据businessId更新情报板发布日志
	 * 
	 * @param businessId
	 *            发起者生成业务id
	 * @param flag
	 *            成功失败标志 0-正在发布 1-发布成功 2-发布失败
	 */
	public void updateCmsPublishLog(String businessId, Short flag);

	/**
	 * 根据情报板id查询情报板发布日志
	 * 
	 * @param deviceId
	 *            情报板id
	 * @return TmCmsPublishLog
	 */
	public TmCmsPublishLog getCmsLog(Long deviceId);

	/**
	 * 条件查询情报板日志列表
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param cmsName
	 *            名称
	 * @param start
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return List<TmCmsPublishLog>
	 */
	public List<TmCmsPublishLog> listCmsLog(Long beginTime, Long endTime,
			String cmsName, Integer start, Integer limit);

	/**
	 * 根据businessIds查询情报板发布列表
	 * 
	 * @param businessIds
	 *            业务id
	 * @return List<TmCmsPublishLog>
	 */
	public List<TmCmsPublishLog> listCmsInfo(List<String> businessIds);

	/**
	 * 统计情报板发布日志数量
	 * 
	 * @param cmsName
	 *            情报板名称
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return Integer
	 */
	public Integer countCmsLog(String cmsName, Long beginTime, Long endTime);

}
