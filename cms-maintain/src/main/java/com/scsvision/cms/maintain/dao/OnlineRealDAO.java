package com.scsvision.cms.maintain.dao;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.database.entity.OnlineReal;

/**
 * OnlineRealDAO
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 上午10:48:20
 */
@Local
public interface OnlineRealDAO extends OnlineBaseDAO<OnlineReal> {
	/**
	 * 查询在线设备列表
	 * 
	 * @return 在线设备列表
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午3:58:52
	 */
	public List<OnlineReal> listOnlineDevice();

	/**
	 * 获取指定条件的资源在线数据
	 * 
	 * @param types
	 *            资源类型列表
	 * @param sns
	 *            资源SN列表
	 * @param start
	 *            开始行号
	 * @param limit
	 *            要查询的行数
	 * @return 资源在线数据列表
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午5:35:06
	 */
	public List<OnlineReal> listOnlineReal(List<Integer> types,
			List<String> sns, Integer start, Integer limit);

	/**
	 * 根据Ticket获取资源在线对象
	 * 
	 * @param ticket
	 *            Ticket
	 * @return 资源在线对象
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午2:52:53
	 */
	public OnlineReal getOnlineReal(String ticket);

	/**
	 * 查询在线数量
	 * 
	 * @param type
	 *            要查询的资源
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午2:02:28
	 */
	public Integer countOnlineRealList(List<Integer> types);

	/**
	 * 根据id获取资源在线对象
	 * 
	 * @param id
	 *            对象id
	 * @return 资源在线对象
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午3:35:06
	 */
	public OnlineReal getOnlineRealById(String id);

	/**
	 * 查询摄像头在线列表
	 * 
	 * @return List<OnlineReal>
	 */
	public List<OnlineReal> mapOnlineCamera();

	/**
	 * 根据设备id查询在线对象
	 * 
	 * @param deviceId
	 *            设备真实id
	 * @return OnlineReal
	 */
	public OnlineReal getOnlineRealByDeviceId(Long deviceId);

}
