package com.scsvision.database.dao;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.database.entity.VirtualOrgan;

/**
 * VOrganDAO
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午4:42:18
 */
@Local
public interface VOrganDAO extends BaseDAO<VirtualOrgan, Long> {
	/**
	 * 获取用户组根机构
	 * 
	 * @param ugId
	 *            用户组ID
	 * @return 用户组根机构
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午4:44:57
	 */
	public VirtualOrgan getUgRoot(Long ugId);

	/**
	 * 获取下级资源资源列表
	 * 
	 * @param id
	 *            父机构ID
	 * @return 所有下级资源列表
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午5:17:40
	 */
	public List<VirtualOrgan> listUgOrgan(Long id);

	/**
	 * 获取下级资源资源列表
	 * 
	 * @param id
	 *            父机构ID
	 * @return 直接下级资源列表
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午5:30:27
	 */
	public List<VirtualOrgan> listChildren(Long id);

	/**
	 * 根据userGroupID获取平台机构树
	 * 
	 * @return 平台机构树
	 * @author sjt
	 *         <p />
	 *         Create at 2015 上午10:18:25
	 */
	public List<VirtualOrgan> getvOrganList(Long userGroupId);

	/**
	 * 查询用户组下的虚拟节点列表
	 * 
	 * @param ugId
	 *            用户组ID
	 * @param types
	 *            虚拟节点类型(in 查询)
	 * @param visible
	 *            是否显示vorgan
	 * @return 用户组下的虚拟节点列表
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 上午11:52:10
	 */
	public List<VirtualOrgan> listUgOrganInTypes(Long ugId,
			List<Integer> types, Integer visible);

	/**
	 * 查询机构下的虚拟节点列表，包含自身
	 * 
	 * @param id
	 *            父机构ID
	 * @param types
	 *            虚拟节点类型(in 查询)
	 * @return 机构下的虚拟节点列表
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 上午10:56:03
	 */
	public List<VirtualOrgan> listChildInTypes(Long id, List<Integer> types);

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
	 * 查询机构下的视频设备列表
	 * 
	 * @param id
	 *            父机构ID
	 * @param types
	 *            视频设备类型(in 查询)
	 * @return 视频设备列表
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午5:37:13
	 */
	public List<VirtualOrgan> listChildDeviceInTypes(Long id,
			List<Integer> types);

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
	 *         Create at 2015年 下午3:17:10
	 */
	public List<VirtualOrgan> listVOrgan(Long deviceId, Integer type);

	/**
	 * 根据真实id删除虚拟机构
	 * 
	 * @param deviceId
	 *            真实id
	 */
	public void deleteByDeviceId(Long deviceId);

	/**
	 * 获取父机构下的视频类虚拟机构节点列表
	 * 
	 * @param parentId
	 *            父机构ID
	 * @return 父机构下的视频类虚拟机构节点列表
	 * @author MIKE
	 *         <p />
	 *         Create at 2015年12月25日 上午11:12:03
	 */
	public List<VirtualOrgan> listSvVirtualOrgan(Long parentId);
}
