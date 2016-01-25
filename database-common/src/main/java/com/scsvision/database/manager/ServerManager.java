package com.scsvision.database.manager;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.database.entity.Server;

@Local
public interface ServerManager {
	/**
	 * 上报服务器配置信息
	 * 
	 * @param entity
	 *            服务器信息
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午10:09:19
	 */
	public void serverConfig(Server entity);

	/**
	 * 根据标准号获取服务器信息
	 * 
	 * @param sn
	 *            服务器编号
	 * @return 服务器信息
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午10:38:59
	 */
	public Server getServerBySN(String sn);

	/**
	 * 根据type获取服务器信息
	 * 
	 * @param type
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午2:32:36
	 */
	public List<Server> getServerByType(Integer type);

	/**
	 * 创建服务器
	 * 
	 * @param server
	 *            服务器信息
	 * @return 新建服务器id
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午10:20:30
	 */
	public Long createServer(Server server);

	/**
	 * 根据id获取服务器对象
	 * 
	 * @param id
	 *            服务器id
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午11:11:34
	 */
	public Server getServerById(Long id);

	/**
	 * 修改服务器信息
	 * 
	 * @param server
	 *            要修改的服务器信息
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午11:17:22
	 */
	public void updateServer(Server server);

	/**
	 * 删除服务器对象
	 * 
	 * @param id
	 *            要删除对象的id
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午11:33:00
	 */
	public void deleteServer(Long id);

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
	 *         Create at 2015年 下午2:15:16
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
	 *         Create at 2015年 下午2:26:54
	 */
	public Integer countServers(String name, Integer type);

}
