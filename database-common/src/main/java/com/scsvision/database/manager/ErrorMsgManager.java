package com.scsvision.database.manager;

import java.util.Map;

import javax.ejb.Local;

@Local
public interface ErrorMsgManager {
	/**
	 * 
	 * @return 返回错误码
	 */
	public Map<String, String> getErrorMsgMap();
}
