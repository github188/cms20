/**
 * 
 */
package com.scsvision.cms.util.cache;

import java.util.Date;

import com.meetup.memcached.MemcachedClient;
import com.meetup.memcached.SockIOPool;

/**
 * @author MIKE
 *
 */
public class MemcacheUtil {
	private static MemcachedClient client = null;
	private static SockIOPool pool = null;
	public static final String POOL_NAME = "com.meetup.memcached.SockIOPool";

	synchronized public static void init() {
		if (null == client) {
			pool = SockIOPool.getInstance(POOL_NAME);
			pool.setServers(new String[] { "192.168.1.2:11211" });
			pool.setFailover(false);
			pool.setFailback(true);
			pool.setInitConn(4);
			pool.setMinConn(4);
			pool.setMaxConn(4);
			pool.setMaintSleep(30000);
			pool.setNagle(false);
			pool.setSocketTO(30000);
			pool.setAliveCheck(false);
			pool.initialize();

			client = new MemcachedClient(POOL_NAME);
			client.setCompressEnable(true);
			client.setCompressThreshold(30720);
		}
	}

	public static void put(String key, Object value, int expire) {
		if (null == client) {
			init();
		}
		client.set(key, value, new Date(expire * 1000));
	}

	public static Object get(String key) {
		if (null == client) {
			init();
		}
		return client.get(key);
	}
}
