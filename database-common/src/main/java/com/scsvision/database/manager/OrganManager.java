package com.scsvision.database.manager;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.dom4j.Element;

import com.scsvision.database.entity.Organ;
import com.scsvision.database.entity.VirtualOrgan;
import com.scsvision.database.service.vo.OrganVO;

@Local
public interface OrganManager {

	/**
	 * 根据userGroupID获取平台机构树
	 * 
	 * @return 平台机构树
	 * @author sjt
	 *         <p />
	 *         Create at 2015 上午10:18:25
	 */
	public List<VirtualOrgan> getvOrganList(Long userGroupId);

	public Map<Long, Organ> getOrgans();

	/**
	 * 获取平台中的所有真实机构ID为key，机构对象为value的映射表
	 * 
	 * @param ids
	 *            机构id列表
	 * @return 键值对
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午3:21:58
	 */
	public Map<Long, Organ> mapOrgan(List<Long> ids);

	/**
	 * 获取以Id为Key，VirtualOrgan为值的map
	 * 
	 * @param ids
	 *            id列表
	 * @return map
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午4:17:23
	 */
	public Map<Long, VirtualOrgan> mapVOrgan(List<Long> ids);

	/**
	 * 获取用户组根机构
	 * 
	 * @param ugId
	 *            用户组ID
	 * @return 用户组根机构
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午4:35:40
	 */
	public VirtualOrgan getUgRootOrgan(Long ugId);

	/**
	 * 获取用户组的资源列表
	 * 
	 * @param id
	 *            父机构ID
	 * @return 本级和直接下级节点
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午5:17:40
	 */
	public List<VirtualOrgan> listUgVirtualOrgan(Long id);

	/**
	 * 获取下级资源列表
	 * 
	 * @param id
	 *            父机构ID
	 * @return 下级资源列表
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午5:33:49
	 */
	public List<VirtualOrgan> listChildVirtualOrgan(Long id);

	/**
	 * 获取给定ID的虚拟机构
	 * 
	 * @param id
	 *            虚拟机构ID
	 * @return 虚拟机构
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 上午11:50:24
	 */
	public VirtualOrgan getVOrgan(Long id);

	/**
	 * 根据机构id查询当前机构以及下级机构
	 * 
	 * @param organId
	 *            机构id
	 * @return 机构集合
	 */
	public List<Organ> listOrganlikePath(Long organId);

	/**
	 * 获取用户组具有权限的所有路段和高速公路
	 * 
	 * @param ugId
	 *            用户组ID
	 * @return 用户组具有权限的所有路段和高速公路
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 上午11:47:24
	 */
	public List<VirtualOrgan> listUgRoad(Long ugId);

	/**
	 * 获取虚拟机构树
	 */
	public Element organTree(List<OrganVO> oDeviceList, OrganVO vorgan);

	/**
	 * 获取用户组具有权限的所有真实机构
	 * 
	 * @param ugId
	 *            用户组ID
	 * @return 用户组具有权限的所有真实机构
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 上午10:39:58
	 */
	public List<VirtualOrgan> listUgRealOrgan(Long ugId);

	/**
	 * 查询给定虚拟机构下的所有真实机构列表，包含自身
	 * 
	 * @param id
	 *            虚拟机构ID
	 * @return 给定虚拟机构下的所有真实机构列表，包含自身
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 上午10:49:54
	 */
	public List<VirtualOrgan> listRealChildOrgan(Long id);

	/**
	 * 获取用户组具有权限的所有设备列表
	 * 
	 * @param ugId
	 *            用户组ID
	 * @return 用户组具有权限的所有设备列表
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 上午9:47:56
	 */
	public List<VirtualOrgan> listUgDevice(Long ugId);

	/**
	 * 查询给定机构下的所有摄像头
	 * 
	 * @param id
	 *            机构Id
	 * @return 摄像头列表
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午3:15:47
	 */
	public List<VirtualOrgan> listOrganChannel(Long id);

	/**
	 * 获取用户组具有权限的所有机构(包括虚拟机构)visible为0
	 * 
	 * @param userGroupId
	 *            用户组id
	 * @return List<VirtualOrgan>
	 */
	public List<VirtualOrgan> listUgAllOrgan(Long userGroupId);

	/**
	 * 查询用户组车检器列表(只查询visible为0)
	 * 
	 * @param userGroupId
	 *            用户组id
	 * @return 车检器
	 */
	public List<VirtualOrgan> listUgAllVd(Long userGroupId);

	/**
	 * 查询用户组车检器列表(只查询visible为0)
	 * 
	 * @param userGroupId
	 *            用户组id
	 * @return 情报板
	 */
	public List<VirtualOrgan> listUgAllVms(Long userGroupId);

	/**
	 * 根据机构id模糊查询path下的情报板
	 * 
	 * @param id
	 *            机构id
	 * @return 情报板列表
	 */
	public List<VirtualOrgan> listVmsByOrgan(Long id);

	/**
	 * 根据deviceId和设备类型获取VOrgan列表
	 * 
	 * @param deviceId
	 *            设备id
	 * @param type
	 *            设备类型
	 * @return 列表
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午3:27:10
	 */
	public List<VirtualOrgan> listVOrgan(Long deviceId, Integer type);

