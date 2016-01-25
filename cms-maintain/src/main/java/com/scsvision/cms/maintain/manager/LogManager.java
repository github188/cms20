/**
 * 
 */
package com.scsvision.cms.maintain.manager;

import java.util.List;

import javax.ejb.Local;

import org.dom4j.DocumentException;

import com.scsvision.database.entity.TmCmsPublishLog;

/**
 * @author wangbinyu
 *
 */
@Local
public interface LogManager {

	/**
	 * 批量保存日志
	 * 
	 * @param ticketId
	 *            票据id
	 * @param method
	 *            方法名称
	 * @param code
	 *            成功失败编码
	 * @param message
	 *            成功失败消息
	 * @param targetId
	 *            目标id
	 * @param targetName
	 *            目标名称
	 * @param targetType
	 *            目标类型
	 * @param businessId
	 *            businessId
	 * @param resourceId
	 *            操作目标id
	 * @param conditions
	 *            查询条件 create、update、delete等
	 * @param reason
	 *            操作原因
	 * @param content
	 *            操作内容
	 * @param flag
	 *            情报板日志状态 0-正在发布 1-成功 2-失败
	 * @return 日志id
	 * @throws DocumentException 
	 */
	public String saveUserLog(String ticketId, String method, String code,
			String message, String targetId, String targetName,
			String targetType, String businessId, String resourceId,
			String conditions, String reason, String content, String flag) throws DocumentException;

	/**
	 * 
	 * @param businessId
	 *            发起者生成编号
	 * @param flag
	 *            情报板是否发布成功标志
	 */
	public void updateUserLog(String businessId, Short flag);

	/**
	 * 根据情报板id查询日志
	 * 
	 * @param deviceId
	 *            情报板id
	 * @return TmCmsPublishLog
	 */
	public List<TmCmsPublishLog> getCmsLog(Long deviceId);

	/**
	 * 条件查询情报板发布日志列表
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param cmsName
	 *            名称
	 * @param start
	 *            开始条数
	 * @param limit
	 *            需要查询条数
	 * @return List<TmCmsPublishLog>
	 */
	public List<TmCmsPublishLog> listCmsLog(Long beginTime, Long endTime,
			String cmsName, Integer start, Integer limit);

	/**
	 * 根据businessIds查询情报板日志列表
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
