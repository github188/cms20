package com.scsvision.cms.sv.manager.impl;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;

import com.scsvision.cms.sv.dao.UserDeviceFavoriteDAO;
import com.scsvision.cms.sv.dao.UserFavoriteDAO;
import com.scsvision.cms.sv.manager.UserFavoriteManager;
import com.scsvision.database.entity.UserDeviceFavorite;
import com.scsvision.database.entity.UserFavorite;

/**
 * UserFavoriteManagerImpl
 * 
 * @author sjt
 *         <p />
 *         Create at 2016年 下午1:57:40
 */
@Stateless
public class UserFavoriteManagerImpl implements UserFavoriteManager {
	@EJB(beanName = "UserFavoriteDAOImpl")
	private UserFavoriteDAO userFavoriteDAO;
	@EJB(beanName = "UserDeviceFavoriteDAOImpl")
	private UserDeviceFavoriteDAO userDeviceFavoriteDAO;

	@Override
	public Long createFavorite(String name, String note, Long userId,
			List<Long> ids) {
		UserFavorite entity = new UserFavorite();
		entity.setCreateTime(System.currentTimeMillis());
		entity.setFavoriteName(name);
		entity.setNote(note);
		entity.setUserId(userId);
		userFavoriteDAO.save(entity);
		Long fid = entity.getId();
		for (Long id : ids) {
			UserDeviceFavorite userDeviceFavorite = new UserDeviceFavorite();
			userDeviceFavorite.setDeviceId(id);
			userDeviceFavorite.setFavoriteId(fid);
			userDeviceFavoriteDAO.save(userDeviceFavorite);
		}
		return fid;
	}

	@Override
	public void updateFavorite(String name, String note, Long id, List<Long> ids) {
		UserFavorite entity = userFavoriteDAO.get(id);
		if (StringUtils.isNotBlank(name)) {
			entity.setFavoriteName(name);
		}
		if (StringUtils.isNotBlank(note)) {
			entity.setNote(note);
		}
		if (ids.size() > 0) {
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("favoriteId", id);
			List<UserDeviceFavorite> list = userDeviceFavoriteDAO
					.findByPropertys(map);
			for (UserDeviceFavorite userDeviceFavorite : list) {
				userDeviceFavoriteDAO.delete(userDeviceFavorite);
			}
			for (Long fid : ids) {
				UserDeviceFavorite userDeviceFavorite = new UserDeviceFavorite();
				userDeviceFavorite.setDeviceId(fid);
				userDeviceFavorite.setFavoriteId(id);
				userDeviceFavoriteDAO.save(userDeviceFavorite);
			}
		}
		userFavoriteDAO.update(entity);
	}

	@Override
	public void deleteFavorite(Long id) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("favoriteId", id);
		List<UserDeviceFavorite> list = userDeviceFavoriteDAO
				.findByPropertys(map);
		for (UserDeviceFavorite entity : list) {
			userDeviceFavoriteDAO.delete(entity);
		}
		userFavoriteDAO.deleteById(id);
	}

	@Override
	public List<UserFavorite> listUserFavorite(Long userId) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("userId", userId);
		List<UserFavorite> list = userFavoriteDAO.findByPropertys(map);
		return list;
	}

	@Override
	public List<Long> listDeviceIds(Long favoriteId) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("favoriteId", favoriteId);
		List<UserDeviceFavorite> list = userDeviceFavoriteDAO
				.findByPropertys(map);
		List<Long> deviceIds = new LinkedList<Long>();
		for (UserDeviceFavorite udf : list) {
			deviceIds.add(udf.getDeviceId());
		}
		return deviceIds;
	}
}
