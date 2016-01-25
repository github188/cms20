package com.scsvision.center.jms;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;

/**
 * @author MIKE
 *
 */
public class MethodMap {
	private static MethodMap instance = new MethodMap();

	private Map<String, String> map;

	// singleton
	private MethodMap() {
		init();
	}

	public static MethodMap getInstance() {
		return instance;
	}

	private void init() {
		map = new HashMap<String, String>();
		// maintain
		map.put("Offline", "cms-maintain-2.0/OnlineController#offline");
		map.put("SaveUserLog", "cms-maintain-2.0/LogController#saveUserLog#32121");
		map.put("UpdateUserLog", "cms-maintain-2.0/LogController#updateUserLog#321321");
		map.put("UpdateStatus", "cms-maintain-2.0/OnlineController#updateStatus");
		
		// common
		map.put("ServerConfig", "cms-common-2.0/ServerController#serverConfig");
		
		// sv
		map.put("ListPtsDevice", "cms-sv-2.0/SvDeviceController#listPtsDevice");
		
		// gather
		map.put("DasReport", "cms-gather-2.0/GatherController#dasReport");
		
		// tm
		map.put("ListDasDevice", "cms-tm-2.0/TmDeviceController#listDasDevice");
		
		// em
		map.put("CreateAlarm", "cms-em-2.0/EventController#createAlarm#param2");
		
		// duty
		
		// statistic
	}

	/**
	 * @param 接口方法
	 * 
	 * @return result[0]-接口处理类jndi名称, result[1]-接口处理方法
	 */
	public String[] getBean(String method) {
		String value = map.get(method);
		if (StringUtils.isBlank(value)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"Method[" + method + "] not supported !");
		}
		String[] result = value.split("#");
		result[0] = "java:global/cms/" + result[0];
		return result;
	}
}
