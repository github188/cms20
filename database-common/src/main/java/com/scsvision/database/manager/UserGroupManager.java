package com.scsvision.database.manager;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.scsvision.database.entity.UserGroup;

/**
 * UserGroupManager
 * 
 * @author sjt
 *         <p />
 *         Create at 2015年 上午11:02:54
 */
@Local
public interface UserGroupManager {

	/**
	 * 创建用户组
	 * 
	 * @param userGroup
	 *            用户组
	 * @return 用户组id
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午11:04:59
	 */
	public Long createUserGroup(UserGroup userGroup);

	/**
	 * 根据id获取用户组
	 * 
	 * @param id
	 *            用户组id
	 * @return 用户组
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午11:13:23
	 */
	public UserGroup getUserGroup(Long id);

	/**
	 * 修改用户组
	 * 
	 * @param userGroup
	 *            要修改的用户组
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午11:16:02
	 */
	public void updateUserGroup(UserGroup userGroup);

	/**
	 * 删除用户组
	 * 
	 * @param id
	 *            要删除的用户组id
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午3:48:57
	 */
	public void deleteUserGroup(Long id);

	/**
	 * 查询用户组列表
	 * 
	 * @param name
	 *            用户组名称
	 * @param start
	 *            开始行号
	 * @param limit
	 *            要查询的行数
	 * @return 列表
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午4:04:32
	 */
	public List<UserGroup> listUserGroup(String name, int start, int limit);

	/**
	 * 获取满足条件的用户组条数
	 * 
	 * @param name
	 *            用户组名称
	 * @return 数量
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午4:06:07
	 */
	public Integer countUserGroup(String name);
	/**
	 * 获取以userGroupId为key，UserGroup为value的map
	 * 
	 * @return
	 */
	public Map<Long, UserGroup> getMapUserGroup();

}
