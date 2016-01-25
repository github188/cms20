package com.scsvision.database.manager;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.dom4j.Element;

import com.scsvision.database.entity.DicManufacture;
import com.scsvision.database.entity.Organ;
import com.scsvision.database.entity.SvDevice;
import com.scsvision.database.entity.VirtualOrgan;
import com.scsvision.database.service.vo.OrganDeviceVO;

@Local
public interface SvDeviceManager {
	/**
	 * 根据deviceId和type获取设备
	 * 
	 * @param deviceId
	 *            设备id
	 * @return 设备对象
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午5:58:26
	 */
	public SvDevice getSvDevice(Long deviceId);

	public Map<Long, SvDevice> getSvDevices();

	/**
	 * 修改设备坐标
	 * 
	 * @param svDevice
	 *            设备对象
	 * @return
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午3:39:43
	 */
	public void saveSvMarkers(SvDevice svDevice);

	/**
	 * 获取以ID为key,SvDevice为Value的映射集合
	 * 
	 * @param ids
	 *            ID列表，如果为空将查询所有的数据
	 * @return ID为key,SvDevice为Value的集合
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午10:52:56
	 */
	public Map<Long, SvDevice> mapSvDevice(List<Long> ids);

	/**
	 * 生成机构树，返回6个参数
	 * 
	 * @param rootOrgan
	 *            根机构
	 * @param organMap
	 *            所有真实机构键值映射
	 * @param deviceMap
	 *            设备键值映射
	 * @param list
	 *            机构树节点
	 * @param manufacturerMap
	 *            厂商键值映射
	 * @return 机构树
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午11:08:34
	 */
	public Element generateOrganTree(VirtualOrgan rootOrgan,
			Map<Long, Organ> organMap, Map<Long, SvDevice> deviceMap,
			List<VirtualOrgan> list, Map<Long, DicManufacture> manufacturerMap);

	/**
	 * 生成机构树，返回
	 * 
	 * @param rootOrgan
	 *            根机构
	 * @param organId
	 *            机构id
	 * @param devices
	 *            满足条件的机构列表
	 * @param isRec
	 *            是否递归
	 * @return 机构树
	 * @author sjt
	 *         <p />
	 *         Create at 2015 上午9:37:06
	 */
	public Element createOrganTree(Long organId, VirtualOrgan rootOrgan,
			List<OrganDeviceVO> devices, String isRec);

	/**
	 * 获取摄像头列表
	 * 
	 * @param list
	 *            指定机构下机构列表
	 * @param map
	 *            设备map
	 * @param manufacturerMap
	 *            厂商键值映射
	 * @param node
	 *            选择的节点
	 * @return 摄像头列表
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午3:27:38
	 */
	public Element getChannelList(List<VirtualOrgan> list,
			Map<Long, SvDevice> map, Map<Long, DicManufacture> manufactureMap,
			VirtualOrgan node);

	/**
	 * 根据属性获取服务器列表
	 * 
	 * @param ptsId
	 *            查询属性ptsId
	 * @return 设备列表
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午2:17:29
	 */
	public List<SvDevice> listSvDeviceByPtsId(Long ptsId);

	/**
	 * PTS获取管辖的摄像头信息
	 * 
	 * @param list
	 *            满足条件的设备列表
	 * @param manufactureMap
	 *            厂商键值映射
	 * @return PTS管辖的摄像头信息
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午2:31:28
	 */
	public Element getPTSChannelList(List<SvDevice> list,
			Map<Long, DicManufacture> manufactureMap);

	/**
	 * 删除机构下dvr和camera
	 * 
	 * @param organId
	 *            机构id
	 */
	public void deleteSvDevice(Long organId);

