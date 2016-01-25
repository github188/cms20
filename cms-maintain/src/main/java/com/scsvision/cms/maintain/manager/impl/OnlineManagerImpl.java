package com.scsvision.cms.maintain.manager.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scsvision.cms.annotation.Cache;
import com.scsvision.cms.constant.TypeDefinition;
import com.scsvision.cms.interceptor.CacheInterceptor;
import com.scsvision.cms.maintain.dao.OnlineDAO;
import com.scsvision.cms.maintain.dao.OnlineRealDAO;
import com.scsvision.cms.maintain.manager.OnlineManager;
import com.scsvision.database.dao.StandardNumberDAO;
import com.scsvision.database.dao.SvDeviceDAO;
import com.scsvision.database.dao.TmDeviceDAO;
import com.scsvision.database.entity.Online;
import com.scsvision.database.entity.OnlineReal;
import com.scsvision.database.entity.StandardNumber;
import com.scsvision.database.entity.SvDevice;
import com.scsvision.database.entity.TmDevice;
import com.scsvision.database.entity.User;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Interceptors(CacheInterceptor.class)
public class OnlineManagerImpl implements OnlineManager {

	private final Logger logger = LoggerFactory
			.getLogger(OnlineManagerImpl.class);

	@EJB(beanName = "OnlineRealDAOImpl")
	private OnlineRealDAO onlineRealDAO;
	@EJB(beanName = "OnlineDAOImpl")
	private OnlineDAO onlineDAO;
	@EJB(beanName = "StandardNumberDAOImpl")
	private StandardNumberDAO snDAO;
	@EJB(beanName = "SvDeviceDAOImpl")
	private SvDeviceDAO svDeviceDAO;
	@EJB(beanName = "TmDeviceDAOImpl")
	private TmDeviceDAO tmDeviceDAO;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public OnlineReal csLogin(User user, String clientType, String ip) {
		String ticket = user.getViedoPriv().toString()
				+ user.getRoadPriv().toString()
				+ user.getTunnelPriv().toString()
				+ user.getWorkPriv().toString()
				+ user.getAlarmPriv().toString()
				+ user.getEventPriv().toString()
				+ user.getInfoPriv().toString()
				+ user.getStatisticPriv().toString()
				+ user.getSystemPriv().toString();
		OnlineReal onlineReal = new OnlineReal();
		onlineReal.setResourceId(user.getId());
		onlineReal.setStandardNumber(user.getStandardNumber());
		onlineReal.setIp(ip);
		onlineReal.setOnlineTime(System.currentTimeMillis());
		onlineReal.setUpdateTime(System.currentTimeMillis());
		onlineReal.setClientType(clientType);
		onlineReal.setName(user.getLogonName());
		onlineReal.setType(TypeDefinition.RESOURCE_USER);
		onlineReal = onlineRealDAO.insert(onlineReal);

		ticket = onlineReal.getId() + "-" + ticket;
		onlineReal.setTicket(ticket);
		onlineRealDAO.update(onlineReal.getId(), onlineReal);

		return onlineReal;
	}

	@Override
	public Map<String, OnlineReal> mapOnlineDevice() {
		List<OnlineReal> list = onlineRealDAO.listOnlineDevice();
		Map<String, OnlineReal> map = new HashMap<String, OnlineReal>();
		for (OnlineReal online : list) {
			map.put(online.getStandardNumber(), online);
		}
		return map;
	}

	@Override
	public List<OnlineReal> listOnlineRealInTypes(List<Integer> types) {
		return onlineRealDAO.listOnlineReal(types, null, null, null);
	}

	@Override
	public void offline(OnlineReal onlineReal, Integer kickFlag) {
		// 历史表增加记录
		long offlineTime = System.currentTimeMillis();
		Online online = new Online();
		online.setClientType(onlineReal.getClientType());
		online.setDuration(offlineTime - onlineReal.getOnlineTime().longValue());
		online.setIp(onlineReal.getIp());
		online.setKickFlag(kickFlag);
		online.setName(onlineReal.getName());
		online.setOfflineTime(offlineTime);
		online.setOnlineTime(onlineReal.getOnlineTime());
		online.setResourceId(onlineReal.getResourceId());
		online.setStandardNumber(onlineReal.getStandardNumber());
		online.setType(online.getType());
		online.setTicket(onlineReal.getTicket());
		onlineDAO.insert(online);
		// 删除在线表
		onlineRealDAO.delete(onlineReal.getId());
	}

	@Override
	public OnlineReal getOnlineReal(String ticket) {
		return onlineRealDAO.getOnlineReal(ticket);
	}

	@Override
	public Integer countOnlineRealList(List<Integer> types) {
		return onlineRealDAO.countOnlineRealList(types);
	}

