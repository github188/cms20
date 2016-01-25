/**
 * 
 */
package com.scsvision.cms.maintain.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.cms.interceptor.CacheInterceptor;
import com.scsvision.cms.maintain.dao.OnlineDAO;
import com.scsvision.cms.maintain.dao.OnlineRealDAO;
import com.scsvision.cms.maintain.dao.TmCmsPublishLogDAO;
import com.scsvision.cms.maintain.dao.UserLogDAO;
import com.scsvision.cms.maintain.manager.LogManager;
import com.scsvision.cms.util.cache.SpyMemcacheUtil;
import com.scsvision.database.dao.DicMethodDAO;
import com.scsvision.database.dao.TmDeviceDAO;
import com.scsvision.database.dao.UserDAO;
import com.scsvision.database.data.CacheData;
import com.scsvision.database.entity.DicMethod;
import com.scsvision.database.entity.Online;
import com.scsvision.database.entity.OnlineReal;
import com.scsvision.database.entity.TmCmsPublishLog;
import com.scsvision.database.entity.TmDevice;
import com.scsvision.database.entity.User;
import com.scsvision.database.entity.UserLog;

/**
 * @author wangbinyu
 *
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Interceptors(CacheInterceptor.class)
public class LogManagerImpl implements LogManager {
	private final Logger logger = LoggerFactory.getLogger(LogManagerImpl.class);

	@EJB(beanName = "OnlineRealDAOImpl")
	private OnlineRealDAO onlineRealDAO;

	@EJB(beanName = "OnlineDAOImpl")
	private OnlineDAO onlineDAO;

	@EJB(beanName = "UserDAOImpl")
	private UserDAO userDAO;

	@EJB(beanName = "UserLogDAOImpl")
	private UserLogDAO userLogDAO;

	@EJB(beanName = "DicMethodDAOImpl")
	private DicMethodDAO dicMethodDAO;

	@EJB(beanName = "TmDeviceDAOImpl")
	private TmDeviceDAO tmDeviceDAO;

	@EJB(beanName = "TmCmsPublishLogDAOImpl")
	private TmCmsPublishLogDAO tmCmsPublishLogDAO;

	@Override
	public String saveUserLog(String ticketId, String method, String code,
			String message, String targetId, String targetName,
			String targetType, String businessId, String resourceId,
			String conditions, String reason, String content, String flag)
			throws DocumentException {
		// onlineReal和user暂时没有做缓存
		OnlineReal onlineReal = onlineRealDAO.getOnlineReal(ticketId);
		Online online = null;
		if (null == onlineReal) {
			online = onlineDAO.getOnline(ticketId);
			if (online == null) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"ticketId:" + ticketId + " is offline");
			}
		}
		User user = userDAO.get(onlineReal != null ? onlineReal.getResourceId()
				: online.getResourceId());

		// 从缓存读取操作名称 如果缓存为null则查询数据
		String operationName = CacheData.getInstance().dicMethod.get(method);
		if (StringUtils.isBlank(operationName)) {
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			params.put("method", method);
			List<DicMethod> list = dicMethodDAO.findByPropertys(params);
			if (list.size() > 0) {
				operationName = list.get(0).getOperationName();
				CacheData.getInstance().dicMethod.put(method, operationName);
			} else {
				throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
						"DicMethod [" + method + "] not found !");
			}
		}
		// 更新旧日志flag标志
		// userLogDAO.updateOldRerecord(Long.parseLong(targetId), method);

		UserLog userLog = new UserLog();
		userLog.setBusinessId(businessId);
		userLog.setClientType(onlineReal != null ? onlineReal.getClientType()
				: online.getClientType());
		userLog.setCode(Long.parseLong(code));
		userLog.setConditions(conditions);
		userLog.setCreateTime(new Date());
		userLog.setFlag(StringUtils.isNotBlank(flag) ? Short.parseShort(flag)
				: null);
		userLog.setMethod(method);
		userLog.setOperationName(operationName);
		userLog.setReason(reason);
		userLog.setResourceId(StringUtils.isNoneBlank(resourceId) ? Long
				.parseLong(resourceId) : null);
		Long deviceId = null;
		if (method.equals("PublishCms")) {
			// 从缓存里边取数据
			deviceId = CacheData.getInstance().deviceIdMap.get(targetId);
			// 没有就查询一次数据库然后放缓存
			if (deviceId == null) {
				LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
				params.put("standardNumber", targetId);
				List<TmDevice> devices = tmDeviceDAO.findByPropertys(params);
				if (devices.size() > 0) {
					deviceId = devices.get(0).getId();
					userLog.setTargetId(deviceId);
					// 存入缓存
					CacheData.getInstance().deviceIdMap.put(targetId, deviceId);
				} else {
					logger.info("targetId is not find deviceId:" + targetId);
				}
			}

			if (StringUtils.isNotBlank(content)) {
				Document document = DocumentHelper.parseText(content);
				Element root = document.getRootElement();
				Element device = root.element("Device");
				List<Element> regions = device.elements();
				List<TmCmsPublishLog> cmsLogs = new ArrayList<TmCmsPublishLog>();
				for (Element region : regions) {
					List<Element> list = region.elements();
					for (Element item : list) {
						// 保存情报板发布日志
						TmCmsPublishLog cmsLog = new TmCmsPublishLog();
						cmsLog.setBusinessId(businessId);
						cmsLog.setCmsId(deviceId);
						cmsLog.setContent(item.asXML());
						if (StringUtils.isNotBlank(flag)) {
							cmsLog.setFlag(Short.parseShort(flag));
						} else {
							logger.info("save cms log flag is not null !!!!");
						}
						cmsLog.setSendTime(System.currentTimeMillis());
						cmsLog.setUserId(user.getId());
						cmsLog.setUserName(user.getName());
						cmsLog.setCmsName(targetName);
						cmsLogs.add(cmsLog);
					}
				}
				if (cmsLogs.size() > 0) {
					tmCmsPublishLogDAO.batchInsert(cmsLogs);
				}
			}
		} else {
			userLog.setTargetId(Long.parseLong(targetId));
		}

		userLog.setTargetName(targetName);
		userLog.setTargetType(Long.parseLong(targetType));
		userLog.setUserName(onlineReal != null ? onlineReal.getName() : online
				.getName());
		userLog.setUserId(onlineReal != null ? onlineReal.getResourceId()
				: online.getResourceId());
		userLog.setUserGroupId(user.getUserGroupId());
		userLog.setContent(content);
		userLog = userLogDAO.insert(userLog);

		return userLog.getId();
	}

	@Override
	public void updateUserLog(String businessId, Short flag) {
		// 更新用户日志情报板发布标志
		userLogDAO.updateUserLog(businessId, flag);
		// 更新情报板日志发布标志
		tmCmsPublishLogDAO.updateCmsPublishLog(businessId, flag);

	}

	@Override
	public List<TmCmsPublishLog> getCmsLog(Long deviceId) {
		List<TmCmsPublishLog> logs = new ArrayList<TmCmsPublishLog>();
		TmCmsPublishLog log = tmCmsPublishLogDAO.getCmsLog(deviceId);
		if (log != null) {
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			params.put("sendTime", log.getSendTime());
			logs = tmCmsPublishLogDAO.findByPropertys(params);
		}
		return logs;
	}

	@Override
	public List<TmCmsPublishLog> listCmsLog(Long beginTime, Long endTime,
			String cmsName, Integer start, Integer limit) {
		return tmCmsPublishLogDAO.listCmsLog(beginTime, endTime, cmsName,
				start, limit);
	}

	@Override
	public List<TmCmsPublishLog> listCmsInfo(List<String> businessIds) {
		List<TmCmsPublishLog> logs = new ArrayList<TmCmsPublishLog>();
		for (String businessId : businessIds) {
			// 从缓存取数据
			if (null != (TmCmsPublishLog) SpyMemcacheUtil.get(businessId)) {
				logs.add((TmCmsPublishLog) SpyMemcacheUtil.get(businessId));
			}
		}
		if (logs.size() <= 0) {
			logs = tmCmsPublishLogDAO.listCmsInfo(businessIds);
			// 放入缓存 保存5秒
			for (TmCmsPublishLog entity : logs) {
				SpyMemcacheUtil.put(entity.getBusinessId(), entity, 5);
			}
		}
		return logs;
	}

	@Override
	public Integer countCmsLog(String cmsName, Long beginTime, Long endTime) {
		return tmCmsPublishLogDAO.countCmsLog(cmsName, beginTime, endTime);
	}
}
