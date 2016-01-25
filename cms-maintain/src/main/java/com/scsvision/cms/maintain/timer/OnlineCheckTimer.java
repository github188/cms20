package com.scsvision.cms.maintain.timer;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

import org.apache.commons.lang3.StringUtils;

import com.scsvision.center.jms.util.amq.AmqUtil;
import com.scsvision.cms.constant.TypeDefinition;
import com.scsvision.cms.maintain.manager.OnlineManager;
import com.scsvision.cms.util.cache.SpyMemcacheUtil;
import com.scsvision.cms.util.file.Configuration;
import com.scsvision.database.entity.OnlineReal;

/**
 * OnlineCheckTimer
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午4:41:26
 */
@Singleton
public class OnlineCheckTimer {

	@EJB(beanName = "OnlineManagerImpl")
	private OnlineManager onlineManager;
	@EJB(beanName = "AmqUtil")
	private AmqUtil amqUtil;

	/**
	 * 心跳过期时间3分钟
	 */
	public static final long ONLINE_EXPIRE_TIME = 180000l;
	/**
	 * 设备掉线过期时间10分钟
	 */
	public static final long DEVICE_ONLINE_EXPIRE_TIME = 600000l;
	/**
	 * 集群环境下,心跳检查锁
	 */
	public static final String ONLINE_CHECK_LOCK = "online-check-lock";

	public long LockTime = 0l;

	@Schedule(second = "*", minute = "*/2", hour = "*", dayOfMonth = "*", month = "*", year = "*", persistent = false)
	public void checkOnline() {
		// 检查资源在线任务
		try {
			Long lock = (Long) SpyMemcacheUtil.get(ONLINE_CHECK_LOCK);
			// 获取到检查锁
			if (null == lock || lock.longValue() == LockTime) {
				LockTime = System.currentTimeMillis();
				SpyMemcacheUtil.put(ONLINE_CHECK_LOCK, Long.valueOf(LockTime),
						180);

				List<OnlineReal> list = onlineManager.list(0, 99999);
				long current = System.currentTimeMillis();
				// 除设备外的其他资源过期
				long expire = current - ONLINE_EXPIRE_TIME;
				// 设备过期
				long deviceExpire = current - DEVICE_ONLINE_EXPIRE_TIME;
				// 过期的设备SN列表
				List<String> expireSns = new LinkedList<String>();
				for (OnlineReal online : list) {
					// 设备类
					if (StringUtils.isBlank(online.getClientType())) {
						if (deviceExpire > online.getUpdateTime().longValue()) {
							expireSns.add(online.getStandardNumber());
						}
					}
					// 资源类
					else {
						// 离线
						if (expire > online.getUpdateTime().longValue()) {
							amqUtil.sendToTopic(
									buildOffline(online.getTicket()),
									"TopicEvent",
									"Offline",
									UUID.randomUUID().toString(),
									"",
									Configuration.getInstance().getProperties(
											"standardNumber"));
						}
					}
				}
				// 设备下线
				if (expireSns.size() > 0) {
					amqUtil.sendToQueue(
							buildUpdateStatus(expireSns),
							"QueueCMS",
							"UpdateStatus",
							UUID.randomUUID().toString(),
							"",
							Configuration.getInstance().getProperties(
									"standardNumber"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 资源离线xml
	private String buildOffline(String ticket) {
		StringBuffer sb = new StringBuffer();
		sb.append("<Request Method=\"Offline\">");
		sb.append(System.lineSeparator());
		sb.append("  <Ticket>");
		sb.append(ticket);
		sb.append("</Ticket>");
		sb.append(System.lineSeparator());
		sb.append("  <KickFlag>");
		sb.append(TypeDefinition.OFFLINE_EXPIRE);
		sb.append("</KickFlag>");
		sb.append(System.lineSeparator());
		sb.append("</Request>");
		return sb.toString();
	}

	// 设备更新状态xml
	private String buildUpdateStatus(List<String> sns) {
		StringBuffer sb = new StringBuffer();
		sb.append("<Request Method=\"UpdateStatus\">");
		for (String sn : sns) {
			sb.append(System.lineSeparator());
			sb.append("  <Resource SN=\"");
			sb.append(sn);
			sb.append("\" Status=\"0\" />");
		}
		sb.append(System.lineSeparator());
		sb.append("</Request>");
		return sb.toString();
	}
}
