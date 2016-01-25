package com.scsvision.database.dao;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.database.entity.Server;

/**
 * @author sjt
 *
 */
@Local
public interface ServerDAO extends BaseDAO<Server, Long> {
	/**
	 * 根据标准号获取服务器信息
	 * 
	 * @param sn
	 *            服务器编号
	 * @return 服务器信息
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午10:28:59
	 */
	public Server getServerBySN(String sn);

	/**
	 * 根据type获取服务器信息
	 * 
	 * @param type
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午2:12:36
	 */
	public List<Server> getServerByType(Integer type);

	/**
	 * 根据条件查询服务器列表
	 * 
	 * @param name
	 *            服务器名字
	 * @param type
	 *            服务器类型
	 * @param limit
	 *            每次查询条数
	 * @param start
	 *            开始行号
	 * @return 列表
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午2:18:16
	 */
	public List<Server> listServer(String name, Integer type, int limit,
			int start);

	/**
	 * 获取服务器的数量
	 * 
	 * @param name
	 *            服务器名字
	 * @param type
	 *            服务器类型
	 * @return 条数
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午2:30:54
	 */
	public Integer countServers(String name, Integer type);
}
