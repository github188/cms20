package com.scsvision.cms.em.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scsvision.cms.constant.Header;
import com.scsvision.cms.constant.TypeDefinition;
import com.scsvision.cms.em.dto.AlarmDeviceDTO;
import com.scsvision.cms.em.dto.DeviceAlarmDTO;
import com.scsvision.cms.em.dto.DeviceAlarmDTO.Alarm.Image;
import com.scsvision.cms.em.dto.EventAndAlarmCountDTO;
import com.scsvision.cms.em.dto.EventRealDTO;
import com.scsvision.cms.em.dto.ListEventOrganInfoDTO;
import com.scsvision.cms.em.dto.RealEventDTO;
import com.scsvision.cms.em.dto.SimpleEventDTO;
import com.scsvision.cms.em.dto.SimpleEventDTO.SimpleEvent;
import com.scsvision.cms.em.dto.TunnelBrightListDTO;
import com.scsvision.cms.em.manager.EventDeviceManager;
import com.scsvision.cms.em.manager.EventManager;
import com.scsvision.cms.em.manager.EventManualManager;
import com.scsvision.cms.em.manager.EventRealManager;
import com.scsvision.cms.em.manager.ResourceManager;
import com.scsvision.cms.em.service.vo.ResourceVO;
import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.cms.response.BaseDTO;
import com.scsvision.cms.time.util.TimeUtil;
import com.scsvision.cms.util.annotation.Logon;
import com.scsvision.cms.util.interceptor.SessionCheckInterceptor;
import com.scsvision.cms.util.request.RequestReader;
import com.scsvision.cms.util.request.SimpleRequestReader;
import com.scsvision.cms.util.xml.XmlUtil;
import com.scsvision.database.entity.DicManagement;
import com.scsvision.database.entity.Event;
import com.scsvision.database.entity.EventDevice;
import com.scsvision.database.entity.EventDeviceReal;
import com.scsvision.database.entity.EventManualReal;
import com.scsvision.database.entity.EventReal;
import com.scsvision.database.entity.EventResource;
import com.scsvision.database.entity.Organ;
import com.scsvision.database.entity.SvDevice;
import com.scsvision.database.entity.TmDevice;
import com.scsvision.database.entity.User;
import com.scsvision.database.entity.VirtualOrgan;
import com.scsvision.database.manager.DicManagementManager;
import com.scsvision.database.manager.EventResourceManager;
import com.scsvision.database.manager.OrganManager;
import com.scsvision.database.manager.ResourceImageManager;
import com.scsvision.database.manager.ServerManager;
import com.scsvision.database.manager.StandardNumberManager;
import com.scsvision.database.manager.TmDeviceManager;
import com.scsvision.database.manager.UserManager;
import com.scsvsion.cms.em.comparator.OrganComparator;

