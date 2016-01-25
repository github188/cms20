package com.scsvision.database.manager.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;

import com.scsvision.cms.interceptor.CacheInterceptor;
import com.scsvision.database.dao.UserGroupDAO;
import com.scsvision.database.entity.UserGroup;
import com.scsvision.database.manager.UserGroupManager;

/**
 * UserGroupManagerImpl
 * 
 * @author sjt
 *         <p />
 *         Create at 2015年 上午11:08:37
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Interceptors(CacheInterceptor.class)
public class UserGroupManagerImpl implements UserGroupManager {

	@EJB(beanName = "UserGroupDAOImpl")
	private UserGroupDAO userGroupDAO;

	@Override
	public Long createUserGroup(UserGroup userGroup) {
		userGroupDAO.save(userGroup);
		return userGroup.getId();
	}

	@Override
	public UserGroup getUserGroup(Long id) {
		return userGroupDAO.get(id);
	}

	@Override
	public void updateUserGroup(UserGroup userGroup) {
		userGroupDAO.update(userGroup);
	}

	@Override
	public void deleteUserGroup(Long id) {
		UserGroup entity = userGroupDAO.get(id);
		userGroupDAO.delete(entity);
	}

	@Override
	public List<UserGroup> listUserGroup(String name, int start, int limit) {
		return userGroupDAO.listUserGroup(name, start, limit);
	}

	@Override
	public Integer countUserGroup(String name) {
		return userGroupDAO.countUserGroup(name);
	}

	@Override
	public Map<Long, UserGroup> getMapUserGroup() {
		List<UserGroup> userGroups = userGroupDAO.list();
		Map<Long, UserGroup> map = new HashMap<Long, UserGroup>();
		for (Iterator iterator = userGroups.iterator(); iterator.hasNext();) {
			UserGroup userGroup = (UserGroup) iterator.next();
			map.put(userGroup.getId(), userGroup);
		}
		return map;
	}

}