	@Override
	public List<OnlineReal> listOnlineRealByType(List<Integer> types,
			int start, int limit) {
		return onlineRealDAO.listOnlineReal(types, null, start, limit);
	}

	@Override
	public OnlineReal getOnlineRealById(String id) {
		return onlineRealDAO.getOnlineRealById(id);
	}

	@Override
	public void reportOffline(List<String> sns) {
		List<OnlineReal> list = onlineRealDAO.listOnlineReal(null, sns, null,
				null);
		// 待插入的历史会话列表
		List<Online> insertList = new LinkedList<Online>();
		long now = System.currentTimeMillis();

		for (OnlineReal onlineReal : list) {
			Online online = new Online();
			online.setClientType(onlineReal.getClientType());
			online.setDuration(now - onlineReal.getOnlineTime().longValue());
			online.setIp(onlineReal.getIp());
			online.setName(onlineReal.getName());
			online.setOfflineTime(now);
			online.setOnlineTime(onlineReal.getOnlineTime());
			online.setResourceId(onlineReal.getResourceId());
			online.setStandardNumber(onlineReal.getStandardNumber());
			online.setType(onlineReal.getType());
			online.setTicket(onlineReal.getTicket());
			insertList.add(online);

			onlineRealDAO.delete(onlineReal.getId());
		}

		if (insertList.size() > 0) {
			onlineDAO.batchInsert(insertList);
		}
	}

	@Override
	public void reportOnline(List<String> sns, Map<String, Long> map) {
		List<OnlineReal> list = onlineRealDAO.listOnlineReal(null, sns, null,
				null);
		long now = System.currentTimeMillis();

		for (OnlineReal onlineReal : list) {
			onlineReal.setUpdateTime(now);
			onlineRealDAO.update(onlineReal.getId(), onlineReal);

			// 移除在线表能查找到的SN
			sns.remove(onlineReal.getStandardNumber());
		}

		// 新上线的会话列表
		List<OnlineReal> insertList = new LinkedList<OnlineReal>();
		for (String sn : sns) {
			StandardNumber standardNumber = snDAO.findBySn(sn);
			if (null == sn) {
				logger.error("StandardNumber[" + standardNumber
						+ "] not found in commmon_standard_number !");
				continue;
			}
			Long resourceId = map.get(sn);
			if (null == resourceId) {
				logger.error("StandardNumber["
						+ standardNumber
						+ "] not found in Device cache, if is new added device please flush_all the memcached !");
				continue;
			}

			OnlineReal entity = new OnlineReal();
			entity.setName(standardNumber.getName());
			entity.setType(Integer.parseInt(standardNumber.getType()));
			entity.setResourceId(resourceId);
			entity.setOnlineTime(now);
			entity.setStandardNumber(sn);
			entity.setUpdateTime(now);
			insertList.add(entity);
		}
		if (insertList.size() > 0) {
			onlineRealDAO.batchInsert(insertList);
		}
	}

	@Override
	public List<OnlineReal> list(int start, int limit) {
		return onlineRealDAO.list(start, limit);
	}

	@Override
	@Cache
	public Map<String, String> mapAllDevice() {
		Map<String, String> map = new HashMap<String, String>();
		// 所有视频设备
		List<SvDevice> svList = svDeviceDAO.list();
		for (SvDevice sv : svList) {
			map.put(sv.getId().toString() + "-" + sv.getType()
					+ sv.getSubType(), sv.getStandardNumber());
		}
		// 所有数据设备
		List<TmDevice> tmList = tmDeviceDAO.list();
		for (TmDevice tm : tmList) {
			map.put(tm.getId().toString() + "-" + tm.getType()
					+ tm.getSubType(), tm.getStandardNumber());
		}
		return map;
	}

	@Override
	public Map<Long, OnlineReal> mapOnlineCamera() {
		List<OnlineReal> list = onlineRealDAO.mapOnlineCamera();
		Map<Long, OnlineReal> map = new HashMap<Long, OnlineReal>();
		for (OnlineReal online : list) {
			map.put(online.getResourceId(), online);
		}
		return map;
	}

	@Override
	public Map<Long, OnlineReal> mapOnlineAllDevice() {
		List<OnlineReal> list = onlineRealDAO.listOnlineDevice();
		Map<Long, OnlineReal> map = new HashMap<Long, OnlineReal>();
		for (OnlineReal online : list) {
			map.put(online.getResourceId(), online);
		}
		return map;
	}

	@Override
	public OnlineReal getOnlineRealByDeviceId(Long deviceId) {
		return onlineRealDAO.getOnlineRealByDeviceId(deviceId);
	}

	@Override
	public Online getOnlineByDeviceId(Long deviceId) {
		return onlineDAO.getOnlineByDeviceId(deviceId);
	}
}
