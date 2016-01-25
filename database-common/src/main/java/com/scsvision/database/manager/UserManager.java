package com.scsvision.database.manager;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.cms.exception.BusinessException;
import com.scsvision.database.entity.User;

/**
 * UserManager
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015年8月6日 下午5:40:54
 */
@Local
public interface UserManager {
	public List<User> listUser(String logonName, int start, int limit);
	
	public Integer countUser(String logonName, int start, int limit);

	public User createUser(User user);

	public void updateUser(User user) throws BusinessException;

	public void deleteUser(Long id);

	public User getUser(Long id);

	public List<User> listUser();

	/**
	 * 根据用户登录名获取用户
	 * 
	 * @param name
	 *            用户登录名
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午4:17:29
	 */
	public List<User> getUserByName(String name);
}
