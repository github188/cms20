package com.scsvision.database.manager;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.dom4j.Element;

import com.scsvision.database.entity.DicManufacture;
import com.scsvision.database.entity.Organ;
import com.scsvision.database.entity.TmDevice;
import com.scsvision.database.entity.VirtualOrgan;

/**
 * TmDeviceManager
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午2:30:28
 */
@Local
public interface TmDeviceManager {
	/**
	 * 获取以ID为key,TmDevice为Value的映射集合
	 * 
	 * @param ids
	 *            ID列表，如果为空将查询所有的数据
	 * @return ID为key,TmDevice为Value的映射集合
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午2:36:10
	 */
	public Map<Long, TmDevice> mapTmDevice(List<Long> ids);

	/**
	 * 生成机构树
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
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午3:35:44
	 */
	public Element generateOrganTree(VirtualOrgan rootOrgan,
			Map<Long, Organ> organMap, Map<Long, TmDevice> deviceMap,
			List<VirtualOrgan> list, Map<Long, DicManufacture> manufacturerMap);

	/**
	 * 条件查询数据设备
	 * 
	 * @param organs
	 *            机构id
	 * @param types
	 *            数据设备类型
	 * @return 数据设备map
	 */
	public Map<Long, TmDevice> mapDeviceByOrgan(List<Organ> organs,
			Integer[] types);

	/**
	 * 根据id获取TmDevice
	 * 
	 * @param id
	 * @return TmDevice
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午5:45:54
	 */
	public TmDevice getTmDevice(Long id);

	/**
	 * 修改设备坐标
	 * 
	 * @param tmDevice
	 *            设备对象
	 * @return
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午5:49:43
	 */
	public void saveTmMarkers(TmDevice tmDevice);

	/**
	 * 获取给定节点下的数据设备列表
	 * 
	 * @param list
	 *            子节点列表
	 * @param map
	 *            设备对象map
	 * @param manufactureMap
	 *            厂商map
	 * @param node
	 *            指定 节点
	 * @return 数据设备列表
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午11:06:58
	 */
	public Element getdeviceList(List<VirtualOrgan> list,
			Map<Long, TmDevice> map, Map<Long, DicManufacture> manufactureMap,
			VirtualOrgan node);

	/**
	 * 设备与路段真实机构的归属关系查询，隧道和桥梁上的设备归属到上级路段中
	 * 
	 * @param type
	 *            设备主类型
	 * @return 设备ID为key， 路段真实机构ID为value的映射
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午5:48:52
	 */
	public Map<Long, Long> mapDeviceByRoad(String type);

	/**
	 * 获取所有的数据设备以SN为key,设备对象为value的键值集合
	 * 
	 * @return
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午5:28:56
	 */
	public Map<String, TmDevice> mapDeviceBySn();

	/**
	 * 获取DAS管辖的所有设备列表
	 * 
	 * @param dasId
	 *            das_id
	 * @return DAS管辖的所有设备列表
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午12:00:15
	 */
	public List<TmDevice> listDasDevice(Long dasId);

	/**
	 * 删除机构下的数据设备
	 * 
	 * @param organId
	 *            机构id
	 */
	public void deleteTmDevice(Long organId);

	/**
	 * 创建数据设备
	 * 
	 * @param entity
	 *            设备信息
	 * @return 新建设备id
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午4:06:44
	 */
	public Long createTmDevice(TmDevice entity);

	/**
	 * 修改设备信息
	 * 
	 * @param entity
	 *            要修改的信息
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午10:13:34
	 */
	public void updateTmDevice(TmDevice entity);

	/**
	 * 根据id删除数据设备
	 * 
	 * @param id
	 *            要删除设备的id
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午3:12:26
	 */
	public void deleteTmDeviceById(Long id);

	/**
	 * 根据条件查询数据设备列表
	 * 
	 * @param organId
	 *            设备所在机构
	 * @param name
	 *            设备名称
	 * @param standardNumber
	 *            设备标准号
	 * @param stakeNumber
	 *            设备桩号
	 * @param type
	 *            设备类型
	 * @param start
	 *            开始查询条数
	 * @param limit
	 *            每次查询条数
	 * @return 数据设备列表
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午9:54:10
	 */
	public List<TmDevice> listTmDevice(Long organId, String name,
			String standardNumber, String stakeNumber, String Type, int limit,
			int start);

	/**
	 * 获取某机构下的设备数量
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            设备名称
	 * @param standardNumber
	 *            设备标准号
	 * @param stakeNumber
	 *            设备桩号
	 * @param type
	 *            设备类型
	 * @return 设备数量
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午10:17:47
	 */
	public Integer countTmDevice(Long organId, String name,
			String standardNumber, String stakeNumber, String Type);

	/**
	 * 获取所有数据设备的SN-ID映射表
	 * 
	 * @return SN为key, ID为value的映射表
	 * @author MIKE
	 *         <p />
	 *         Create at 2015年12月31日 上午9:51:16
	 */
	public Map<String, Long> mapBySn();
}
