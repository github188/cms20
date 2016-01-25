/**
 * 
 */
package com.scsvision.database.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangbinyu
 *
 */
public class CacheData {
	private static CacheData instance = new CacheData();
	// 方法名称为key 操作名称为value
	public Map<String, String> dicMethod = new ConcurrentHashMap<String, String>();
	// 设备sn为key 设备真实id为value
	public Map<String, Long> deviceIdMap = new ConcurrentHashMap<String, Long>();

	private CacheData() {

	}

	public static CacheData getInstance() {
		return instance;
	}
}
