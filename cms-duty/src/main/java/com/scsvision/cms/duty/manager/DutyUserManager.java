package com.scsvision.cms.duty.manager;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.cms.exception.BusinessException;
import com.scsvision.database.entity.DutyUser;

/**
 * DutyTeamManager
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午1:48:59
 */
@Local
public interface DutyUserManager {
	/**
	 * 值班账户登录
	 * 
	 * @param dutyUser
	 *            值班账户对象
	 * @param action
	 *            值班动作 0-下班，1-上班
	 * @throws BusinessException
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午1:50:12
	 */
	public void dutyLogin(DutyUser dutyUser, Short action)
			throws BusinessException;

	/**
	 * 创建值班班次
	 * 
	 * @param dutyTeam
	 *            值班班次
	 * @return 新增班次id
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午10:02:57
	 */
	public Long createDutyUser(DutyUser dutyUser);

	/**
	 * 根据id获取值班班次
	 * 
	 * @param id
	 *            值班班次id
	 * @return 值班班次
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午10:56:29
	 */
	public DutyUser getDutyUser(Long id);

	/**
	 * 修改值班班次
	 * 
	 * @param entity
	 *            要修改的信息
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午11:01:54
	 */
	public void updateDutyUser(DutyUser entity);

	/**
	 * 删除值班班次
	 * 
	 * @param id
	 *            班次id
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午2:11:42
	 */
	public void deleteDutyUser(Long id);

	/**
	 * 查询客户端登录用户所属机构下的所有值班账户
	 * 
	 * @param userId
	 *            客户端登录用户ID
	 * @return 值班账户
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午2:30:00
	 */
	public List<DutyUser> listDutyUser(Long userId);

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
	 * 根据登录名查询
	 * 
	 * @param loginName
	 *            登录名
	 * @return
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午3:17:48
	 */
	public DutyUser getByLoginName(String loginName);

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
	 *         Create at 2016年 上午10:10:31
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
	 *         Create at 2016年 上午10:30:31
	 */
	public Integer dutyUserCount(Long organId);
}
