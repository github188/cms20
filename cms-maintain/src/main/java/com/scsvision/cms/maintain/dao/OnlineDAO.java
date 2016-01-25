package com.scsvision.cms.maintain.dao;

import javax.ejb.Local;

import com.scsvision.database.entity.Online;

/**
 * OnlineDAO
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 上午10:50:35
 */
@Local
public interface OnlineDAO extends OnlineBaseDAO<Online> {

	/**
	 * 根据ticketId查询历史会话
	 * 
	 * @param ticketId
	 *            用户ticketId
	 * @return Online
	 */
	public Online getOnline(String ticketId);

	/**
	 * 根据deviceId查询Online对象
	 * 
	 * @param deviceId
	 *            设备真实id
	 * @return Online
	 */
	public Online getOnlineByDeviceId(Long deviceId);

}