@Stateless
@Interceptors(SessionCheckInterceptor.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EventController {

	private final Logger logger = LoggerFactory
			.getLogger(EventController.class);

	@EJB(beanName = "EventDeviceManagerImpl")
	private EventDeviceManager eventDeviceManager;
	@EJB(beanName = "EventManualManagerImpl")
	private EventManualManager eventManualManager;
	@EJB(beanName = "EventManagerImpl")
	private EventManager eventManager;
	@EJB(beanName = "EventRealManagerImpl")
	private EventRealManager eventRealManager;
	@EJB(beanName = "UserManagerImpl")
	private UserManager userManager;
	@EJB(beanName = "OrganManagerImpl")
	private OrganManager organManager;
	@EJB(beanName = "ResourceImageManagerImpl")
	private ResourceImageManager resourceImageManager;
	@EJB(beanName = "EventResourceManagerImpl")
	private EventResourceManager eventResourceManager;
	@EJB(beanName = "ServerManagerImpl")
	private ServerManager serverManager;
	@EJB(beanName = "ResourceManagerImpl")
	private ResourceManager resourceManager;
	@EJB(beanName = "StandardNumberManagerImpl")
	private StandardNumberManager standardNumberManager;
	@EJB(beanName = "TmDeviceManagerImpl")
	private TmDeviceManager tmDeviceManager;
	@EJB(beanName = "DicManagementManagerImpl")
	private DicManagementManager dicManagementManager;

	@Logon
	public Object listEventAlarmRealJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long vorganId = reader.getLong("organId", true);
		Long userId = reader.getUserId();
		Long beginTime = reader.getLong("beginTime", true);
		Long endTime = reader.getLong("endTime", true);
		Integer alarmLevel = reader.getInteger("alarmLevel", true);
		String confirmUserName = reader.getString("confirmUserName", true);
		Integer subType = reader.getInteger("subType", false);
		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 20;
		}
		// 默认查一个月
		if (null == beginTime) {
			beginTime = Long
					.valueOf(System.currentTimeMillis() - 30 * 24 * 3600000l);
		}
		if (null == endTime) {
			endTime = Long.valueOf(System.currentTimeMillis());
		}
		// 待查询的虚拟机构
		List<VirtualOrgan> organs = null;
		// 如果路段不为空,基于路段查询
		if (null != vorganId) {
			organs = organManager.listRealChildOrgan(vorganId);
		}
		// 基于用户组查询
		else {
			// 获取用户组
			User user = userManager.getUser(userId);
			// 获取用户组下的所有真实机构
			organs = organManager.listUgRealOrgan(user.getUserGroupId());
		}
		// 转换后的真实机构ID列表
		List<Long> organIds = new LinkedList<Long>();
		for (VirtualOrgan vo : organs) {
			if (vo.getDeviceId() != null) {
				organIds.add(vo.getDeviceId());
			}
		}

		Integer count = eventManager.countAlarmReal(TypeDefinition.EVENT_ALARM,
				subType, alarmLevel, beginTime, endTime, confirmUserName,
				organIds, null);

		List<EventReal> events = eventManager.listEventAlarmReal(
				TypeDefinition.EVENT_ALARM, subType, alarmLevel, beginTime,
				endTime, confirmUserName, start, limit, organIds, null);
		// 以真实机构ID为key， 虚拟机构对象为value的映射
		Map<Long, VirtualOrgan> map = new HashMap<Long, VirtualOrgan>();
		for (VirtualOrgan vo : organs) {
			map.put(vo.getDeviceId(), vo);
		}

		// 返回
		DeviceAlarmDTO dto = new DeviceAlarmDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMessage(count + "");
		dto.setMethod(request.getHeader(Header.METHOD));
		List<DeviceAlarmDTO.Alarm> list = new LinkedList<DeviceAlarmDTO.Alarm>();
		for (EventReal e : events) {
			DeviceAlarmDTO.Alarm vo = dto.new Alarm();
			vo.setId(e.getId());
			vo.setAlarmContent(e.getAlarmContent() != null ? e
					.getAlarmContent() : "");
			vo.setConfirmFlag(e.getConfirmFlag());
			vo.setCreateTime(e.getCreateTime());
			vo.setConfirmUserName(e.getConfirmUserName() != null ? e
					.getConfirmUserName() : "");
			vo.setConfirmNote(e.getConfirmNote() != null ? e.getConfirmNote()
					: "");
			vo.setName(e.getName() != null ? e.getName() : "");
			vo.setAlarmLevel(e.getEventLevel());
			vo.setOrganName(map.get(e.getOrganId()).getName());
			list.add(vo);
		}
		dto.setAlarmResponse(list);
		return dto;
	}

	@Logon
	public Object listThresholdAlarmRealJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String resourceName = reader.getString("deviceName", true);
		Long vorganId = reader.getLong("organId", true);
		Long userId = reader.getUserId();
		Long beginTime = reader.getLong("beginTime", true);
		Long endTime = reader.getLong("endTime", true);
		Integer alarmLevel = reader.getInteger("alarmLevel", true);
		String confirmUserName = reader.getString("confirmUserName", true);
		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 20;
		}
		// 默认查一个月
		if (null == beginTime) {
			beginTime = Long
					.valueOf(System.currentTimeMillis() - 30 * 24 * 3600000l);
		}
		if (null == endTime) {
			endTime = Long.valueOf(System.currentTimeMillis());
		}
		// 待查询的虚拟机构
		List<VirtualOrgan> organs = null;
		// 如果路段不为空,基于路段查询
		if (null != vorganId) {
			organs = organManager.listRealChildOrgan(vorganId);
		}
		// 基于用户组查询
		else {
			// 获取用户组
			User user = userManager.getUser(userId);
			// 获取用户组下的所有真实机构
			organs = organManager.listUgRealOrgan(user.getUserGroupId());
		}
		// 转换后的真实机构ID列表
		List<Long> organIds = new LinkedList<Long>();
		for (VirtualOrgan vo : organs) {
			if (vo.getDeviceId() != null) {
				organIds.add(vo.getDeviceId());
			}
		}

		Integer count = eventManager.countAlarmReal(TypeDefinition.EVENT_ALARM,
				TypeDefinition.EVENT_TYPE_VA, alarmLevel, beginTime, endTime,
				confirmUserName, organIds, resourceName);

		List<EventReal> events = eventManager.listEventAlarmReal(
				TypeDefinition.EVENT_ALARM, TypeDefinition.EVENT_TYPE_VA,
				alarmLevel, beginTime, endTime, confirmUserName, start, limit,
				organIds, resourceName);
		// 以真实机构ID为key， 虚拟机构对象为value的映射
		Map<Long, VirtualOrgan> omap = new HashMap<Long, VirtualOrgan>();
		for (VirtualOrgan vo : organs) {
			omap.put(vo.getDeviceId(), vo);
		}

		// 根据event_real的ids查询event_device_real
		List<Long> ids = new ArrayList<Long>();
		for (EventReal real : events) {
			ids.add(real.getId());
		}
		Map<Long, EventDeviceReal> map = eventManager
				.listEeventDeviceRealByIds(ids);

		DeviceAlarmDTO dto = new DeviceAlarmDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMessage(count + "");
		dto.setMethod(request.getHeader(Header.METHOD));
		List<DeviceAlarmDTO.Alarm> list = new LinkedList<DeviceAlarmDTO.Alarm>();
		for (EventReal e : events) {
			DeviceAlarmDTO.Alarm vo = dto.new Alarm();
			vo.setId(e.getId());
			vo.setAlarmContent(e.getAlarmContent() != null ? e
					.getAlarmContent() : "");
			vo.setConfirmFlag(e.getConfirmFlag());
			vo.setCreateTime(e.getCreateTime());
			vo.setConfirmUserName(e.getConfirmUserName() != null ? e
					.getConfirmUserName() : "");
			vo.setConfirmNote(e.getConfirmNote() != null ? e.getConfirmNote()
					: "");
			vo.setName(e.getName() != null ? e.getName() : "");
			vo.setAlarmLevel(e.getEventLevel());
			vo.setOrganName(omap.get(e.getOrganId()).getName());
			vo.setDeviceType(map.get(e.getId()).getDeviceType());
			vo.setStakeNumber(map.get(e.getId()).getStakeNumber());
			vo.setThreshold(map.get(e.getId()).getThreshold());
			list.add(vo);
		}
		dto.setAlarmResponse(list);
		return dto;
	}

	@Logon
	public Object countEventAndAlarmJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long userId = reader.getUserId();

		// 真实机构ID列表
		List<Long> organIds = new LinkedList<Long>();

		// 查询虚拟子机构
		List<VirtualOrgan> organs = null;
		User user = userManager.getUser(userId);
		organs = organManager.listUgAllOrgan(user.getUserGroupId());
		if (organs.size() == 0) {
			throw new BusinessException(ErrorCode.DIRTY_DATA_FOUND,
					"Organ has not contain any real Organ !");
		}
		for (VirtualOrgan vo : organs) {
			if (vo.getDeviceId() != null) {
				organIds.add(vo.getDeviceId());
			}
		}

		List<Integer> counts = eventRealManager.countEventAndAlarm(organIds);
		EventAndAlarmCountDTO dto = new EventAndAlarmCountDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMessage("");
		dto.setMethod(request.getHeader(Header.METHOD));
		EventAndAlarmCountDTO.QueryCount count = dto.new QueryCount();
		count.setTraffic(counts.get(0));
		count.setTerrible(counts.get(1));
		count.setThresHoldAlarm(counts.get(2));
		count.setOffline(counts.get(3));
		count.setMechanicalFault(counts.get(4));
		dto.setQueryCount(count);
		return dto;
	}

	public Object saveSimpleEventJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long organId = reader.getLong("organId", false);
		String beginStake = reader.getString("beginStake", true);
		String endStake = reader.getString("endStake", true);
		String sourceName = reader.getString("sourceName", true);
		String phone = reader.getString("phone", true);
		Integer crowdMeter = reader.getInteger("crowdMeter", true);
		String description = reader.getString("description", true);
		Integer navigation = reader.getInteger("navigation", true);
		Long createTime = reader.getLong("createTime", true);

		EventReal eventReal = new EventReal();
		eventReal.setConfirmFlag(TypeDefinition.CONFIRM_FLAG_YES);
		eventReal.setConfirmUserName(sourceName);
		eventReal.setEventLevel(TypeDefinition.COMMON_EVENT);
		eventReal.setOrganId(organId);
		eventReal.setType(TypeDefinition.EVENT_MANUAL);
		eventReal.setSourceName(sourceName);
		eventReal.setSubType(TypeDefinition.EVENT_TYPE_TA);
		eventReal.setResourceFlag(TypeDefinition.EVENT_RESOURCE_FLAG_FALSE);
		if (null == createTime) {
			eventReal.setCreateTime(System.currentTimeMillis());
		} else {
			eventReal.setCreateTime(createTime);
		}

		Long eventRealId = eventRealManager.saveEventReal(eventReal);

		EventManualReal eventManualReal = new EventManualReal();
		eventManualReal.setId(eventRealId);
		eventManualReal.setNavigation(navigation);
		eventManualReal.setBeginStake(beginStake);
		eventManualReal.setEndStake(endStake);
		eventManualReal.setDescription(description);
		eventManualReal.setCrowdMeter(crowdMeter);
		eventManualReal.setPhone(phone);

		eventManualManager.createEventManualReal(eventManualReal);

		BaseDTO dto = new BaseDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMethod(request.getHeader(Header.METHOD));
		dto.setMessage(eventRealId + "");
		return dto;
	}

	public Object updateSimpleEventJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long id = reader.getLong("id", false);
		Long organId = reader.getLong("organId", true);
		String beginStake = reader.getString("beginStake", true);
		String endStake = reader.getString("endStake", true);
		String sourceName = reader.getString("sourceName", true);
		String phone = reader.getString("phone", true);
		String description = reader.getString("description", true);
		Integer crowdMeter = reader.getInteger("crowdMeter", true);
		Integer navigation = reader.getInteger("navigation", true);
		Long createTime = reader.getLong("createTime", true);

		EventReal eventReal = eventRealManager.getEventRealDetail(id);

		if (null != organId) {
			eventReal.setOrganId(organId);
		}
		if (StringUtils.isNotBlank(sourceName)) {
			eventReal.setSourceName(sourceName);
		}
		if (null != createTime) {
			eventReal.setCreateTime(createTime);
		}

		eventRealManager.updateEventReal(eventReal);

		EventManualReal eventManualReal = eventManualManager
				.getEventManualReal(id);
		if (StringUtils.isNotBlank(beginStake)) {
			eventManualReal.setBeginStake(beginStake);
		}
		if (StringUtils.isNotBlank(endStake)) {
			eventManualReal.setEndStake(endStake);
		}
		if (null != navigation) {
			eventManualReal.setNavigation(navigation);
		}
		if (StringUtils.isNotBlank(description)) {
			eventManualReal.setDescription(description);
		}
		if (StringUtils.isNotBlank(phone)) {
			eventManualReal.setPhone(phone);
		}
		if (null != crowdMeter) {
			eventManualReal.setCrowdMeter(crowdMeter);
		}

		eventManualManager.updateEventManualReal(eventManualReal);

		BaseDTO dto = new BaseDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}

	@Logon
	public Object listSimpleEventJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Integer start = reader.getInteger("start", true);
		Long organId = reader.getLong("organId", true);
		String confirmUserName = reader.getString("confirmUserName", true);
		Short confirmFlag = reader.getShort("confirmFlag", true);
		Long beginTime = reader.getLong("beginTime", true);
		Long endTime = reader.getLong("endTime", true);

		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 20;
		}

		// 默认查一个月
		if (null == beginTime) {
			beginTime = Long
					.valueOf(System.currentTimeMillis() - 30 * 24 * 3600000l);
		}

		if (null == endTime) {
			endTime = Long.valueOf(System.currentTimeMillis());
		}

		// 真实机构ID列表
		List<Long> organIds = new LinkedList<Long>();
		if (organId != null) {
			organIds.add(organId);
		}

		int count = eventRealManager.countEventReal(null,
				TypeDefinition.EVENT_MANUAL, TypeDefinition.EVENT_TYPE_TA, "",
				null, start, limit, beginTime, endTime, organIds,
				confirmUserName, confirmFlag, TypeDefinition.COMMON_EVENT);
		List<EventReal> events = eventRealManager.listEventRealByType(null,
				TypeDefinition.EVENT_MANUAL, TypeDefinition.EVENT_TYPE_TA, "",
				null, start, limit, beginTime, endTime, organIds,
				confirmUserName, confirmFlag, TypeDefinition.COMMON_EVENT);
		// 事件ids
		List<Long> eventIds = new LinkedList<Long>();
		for (EventReal e : events) {
			eventIds.add(e.getId());
		}

		// 事件手动填报子表
		Map<Long, EventManualReal> mmap = eventManualManager
				.mapEventReal(eventIds);

		SimpleEventDTO dto = new SimpleEventDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMessage(count + "");
		dto.setMethod(request.getHeader(Header.METHOD));
		List<SimpleEvent> list = new ArrayList<SimpleEventDTO.SimpleEvent>();

		for (EventReal eventReal : events) {
			SimpleEvent event = dto.new SimpleEvent();
			event.setId(eventReal.getId());
			event.setOrganId(event.getOrganId());
			event.setOrganName(organManager
					.getOrganById(eventReal.getOrganId()).getName());

			event.setCreateTime(TimeUtil.getStringTime(
					eventReal.getCreateTime(), 2));
			event.setSourceName(eventReal.getSourceName() != null ? eventReal
					.getSourceName() : "");
			event.setSubType(eventReal.getSubType() != null ? eventReal
					.getSubType().toString() : "");
			// 子表相关属性赋值
			if (mmap.get(eventReal.getId()) != null) {
				event.setNavigation(mmap.get(eventReal.getId()).getNavigation() != null ? mmap
						.get(eventReal.getId()).getNavigation().toString()
						: "");
				event.setCrowdMeter(mmap.get(eventReal.getId()).getCrowdMeter() != null ? mmap
						.get(eventReal.getId()).getCrowdMeter().toString()
						: "");
				event.setBeginStake(mmap.get(eventReal.getId()).getBeginStake());
				event.setEndStake(mmap.get(eventReal.getId()).getEndStake());
				event.setDescription(mmap.get(eventReal.getId())
						.getDescription());
			} else {
				event.setNavigation("");
			}

			event.setPhone(mmap.get(eventReal.getId()) != null ? mmap.get(
					eventReal.getId()).getPhone() : "");
			list.add(event);
		}
		dto.setSimpleEvents(list);

		return dto;

	}

	public Object saveEventRealJson(HttpServletRequest request)
			throws UnsupportedEncodingException {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Integer subType = reader.getInteger("subType", false);
		Long organId = reader.getLong("organId", false);
		String roadCode = reader.getString("roadCode", false);
		Integer roadType = reader.getInteger("roadType", true);
		String location = reader.getString("location", true);
		String beginStake = reader.getString("beginStake", true);
		String endStake = reader.getString("endStake", true);
		Integer navigation = reader.getInteger("navigation", true);
		// 行政区划
		String administration = reader.getString("administration", true);
		String managerUnit = reader.getString("managerUnit", true);
		String sourceName = reader.getString("sourceName", true);
		String phone = reader.getString("phone", true);
		Short laneNumber = reader.getShort("laneNumber", true);
		Long createTime = reader.getLong("createTime", true);
		Long estimatesRecoverTime = reader
				.getLong("estimatesRecoverTime", true);
		String impactProvince = reader.getString("impactProvince", true);
		Integer hurtNumber = reader.getInteger("hurtNumber", true);
		Integer deathNumber = reader.getInteger("deathNumber", true);
		Integer delayHumanNumber = reader.getInteger("delayHumanNumber", true);
		Integer crowdMeter = reader.getInteger("crowdMeter", true);
		Integer destroyCarNumber = reader.getInteger("destroyCarNumber", true);
		Integer delayCarNumber = reader.getInteger("delayCarNumber", true);
		String description = reader.getString("description", true);
		List<String> detailType = reader.getArray("detailType", false);

		String imageIds = reader.getString("imageIds", true);

		String detailTypes = "";
		if (detailType != null && detailType.size() > 0) {
			for (String str : detailType) {
				if (detailType.get(0).equals(str)) {
					str = URLDecoder.decode(str, "utf-8");
					detailTypes = str;
				} else {
					str = URLDecoder.decode(str, "utf-8");
					detailTypes += "," + str;
				}
			}
		}

		EventReal eventReal = new EventReal();
		eventReal.setCreateTime(createTime == null ? System.currentTimeMillis()
				: createTime);
		eventReal.setLocation(location);
		eventReal.setOrganId(organId);
		eventReal.setSourceName(sourceName);
		eventReal.setSubType(subType);
		eventReal.setConfirmUserName(sourceName);
		eventReal.setConfirmFlag(TypeDefinition.CONFIRM_FLAG_YES);
		eventReal.setConfirmTime(createTime);
		eventReal.setConfirmNote(null);
		eventReal.setType(TypeDefinition.EVENT_MANUAL);
		eventReal.setEventLevel(TypeDefinition.TERRIBLE_EVENT);
		if (StringUtils.isNotBlank(imageIds)) {
			eventReal.setResourceFlag(TypeDefinition.EVENT_RESOURCE_FLAG_TRUE);
		} else {
			eventReal.setResourceFlag(TypeDefinition.EVENT_RESOURCE_FLAG_FALSE);
		}
		eventReal.setDetailType(detailTypes);
		Long eventRealId = eventRealManager.saveEventReal(eventReal);

		EventManualReal eventManualReal = new EventManualReal();

		eventManualReal.setBeginStake(beginStake);
		eventManualReal.setAdministration(administration);
		eventManualReal.setEndStake(endStake);
		eventManualReal.setEstimatesRecoverTime(estimatesRecoverTime);
		eventManualReal.setHurtNumber(hurtNumber);
		eventManualReal.setDeathNumber(deathNumber);
		eventManualReal.setDelayHumanNumber(delayHumanNumber);
		eventManualReal.setCrowdMeter(crowdMeter);
		eventManualReal.setDelayCarNumber(delayCarNumber);
		eventManualReal.setDestroyCarNumber(destroyCarNumber);
		eventManualReal.setDescription(description);
		eventManualReal.setImpactProvince(impactProvince);
		eventManualReal.setRoadType(roadType);
		eventManualReal.setAdministration(administration);
		eventManualReal.setRoadCode(roadCode);
		eventManualReal.setPhone(phone);
		eventManualReal.setNavigation(navigation);
		eventManualReal.setManagerUnit(managerUnit);
		eventManualReal.setLaneNumber(laneNumber);
		eventManualReal.setId(eventRealId);
		eventManualManager.createEventManualReal(eventManualReal);

		if (eventReal.getResourceFlag().equals(
				TypeDefinition.EVENT_RESOURCE_FLAG_TRUE)) {
			String[] images = imageIds.split(",");
			for (String id : images) {
				resourceManager.saveResource(eventRealId, Long.valueOf(id));
			}
		}

		BaseDTO dto = new BaseDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMethod(request.getHeader(Header.METHOD));
		dto.setMessage(eventRealId + "");

		return dto;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Object updateEventRealJson(HttpServletRequest request)
			throws UnsupportedEncodingException {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long id = reader.getLong("id", false);
		Integer subType = reader.getInteger("subType", true);
		Long organId = reader.getLong("organId", true);
		String roadCode = reader.getString("roadCode", true);
		Integer roadType = reader.getInteger("roadType", true);
		String location = reader.getString("location", true);
		String beginStake = reader.getString("beginStake", true);
		String endStake = reader.getString("endStake", true);
		Integer navigation = reader.getInteger("navigation", true);
		// 行政区划
		String administration = reader.getString("administration", true);
		String managerUnit = reader.getString("managerUnit", true);
		String sourceName = reader.getString("sourceName", true);
		String phone = reader.getString("phone", true);
		Short laneNumber = reader.getShort("laneNumber", true);
		Long createTime = reader.getLong("createTime", true);
		Long estimatesRecoverTime = reader
				.getLong("estimatesRecoverTime", true);
		String impactProvince = reader.getString("impactProvince", true);
		Integer hurtNumber = reader.getInteger("hurtNumber", true);
		Integer deathNumber = reader.getInteger("deathNumber", true);
		Integer delayHumanNumber = reader.getInteger("delayHumanNumber", true);
		Integer crowdMeter = reader.getInteger("crowdMeter", true);
		Integer destroyCarNumber = reader.getInteger("destroyCarNumber", true);
		Integer delayCarNumber = reader.getInteger("delayCarNumber", true);
		String description = reader.getString("description", true);
		List<String> detailType = reader.getArray("detailType", false);

		String imageIds = reader.getString("imageIds", true);

		String detailTypes = "";
		if (detailType != null && detailType.size() > 0) {
			for (String str : detailType) {
				if (detailType.get(0).equals(str)) {
					str = URLDecoder.decode(str, "utf-8");
					detailTypes = str;
				} else {
					str = URLDecoder.decode(str, "utf-8");
					detailTypes += "," + str;
				}
			}
		}

		EventReal eventReal = eventRealManager.getEventRealDetail(id);
		if (null == eventReal) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
					"not found entity by [" + id + "]");
		}
		if (null != subType) {
			eventReal.setSubType(subType);
		}
		if (null != organId) {
			eventReal.setOrganId(organId);
		}
		if (null != createTime) {
			eventReal.setCreateTime(createTime);
		}
		if (StringUtils.isNotBlank(location)) {
			eventReal.setLocation(location);
		}
		if (StringUtils.isNotBlank(sourceName)) {
			eventReal.setSourceName(sourceName);
			eventReal.setConfirmUserName(sourceName);
		}
		if (StringUtils.isNotBlank(imageIds)) {
			eventReal.setResourceFlag(TypeDefinition.EVENT_RESOURCE_FLAG_TRUE);
		} else {
			eventReal.setResourceFlag(TypeDefinition.EVENT_RESOURCE_FLAG_FALSE);
		}
		if (StringUtils.isNotBlank(detailTypes)) {
			eventReal.setDetailType(detailTypes);
		}
		eventRealManager.updateEventReal(eventReal);

		EventManualReal eventManualReal = eventManualManager
				.getEventManualReal(id);
		if (null == eventManualReal) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
					"not found child_Entity by [" + id + "]");
		}

		if (null != estimatesRecoverTime) {
			eventManualReal.setEstimatesRecoverTime(estimatesRecoverTime);
		}
		if (null != hurtNumber) {
			eventManualReal.setHurtNumber(hurtNumber);
		}
		if (null != deathNumber) {
			eventManualReal.setDeathNumber(deathNumber);
		}
		if (null != delayHumanNumber) {
			eventManualReal.setDelayHumanNumber(delayHumanNumber);
		}
		if (null != crowdMeter) {
			eventManualReal.setCrowdMeter(crowdMeter);
		}
		if (null != delayCarNumber) {
			eventManualReal.setDelayCarNumber(delayCarNumber);
		}
		if (null != destroyCarNumber) {
			eventManualReal.setDestroyCarNumber(destroyCarNumber);
		}
		if (null != roadType) {
			eventManualReal.setRoadType(roadType);
		}
		if (null != navigation) {
			eventManualReal.setNavigation(navigation);
		}
		if (null != laneNumber) {
			eventManualReal.setLaneNumber(laneNumber);
		}

		if (StringUtils.isNotBlank(beginStake)) {
			eventManualReal.setBeginStake(beginStake);
		}
		if (StringUtils.isNotBlank(administration)) {
			eventManualReal.setAdministration(administration);
		}
		if (StringUtils.isNotBlank(endStake)) {
			eventManualReal.setEndStake(endStake);
		}
		if (StringUtils.isNotBlank(description)) {
			eventManualReal.setDescription(description);
		}
		if (StringUtils.isNotBlank(impactProvince)) {
			eventManualReal.setImpactProvince(impactProvince);
		}
		if (StringUtils.isNotBlank(roadCode)) {
			eventManualReal.setRoadCode(roadCode);
		}
		if (StringUtils.isNotBlank(phone)) {
			eventManualReal.setPhone(phone);
		}
		if (StringUtils.isNotBlank(managerUnit)) {
			eventManualReal.setManagerUnit(managerUnit);
		}

		eventManualManager.updateEventManualReal(eventManualReal);

		resourceManager.deleteResourceByEventRealId(eventReal.getId());

		if (eventReal.getResourceFlag().equals(
				TypeDefinition.EVENT_RESOURCE_FLAG_TRUE)) {
			String[] images = imageIds.split(",");
			for (String imId : images) {
				resourceManager.saveResource(eventReal.getId(),
						Long.valueOf(imId));
			}
		}

		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;

	}

	public Object getEventRealDetailJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long id = reader.getLong("id", false);
		EventReal eventReal = eventRealManager.getEventRealDetail(id);
		if (null != eventReal) {
			EventManualReal eventManualReal = eventManualManager
					.getEventManualReal(id);
			EventRealDTO dto = new EventRealDTO();
			dto.setMethod(request.getHeader(Header.METHOD));
			EventRealDTO.Event event = dto.new Event();
			// 基本事件录入
			event.setId(eventReal.getId());
			event.setLocation(eventReal.getLocation() != null ? eventReal
					.getLocation() : "");
			event.setSubType(eventReal.getSubType());
			event.setCreateTime(TimeUtil.getStringTime(
					eventReal.getCreateTime(), 2));
			event.setOrganId(eventReal.getOrganId());
			event.setOrganName(organManager
					.getOrganById(eventReal.getOrganId()).getName());
			event.setSourceName(eventReal.getSourceName() != null ? eventReal
					.getSourceName() : "");

			// 手动事件录入
			event.setRoadCode(eventManualReal != null ? eventManualReal
					.getRoadCode() : "");
			event.setBeginStake(eventManualReal != null ? eventManualReal
					.getBeginStake() : "");
			event.setEndStake(eventManualReal != null ? eventManualReal
					.getBeginStake() : "");
			event.setImpactProvince(eventManualReal != null ? eventManualReal
					.getImpactProvince() : "");
			event.setDescription(eventManualReal != null ? eventManualReal
					.getDescription() : "");
			event.setManagerUnit(eventManualReal != null ? eventManualReal
					.getManagerUnit() : "");
			event.setPhone(eventManualReal != null ? eventManualReal.getPhone()
					: "");
			event.setAdministration(eventManualReal != null ? eventManualReal
					.getAdministration() : "");

			// 特殊属性字段进行赋值
			if (eventManualReal != null) {
				event.setDestoryCarNumber(eventManualReal.getDestroyCarNumber() != null ? eventManualReal
						.getDestroyCarNumber().toString() : "");
				event.setRoadType(eventManualReal.getRoadType() != null ? eventManualReal
						.getRoadType().toString() : "");
				event.setHurtNumber(eventManualReal.getHurtNumber() != null ? eventManualReal
						.getHurtNumber().toString() : "");
				event.setDelayHumanNumber(eventManualReal.getDelayHumanNumber() != null ? eventManualReal
						.getDelayHumanNumber().toString() : "");
				event.setDeathNumber(eventManualReal.getDeathNumber() != null ? eventManualReal
						.getDeathNumber().toString() : "");
				event.setDelayCarNumber(eventManualReal.getDelayCarNumber() != null ? eventManualReal
						.getDelayCarNumber().toString() : "");
				event.setCrowdMeter(eventManualReal.getCrowdMeter() != null ? eventManualReal
						.getCrowdMeter().toString() : "");
				event.setNavigation(eventManualReal.getNavigation() != null ? eventManualReal
						.getNavigation().toString() : "");

				event.setLaneNumber(eventManualReal.getLaneNumber() != null ? eventManualReal
						.getLaneNumber().toString() : "");
				event.setEstimatesRecoverTime(eventManualReal
						.getEstimatesRecoverTime() != null ? TimeUtil
						.getStringTime(
								eventManualReal.getEstimatesRecoverTime(), 2)
						: "");
			} else {

				event.setDestoryCarNumber("");
				event.setRoadType("");
				event.setDeathNumber("");
				event.setDelayCarNumber("");
				event.setCrowdMeter("");
				event.setNavigation("");

				event.setLaneNumber("");
				event.setEstimatesRecoverTime("");
			}
			// 获取事件子类型
			if (StringUtils.isNotBlank(eventReal.getDetailType())) {
				String[] dTypes = eventReal.getDetailType().split(",");
				List<String> dtypes = Arrays.asList(dTypes);
				event.setDetailTypes(dtypes);
			}
			// 获取资源图片
			if (eventReal.getResourceFlag().shortValue() == TypeDefinition.EVENT_RESOURCE_FLAG_TRUE) {
				List<EventResource> imageList = resourceManager
						.listEventRealResource(eventReal.getId());
				List<ResourceVO> images = new ArrayList<ResourceVO>();
				for (EventResource source : imageList) {
					ResourceVO vo = new ResourceVO();
					vo.setId(source.getResourceId());
					String address = resourceImageManager
							.findAddressById(source.getResourceId());
					vo.setAddress(address);
					images.add(vo);
				}
				event.setImage(images);
			}
			dto.setEvent(event);
			return dto;
		} else {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "该对象不存在！");
		}
	}

	public Object getDeviceAlarmDetailJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long id = reader.getLong("id", false);
		Event event = eventManager.getEvent(id);
		List<Long> ids = null;
		if (event.getResourceFlag().shortValue() == TypeDefinition.EVENT_RESOURCE_FLAG_TRUE) {
			ids = eventResourceManager.getResourceIdsByEventId(id);
		}
		EventDevice eventDevice = eventDeviceManager.getEventDevice(id);
		TmDevice tmDevice = tmDeviceManager.getTmDevice(eventDevice
				.getDeviceId());

		DeviceAlarmDTO dto = new DeviceAlarmDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		DeviceAlarmDTO.Alarm node = dto.new Alarm();
		node.setName(event.getName());
		node.setDeviceName(tmDevice.getName() != null ? tmDevice.getName() : "");
		node.setOrganName(eventDevice.getOrganName() != null ? eventDevice
				.getOrganName() : "");
		node.setAlarmLevel(event.getEventLevel());
		node.setConfirmFlag(event.getConfirmFlag());
		node.setCreateTime(event.getCreateTime());
		node.setLocation(event.getLocation() != null ? event.getLocation() : "");
		node.setRecoverTime(event.getRecoverTime());
		node.setConfirmTime(event.getConfirmTime());
		node.setSubType(event.getSubType());
		node.setCurrentValue(eventDevice.getCurrentValue() != null ? eventDevice
				.getCurrentValue() : "");
		node.setDeviceType(eventDevice.getDeviceType());
		node.setAlarmReason(event.getAlarmContent() != null ? event
				.getAlarmContent() : "");
		node.setConfirmUserName(event.getConfirmUserName() != null ? event
				.getConfirmUserName() : "");
		node.setStakeNumber(eventDevice.getStakeNumber() + "");
		node.setNavigation(eventDevice.getNavigation());
		List<Image> images = new LinkedList<Image>();
		if (null != ids) {
			Map<Long, ResourceVO> resourceMap = resourceManager
					.mapResourcedByIds(ids);
			for (Long resourceId : ids) {
				String url = resourceMap.get(resourceId).getAddress();
				Image img = node.new Image();
				img.setId(resourceId + "");
				img.setUrl(url);
				images.add(img);
			}
		}
		node.setImage(images);
		dto.setAlarm(node);
		return dto;
	}

	public void createAlarm(String message, String businessId) {
		RequestReader reader = new RequestReader(message);
		Element root = reader.getRoot();
		List<Element> alarms = root.elements();

		Map<Long, Organ> map = organManager.mapOrganById();

		for (Element alarm : alarms) {
			EventReal eventReal = new EventReal();
			EventDeviceReal eventDeviceReal = new EventDeviceReal();

			eventReal.setAlarmContent(alarm.attributeValue("AlarmContent"));
			eventReal.setBusinessId(businessId);
			eventReal.setCreateTime(XmlUtil.getLong(alarm, "AlarmTime"));
			eventReal.setConfirmFlag((short) 0);
			Integer eventLevel = XmlUtil.getInteger(alarm, "Level");
			if (null == eventLevel) {
				eventLevel = Integer.valueOf(1);
			}
			eventReal.setEventLevel(eventLevel);
			String detailType = null;
			Integer subType = XmlUtil.getInteger(alarm, "SubType");
			if (null == subType) {
				logger.error("BusinessId["
						+ businessId
						+ "] for Method[CreateAlarm] has a empty attribute in [SubType], see more details in AMQ logs !");
				continue;
			}
			if (TypeDefinition.EVENT_TYPE_DOL == subType.intValue() / 10) {
				detailType = subType % 10 + "";
				eventReal.setName("离线报警");
			} else if (TypeDefinition.EVENT_TYPE_VA == subType.intValue() / 1000) {
				eventReal.setName("阀值报警");
				detailType = subType.intValue() % 1000 + "";
			} else {
				// 待定
			}
			eventReal.setResourceFlag(TypeDefinition.EVENT_RESOURCE_FLAG_FALSE);
			eventReal.setType(TypeDefinition.EVENT_ALARM);
			eventReal.setSubType(subType / 10);
			eventReal.setDetailType(detailType);
			eventDeviceReal.setCurrentValue(alarm
					.attributeValue("CurrentValue"));
			eventDeviceReal.setThreshold(alarm.attributeValue("Threshold"));

			// 查询设备信息
			String sn = alarm.attributeValue("StandardNumber");
			Object obj = standardNumberManager.getByStandardNumber(sn);
			// 设置设备信息
			if (obj instanceof SvDevice) {
				SvDevice device = (SvDevice) obj;
				eventReal.setLocation(device.getLocation());
				eventReal.setOrganId(device.getOrganId());
				eventReal.setSourceName(device.getName());

				eventDeviceReal.setDeviceId(device.getId());
				eventDeviceReal.setDeviceType(Integer.valueOf(device.getType()
						+ device.getSubType()));
				eventDeviceReal.setNavigation(device.getNavigation());
				eventDeviceReal.setStakeNumber(device.getStakeNumber());
				Organ organ = map.get(device.getOrganId());
				if (null == organ) {
					logger.error("OrganId for SvDevice[" + device.getId()
							+ "] is a dirty data !");
					continue;
				}
				eventDeviceReal.setOrganName(organ.getName());
			} else if (obj instanceof TmDevice) {
				TmDevice device = (TmDevice) obj;
				eventReal.setLocation(device.getStakeNumber());
				eventReal.setOrganId(device.getOrganId());
				eventReal.setSourceName(device.getName());

				eventDeviceReal.setDeviceId(device.getId());
				eventDeviceReal.setDeviceType(Integer.valueOf(device.getType()
						+ device.getSubType()));
				eventDeviceReal.setNavigation(device.getNavigation());
				eventDeviceReal.setStakeNumber(device.getStakeNumber());
				Organ organ = map.get(device.getOrganId());
				if (null == organ) {
					logger.error("OrganId for TmDevice[" + device.getId()
							+ "] is a dirty data !");
					continue;
				}
				eventDeviceReal.setOrganName(organ.getName());
			} else {
				// 待实现
			}

			eventDeviceManager.createAlarm(eventReal, eventDeviceReal);
		}
	}

	@Logon
	public Object listTunnelBrightJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long organId = reader.getLong("organId", true);
		// 用户组资源列表
		List<VirtualOrgan> listOrgan = null;
		List<Long> deviceIds = new LinkedList<Long>();
		Long userId = reader.getUserId();
		// 父机构ID,如果为空则是用户组根机构
		VirtualOrgan parentOrgan = null;
		if (null == organId) {
			User user = userManager.getUser(userId);
			parentOrgan = organManager.getUgRootOrgan(user.getUserGroupId());
			organId = parentOrgan.getId();
		}
		listOrgan = organManager.listUgVirtualOrgan(organId);
		// 只关注桥梁和隧道
		Map<Long, VirtualOrgan> vorganMap = new HashMap<Long, VirtualOrgan>();
		for (VirtualOrgan vo : listOrgan) {
			if (vo.getType().intValue() == TypeDefinition.ORGAN_TUNNEL
					|| vo.getType().intValue() == TypeDefinition.ORGAN_BRIDGE) {
				deviceIds.add(vo.getDeviceId());
				vorganMap.put(vo.getDeviceId(), vo);
			}
		}
		// 查询机构Map
		Map<Long, Organ> organMap = null;
		if (deviceIds.size() > 0) {
			organMap = organManager.mapOrgan(deviceIds);
		}

		Map<Long, EventDeviceReal> eventMap = eventDeviceManager
				.edviceRealMap(null);
		TunnelBrightListDTO dto = new TunnelBrightListDTO();
		dto.setMethod("ListTunnelBright");
		List<TunnelBrightListDTO.TunnelBright> list = new LinkedList<TunnelBrightListDTO.TunnelBright>();
		if (null != organMap) {
			for (Entry<Long, Organ> o : organMap.entrySet()) {
				TunnelBrightListDTO.TunnelBright tunnelBright = dto.new TunnelBright();
				tunnelBright.setBeginStake(o.getValue().getBeginStake());
				tunnelBright.setEndStake(o.getValue().getEndStake());
				tunnelBright
						.setId(vorganMap.get(o.getKey()).getId().toString());
				tunnelBright.setLaneNumber(o.getValue().getLaneNumber()
						.toString());
				tunnelBright.setLength(o.getValue().getLength().toString());
				tunnelBright.setName(o.getValue().getName());
				Organ parOrgan = organManager.getOrganById(o.getValue()
						.getParentId());
				tunnelBright.setOrganName(parOrgan.getName());
				tunnelBright.setType(o.getValue().getType() + "");
				for (Entry<Long, EventDeviceReal> e : eventMap.entrySet()) {
					if (null != e.getValue().getDeviceId()
							&& o.getValue().getId().longValue() == e.getValue()
									.getDeviceId().longValue()) {
						tunnelBright.setAlarm(true);
						continue;
					} else {
						tunnelBright.setAlarm(false);
					}
				}
				list.add(tunnelBright);
			}
		}
		dto.setList(list);
		return dto;
	}

	public Object saveEventJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long eventId = reader.getLong("eventId", false);

		eventManager.saveEvent(eventId);

		BaseDTO dto = new BaseDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMessage("");
		dto.setMethod("SaveEvent");
		return dto;
	}

	@Logon
	public Object listDeviceAlarmRealJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Integer subType = reader.getInteger("subType", false);
		String detailType = reader.getString("detailType", true);
		Integer start = reader.getInteger("start", true);
		Long organId = reader.getLong("organId", true);
		Integer deviceType = reader.getInteger("deviceType", true);
		String sourceName = reader.getString("sourceName", true);
		String confirmUserName = reader.getString("confirmUserName", true);
		Short confirmFlag = reader.getShort("confirmFlag", true);
		Long beginTime = reader.getLong("beginTime", true);
		Long endTime = reader.getLong("endTime", true);
		Long userId = reader.getUserId();
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 20;
		}

		// 默认查一个月
		if (null == beginTime) {
			beginTime = Long
					.valueOf(System.currentTimeMillis() - 30 * 24 * 3600000l);
		}
		if (null == endTime) {
			endTime = Long.valueOf(System.currentTimeMillis());
		}
		// 真实机构ID列表
		List<Long> organIds = new LinkedList<Long>();

		if (null != organId) {
			organIds.add(organId);
		} else {
			// 查询虚拟子机构
			List<VirtualOrgan> organs = null;
			User user = userManager.getUser(userId);
			organs = organManager.listUgAllOrgan(user.getUserGroupId());
			if (organs.size() == 0) {
				throw new BusinessException(ErrorCode.DIRTY_DATA_FOUND,
						"Organ has not contain any real Organ !");
			}
			for (VirtualOrgan vo : organs) {
				if (vo.getDeviceId() != null) {
					organIds.add(vo.getDeviceId());
				}
			}
		}

		int count = eventRealManager.countEventReal(deviceType,
				TypeDefinition.EVENT_ALARM, subType, sourceName, detailType,
				start, limit, beginTime, endTime, organIds, confirmUserName,
				confirmFlag, null);
		List<EventReal> events = eventRealManager.listEventRealByType(
				deviceType, TypeDefinition.EVENT_ALARM, subType, sourceName,
				detailType, start, limit, beginTime, endTime, organIds,
				confirmUserName, confirmFlag, null);

		// 阀值报警子类型，关联查询子表
		Map<Long, EventDeviceReal> eventDeviceMap = null;

		List<Long> thresholdIds = new LinkedList<Long>();
		for (EventReal event : events) {
			thresholdIds.add(event.getId());
		}
		if (thresholdIds.size() > 0) {
			eventDeviceMap = eventManager
					.listEeventDeviceRealByIds(thresholdIds);
		}

		AlarmDeviceDTO dto = new AlarmDeviceDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMethod(request.getHeader(Header.METHOD));
		dto.setMessage(count + "");
		List<AlarmDeviceDTO.Alarm> alrAlarms = new ArrayList<AlarmDeviceDTO.Alarm>();
		for (EventReal event : events) {
			AlarmDeviceDTO.Alarm alarm = dto.new Alarm();
			alarm.setId(event.getId());
			alarm.setSubType(event.getType());
			alarm.setCreateTime(TimeUtil.getStringTime(event.getCreateTime(), 2));
			alarm.setAlarmLevel(event.getEventLevel());
			alarm.setLocation(event.getLocation() == null ? "" : event
					.getLocation());
			alarm.setAlarmReason(event.getAlarmContent() == null ? "" : event
					.getAlarmContent());
			alarm.setConfirmFlag(event.getConfirmFlag());
			alarm.setConfirmNote(event.getConfirmNote() == null ? "" : event
					.getConfirmNote());
			alarm.setConfirmUserName(event.getConfirmUserName() == null ? ""
					: event.getConfirmUserName());
			alarm.setDeviceName(event.getSourceName() == null ? "" : event
					.getSourceName());
			alarm.setOrganId(event.getOrganId() == null ? "" : event
					.getOrganId() + "");
			alarm.setOrganName(eventDeviceMap.get(event.getId()) != null ? eventDeviceMap
					.get(event.getId()).getOrganName() : "");
			alarm.setDeviceType(eventDeviceMap.get(event.getId()) != null ? eventDeviceMap
					.get(event.getId()).getDeviceType().toString()
					: "");
			alarm.setThreshold(eventDeviceMap.get(event.getId()) != null ? eventDeviceMap
					.get(event.getId()).getThreshold() : "");
			alarm.setCurrentValue(eventDeviceMap.get(event.getId()) != null ? eventDeviceMap
					.get(event.getId()).getCurrentValue() : "");
			alarm.setConfirmTime(event.getConfirmTime() != null ? TimeUtil
					.getStringTime(event.getConfirmTime(), 2) : "");

			alrAlarms.add(alarm);
		}
		dto.setAlarm(alrAlarms);

		return dto;
	}

	@Logon
	public Object ListMechanicalFaultRealJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String detailType = reader.getString("detailType", true);
		Integer deviceType = reader.getInteger("deviceType", true);
		Integer subType = reader.getInteger("subType", true);
		Integer start = reader.getInteger("start", true);
		Long organId = reader.getLong("organId", true);
		String sourceName = reader.getString("sourceName", true);
		String confirmUserName = reader.getString("confirmUserName", true);
		Short confirmFlag = reader.getShort("confirmFlag", true);
		Integer alarmLevel = reader.getInteger("alarmLevel", true);
		Long beginTime = reader.getLong("beginTime", true);
		Long endTime = reader.getLong("endTime", true);
		Long userId = reader.getUserId();
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 20;
		}

		// 默认查一个月
		if (null == beginTime) {
			beginTime = Long
					.valueOf(System.currentTimeMillis() - 30 * 24 * 3600000l);
		}
		if (null == endTime) {
			endTime = Long.valueOf(System.currentTimeMillis());
		}
		// 真实机构ID列表
		List<Long> organIds = new LinkedList<Long>();

		if (null != organId) {
			organIds.add(organId);
		} else {
			// 查询虚拟子机构
			List<VirtualOrgan> organs = null;
			User user = userManager.getUser(userId);
			organs = organManager.listUgAllOrgan(user.getUserGroupId());
			if (organs.size() == 0) {
				throw new BusinessException(ErrorCode.DIRTY_DATA_FOUND,
						"Organ has not contain any real Organ !");
			}
			for (VirtualOrgan vo : organs) {
				if (vo.getDeviceId() != null) {
					organIds.add(vo.getDeviceId());
				}
			}
		}

		int count = eventRealManager.countEventReal(deviceType,
				TypeDefinition.EVENT_ALARM, subType, sourceName, detailType,
				start, limit, beginTime, endTime, organIds, confirmUserName,
				confirmFlag, alarmLevel);
		List<EventReal> events = eventRealManager.listEventRealByType(
				deviceType, TypeDefinition.EVENT_ALARM, subType, sourceName,
				detailType, start, limit, beginTime, endTime, organIds,
				confirmUserName, confirmFlag, alarmLevel);

		// 阀值报警子类型，关联查询子表
		Map<Long, EventDeviceReal> eventDeviceMap = null;

		List<Long> thresholdIds = new LinkedList<Long>();
		for (EventReal event : events) {
			thresholdIds.add(event.getId());
		}
		if (thresholdIds.size() > 0) {
			eventDeviceMap = eventManager
					.listEeventDeviceRealByIds(thresholdIds);
		}

		AlarmDeviceDTO dto = new AlarmDeviceDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMethod(request.getHeader(Header.METHOD));
		dto.setMessage(count + "");
		List<AlarmDeviceDTO.Alarm> alrAlarms = new ArrayList<AlarmDeviceDTO.Alarm>();
		for (EventReal event : events) {
			AlarmDeviceDTO.Alarm alarm = dto.new Alarm();
			alarm.setId(event.getId());
			alarm.setSubType(event.getType());
			alarm.setCreateTime(TimeUtil.getStringTime(event.getCreateTime(), 2));
			alarm.setAlarmLevel(event.getEventLevel());
			alarm.setLocation(event.getLocation() != null ? event.getLocation()
					: "");
			alarm.setAlarmReason(event.getAlarmContent() != null ? event
					.getAlarmContent() : "");
			alarm.setConfirmFlag(event.getConfirmFlag());
			alarm.setConfirmNote(event.getConfirmNote() != null ? event
					.getConfirmNote() : "");
			alarm.setConfirmUserName(event.getConfirmUserName() != null ? event
					.getConfirmUserName() : "");
			alarm.setDeviceName(event.getSourceName() != null ? event
					.getSourceName() : "");
			alarm.setOrganName(eventDeviceMap.get(event.getId()) != null ? eventDeviceMap
					.get(event.getId()).getOrganName() : "");
			alarm.setDeviceType(eventDeviceMap.get(event.getId()) != null ? eventDeviceMap
					.get(event.getId()).getDeviceType().toString()
					: "");
			alarm.setThreshold(eventDeviceMap.get(event.getId()) != null ? eventDeviceMap
					.get(event.getId()).getThreshold() : "");
			alarm.setCurrentValue(eventDeviceMap.get(event.getId()) != null ? eventDeviceMap
					.get(event.getId()).getCurrentValue() : "");
			alarm.setConfirmTime(event.getConfirmTime() != null ? TimeUtil
					.getStringTime(event.getConfirmTime(), 2) : "");

			alrAlarms.add(alarm);
		}
		dto.setAlarm(alrAlarms);

		return dto;
	}

	@Logon
	public Object ListEventManualRealJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Integer subType = reader.getInteger("subType", true);
		Integer start = reader.getInteger("start", true);
		Long organId = reader.getLong("organId", true);
		String confirmUserName = reader.getString("confirmUserName", true);
		Long beginTime = reader.getLong("beginTime", true);
		Long endTime = reader.getLong("endTime", true);
		Long userId = reader.getUserId();
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 20;
		}
		// 默认查一个月
		if (null == beginTime) {
			beginTime = Long
					.valueOf(System.currentTimeMillis() - 30 * 24 * 3600000l);
		}
		if (null == endTime) {
			endTime = Long.valueOf(System.currentTimeMillis());
		}
		// 真实机构ID列表
		List<Long> organIds = new LinkedList<Long>();
		if (null != organId) {
			organIds.add(organId);
		} else {
			// 查询虚拟子机构
			List<VirtualOrgan> organs = null;
			User user = userManager.getUser(userId);
			organs = organManager.listUgAllOrgan(user.getUserGroupId());
			if (organs.size() == 0) {
				throw new BusinessException(ErrorCode.DIRTY_DATA_FOUND,
						"Organ has not contain any real Organ !");
			}
			for (VirtualOrgan vo : organs) {
				if (vo.getDeviceId() != null) {
					organIds.add(vo.getDeviceId());
				}
			}
		}

		int count = eventRealManager.countEventReal(null,
				TypeDefinition.EVENT_MANUAL, subType, "", null, start, limit,
				beginTime, endTime, organIds, confirmUserName, null,
				TypeDefinition.TERRIBLE_EVENT);
		List<EventReal> events = eventRealManager.listEventRealByType(null,
				TypeDefinition.EVENT_MANUAL, subType, "", null, start, limit,
				beginTime, endTime, organIds, confirmUserName, null,
				TypeDefinition.TERRIBLE_EVENT);
		// 事件ids
		List<Long> eventIds = new LinkedList<Long>();
		for (EventReal e : events) {
			eventIds.add(e.getId());
		}

		// 事件手动填报子表
		Map<Long, EventManualReal> mmap = eventManualManager
				.mapEventReal(eventIds);

		RealEventDTO dto = new RealEventDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMessage(count + "");
		dto.setMethod("ListEventManual");

		List<RealEventDTO.Event> list = new ArrayList<RealEventDTO.Event>();

		for (EventReal eventReal : events) {
			RealEventDTO.Event event = dto.new Event();
			// 基本事件录入
			event.setId(eventReal.getId());
			event.setLocation(eventReal.getLocation() != null ? eventReal
					.getLocation() : "");
			event.setSubType(eventReal.getSubType());
			event.setCreateTime(TimeUtil.getStringTime(
					eventReal.getCreateTime(), 2));
			event.setOrganId(eventReal.getOrganId());
			event.setSourceName(eventReal.getSourceName() != null ? eventReal
					.getSourceName() : "");
			event.setOrganName(organManager
					.getOrganById(eventReal.getOrganId()).getName());

			// 手动事件录入
			event.setRoadCode(mmap.get(eventReal.getId()) != null ? mmap.get(
					eventReal.getId()).getRoadCode() : "");
			event.setBeginStake(mmap.get(eventReal.getId()) != null ? mmap.get(
					eventReal.getId()).getBeginStake() : "");
			event.setEndStake(mmap.get(eventReal.getId()) != null ? mmap.get(
					eventReal.getId()).getBeginStake() : "");
			event.setImpactProvince(mmap.get(eventReal.getId()) != null ? mmap
					.get(eventReal.getId()).getImpactProvince() : "");
			event.setDescription(mmap.get(eventReal.getId()) != null ? mmap
					.get(eventReal.getId()).getDescription() : "");
			event.setManagerUnit(mmap.get(eventReal.getId()) != null ? mmap
					.get(eventReal.getId()).getManagerUnit() : "");
			event.setPhone(mmap.get(eventReal.getId()) != null ? mmap.get(
					eventReal.getId()).getPhone() : "");
			event.setAdministration(mmap.get(eventReal.getId()) != null ? mmap
					.get(eventReal.getId()).getAdministration() : "");

			// 特殊属性字段进行赋值
			if (mmap.get(eventReal.getId()) != null) {

				event.setDestoryCarNumber(mmap.get(eventReal.getId())
						.getDestroyCarNumber() != null ? mmap
						.get(eventReal.getId()).getDestroyCarNumber()
						.toString() : "");
				event.setRoadType(mmap.get(eventReal.getId()).getRoadType() != null ? mmap
						.get(eventReal.getId()).getRoadType().toString()
						: "");
				event.setHurtNumber(mmap.get(eventReal.getId()).getHurtNumber() != null ? mmap
						.get(eventReal.getId()).getHurtNumber().toString()
						: "");
				event.setDelayHumanNumber(mmap.get(eventReal.getId())
						.getDelayHumanNumber() != null ? mmap
						.get(eventReal.getId()).getDelayHumanNumber()
						.toString() : "");
				event.setDeathNumber(mmap.get(eventReal.getId())
						.getDeathNumber() != null ? mmap.get(eventReal.getId())
						.getDeathNumber().toString() : "");
				event.setDelayCarNumber(mmap.get(eventReal.getId())
						.getDelayCarNumber() != null ? mmap
						.get(eventReal.getId()).getDelayCarNumber().toString()
						: "");
				event.setCrowdMeter(mmap.get(eventReal.getId()).getCrowdMeter() != null ? mmap
						.get(eventReal.getId()).getCrowdMeter().toString()
						: "");
				event.setNavigation(mmap.get(eventReal.getId()).getNavigation() != null ? mmap
						.get(eventReal.getId()).getNavigation().toString()
						: "");

				event.setLaneNumber(mmap.get(eventReal.getId()).getLaneNumber() != null ? mmap
						.get(eventReal.getId()).getLaneNumber().toString()
						: "");
				event.setEstimatesRecoverTime(mmap.get(eventReal.getId())
						.getEstimatesRecoverTime() != null ? TimeUtil
						.getStringTime(mmap.get(eventReal.getId())
								.getEstimatesRecoverTime(), 2) : "");
			} else {

				event.setDestoryCarNumber("");
				event.setRoadType("");
				event.setHurtNumber("");
				event.setDelayHumanNumber("");
				event.setDeathNumber("");
				event.setDelayCarNumber("");
				event.setCrowdMeter("");
				event.setNavigation("");

				event.setLaneNumber("");
				event.setEstimatesRecoverTime("");
			}
			if (StringUtils.isNotBlank(eventReal.getDetailType())) {
				String[] dTypes = eventReal.getDetailType().split(",");
				List<String> dtypes = Arrays.asList(dTypes);
				event.setDetailTypes(dtypes);
			}
			if (null != eventReal.getResourceFlag()) {
				List<EventResource> imageList = resourceManager
						.listEventRealResource(eventReal.getId());
				List<ResourceVO> images = new ArrayList<ResourceVO>();
				for (EventResource source : imageList) {
					ResourceVO vo = new ResourceVO();
					vo.setId(source.getResourceId());
					vo.setAddress(resourceImageManager.findAddressById(source
							.getResourceId()));
					images.add(vo);
				}
				event.setImage(images);
			}
			list.add(event);
		}
		dto.setEventReals(list);

		return dto;
	}

	@Logon
	public Object listEventOrganInfoJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long userId = reader.getUserId();
		User user = userManager.getUser(userId);
		List<VirtualOrgan> vorgans = organManager.listRoad(user
				.getUserGroupId());
		// 获取真实的路段
		List<Long> ids = new ArrayList<Long>();
		for (VirtualOrgan vorgan : vorgans) {
			if (vorgan.getDeviceId() != null) {
				ids.add(vorgan.getDeviceId());
			}
		}
		List<Organ> organs = null;
		if (ids.size() > 0) {
			organs = organManager.listOrganByIds(ids);
		}
		List<DicManagement> managements = dicManagementManager.list();
		ListEventOrganInfoDTO dto = new ListEventOrganInfoDTO();
		List<ListEventOrganInfoDTO.organVO> organList = new ArrayList<ListEventOrganInfoDTO.organVO>();
		for (Organ organ : organs) {
			ListEventOrganInfoDTO.organVO vo = dto.new organVO();
			vo.setId(organ.getId() + "");
			vo.setName(organ.getName());
			vo.setCode(organ.getRoadNumber() != null ? organ.getRoadNumber()
					: "");
			organList.add(vo);
		}
		// 机构按照名称排序
		Collections.sort(organList, new OrganComparator());

		dto.setMethod(request.getHeader(Header.METHOD));
		dto.setManagements(managements);
		dto.setOrgans(organList);
		dto.setUserName(user.getName());
		dto.setId(user.getId() + "");
		return dto;
	}

	@Logon
	public Object listHistoryAlarmJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long beginTime = reader.getLong("beginTime", true);
		Long endTime = reader.getLong("endTime", true);
		Long userId = reader.getUserId();
		Integer level = reader.getInteger("level", true);
		Long vorganId = reader.getLong("vorganId", true);
		Integer subType = reader.getInteger("subType", true);
		Integer start = reader.getInteger("start", true);
		// 默认查一个月
		if (null == beginTime) {
			beginTime = Long
					.valueOf(System.currentTimeMillis() - 30 * 24 * 3600000l);
		}
		if (null == endTime) {
			endTime = Long.valueOf(System.currentTimeMillis());
		}
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 50;
		}
		// 待查询的虚拟机构
		List<VirtualOrgan> organs = null;
		// 如果路段不为空,基于路段查询
		if (null != vorganId) {
			organs = organManager.listRealChildOrgan(vorganId);
		}
		// 基于用户组查询
		else {
			// 获取用户组
			User user = userManager.getUser(userId);
			// 获取用户组下的所有真实机构
			organs = organManager.listUgRealOrgan(user.getUserGroupId());
		}
		if (organs.size() == 0) {
			throw new BusinessException(ErrorCode.DIRTY_DATA_FOUND,
					"VirtualOrgan[" + vorganId
							+ "] has not contain any real Organ !");
		}
		// 转换后的真实机构ID列表
		List<Long> organIds = new LinkedList<Long>();
		for (VirtualOrgan vo : organs) {
			if (vo.getDeviceId() != null) {
				organIds.add(vo.getDeviceId());
			}
		}

		int count = eventManager.countAlarm(beginTime, endTime, organIds,
				level, subType);
		List<Event> events = eventManager.listAlarm(beginTime, endTime,
				organIds, level, subType, start, limit);
		// 以真实机构ID为key， 虚拟机构对象为value的映射
		Map<Long, VirtualOrgan> map = new HashMap<Long, VirtualOrgan>();
		for (VirtualOrgan vo : organs) {
			map.put(vo.getDeviceId(), vo);
		}
		// 阀值报警子类型，关联查询子表
		Map<Long, EventDevice> eventDeviceMap = null;
		List<Long> thresholdIds = new LinkedList<Long>();
		for (Event event : events) {
			if (event.getSubType().intValue() == TypeDefinition.EVENT_TYPE_VA) {
				thresholdIds.add(event.getId());
			}
		}
		if (thresholdIds.size() > 0) {
			eventDeviceMap = eventDeviceManager.mapEventDevice(thresholdIds);
		}

		// 返回
		DeviceAlarmDTO dto = new DeviceAlarmDTO();
		dto.setMessage(count + "");
		dto.setMethod(request.getHeader(Header.METHOD));
		List<DeviceAlarmDTO.Alarm> list = new LinkedList<DeviceAlarmDTO.Alarm>();
		for (Event event : events) {
			DeviceAlarmDTO.Alarm vo = dto.new Alarm();
			vo.setName(event.getName());
			vo.setLocation(event.getLocation());
			vo.setId(event.getId());
			vo.setSourceName(event.getSourceName());
			vo.setConfirmFlag(event.getConfirmFlag());
			vo.setConfirmTime(event.getConfirmTime());
			vo.setConfirmUserName(event.getConfirmUserName());
			vo.setRecoverTime(event.getRecoverTime());
			vo.setAlarmContent(event.getAlarmContent());
			vo.setConfirmNote(event.getConfirmNote());
			vo.setSubType(event.getSubType());
			vo.setCreateTime(event.getCreateTime());
			vo.setEventLevel(event.getEventLevel());
			vo.setResourceFlag(event.getResourceFlag());
			vo.setOrganName(map.get(event.getOrganId()).getName());
			if (event.getSubType().intValue() == TypeDefinition.EVENT_TYPE_VA) {
				EventDevice eventDevice = eventDeviceMap.get(event.getId());
				if (null == eventDevice) {
					throw new BusinessException(ErrorCode.DIRTY_DATA_FOUND,
							"Child record not found for Event["
									+ event.getId().longValue() + "] !");
				}
				vo.setThreshold(eventDevice.getThreshold());
				vo.setCurrentValue(eventDevice.getCurrentValue());
			}
			list.add(vo);
		}
		dto.setAlarmResponse(list);
		return dto;
	}

	public Object deviceAlarmRealConfirmJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long id = reader.getLong("id", false);
		String confirmUserName = reader.getString("confirmUserName", false);
		String confirmNote = reader.getString("confirmNote", true);

		EventReal eventReal = eventRealManager.getEventRealDetail(id);
		if (null == eventReal) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
					"not found entity by [" + id + "]");
		}

		eventReal.setConfirmTime(System.currentTimeMillis());
		eventReal.setConfirmFlag(TypeDefinition.CONFIRM_FLAG_YES);
		eventReal.setConfirmNote(confirmNote);
		eventReal.setConfirmUserName(confirmUserName);

		eventRealManager.updateEventReal(eventReal);
		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;

	}
}