	/**
	 * 创建视频服务器
	 * 
	 * @param organId
	 *            机构id
	 * @param subType
	 *            设备子类型 00-Dvr 01-Dvs
	 * @param standardNumber
	 *            标准号
	 * @param name
	 *            名称
	 * @param location
	 *            位置
	 * @param ptsId
	 *            协转Id
	 * @param manufacturerId
	 *            厂商id
	 * @param channelAmount
	 *            通道数 默认为8
	 * @param ip
	 *            ip地址
	 * @param port
	 *            端口
	 * @param extend
	 *            扩展信息
	 * @param user
	 *            用户名
	 * @param password
	 *            密码
	 * @return Integer
	 */
	public Long createDvr(Long organId, String subType, String standardNumber,
			String name, String location, Long ptsId, Long manufacturerId,
			Integer channelAmount, String ip, String port, String extend,
			String user, String password);

	/**
	 * 
	 * @param id
	 *            dvr id
	 * @param organId
	 *            机构id
	 * @param standardNumber
	 *            标准号
	 * @param name
	 *            名称
	 * @param location
	 *            位置
	 * @param ptsId
	 *            协转Id
	 * @param manufacturerId
	 *            厂商id
	 * @param channelAmount
	 *            通道数 默认为8
	 * @param ip
	 *            ip地址
	 * @param port
	 *            端口
	 * @param extend
	 *            扩展信息
	 * @param user
	 *            用户名
	 * @param password
	 *            密码
	 */
	public void updateDvr(Long id, Long organId, String subType,
			String standardNumber, String name, String location, Long ptsId,
			Long manufacturerId, Integer channelAmount, String ip, String port,
			String extend, String user, String password);

	/**
	 * 获取dvr
	 * 
	 * @param id
	 *            id
	 * @return SvDevice
	 */
	public SvDevice getDvr(Long id);

	/**
	 * 根据ip和名称查询视频服务器
	 * 
	 * @param ip
	 *            视频服务器ip
	 * @param name
	 *            视频服务器名称
	 * @return Integer
	 */
	public Integer countDvr(String ip, String name);

	/**
	 * 条件查询视频服务器
	 * 
	 * @param ip
	 *            ip地址
	 * @param name
	 *            名称
	 * @param start
	 *            开始条数
	 * @param limit
	 *            需要查询条数
	 * @return List<SvDevice>
	 */
	public List<SvDevice> listDvr(String ip, String name, Integer start,
			Integer limit);

	/**
	 * 创建摄像头
	 * 
	 * @param standardNumber
	 *            标准号
	 * @param name
	 *            名称
	 * @param parentId
	 *            视频服务器id
	 * @param location
	 *            安装地址
	 * @param organId
	 *            机构id
	 * @param crsId
	 *            存储服务器id
	 * @param manufacturerId
	 *            厂商id
	 * @param solarBatteryId
	 *            太阳能电池id
	 * @param gpsId
	 *            gps设备id
	 * @param channelNumber
	 *            通道号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param stakeNumber
	 *            桩号
	 * @param navigation
	 *            上下行 0-上行 1-下行
	 * @param storeType
	 *            码流类型
	 * @param extend
	 *            扩展字段
	 * @param owner
	 *            设备归属
	 * @param civilCode
	 *            行政区域
	 * @param block
	 *            警区
	 * @param certNum
	 *            证书序列号
	 * @param certifiable
	 *            证书有效标识 缺省为0：无效 1有效 //默认为1
	 * @param errCode
	 *            无效错误码
	 * @param endTime
	 *            有效日期
	 * @param secrecy
	 *            保密属性 缺省0:不涉密 1：涉密 omc
	 * @param deviceModel
	 *            设备型号
	 * @param subType
	 *            设备子类型
	 * @param ptsId
	 *            pts服务器id
	 * @return 摄像头id
	 */
	public Long createCamera(String standardNumber, String name, Long parentId,
			String location, Long organId, Long crsId, Long manufacturerId,
			Long solarBatteryId, Long gpsId, String channelNumber,
			String longitude, String latitude, String stakeNumber,
			String navigation, Integer storeType, String extend, String owner,
			String civilCode, String block, String certNum, String certifiable,
			String errCode, Long endTime, String secrecy, String deviceModel,
			String subType, Long ptsId);

