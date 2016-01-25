package com.scsvision.database.manager;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.scsvision.database.entity.ResourceImage;

/**
 * ResourceImageManager
 * 
 * @author sjt
 *         <p />
 *         Create at 2015 下午2:04:47
 */
@Local
public interface ResourceImageManager {
	/**
	 * 根据id列表获取以图片id为主键，图片实体为值的键值对
	 * 
	 * @param ids
	 *            图片id列表
	 * @return 键值对
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午2:06:26
	 */
	public Map<Long, ResourceImage> mapImageByIds(List<Long> ids);

	/**
	 * 创建资源图片
	 * 
	 * @param entity
	 *            要创建的图片资源
	 * @return 创建成功的图片ID
	 * @author MIKE
	 *         <p />
	 *         Create at 2016年1月4日 上午9:59:31
	 */
	public Long createImage(ResourceImage entity);
	
	public String findAddressById(Long resourceId);
}
