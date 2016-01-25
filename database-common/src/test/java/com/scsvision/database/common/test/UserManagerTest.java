package com.scsvision.database.common.test;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.apache.openejb.api.LocalClient;

import com.scsvision.database.entity.User;
import com.scsvision.database.manager.UserManager;

/**
 * @author MIKE
 *
 */
@LocalClient
@Stateless
public class UserManagerTest extends BaseTest {
	@EJB(beanName = "UserManagerImpl")
	private UserManager userManager;

	public void testListUser() {
		List<User> users = userManager.listUser("黄不", 1, 10);
		// List<User> users = userManager.listUser();
		for (User u : users) {
			System.out.println(u.getId());
		}

	}

	// public void testUpdateUser() {
	// User user = userManager.getUser(Long.valueOf(10002));
	// try {
	// user.setName("HBJ");
	// userManager.updateUser(user);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	public void testSaveUser() {
		User user = new User();
		user.setEventPriv(Short.valueOf("1"));
		user.setLogonName("hbj");
		user.setName("test");
		user.setPassword("test");
		user.setRoadPriv(Short.valueOf("1"));
		user.setStandardNumber("23455500000002221");
		user.setStatisticPriv(Short.valueOf("1"));
		user.setTunnelPriv(Short.valueOf("1"));
		user.setUserGroupId(1l);
		userManager.createUser(user);
		System.out.println(user.getId());
	}
}
