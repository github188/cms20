package com.scsvision.cms.em.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;

import com.scsvision.cms.constant.TypeDefinition;
import com.scsvision.cms.em.dao.EventDeviceRealDAO;
import com.scsvision.cms.em.dao.EventRealDAO;
import com.scsvision.cms.em.manager.EventRealManager;
import com.scsvision.cms.interceptor.CacheInterceptor;
import com.scsvision.database.entity.EventDevice;
import com.scsvision.database.entity.EventDeviceReal;
import com.scsvision.database.entity.EventReal;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Interceptors(CacheInterceptor.class)
public class EventRealManagerImpl implements EventRealManager {
	@EJB(beanName = "EventRealDAOImpl")
	private EventRealDAO eventRealDAO;

	@EJB(beanName = "EventDeviceRealDAOImpl")
	private EventDeviceRealDAO eventDeviceRealDAO;

	@Override
	public Integer getEventRealCount(Integer type) {
		return eventRealDAO.getEventRealCount(type);
	}

	@Override
	public List<EventReal> listEventRealByType(Integer type, int start,
			int limit, Long beginTime, Long endTime, String sourceName) {
		return eventRealDAO.listEventRealByType(type, start, limit, beginTime,
				endTime, sourceName);
	}

	@Override
	public List<Integer> countEventAndAlarm(List<Long> organIds) {
		List<Integer> counts = new LinkedList<Integer>();
		int count = 0;
		// 交通事故数量
		count = eventRealDAO.countEventRealByType(TypeDefinition.EVENT_MANUAL,
				TypeDefinition.EVENT_TYPE_TA, TypeDefinition.COMMON_EVENT,
				null, organIds);
		counts.add(count);
		// 重大事故数量
		count = eventRealDAO.countEventRealByType(TypeDefinition.EVENT_MANUAL,
				null, TypeDefinition.TERRIBLE_EVENT, null, organIds);
		counts.add(count);
		// 设备阀值报警数量
		count = eventRealDAO.countEventRealByType(TypeDefinition.EVENT_ALARM,
				TypeDefinition.EVENT_TYPE_VA, null, null, organIds);
		counts.add(count);
		// 设备网络报警数量
		count = eventRealDAO.countEventRealByType(TypeDefinition.EVENT_ALARM,
				TypeDefinition.EVENT_TYPE_DOL, null,
				String.valueOf(TypeDefinition.EVENT_TYPE_WEB_FAULT), organIds);
		counts.add(count);
		// 设备故障报警数量
		count = eventRealDAO.countEventRealByType(TypeDefinition.EVENT_ALARM,
				TypeDefinition.EVENT_TYPE_DOL, null,
				String.valueOf(TypeDefinition.EVENT_TYPE_DEVICE_FAULT),
				organIds);
		counts.add(count);
		return counts;
	}

	@Override
	public Integer countEventReal(Integer type, Long beginTime, Long endTime,
			String sourceName) {
		return eventRealDAO
				.countEventReal(type, beginTime, endTime, sourceName);
	}

	@Override
	public Integer countEventReal(Integer deviceType, Integer type,
			Integer subType, String sourceName, String detailType, int start,
			int limit, Long beginTime, Long endTime, List<Long> organIds,
			String confirmUserName, Short confirmFlag, Integer alarmLevel) {
		// 如果不为首先查询主表对象集合 拿到IDS查询子表count
		if (deviceType != null) {
			List<EventReal> eventReals = eventRealDAO.listEventRealByType(type,
					subType, detailType, sourceName, 0, 0, beginTime, endTime,
					organIds, confirmUserName, confirmFlag, alarmLevel);
			
			if (eventReals.size() > 0) {
				List<Long> ids = new ArrayList<Long>();
				for (Iterator iterator = eventReals.iterator(); iterator.hasNext();) {
					EventReal eventReal = (EventReal) iterator.next();
					ids.add(eventReal.getId());
				}
				return eventDeviceRealDAO.countByDeviceType(deviceType, ids);
			} 

		}
		return eventRealDAO.countEventReal(type, subType, detailType,
				sourceName, start, limit, beginTime, endTime, organIds,
				confirmUserName, confirmFlag, alarmLevel);
	}

	@Override
	public Long saveEventReal(EventReal entity) {
		eventRealDAO.save(entity);
		return entity.getId();
	}

	@Override
	public EventReal getEventRealDetail(Long id) {
		EventReal entity = eventRealDAO.get(id);
		return entity;
	}

	@Override
	public void deleteEventReal(Long id) {
		EventReal entity = eventRealDAO.get(id);
		eventRealDAO.delete(entity);
	}

	@Override
	public void updateEventReal(EventReal entity) {
		eventRealDAO.update(entity);
	}

	@Override
	public List<EventReal> listEventRealByType(Integer deviceType,
			Integer type, Integer subType, String sourceName,
			String detailType, int start, int limit, Long beginTime,
			Long endTime, List<Long> organIds, String confirmUserName,
			Short confirmFlag, Integer alarmLevel) {

		// 如果不为首先查询主表对象集合 拿到IDS查询子表count
		if (deviceType != null) {
			// 查询主表集合
			List<EventReal> eventReals = eventRealDAO.listEventRealByType(type,
					subType, detailType, sourceName, 0, 0, beginTime, endTime,
					organIds, confirmUserName, confirmFlag, alarmLevel);			
			if (eventReals.size() > 0) {
				// 定义主表ids集合
				List<Long> ids = new ArrayList<Long>();
				// 定义主表map集合（以id为key，对象为value）
				Map<Long, EventReal> map = new HashMap<Long, EventReal>();
				for (Iterator iterator = eventReals.iterator(); iterator.hasNext();) {
					EventReal eventReal = (EventReal) iterator.next();
					ids.add(eventReal.getId());
					map.put(eventReal.getId(), eventReal);
				}
				// 查询设备子表设备类型集合
				List<EventDeviceReal> eventDevices = eventDeviceRealDAO.listByDeviceType(deviceType, ids, start, limit);
				List<EventReal> listEventReals = new ArrayList<EventReal>();
				for (Iterator iterator = eventDevices.iterator(); iterator.hasNext();) {
					EventDeviceReal eventDeviceReal = (EventDeviceReal) iterator.next();
					listEventReals.add(map.get(eventDeviceReal.getId()));
				}
				return listEventReals;
			}
		}
		return eventRealDAO.listEventRealByType(type, subType, detailType,
				sourceName, start, limit, beginTime, endTime, organIds,
				confirmUserName, confirmFlag, alarmLevel);
	}

}