	/**
	 * 更新摄像头
	 * 
	 * @param id
	 *            摄像头id
	 * @param standardNumber
	 *            标准号
	 * @param name
	 *            名称
	 * @param parentId
	 *            视频服务器id
	 * @param location
	 *            安装地址
	 * @param organId
	 *            机构id
	 * @param crsId
	 *            存储服务器id
	 * @param manufacturerId
	 *            厂商id
	 * @param solarBatteryId
	 *            太阳能电池id
	 * @param gpsId
	 *            gps设备id
	 * @param channelNumber
	 *            通道号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param stakeNumber
	 *            桩号
	 * @param navigation
	 *            上下行 0-上行 1-下行
	 * @param storeType
	 *            码流类型
	 * @param extend
	 *            扩展字段
	 * @param owner
	 *            设备归属
	 * @param civilCode
	 *            行政区域
	 * @param block
	 *            警区
	 * @param certNum
	 *            证书序列号
	 * @param certifiable
	 *            证书有效标识 缺省为0：无效 1有效 //默认为1
	 * @param errCode
	 *            无效错误码
	 * @param endTime
	 *            有效日期
	 * @param secrecy
	 *            保密属性 缺省0:不涉密 1：涉密 omc
	 * @param deviceModel
	 *            设备型号
	 * @param subType
	 *            设备子类型
	 * @param centerStorePlan
	 *            中心存储计划
	 * @param localStorePlan
	 *            本地存储计划
	 * @param ptsId
	 *            pts服务器id
	 */
	public void updateCamera(Long id, String standardNumber, String name,
			Long parentId, String location, Long organId, Long crsId,
			Long manufacturerId, Long solarBatteryId, Long gpsId,
			String channelNumber, String longitude, String latitude,
			String stakeNumber, String navigation, Integer storeType,
			String extend, String owner, String civilCode, String block,
			String certNum, String certifiable, String errCode, Long endTime,
			String secrecy, String deviceModel, String subType,
			String centerStorePlan, String localStorePlan, Long ptsId);

	/**
	 * 删除摄像头
	 * 
	 * @param id
	 *            摄像头id
	 */
	public void deleteCamera(Long id);

	/**
	 * 列表查询摄像头
	 * 
	 * @param name
	 *            摄像头名称
	 * @param stakeNumber
	 *            桩号
	 * @param standardNumber
	 *            标准号
	 * @param dvrId
	 *            视频服务器id
	 * @param start
	 *            开始条数
	 * @param limit
	 *            需要条数
	 * @return 摄像头列表
	 */
	public List<SvDevice> listCamera(String name, String stakeNumber,
			String standardNumber, Long dvrId, Integer start, Integer limit);

	/**
	 * 统计查询摄像头数量
	 * 
	 * @param name
	 *            名称
	 * @param stakeNumber
	 *            桩号
	 * @param standardNumber
	 *            标准号
	 * @param dvrId
	 *            视频服务器id
	 * @return int
	 */
	public int countCamera(String name, String stakeNumber,
			String standardNumber, Long dvrId);

	/**
	 * 删除视频服务器
	 * 
	 * @param id
	 *            视频服务器id
	 */
	public void deleteDvr(Long id);

	/**
	 * 获取所有视频设备的SN-ID映射表
	 * 
	 * @return SN为key, ID为value的映射表
	 * @author MIKE
	 *         <p />
	 *         Create at 2015年12月31日 上午9:46:16
	 */
	public Map<String, Long> mapBySn();

	/**
	 * 查询桩号范围内的摄像头列表
	 * 
	 * @param floatStake
	 *            桩号
	 * @param organId
	 *            路段机构ID
	 * @param range
	 *            范围单位米
	 * @return 满足条件的摄像头列表
	 * @author MIKE
	 *         <p />
	 *         Create at 2016年1月11日 下午3:36:57
	 */
	public List<SvDevice> listCameraByStake(float floatStake, Long organId,
			Integer range);

	/**
	 * 修改视频设备的经纬度
	 * 
	 * @param id
	 *            设备id
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @author sjt
	 *         <p />
	 *         Create at 2016年 下午2:28:43
	 */
	public void updateDeviceLocation(Long id, String longitude, String latitude);
}
