/**
 * 
 */
package com.scsvision.cms.util.cache;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;

/**
 * @author MIKE
 *
 */
public class XMemcacheUtil {

	private static MemcachedClient client = null;

	synchronized public static void init() {
		MemcachedClientBuilder builder = new XMemcachedClientBuilder(
				"192.168.1.2:11211");
		builder.setConnectionPoolSize(1);
		builder.getConfiguration().setSessionIdleTimeout(10000);
		try {
			client = builder.build();
			client.setOpTimeout(5000l);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void put(String key, Object value, int expire) {
		if (null == client) {
			init();
		}
		try {
			client.set(key, expire, value);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
	}

	public static Object get(String key) {
		if (null == client) {
			init();
		}
		try {
			return client.get(key);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void shutdown() {
		if (null != client) {
			try {
				client.shutdown();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
