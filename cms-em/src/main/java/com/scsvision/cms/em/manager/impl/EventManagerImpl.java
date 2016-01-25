package com.scsvision.cms.em.manager.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;

import com.scsvision.cms.annotation.Cache;
import com.scsvision.cms.constant.TypeDefinition;
import com.scsvision.cms.em.dao.EventDAO;
import com.scsvision.cms.em.dao.EventDeviceRealDAO;
import com.scsvision.cms.em.dao.EventManualDAO;
import com.scsvision.cms.em.dao.EventManualRealDAO;
import com.scsvision.cms.em.dao.EventRealDAO;
import com.scsvision.cms.em.manager.EventManager;
import com.scsvision.cms.em.service.vo.StatEventTypeVO;
import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.cms.interceptor.CacheInterceptor;
import com.scsvision.database.entity.Event;
import com.scsvision.database.entity.EventDeviceReal;
import com.scsvision.database.entity.EventManual;
import com.scsvision.database.entity.EventManualReal;
import com.scsvision.database.entity.EventReal;

/**
 * EventManagerImpl
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午2:45:58
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Interceptors(CacheInterceptor.class)
public class EventManagerImpl implements EventManager {
	@EJB(beanName = "EventDAOImpl")
	private EventDAO eventDAO;

	@EJB(beanName = "EventRealDAOImpl")
	private EventRealDAO eventRealDAO;

	@EJB(beanName = "EventManualRealDAOImpl")
	private EventManualRealDAO eventManualRealDAO;

	@EJB(beanName = "EventDeviceRealDAOImpl")
	private EventDeviceRealDAO eventDeviceRealDAO;

	@EJB(beanName = "EventManualDAOImpl")
	private EventManualDAO eventManualDAO;

	@Override
	@Cache(expire = 86400)
	public Map<Integer, List<StatEventTypeVO>> statManualEventType(
			Long beginTime, Long endTime, List<Long> organIds) {
		List<Event> events = eventDAO.listEvent(beginTime, endTime,
				TypeDefinition.EVENT_MANUAL, organIds, null, null, 0,
				Integer.MAX_VALUE - 1);
		// 返回map
		Map<Integer, List<StatEventTypeVO>> map = new HashMap<Integer, List<StatEventTypeVO>>();
		// 初始化map
		map.put(TypeDefinition.EVENT_TYPE_NW,
				initStatEventTypeByMonth(beginTime, endTime));
//		map.put(TypeDefinition.EVENT_TYPE_ED,
//				initStatEventTypeByMonth(beginTime, endTime));
//		map.put(TypeDefinition.EVENT_TYPE_OTHER,
//				new LinkedList<StatEventTypeVO>());
		map.put(TypeDefinition.EVENT_TYPE_TA,
				initStatEventTypeByMonth(beginTime, endTime));
		map.put(TypeDefinition.EVENT_TYPE_TC,
				initStatEventTypeByMonth(beginTime, endTime));
//		map.put(TypeDefinition.EVENT_TYPE_VD,
//				initStatEventTypeByMonth(beginTime, endTime));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

		for (Event event : events) {
			List<StatEventTypeVO> list = map.get(event.getSubType());
			for (StatEventTypeVO vo : list) {
				Date date = new Date(event.getCreateTime().longValue());
				if (vo.getDate().equals(sdf.format(date))) {
					vo.setTimes(vo.getTimes() + 1);
					break;
				}
			}

		}
		return map;
	}

	@Override
	@Cache
	public Map<Long, List<StatEventTypeVO>> statAlarmTimes(Long beginTime,
			Long endTime, List<Long> organIds) {
		List<Event> events = eventDAO.listEvent(beginTime, endTime,
				TypeDefinition.EVENT_ALARM, organIds, null, null, 0,
				Integer.MAX_VALUE - 1);
		// 返回map
		Map<Long, List<StatEventTypeVO>> map = new HashMap<Long, List<StatEventTypeVO>>();
		for (Long organId : organIds) {
			map.put(organId, initStatEventTypeByDay(beginTime, endTime));
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (Event event : events) {
			List<StatEventTypeVO> list = map.get(event.getOrganId());
			for (StatEventTypeVO vo : list) {
				Date date = new Date(event.getCreateTime().longValue());
				if (vo.getDate().equals(sdf.format(date))) {
					vo.setTimes(vo.getTimes() + 1);
					break;
				}
			}
		}

		return map;
	}

	/**
	 * 初始化按月统计事件类型列表，每月一个对象
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 每月一个对象
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午3:42:33
	 */
	private List<StatEventTypeVO> initStatEventTypeByMonth(Long beginTime,
			Long endTime) {
		List<StatEventTypeVO> list = new LinkedList<StatEventTypeVO>();
		Calendar begin = Calendar.getInstance();
		begin.setTimeInMillis(beginTime.longValue());

		Calendar end = Calendar.getInstance();
		end.setTimeInMillis(endTime.longValue());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		while (begin.before(end)) {
			StatEventTypeVO vo = new StatEventTypeVO();
			vo.setDate(sdf.format(begin.getTime()));
			vo.setTimes(0);
			list.add(vo);

			begin.add(Calendar.MONTH, 1);
		}
		return list;
	}

	/**
	 * 初始化按天统计报警事件次数列表，每天一个对象
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 每天一个对象
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午2:00:06
	 */
	private List<StatEventTypeVO> initStatEventTypeByDay(Long beginTime,
			Long endTime) {
		List<StatEventTypeVO> list = new LinkedList<StatEventTypeVO>();
		Calendar begin = Calendar.getInstance();
		begin.setTimeInMillis(beginTime.longValue());

		Calendar end = Calendar.getInstance();
		end.setTimeInMillis(endTime.longValue());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		while (begin.before(end)) {
			StatEventTypeVO vo = new StatEventTypeVO();
			vo.setDate(sdf.format(begin.getTime()));
			vo.setTimes(0);
			list.add(vo);

			begin.add(Calendar.DAY_OF_MONTH, 1);
		}
		return list;
	}

	@Override
	@Cache
	public List<Event> listAlarm(Long beginTime, Long endTime,
			List<Long> organIds, Integer level, Integer subType, int start,
			int limit) {
		return eventDAO.listEvent(beginTime, endTime,
				TypeDefinition.EVENT_ALARM, organIds, level, subType, start,
				limit);
	}

	@Override
	@Cache
	public int countAlarm(Long beginTime, Long endTime, List<Long> organIds,
			Integer level, Integer subType) {
		return eventDAO.countEvent(beginTime, endTime,
				TypeDefinition.EVENT_ALARM, organIds, level, subType);
	}

	@Override
	public List<Event> listEventByType(Integer subType, int start, int limit,
			Long beginTime, Long endTime, String sourceName) {
		return eventDAO.listEventByType(subType, start, limit, beginTime,
				endTime, sourceName);
	}

	@Override
	public Integer countAlarmReal(Integer type, Integer subType,
			Integer eventLevel, Long beginTime, Long endTime,
			String confirmUserName, List<Long> organIds, String resourceName) {
		return eventRealDAO.countAlarmReal(type, subType, eventLevel,
				beginTime, endTime, confirmUserName, organIds, resourceName);
	}

	@Override
	public List<EventReal> listEventAlarmReal(Integer type, Integer subType,
			Integer eventLevel, Long beginTime, Long endTime,
			String confirmUserName, Integer start, Integer limit,
			List<Long> organIds, String resourceName) {
		return eventRealDAO.listEventAlarmReal(type, subType, eventLevel,
				beginTime, endTime, confirmUserName, start, limit, organIds,
				resourceName);
	}

	@Override
	public Map<Long, EventDeviceReal> listEeventDeviceRealByIds(List<Long> ids) {
		Map<Long, EventDeviceReal> map = eventDeviceRealDAO.map(ids);
		return map;
	}

	@Override
	public Integer countEventByType(Integer type, Long beginTime, Long endTime,
			String sourceName) {
		return eventDAO.countEventByType(type, beginTime, endTime, sourceName);
	}

	@Override
	public Event getEvent(Long id) {
		Event entity = eventDAO.get(id);
		return entity;
	}

	@Override
	public void saveEvent(Long eventId) {
		// 查询实时事件信息
		EventReal real = eventRealDAO.get(eventId);

		EventManualReal manualReal = eventManualRealDAO.get(eventId);
		if (null == real || null == manualReal) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"param evetId " + eventId + " invalid");
		}

		// 保存到历史表
		Event event = new Event();
		event.setAlarmContent(real.getAlarmContent());
		event.setConfirmFlag(real.getConfirmFlag());
		event.setConfirmNote(real.getConfirmNote());
		event.setConfirmTime(real.getConfirmTime());
		event.setConfirmUserName(real.getConfirmUserName());
		event.setEventLevel(real.getEventLevel());
		event.setId(eventId);
		event.setLocation(real.getLocation());
		event.setName(real.getName());
		event.setOrganId(real.getOrganId());
		event.setRecoverTime(System.currentTimeMillis());
		event.setResourceFlag(real.getResourceFlag());
		event.setSourceName(real.getSourceName());
		event.setSubType(real.getSubType());
		event.setType(real.getType());
		event.setDetailType(real.getDetailType());
		eventDAO.save(event);

		EventManual manual = new EventManual();
		manual.setAdministration(manualReal.getAdministration());
		manual.setBeginStake(manualReal.getBeginStake());
		manual.setCrowdMeter(manualReal.getCrowdMeter());
		manual.setDeathNumber(manualReal.getDeathNumber());
		manual.setDelayCarNumber(manualReal.getDelayCarNumber());
		manual.setDelayHumanNumber(manualReal.getDelayHumanNumber());
		manual.setDescription(manualReal.getDescription());
		manual.setEndStake(manualReal.getEndStake());
		manual.setEstimatesRecoverTime(manualReal.getEstimatesRecoverTime());
		manual.setHurtNumber(manualReal.getHurtNumber());
		manual.setId(eventId);
		manual.setImpactProvince(manualReal.getImpactProvince());
		manual.setIsFire(manualReal.getIsFire());
		manual.setLaneNumber(manualReal.getLaneNumber());
		manual.setLatitude(manualReal.getLatitude());
		manual.setLongitude(manualReal.getLongitude());
		manual.setLossAmount(manualReal.getLossAmount());
		manual.setManagerUnit(manualReal.getManagerUnit());
		manual.setNavigation(manualReal.getNavigation());
		manual.setNote(manualReal.getNote());
		manual.setPhone(manualReal.getPhone());
		manual.setRoadCode(manualReal.getRoadCode());
		manual.setRoadName(manualReal.getRoadName());
		manual.setRoadType(manualReal.getRoadType());
		manual.setDestroyCarNumber(manualReal.getDestroyCarNumber());
		eventManualDAO.save(manual);

		// 删除实时事件
		eventRealDAO.delete(real);
		// 删除实时事件子表
		eventManualRealDAO.delete(manualReal);
	}
}
