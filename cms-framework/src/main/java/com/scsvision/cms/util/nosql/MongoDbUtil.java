package com.scsvision.cms.util.nosql;

import java.util.LinkedList;
import java.util.List;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.cms.util.file.Configuration;

/**
 * MongoDbUtil
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午5:09:09
 */
public class MongoDbUtil {

	private static MongoClient client = null;

	synchronized private static void init() {
		if (null == client) {
			String servers = Configuration.getInstance().loadProperties(
					"mongodb.servers");
			if (null == servers || servers.isEmpty()) {
				throw new BusinessException(ErrorCode.CONFIG_MISSING,
						"[mongodb.servers] is not configured by config.properties !");
			}

			String[] addresses = servers.split(",");
			// 单机
			if (addresses.length == 1) {
				String[] arg = addresses[0].split(":");
				if (arg.length != 2) {
					throw new BusinessException(ErrorCode.PARAMETER_INVALID,
							"[mongodb.servers] format must be like: ip1:por1,ip2:port2");
				}
				ServerAddress serverAddress = new ServerAddress(arg[0],
						Integer.valueOf(arg[1]));
				client = new MongoClient(serverAddress);
			}
			// 集群
			else {
				List<ServerAddress> seeds = new LinkedList<ServerAddress>();
				for (String address : addresses) {
					String[] arg = address.split(":");
					if (arg.length != 2) {
						throw new BusinessException(
								ErrorCode.PARAMETER_INVALID,
								"[mongodb.servers] format must be like: ip1:por1,ip2:port2");
					}
					ServerAddress serverAddress = new ServerAddress(arg[0],
							Integer.valueOf(arg[1]));
					seeds.add(serverAddress);
				}

				client = new MongoClient(seeds);
			}
		}
	}

	/**
	 * 获取mongodb数据库连接
	 * 
	 * @param name 数据库名称
	 * @return
	 * @author MIKE
	 * <p />
	 * Create at 2015 上午10:31:23
	 */
	public static MongoDatabase getDatabase(String name) {
		if (null == client) {
			init();
		}
		return client.getDatabase(name);
	}

}
