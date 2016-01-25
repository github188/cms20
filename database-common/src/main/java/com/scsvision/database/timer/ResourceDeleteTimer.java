package com.scsvision.database.timer;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scsvision.cms.constant.TypeDefinition;
import com.scsvision.database.entity.ResourceImage;
import com.scsvision.database.entity.Server;
import com.scsvision.database.manager.ResourceManager;
import com.scsvision.database.manager.ServerManager;

/**
 * 没有关联的资源定时清理任务
 * 
 * @author MIKE
 *         <p />
 *         Create at 2016年1月19日 下午3:19:41
 */
@Singleton
public class ResourceDeleteTimer {

	private final Logger logger = LoggerFactory
			.getLogger(ResourceDeleteTimer.class);

	@EJB(beanName = "ResourceManagerImpl")
	private ResourceManager resourceManager;
	@EJB(beanName = "ServerManagerImpl")
	private ServerManager serverManager;

	@Schedule(second = "10", minute = "10", hour = "3", dayOfMonth = "*", month = "*", year = "*", persistent = false)
	public void delete() {
		try {
			// 获取事件关联表和预置点表中所有的未被关联的图片资源
			List<ResourceImage> toRemoveImages = resourceManager
					.listNoReferencedImages();

			// 删除资源服务器上的资源
			// 获取资源服务器信息
			List<Server> resList = serverManager
					.getServerByType(TypeDefinition.RESOURCE_RES);
			if (resList.size() == 0) {
				logger.error("RES not found, please create 1 first !");
				return;
			}

			for (ResourceImage image : toRemoveImages) {
				// 删除数据库
				String url = resourceManager.deleteResource(image.getId());
				// 删除资源服务器上的资源
				Server res = resList.get(0);
				StringBuilder resAddress = new StringBuilder();
				resAddress.append("http://");
				resAddress.append(res.getIp1());
				resAddress.append(":");
				resAddress.append(res.getPort().toString());
				resAddress.append(url);
				resourceManager.deleteFile(resAddress.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
