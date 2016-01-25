package com.scsvision.cms.maintain.manager;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.scsvision.database.entity.Online;
import com.scsvision.database.entity.OnlineReal;
import com.scsvision.database.entity.User;

@Local
public interface OnlineManager {
	/**
	 * 
	 * csLogin CS客户端用户登录
	 * 
	 * @param user
	 *            登录用户的信息
	 * @param clientType
	 *            客户端类型
	 * @param ip
	 *            客户ip
	 * @return String
	 * @author sjt
	 *         <p />
	 *         Create at 2015 上午
	 */
	public OnlineReal csLogin(User user, String clientType, String ip);

	/**
	 * 查询在线的设备
	 * 
	 * @return 资源SN为key, 在线对象为value的Map
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午4:10:44
	 */
	public Map<String, OnlineReal> mapOnlineDevice();

	/**
	 * 获取指定类型的资源在线数据
	 * 
	 * @param types
	 *            资源类型列表
	 * @return 资源在线数据列表
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午5:35:06
	 */
	public List<OnlineReal> listOnlineRealInTypes(List<Integer> types);

	/**
	 * 资源离线操作
	 * 
	 * @param onlineReal
	 *            资源在线对象
	 * @param kickFlag
	 *            0-正常离线，1-管理员踢出
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 上午10:46:27
	 */
	public void offline(OnlineReal onlineReal, Integer kickFlag);

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
	 * 根据类型查询在线列资源列表
	 * 
	 * @param type
	 *            在线资源类型
	 * @param start
	 *            开始行号
	 * @param limit
	 *            要查询的行数
	 * @return 列表
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午1:59:34
	 */
	public List<OnlineReal> listOnlineRealByType(List<Integer> types,
			int start, int limit);

	/**
	 * 查询在线数量
	 * 
	 * @param type
	 *            要查询的资源
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午2:12:28
	 */
	public Integer countOnlineRealList(List<Integer> type);

	/**
	 * 根据id获取资源在线对象
	 * 
	 * @param id
	 *            对象id
	 * @return 资源在线对象
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午3:31:06
	 */
	public OnlineReal getOnlineRealById(String id);

	/**
	 * 设备在线
	 * 
	 * @param sns
	 *            设备SN列表
	 * @param map
	 *            设备SN与ID的映射表
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 上午10:17:46
	 */
	public void reportOnline(List<String> sns, Map<String, Long> map);

	/**
	 * 设备离线
	 * 
	 * @param sns
	 *            设备SN列表
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 上午10:18:33
	 */
	public void reportOffline(List<String> sns);

	/**
	 * 查询在线资源列表
	 * 
	 * @param start
	 *            分页起始行
	 * @param limit
	 *            要查询的行数
	 * @return
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午2:09:37
	 */
	public List<OnlineReal> list(int start, int limit);

	/**
	 * 所有的设备ID和SN的Map
	 * 
	 * @return key为设备ID-type，value为设备SN的映射表
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 上午10:37:47
	 */
	public Map<String, String> mapAllDevice();

	/**
	 * 查询所有摄像头在线列表
	 * 
	 * @return Map<String, OnlineReal>
	 */
	public Map<Long, OnlineReal> mapOnlineCamera();

	/**
	 * 查询设备状态以resourceId为key
	 * 
	 * @return Map<Long, OnlineReal>
	 */
	public Map<Long, OnlineReal> mapOnlineAllDevice();

	/**
	 * 根据设备真实id查询在线对象
	 * 
	 * @param deviceId
	 *            真实id
	 * @return OnlineReal
	 */
	public OnlineReal getOnlineRealByDeviceId(Long deviceId);

	/**
	 * 根据设备id查询Online对象
	 * 
	 * @param deviceId
	 *            设备真实id
	 * @return Online
	 */
	public Online getOnlineByDeviceId(Long deviceId);
}
