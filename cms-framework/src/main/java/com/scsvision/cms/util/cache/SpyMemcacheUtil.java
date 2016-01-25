/**
 * 
 */
package com.scsvision.cms.util.cache;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;

import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.cms.util.file.Configuration;

/**
 * @author MIKE
 *
 */
public class SpyMemcacheUtil {

	public static int DEFAULT_TIMEOUT = 5;

	private static MemcachedClient client = null;

	synchronized public static void init() {
		if (null == client) {
			try {
				String servers = Configuration.getInstance().loadProperties(
						"memcached.servers");
				if (null == servers || servers.isEmpty()) {
					throw new BusinessException(ErrorCode.CONFIG_MISSING,
							"[memcached.servers] is not configured by config.properties !");
				}
				List<String> addresses = Arrays.asList(servers.split(","));

				client = new MemcachedClient(AddrUtil.getAddresses(addresses));
			} catch (IOException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
						"memcached server not found !");
			}
		}
	}

	public static void put(String key, Object value, int expire) {
		if (null == client) {
			init();
		}
		client.set(key, expire, value);
	}

	public static Object get(String key) {
		if (null == client) {
			init();
		}
		Object obj = null;
		Future<Object> f = client.asyncGet(key);
		try {
			obj = f.get(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
		} catch (Exception e) {
			f.cancel(false);
		}
		return obj;
	}

	public static void shutdown(long delay) {
		if (null != client) {
			client.shutdown(delay, TimeUnit.SECONDS);
		}
	}

	public static void clear() {
		if (null == client) {
			init();
		}
		client.flush();
	}

}
