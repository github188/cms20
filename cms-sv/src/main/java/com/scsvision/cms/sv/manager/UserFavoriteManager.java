package com.scsvision.cms.sv.manager;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.database.entity.UserFavorite;

/**
 * 
 * UserFavoriteManager
 * 
 * @author sjt
 *         <p />
 *         Create at 2016年 下午1:56:41
 */
@Local
public interface UserFavoriteManager {
	/**
	 * 创建收藏夹
	 * 
	 * @param name
	 *            收藏夹名称
	 * @param note
	 *            备注
	 * @param userId
	 *            用户id
	 * @param ids
	 *            收藏的设备id列表
	 * @return 新建收藏夹id
	 * @author sjt
	 *         <p />
	 *         Create at 2016年 下午2:01:17
	 */
	public Long createFavorite(String name, String note, Long userId,
			List<Long> ids);

	/**
	 * 创建收藏夹
	 * 
	 * @param name
	 *            收藏夹名称
	 * @param note
	 *            备注
	 * @param id
	 *            要修改的收藏夹的id
	 * @param ids
	 *            收藏的设备id列表
	 * @author sjt
	 *         <p />
	 *         Create at 2016年 下午4:02:09
	 */
	public void updateFavorite(String name, String note, Long id, List<Long> ids);

	/**
	 * 删除收藏夹
	 * 
	 * @param id
	 *            要删除收藏夹的id
	 * @author sjt
	 *         <p />
	 *         Create at 2016年 上午9:42:08
	 */
	public void deleteFavorite(Long id);

	/**
	 * 根据userId获取收藏夹列表
	 * 
	 * @param userId
	 *            用户id
	 * @return 收藏夹列表
	 * @author sjt
	 *         <p />
	 *         Create at 2016年 下午1:51:45
	 */
	public List<UserFavorite> listUserFavorite(Long userId);

	/**
	 * 根据收藏夹id获取设备id列表
	 * 
	 * @param favoriteId
	 *            收藏夹id
	 * @return 设备id列表
	 * @author sjt
	 *         <p />
	 *         Create at 2016年 下午2:00:53
	 */
	List<Long> listDeviceIds(Long favoriteId);
}
