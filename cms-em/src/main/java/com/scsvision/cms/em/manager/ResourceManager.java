/**
 * 
 */
package com.scsvision.cms.em.manager;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.scsvision.cms.em.service.vo.ResourceVO;
import com.scsvision.database.entity.EventResource;

/**
 * @author wangbinyu
 *
 */
@Local
public interface ResourceManager {

	/**
	 * 保存资源图片
	 * 
	 * @param eventId
	 *            事件ID
	 * @param vos
	 *            资源
	 */
	public void saveResource(String eventId, List<ResourceVO> vos);

	/**
	 * 
	 * @param eventId
	 *            事件id
	 * @param resourceId
	 *            资源id
	 */
	public void saveResource(Long eventId, Long resourceId);

	/**
	 * 根据id列表获取以资源id为主键，资源实体为值的键值对
	 * 
	 * @param ids
	 *            资源id列表
	 * @return 键值对
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午2:06:26
	 */
	public Map<Long, ResourceVO> mapResourcedByIds(List<Long> ids);

	/**
	 * 查询当前事件关联的资源列表
	 * 
	 * @param eventRealId
	 *            当前事件ID
	 * @return 事件关联的资源列表
	 * @author MIKE
	 *         <p />
	 *         Create at 2016年1月19日 下午2:54:33
	 */
	public List<EventResource> listEventRealResource(Long eventRealId);

	/**
	 * 删除事件关联资源关系
	 * 
	 * @param id
	 *            事件id
	 */
	public void deleteResource(Long id);

	public void deleteResourceByEventRealId(Long eventRealId);

	/**
	 * 保存事件资源
	 * 
	 * @param eventId
	 *            事件id
	 * @param vo
	 *            资源
	 */
	public void saveResource1(String eventId, ResourceVO vo);

	/**
	 * 根据资源id删除资源
	 * 
	 * @param eventId
	 *            事件id
	 * @param address
	 *            资源地址（唯一）
	 */
	public void deleteResource(Long eventId, String address);

	public void updateResource(EventResource eResource);

}
