package com.scsvision.database.manager.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;

import com.scsvision.cms.interceptor.CacheInterceptor;
import com.scsvision.database.dao.ServerDAO;
import com.scsvision.database.dao.StandardNumberDAO;
import com.scsvision.database.entity.Server;
import com.scsvision.database.manager.ServerManager;

/**
 * @author sjt
 *
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Interceptors(CacheInterceptor.class)
public class ServerManagerImpl implements ServerManager {

	@EJB(beanName = "ServerDAOImpl")
	private ServerDAO serverDAO;
	@EJB(beanName = "StandardNumberDAOImpl")
	private StandardNumberDAO snDAO;

	@Override
	public void serverConfig(Server entity) {
		serverDAO.update(entity);
	}

	@Override
	public Server getServerBySN(String sn) {
		return serverDAO.getServerBySN(sn);
	}

	@Override
	public List<Server> getServerByType(Integer type) {
		return serverDAO.getServerByType(type);
	}

	@Override
	public Long createServer(Server entity) {
		serverDAO.save(entity);
		snDAO.sync(entity.getStandardNumber(), entity.getName(), Server.class,
				entity.getType().toString(), false);
		return entity.getId();
	}

	@Override
	public Server getServerById(Long id) {
		return serverDAO.get(id);
	}

	@Override
	public void updateServer(Server entity) {
		serverDAO.update(entity);
		snDAO.sync(entity.getStandardNumber(), entity.getName(), Server.class,
				entity.getType().toString(), false);
	}

	@Override
	public void deleteServer(Long id) {
		Server entity = serverDAO.get(id);
		serverDAO.delete(entity);
		snDAO.sync(entity.getStandardNumber(), entity.getName(), Server.class,
				entity.getType().toString(), true);
	}

	@Override
	public List<Server> listServer(String name, Integer type, int limit,
			int start) {
		return serverDAO.listServer(name, type, limit, start);
	}

	@Override
	public Integer countServers(String name, Integer type) {
		return serverDAO.countServers(name, type);
	}

}
