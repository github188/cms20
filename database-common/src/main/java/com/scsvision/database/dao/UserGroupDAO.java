package com.scsvision.database.dao;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.database.entity.UserGroup;

/**
 * UserGroupDAO
 * 
 * @author sjt
 *         <p />
 *         Create at 2015年 上下午10:56:24
 */
@Local
public interface UserGroupDAO extends BaseDAO<UserGroup, Long> {
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
	 *         Create at 2015年 下午4:08:32
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
	 *         Create at 2015年 下午4:19:07
	 */
	public Integer countUserGroup(String name);
}
