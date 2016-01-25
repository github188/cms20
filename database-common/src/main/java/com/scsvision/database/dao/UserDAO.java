package com.scsvision.database.dao;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.database.entity.User;

/**
 * UserDAO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015年8月6日 下午5:26:24
 */
@Local
public interface UserDAO extends BaseDAO<User, Long> {
	public List<User> list(String logonName, int start, int limit);
	
	public Integer count(String logonName, int start, int limit);

	public List<User> getUserByName(String name);
}
