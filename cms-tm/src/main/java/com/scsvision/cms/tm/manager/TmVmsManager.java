/**
 * 
 */
package com.scsvision.cms.tm.manager;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.scsvision.cms.tm.service.vo.VmsInfoVO;
import com.scsvision.cms.tm.service.vo.VmsPublishVO;
import com.scsvision.database.entity.TmCmsPublish;
import com.scsvision.database.entity.TmDevice;
import com.scsvision.database.entity.TmPlayList;
import com.scsvision.database.entity.VirtualOrgan;

/**
 * @author wangbinyu
 *
 */
@Local
public interface TmVmsManager {
	/**
	 * 保存情报板发布信息
	 * 
	 * @param entity
	 *            情报板信息实体
	 * @return 新增情报板信息的ID
	 * @author sjt
	 *         <p />
	 *         Create at 2015 下午2:58:19
	 */
	public Long saveCmsPublish(TmCmsPublish entity);

	/**
	 * 根据id获取实体
	 * 
	 * @param id
	 *            要修改的情报板id
	 * @return 实体信息
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午3:51:28
	 */
	public TmCmsPublish getTmCmsPublish(Long id);

	/**
	 * 修改情报板信息
	 * 
	 * @param entity
	 *            实体
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午4:01:47
	 */
	public void update(TmCmsPublish entity);

	/**
	 * 删除指定的情报板
	 * 
	 * @param ids
	 *            情报板id
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午4:33:49
	 */
	public void delete(List<Long> ids);

	/**
	 * 获取情报板发布信息列表
	 * 
	 * @param start
	 *            开始行号
	 * @param limit
	 *            要查询的行数
	 * @return 情报板列表
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午9:45:18
	 */
	public List<TmCmsPublish> listCmsPublish(Integer start, Integer limit,
			Long playlistId);

	/**
	 * 获取信息数量
	 * 
	 * @param cmsId
	 *            要查询情报板id
	 * @return 数量
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午9:55:52
	 */
	public Integer countCmsPublish(Long cmsId);

	/**
	 * 查询情报板发布信息
	 * 
	 * @param vmses
	 *            情报板集合
	 * @return 情报板发布信息
	 */
	public List<VmsPublishVO> listVmsPushbishVO(List<VirtualOrgan> vmses);

	/**
	 * 根据情报板集合去查询tmdevice列表
	 * 
	 * @param vmses
	 *            情报板集合
	 * @return tmDevice
	 */
	public Map<Long, TmDevice> mapTmDevice(List<VirtualOrgan> vmses);

	/**
	 * 获取情报板详情
	 * 
	 * @param id
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午3:57:40
	 */
	public TmCmsPublish getCmsPublishDetail(Long id);

	/**
	 * 
	 * @param ids
	 *            信息ids
	 * @return 情报板信息列表
	 */
	public List<VmsInfoVO> listVmsInfoByIds(List<Long> ids);

	/**
	 * 根据情报板大小类型查询常用条目
	 * 
	 * @param sizeType
	 *            大小类型
	 * @return List<TmPlayList>
	 */
	public List<TmPlayList> listPlaylist(String sizeType);

	/**
	 * 创建节目单
	 * 
	 * @param name
	 *            节目单名称
	 * @param sizeType
	 *            情报板大小类型
	 * @return Long
	 */
	public Long createPlayList(String name, String sizeType);

	/**
	 * 修改节目单
	 * 
	 * @param id
	 *            节目单id
	 * @param name
	 *            名称
	 * @param sizeType
	 *            情报板大小类型
	 */
	public void updatePlayListJson(Long id, String name, String sizeType);

	/**
	 * 删除节目单
	 * 
	 * @param ids
	 */
	public void deletePlayListJson(Long ids);

	/**
	 * 根据id获取节目单
	 * 
	 * @param id
	 *            节目单id
	 * @return TmPlayList
	 */
	public TmPlayList getPlayListJson(Long id);

	/**
	 * 批量保存常用信息
	 * 
	 * @param list
	 *            常用信息列表
	 */
	public void batchTmCmsPublish(
			List<com.scsvision.database.entity.TmCmsPublish> list);

	/**
	 * 根据节目单删除常用信息
	 * 
	 * @param playlistId
	 *            节目单id
	 */
	public void deleteCmsPublish(long playlistId);
}
