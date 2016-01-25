/**
 * 
 */
package com.scsvision.cms.maintain.dao;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.database.entity.UserLog;

/**
 * @author wangbinyu
 *
 */
@Local
public interface UserLogDAO extends OnlineBaseDAO<UserLog> {

	/**
	 * 从日志中查询情报板发布日志
	 * 
	 * @param ids
	 *            情报板id
	 * @return UserLog
	 */
	public List<UserLog> listCmsInfo(List<Long> ids);

	/**
	 * 更新旧操作日志flag
	 * 
	 * @param targetId
	 *            目标id
	 * @param method
	 *            操作方法
	 * @return
	 */
	public void updateOldRerecord(Long targetId, String method);

	/**
	 * 
	 * @param businessId
	 *            业务发起者生成id
	 * @param flag
	 *            发布情报办状态
	 */
	public void updateUserLog(String businessId, Short flag);

}