	/**
	 * 创建机构
	 * 
	 * @param entity
	 *            要创建的实体
	 * @return 新建机构id
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午1:53:15
	 */
	public Long createOrgan(Organ entity);

	/**
	 * 根据id获取机构信息
	 * 
	 * @param id
	 *            机构id
	 * @return 机构实体
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午2:40:13
	 */
	public Organ getOrganById(Long id);

	/**
	 * 修改机构
	 * 
	 * @param entity
	 *            要修改的机构量
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午2:47:06
	 */
	public void updateOrgan(Organ entity);

	/**
	 * 删除某机构
	 * 
	 * @param id
	 *            机构id
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午4:54:00
	 */
	public void deleteOrgan(Long id);

	/**
	 * 根据需求查询机构列表
	 * 
	 * @param OrganId
	 *            机构的id
	 * @param name
	 *            机构名称
	 * @param start
	 *            起始数量
	 * @param limit
	 *            每页条数
	 * @return 列表
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午9:21:32
	 */
	public List<Organ> listOrgan(Long organId, String name, int start, int limit);

	/**
	 * 根据需求查询机构条数
	 * 
	 * @param OrganId
	 *            机构的id
	 * @param name
	 *            机构名称
	 * @return 数量
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午9:34:59
	 */
	public Integer countOrgan(Long organId, String name);

	/**
	 * 创建虚拟节点
	 * 
	 * @param entity
	 *            虚拟节点信息
	 * @return 新建节点id
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午2:20:31
	 */
	public Long createNode(VirtualOrgan entity);

	/**
	 * 修改虚拟节点
	 * 
	 * @param entity
	 *            要修改的节点
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午2:27:01
	 */
	public void updateNode(VirtualOrgan entity);

	/**
	 * 删除虚拟节点
	 * 
	 * @param id
	 *            要删除节点的id
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午9:47:40
	 */
	public void deleteNode(Long id);

	/**
	 * 查询虚拟机构树
	 * 
	 * @param list
	 *            某用户组能看到的资源列表
	 * @param root
	 *            资源列表根机构
	 * @return 虚拟机构树
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午11:00:17
	 */
	public Element TreeVirtualOrgan(List<VirtualOrgan> list, VirtualOrgan root);

	/**
	 * 获取平台中的所有真实机构ID为key，机构对象为value的映射表
	 * 
	 * @return 平台中的所有机构ID为key，机构对象为value的映射表
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午4:58:47
	 */
	public Map<Long, Organ> mapOrganById();

	/**
	 * 
	 * @param organId
	 *            虚拟机构id
	 * @return List<VirtualOrgan>
	 */
	public List<VirtualOrgan> listAllOrgan(Long organId);

	/**
	 * 根据虚拟机构id查询设备或者机构真实id
	 * 
	 * @param id
	 *            虚拟机构id
	 * @param list
	 *            查询设备类型
	 * @return List<Long>
	 */
	public List<Long> listDeviceIds(Long id, List<Integer> list);

	/**
	 * 获取用户组的全部虚拟节点集合
	 * 
	 * @param id
	 *            用户组根机构ID
	 * @return 虚拟节点ID为key，虚拟节点对象为value的集合
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午2:35:44
	 */
	public Map<Long, VirtualOrgan> mapUgVirtualOrgan(Long id);

	/**
	 * 获取用户组的视频类虚拟节点集合
	 * 
	 * @param id
	 *            用户组根机构ID
	 * @return 虚拟节点ID为key，虚拟节点对象为value的集合
	 * @author MIKE
	 *         <p />
	 *         Create at 2015年12月25日 上午11:07:09
	 */
	public Map<Long, VirtualOrgan> mapUgSvVirtualOrgan(Long id);

	/**
	 * 查询机构列表
	 * 
	 * @return List<Organ>
	 */
	public List<Organ> listAllRealOrgan();

	/**
	 * 根据用户组查询摄像头
	 * 
	 * @param userId
	 *            用户组id
	 * @return List<VirtualOrgan>
	 */
	public List<VirtualOrgan> listUgAllCamera(Long userGroupId);
	
	/**
	 * 根据用户组id查询路段列表
	 * 
	 * @param userGroupId
	 *            用户组id
	 * @return List<VirtualOrgan>
	 */
	public List<VirtualOrgan> listRoad(Long userGroupId);

	/**
	 * 根据机构ids查询机构列表
	 * 
	 * @param ids
	 *            真实机构id
	 * @return List<Organ>
	 */
	public List<Organ> listOrganByIds(List<Long> ids);

	/**
	 * 获取用户组下的所有真实设备
	 * 
	 * @param userGroupId
	 *            用户组ID
	 * @return 真实设备ID为key, VirtualOrgan为value的映射表
	 * @author MIKE
	 *         <p />
	 *         Create at 2016年1月11日 下午4:02:46
	 */
	public Map<Long, VirtualOrgan> mapUgRealDevice(Long userGroupId);
}
