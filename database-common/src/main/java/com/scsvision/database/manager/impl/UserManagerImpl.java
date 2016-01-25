package com.scsvision.database.manager.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;

import com.scsvision.cms.annotation.Cache;
import com.scsvision.cms.constant.TypeDefinition;
import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.interceptor.CacheInterceptor;
import com.scsvision.database.dao.StandardNumberDAO;
import com.scsvision.database.dao.UserDAO;
import com.scsvision.database.entity.User;
import com.scsvision.database.manager.UserManager;

/**
 * UserManagerImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015年8月6日 下午5:41:37
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Interceptors(CacheInterceptor.class)
public class UserManagerImpl implements UserManager {
	@EJB(beanName = "UserDAOImpl")
	private UserDAO userDAO;
	@EJB(beanName = "StandardNumberDAOImpl")
	private StandardNumberDAO snDAO;

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Cache(expire = 120)
	public List<User> listUser(String logonName, int start, int limit) {
		return userDAO.list(logonName, start, limit);
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Cache(expire = 120)
	public Integer countUser(String logonName, int start, int limit) {
		return userDAO.count(logonName, start, limit);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public User createUser(User user) {
		userDAO.save(user);
		snDAO.sync(user.getStandardNumber(), user.getName(), User.class,
				TypeDefinition.RESOURCE_USER + "", false);
		return user;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateUser(User user) throws BusinessException {
		userDAO.update(user);
		snDAO.sync(user.getStandardNumber(), user.getName(), User.class,
				TypeDefinition.RESOURCE_USER + "", false);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteUser(Long id) {
		User user = userDAO.get(id);
		userDAO.delete(user);
		snDAO.sync(user.getStandardNumber(), user.getName(), User.class,
				TypeDefinition.RESOURCE_USER + "", true);
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Cache
	public User getUser(Long id) {
		return userDAO.get(id);
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<User> listUser() {
		return userDAO.list();
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<User> getUserByName(String name) {
		return userDAO.getUserByName(name);
	}

}
