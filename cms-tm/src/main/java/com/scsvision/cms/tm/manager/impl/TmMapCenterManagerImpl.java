/**
 * 
 */
package com.scsvision.cms.tm.manager.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;

import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.cms.interceptor.CacheInterceptor;
import com.scsvision.cms.tm.dao.TmMapCenterDAO;
import com.scsvision.cms.tm.manager.TmMapCenterManager;
import com.scsvision.cms.tm.service.vo.TmMapCenterVO;
import com.scsvision.database.entity.TmMapCenter;

/**
 * @author wangbinyu
 *
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Interceptors(CacheInterceptor.class)
public class TmMapCenterManagerImpl implements TmMapCenterManager {

	@EJB(beanName = "TmMapCenterDAOImpl")
	private TmMapCenterDAO tmMapCenterDAO;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveMapCenter(String longitude, Long userId, String latitude,
			Integer mapLevel) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("userId", userId);
		List<TmMapCenter> list = tmMapCenterDAO.findByPropertys(params);
		if (list.size() > 0) {
			TmMapCenter tmc = list.get(0);
			if (null != latitude) {
				tmc.setLatitude(latitude);
			}
			if (null != longitude) {
				tmc.setLongitude(longitude);
			}
			if (null != mapLevel) {
				tmc.setMapLevel(mapLevel);
			}
			tmMapCenterDAO.update(tmc);
		} else {
			TmMapCenter tmc = new TmMapCenter();
			tmc.setLatitude(latitude);
			tmc.setLongitude(longitude);
			tmc.setUserId(userId);
			tmc.setMapLevel(mapLevel);
			tmMapCenterDAO.save(tmc);
		}

	}

	@Override
	public TmMapCenterVO getMapCenter(Long userId) {
		TmMapCenterVO vo = new TmMapCenterVO();
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("userId", userId);
		List<TmMapCenter> list = tmMapCenterDAO.findByPropertys(params);
		if (list.size() > 0) {
			TmMapCenter tmc = list.get(0);
			vo.setId(tmc.getId() + "");
			vo.setLatitude(tmc.getLatitude());
			vo.setLongitude(tmc.getLongitude());
			vo.setMapLevel(tmc.getMapLevel() + "");
			vo.setUserId(tmc.getUserId() + "");
		} else {
			vo.setId("");
			vo.setLatitude("");
			vo.setLongitude("");
			vo.setMapLevel("");
			vo.setUserId("");
		}
		return vo;
	}

}
