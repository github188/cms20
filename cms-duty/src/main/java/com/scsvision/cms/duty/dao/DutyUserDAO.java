package com.scsvision.cms.duty.dao;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.cms.exception.BusinessException;
import com.scsvision.database.entity.DutyUser;

/**
 * DutyTeamDAO
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 上午9:25:54
 */
@Local
public interface DutyUserDAO extends BaseDutyDAO<DutyUser, Long> {
	/**
	 * 根据登录名查询值班账户
	 * 
	 * @param loginName
	 *            登录名称
	 * @return 值班账户
	 * @throws BusinessException
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午1:53:13
	 */
	public DutyUser getByLoginName(String loginName) throws BusinessException;

	/**
	 * 查询客户端登录用户所属机构下的所有值班账户
	 * 
	 * @param organId
	 *            客户端登录用户所属机构id
	 * @return 值班账户
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午2:30:00
	 */
	public List<DutyUser> listDutyUser(Long organId);

	/**
	 * 获取值班班次数量
	 * 
	 * @param name
	 *            班次名称
	 * @param master
	 *            班长名称
	 * @return 班次列表条数
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午2:40:00
	 */
	public Integer countDutyUser(String name, String master);

	/**
	 * 根据条件查询值班账户列表
	 * 
	 * @param organId
	 *            账户所在机构id
	 * @param start
	 *            开始查询位置
	 * @param limit
	 *            要查询的数量
	 * @return 列表
	 * @author sjt
	 *         <p />
	 *         Create at 2016年 上午10:14:31
	 */
	public List<DutyUser> dutyUserList(Long organId, int start, int limit);

	/**
	 * 根据条件查询值班账户列表数量
	 * 
	 * @param organId
	 *            账户所在机构id
	 * @return 数量
	 * @author sjt
	 *         <p />
	 *         Create at 2016年 上午10:33:31
	 */
	public Integer dutyUserCount(Long organId);
}
